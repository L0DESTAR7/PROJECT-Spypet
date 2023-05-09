import { Socket } from "socket.io";
import DecodedUser from "../types/DecodedUser";
import verifyUserToken from "../helpers/verifyUserToken";
import { Device } from "@prisma/client";
import { verifyDeviceId } from "../helpers/verifyDeviceId";


export default async function connectionHandler(socket: Socket): Promise<string | null> {
  socket.emit("hello", "Hello from Server");
  // Retrieve device type from extra headers
  const deviceType: string = socket.handshake.headers["spypet-device-type"] as string;
  console.log(`Device type detected: ${deviceType}`);
  if (deviceType === "ANDROID") {
    // Retrieve token from auth header
    const token: string = socket.handshake.auth.token;
    // Verify token validity. If invalid decodedUser is null
    const decodedUser: DecodedUser | null = verifyUserToken(token);
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
    const device_id: string = socket.handshake.headers["spypet-device-identifier"] as string;
    // If device_id not found emit room:device-id-error and disconnect
    if (!device_id) {
      socket.emit("room:device-id-error", "Failed to join room. Identifier not provided.");
      socket.disconnect();
      return null;
    }

    // Verify device ID existence and retrieve device object
    const device: Device | null = await verifyDeviceId(device_id);
    // If device not found emit room:device-id-error and disconenct
    if (!device) {
      socket.emit("room:device-id-error", "Failed to join room. No device has such identifier.");
      socket.disconnect();
      return null;
    }

    // Retrieve device_owner_id from device object 
    const device_owner_id: string | null = device.user_id;
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
}
