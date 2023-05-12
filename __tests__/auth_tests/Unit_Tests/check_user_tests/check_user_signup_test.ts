import { Request, Response, NextFunction } from 'express';
import { check_user_sign_up } from '../../../../server/middleware/validators/Auth/user_check';

describe('check_user_sign_up', () => {
  test('should return 400 if email, password, or name is missing', async () => {
    const req = { body: { email: '', password: '', name: '' } } as Request;
    const res = { status: jest.fn().mockReturnThis(), json: jest.fn() } as unknown as Response;
    const next = jest.fn() as NextFunction;
    await check_user_sign_up(req, res, next);
    expect(res.status).toHaveBeenCalledWith(400);
  });

  test('should return 409 if email is already registered', async () => {
    const req = { body: { email: 'walid.lam09@gmail.com', password: 'yamato23-', name: 'Walid Lamkoutar' } } as Request;
    const res = { status: jest.fn().mockReturnThis(), json: jest.fn() } as unknown as Response;
    const next = jest.fn() as NextFunction;
    await check_user_sign_up(req, res, next);
    expect(res.status).toHaveBeenCalledWith(409);
  });

  test('should call next if email is not registered', async () => {
    const req = { body: { email: 'new_user@example.com', password: 'password', name: 'John' } } as Request;
    const res = { status: jest.fn().mockReturnThis(), json: jest.fn() } as unknown as Response;
    const next = jest.fn() as NextFunction;
    await check_user_sign_up(req, res, next);
    expect(next).toHaveBeenCalled();
  });
});



