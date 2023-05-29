"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const updateDevice_1 = require("../middleware/db_ops/updateDevice");
const registerDeviceParser_1 = require("../middleware/parsers/registerDevice/registerDeviceParser");
const deviceValidator_1 = require("../middleware/validators/registerDevice/deviceValidator");
const router = express_1.default.Router();
router.get('/', (req, res) => {
    res.send("This is /registerDevice");
});
router.post('/', registerDeviceParser_1.parseDeviceRegReq, deviceValidator_1.validateRegisterDeviceReq, updateDevice_1.updateDevice, (req, res) => {
    res.status(200).json({ success: true, status: "Device registered!" });
});
exports.default = router;
