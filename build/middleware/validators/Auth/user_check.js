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
Object.defineProperty(exports, "__esModule", { value: true });
exports.check_user_sign_in = exports.check_user_sign_up = void 0;
const Authentication_ops_1 = require("../../db_ops/Authentication_ops");
function check_user_sign_up(req, res, next) {
    return __awaiter(this, void 0, void 0, function* () {
        const { email, password, name } = req.body;
        if (!email || !password || !name) {
            res.status(400).json({ success: false, msg: 'Please enter all fields' });
        }
        else {
            // Check if the Email is already registered
            const existingUser = yield (0, Authentication_ops_1.check_for_user)(email);
            if (existingUser) {
                res.status(409).json({ success: false, msg: 'Email already exists, Try again using another email' });
            }
            else {
                //check if the mail box exists
                next();
            }
        }
    });
}
exports.check_user_sign_up = check_user_sign_up;
function check_user_sign_in(req, res, next) {
    return __awaiter(this, void 0, void 0, function* () {
        const { email } = req.body;
        const existing_user = yield (0, Authentication_ops_1.check_for_user)(email);
        if (!existing_user) {
            res.status(403).send({ success: false, msg: "Authentication Failed" });
        }
        else {
            res.locals.existing_user = existing_user;
            next();
        }
    });
}
exports.check_user_sign_in = check_user_sign_in;
