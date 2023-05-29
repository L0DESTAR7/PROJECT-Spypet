"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.validateOrderReq = void 0;
const validateOrderReq = (req, res, next) => {
    let order = req.body;
    next(order);
};
exports.validateOrderReq = validateOrderReq;
