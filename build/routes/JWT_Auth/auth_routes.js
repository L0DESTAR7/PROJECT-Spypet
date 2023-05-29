"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const user_check_1 = require("../../middleware/validators/Auth/user_check");
const express_1 = __importDefault(require("express"));
const auth_actions_1 = __importDefault(require("./auth_actions"));
const emailController_1 = require("../../middleware/validators/Auth/emailController");
const router = express_1.default.Router();
//Add a new user
router.post('/signup', user_check_1.check_user_sign_up, emailController_1.emailDomainChecker, auth_actions_1.default.signup);
//Authenticate User
router.post('/signin', user_check_1.check_user_sign_in, auth_actions_1.default.signin);
//Reset Password
//router.post('/reset_password')
exports.default = router;
