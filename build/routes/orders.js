"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const orderParser_1 = require("../middleware/parsers/orders/orderParser");
const createOrder_1 = require("../middleware/db_ops/createOrder");
const index_1 = __importDefault(require("../index"));
const router = express_1.default.Router();
router.get('/', (req, res) => {
    const type = req.query.type;
    const date = req.query.date;
    if (!type || !date) {
        // Perhaps separating this if statement into two
        // if's and specifying which argument is missing.
        return res.status(400).json({ error: "Missing required parameters" });
    }
    res.status(200).json({ type: type, date: date });
});
router.post('/', orderParser_1.parseOrderReq, createOrder_1.createOrder, (req, res) => {
    index_1.default.emit('order:created', JSON.stringify(res.locals.order));
    res.status(201).json(res.locals.order);
});
exports.default = router;
