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
exports.validateRegisterDeviceReq = void 0;
const verifyDeviceId_1 = require("../../../helpers/verifyDeviceId");
const verifyUserToken_1 = __importDefault(require("../../../helpers/verifyUserToken"));
const validateRegisterDeviceReq = (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    let reqDevice = req.body;
    const token = req.headers['authorization'].split(' ')[1];
    // STEP1: Validate and Retrieve user object 
    const decodedUser = (0, verifyUserToken_1.default)(token);
    if (!decodedUser) {
        return res.status(401).json({ error: "Authorization error. Invalid token" });
    }
    // STEP2: Verify existence of device with id = device.id 
    const device = yield (0, verifyDeviceId_1.verifyDeviceId)(reqDevice.id);
    console.log(JSON.stringify(device));
    if (!device) {
        return res.status(400).json({ error: "Device Id was not found." });
    }
    // STEP3: Verify device doesn'nt already belong to user 
    if (device.user_id === decodedUser.id) {
        return res.status(409).json({ error: "Device already belongs to this user." });
    }
    // DONE
    res.locals.device = device;
    res.locals.owner = decodedUser.id;
    next();
});
exports.validateRegisterDeviceReq = validateRegisterDeviceReq;
