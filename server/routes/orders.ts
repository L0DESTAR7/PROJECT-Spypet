import express, { Request, Response } from 'express';
import { validateOrderReq } from '../middleware/validators/orders/orderValidator';
import { parseOrderReq } from '../middleware/parsers/orders/orderParser';
import { createOrder } from '../middleware/db_ops/createOrder';
import io from '../index';

const router = express.Router();

router.get('/', (req: Request, res: Response) => {
  const type = req.query.type;
  const date = req.query.date;

  if (!type || !date) {
    // Perhaps separating this if statement into two
    // if's and specifying which argument is missing.
    return res.status(400).json({ error: "Missing required parameters" });
  }

  res.status(200).json({ type: type, date: date });
})


router.post('/',
  parseOrderReq,
  createOrder,
  (req: Request, res: Response) => {
    io.emit('order:created', JSON.stringify(res.locals.order));
    res.status(201).json(res.locals.order);
  })

export default router;
