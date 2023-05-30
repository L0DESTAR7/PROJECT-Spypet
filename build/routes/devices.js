"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const verifyUserToken_1 = __importDefault(require("../helpers/verifyUserToken"));
const getDevices_1 = require("../middleware/db_ops/getDevices");
const router = express_1.default.Router();
router.get('/', (req, res) => {
    const token = req.headers['authorization'].split(' ')[1];
    const decodedUser = (0, verifyUserToken_1.default)(token);
    // STEP1: Validate and Retrieve user object 
    if (!decodedUser) {
        return res.status(401).json({ error: "Authorization error. Invalid token" });
    }
    res.locals.userID = decodedUser.id;
    console.log(`Decoded user: ${JSON.stringify(decodedUser)}`);
    (0, getDevices_1.getDevices)(req, res);
});
exports.default = router;
