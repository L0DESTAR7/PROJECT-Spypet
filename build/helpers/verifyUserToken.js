"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsonwebtoken_1 = __importDefault(require("jsonwebtoken"));
function verifyUserToken(token) {
    let result = null;
    const secret = process.env.SECRET;
    if (!secret) {
        throw new Error("SECRET env variable does not exist");
    }
    jsonwebtoken_1.default.verify(token, secret, function (err, decoded) {
        if (err) {
            console.log('Authentication error: Invalid token !');
        }
        else {
            result = decoded;
        }
    });
    return result;
}
exports.default = verifyUserToken;
