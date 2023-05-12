import { Request, Response, NextFunction } from 'express';
import { parseOrderReq } from '../../server/middleware/parsers/orders/orderParser';
import Order from '../../server/types/order';


describe('parseOrderReq', () => {
  let req: Request;
  let res: Response;
  let next: NextFunction;

  beforeEach(() => {
    req = {
      body: {
        type: 'SPIN',
        params: '123',
        programmedAt: new Date().toISOString(),
        periodicity: 'WEEKLY',
      },
    } as Request;
    res = {} as Response;
    next = jest.fn();
  });

  it('should parse a valid order', () => {
    parseOrderReq(req, res, next);
    expect(res.locals.order).toEqual(req.body);
    expect(next).toHaveBeenCalledTimes(1);
  });

  it('should return an error if the order type is invalid', () => {
    req.body.type = 'INVALID_TYPE';
    const response = parseOrderReq(req, res, next);
    expect(response.status).toBe(400);
    expect(response.body).toEqual({ error: 'Invalid order.type argument.' });
    expect(next).not.toHaveBeenCalled();
  });

  it('should return an error if the order params are invalid', () => {
    req.body.params = 'invalid_params';
    const response = parseOrderReq(req, res, next);
    expect(response.status).toBe(400);
    expect(response.body).toEqual({ error: 'Invalid order.params argument for order of type: SPIN.' });
    expect(next).not.toHaveBeenCalled();
  });

  it('should return an error if the order programmedAt is in the past', () => {
    req.body.programmedAt = new Date('2022-01-01').toISOString();
    const response = parseOrderReq(req, res, next);
    expect(response.status).toBe(400);
    expect(response.body).toEqual({ error: 'Invalid order.programmedAt argument. Date in the past.' });
    expect(next).not.toHaveBeenCalled();
  });

  it('should return an error if the order periodicity is invalid', () => {
    req.body.periodicity = 'invalid_periodicity';
    const response = parseOrderReq(req, res, next);
    expect(response.status).toBe(400);
    expect(response.body).toEqual({ error: 'Invalid order.periodicity argument.' });
    expect(next).not.toHaveBeenCalled();
  });
});
