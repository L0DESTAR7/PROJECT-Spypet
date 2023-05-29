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
exports.createOrder = void 0;
const client_1 = require("@prisma/client");
const prisma = new client_1.PrismaClient();
const createOrder = (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        console.log("A7aa");
        const order = res.locals.order;
        const user = res.locals.user;
        let userID;
        if (user == null) {
            userID = "8db72ec5-acfd-44c4-b467-df81d668a996";
        }
        else {
            userID = user.id;
        }
        console.log(`createOrder received following order object: ${JSON.stringify(order)}`);
        let orderCreateInput;
        orderCreateInput = {
            type: order.type.toString(),
            params: order.params.toString(),
            programedAt: order.programmedAt,
            periodicity: order.periodicity.toString(),
            author: {
                connect: { id: userID }
            },
            device: {
                connect: { id: "9a3e041f-1fe3-4be7-b6d7-39a13e82680b" }
            }
        };
        const createOrder = yield prisma.order.create({
            data: orderCreateInput
        });
        next();
    }
    catch (error) {
        console.error('Error creating order:', error);
        throw new Error('Failed to create order');
    }
    finally {
        yield prisma.$disconnect();
    }
});
exports.createOrder = createOrder;
