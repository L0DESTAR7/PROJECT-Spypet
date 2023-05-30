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
exports.check_for_user = exports.saveUser = void 0;
const password_crypting_1 = __importDefault(require("../hooks/auth_hooks/password_crypting"));
function saveUser(user) {
    return __awaiter(this, void 0, void 0, function* () {
        let userCreateInput;
        userCreateInput = {
            email: user.email,
            name: user.name,
            password: user.password
        };
        const createdUser = yield password_crypting_1.default.user.create({
            data: {
                email: user.email,
                name: user.name,
                password: user.password
            },
        });
        return createdUser;
    });
}
exports.saveUser = saveUser;
function check_for_user(email) {
    return __awaiter(this, void 0, void 0, function* () {
        return password_crypting_1.default.user.findUnique({
            where: {
                email: email,
            },
        });
    });
}
exports.check_for_user = check_for_user;
