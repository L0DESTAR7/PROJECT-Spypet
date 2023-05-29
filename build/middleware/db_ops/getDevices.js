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
exports.getDevices = void 0;
const client_1 = require("@prisma/client");
const prisma = new client_1.PrismaClient();
const getDevices = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const userID = res.locals.userID;
        console.log(`Looking up devices of user of id = ${userID}`);
        // QUITE A DILEMA, SHOULD WE RETRIEVE THE DEVICE ID OR NOT?
        // SHOULD WE RETRIEVE USER ID OR NOT?
        // ANSWERS TO THESE QUESTIONS DEPEND ON HOW THE LITE ANDROID
        // CLIENT WISHES TO USE THE DATA.
        const userWithDevices = yield prisma.user.findUnique({
            where: { id: userID },
            include: { Devices: true },
        });
        console.log(`Look up result: ${JSON.stringify(userWithDevices === null || userWithDevices === void 0 ? void 0 : userWithDevices.Devices)}`);
        if (userWithDevices == null) {
            return res.status(401).json({ error: "userDevices is null" });
        }
        return res.status(200).json({ devices: userWithDevices.Devices });
    }
    catch (error) {
        throw error;
    }
    finally {
    }
});
exports.getDevices = getDevices;
