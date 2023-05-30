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
const jsonwebtoken_1 = __importDefault(require("jsonwebtoken"));
const Authentication_ops_1 = require("../../middleware/db_ops/Authentication_ops");
const password_compare_1 = require("../../middleware/hooks/auth_hooks/password_compare");
const config_1 = __importDefault(require("./config/config"));
const functions = {
    signup: function (req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const user = {
                    email: req.body.email,
                    password: req.body.password,
                    name: req.body.name
                };
                yield (0, Authentication_ops_1.saveUser)(user);
                res.status(200).json({ success: true, msg: 'User Registered Successfully' });
            }
            catch (error) {
                res.status(500).json({ success: false, msg: 'Unable to register user' });
            }
        });
    },
    signin: function (req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            //check if password matches
            const password = req.body.password;
            const isMatch = yield (0, password_compare_1.compare_password)(res.locals.existing_user.password, password);
            if (isMatch) {
                var token = jsonwebtoken_1.default.sign(res.locals.existing_user, config_1.default);
                res.json({ success: true, token: token });
            }
            else {
                res.status(403).send({ success: false, token: 'Authentication failed, wrong password' });
            }
        });
    }
};
exports.default = functions;
