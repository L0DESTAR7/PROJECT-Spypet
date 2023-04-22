import { Request, Response, NextFunction } from 'express';

export const validateOrderReq = (req: Request, res: Response,
  next: NextFunction) => {
  let order = req.body;
  next(order);
}
