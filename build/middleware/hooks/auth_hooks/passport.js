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
const passport_jwt_1 = require("passport-jwt");
const client_1 = require("@prisma/client");
const config_1 = __importDefault(require("../../../routes/JWT_Auth/config/config"));
const prisma = new client_1.PrismaClient();
function pass(passport) {
    const opts = {};
    opts.secretOrKey = config_1.default;
    opts.jwtFromRequest = passport_jwt_1.ExtractJwt.fromAuthHeaderAsBearerToken();
    passport.use(new passport_jwt_1.Strategy(opts, function (jwt_payload, done) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const user = yield prisma.user.findUnique({
                    where: {
                        id: jwt_payload.id
                    }
                });
                if (user) {
                    return done(null, user);
                }
                else {
                    return done(null, false);
                }
            }
            catch (err) {
                return done(err, false);
            }
        });
    }));
}
exports.default = pass;
