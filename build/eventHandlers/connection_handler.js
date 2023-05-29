"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const verifyUserToken_1 = __importDefault(require("../helpers/verifyUserToken"));
const verifyDeviceId_1 = require("../helpers/verifyDeviceId");
function connectionHandler(socket) {
    return __awaiter(this, void 0, void 0, function* () {
        socket.emit("hello", "Hello from Server");
        // Retrieve device type from extra headers
        const deviceType = socket.handshake.headers["spypet-device-type"];
        console.log(`Device type detected: ${deviceType}`);
        if (deviceType === "ANDROID") {
            // Retrieve token from auth header
            const token = socket.handshake.auth.token;
            // Verify token validity. If invalid decodedUser is null
            const decodedUser = (0, verifyUserToken_1.default)(token);
            if (!decodedUser) {
                // Emit room:auth-error event and disconnect
                socket.emit("room:auth-error", "Authentication error: Invalid token ! Disconecting...");
                socket.disconnect();
                return null;
            }
            // Reaching this area means the token is valid and decodedUser holds user details
            console.log("Client authenticated");
            console.log(`Decoded client details: ${JSON.stringify(decodedUser)}`);
            // Join room whose name is the user's id
            const roomId = decodedUser.id;
            socket.join(roomId);
            console.log(socket.rooms);
            // Emit room:joined event to android client
            socket.emit("room:joined", "Room joined successfully !");
            // Emit room:android-joined event to everyone in the room except sender socket
            socket.to(roomId).emit("room:android-joined", {
                roomId: roomId,
                deviceType: "ANDROID"
            });
            return roomId;
        }
        else if (deviceType === "RASPBERRY") {
            // Retrieve spypet device identifier
            const device_id = socket.handshake.headers["spypet-device-identifier"];
            // If device_id not found emit room:device-id-error and disconnect
            if (!device_id) {
                socket.emit("room:device-id-error", "Failed to join room. Identifier not provided.");
                socket.disconnect();
                return null;
            }
            // Verify device ID existence and retrieve device object
            const device = yield (0, verifyDeviceId_1.verifyDeviceId)(device_id);
            // If device not found emit room:device-id-error and disconenct
            if (!device) {
                socket.emit("room:device-id-error", "Failed to join room. No device has such identifier.");
                socket.disconnect();
                return null;
            }
            // Retrieve device_owner_id from device object 
            const device_owner_id = device.user_id;
            // If device_owner_id not found emit room:device-id-error and disconnect
            if (!device_owner_id) {
                socket.emit("room:device-id-error", "Failed to join room. Unable to find owner user.");
                socket.disconnect();
                return null;
            }
            // If device_owner_id exists, join the appropriate room 
            socket.join(device_owner_id);
            console.log(`Spypet device of id = ${device_id}.\n Has joined room: ${device_owner_id}`);
            socket.emit("room:joined", "Room joined successfully !");
            socket.to(device_owner_id).emit("room:spypet-joined", {
                roomId: device_owner_id,
                deviceType: "SPYPET"
            });
            return device_owner_id;
        }
        return null;
    });
}
exports.default = connectionHandler;
