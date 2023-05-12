// Import dependencies and functions to be tested
import { Request, Response, NextFunction } from 'express';
import { check_for_user } from '../../../../server/middleware/db_ops/Authentication_ops';
import { check_user_sign_in } from '../../../../server/middleware/validators/Auth/user_check';


jest.mock('../../../../server/middleware/db_ops/Authentication_ops');

describe('check_user_sign_in function', () => {
  let req: any;
  let res: any;
  let next: any;

  beforeEach(() => {
    req = {
      body: {
        email: 'walid.lam09@gmail.com'
      }
    };
    res = {
      status: jest.fn().mockReturnThis(),
      send: jest.fn(),
      locals: {}
    };
    next = jest.fn();
  });

  it('should set res.locals.existing_user if user exists', async () => {
    const existing_user = { id: '0f74da95-59a2-4111-a065-b6fefffc2ebb', name: 'Walid', email: 'walid.lam09@gmail.com', password: 'yamato23-' };
    (check_for_user as jest.MockedFunction<typeof check_for_user>).mockResolvedValue(existing_user);

    await check_user_sign_in(req, res, next);

    expect(res.locals.existing_user).toBe(existing_user);
    expect(next).toBeCalled();
  });

  it('should send 403 error if user does not exist', async () => {
    (check_for_user as jest.MockedFunction<typeof check_for_user>).mockResolvedValue(null);

    await check_user_sign_in(req, res, next);

    expect(res.status).toBeCalledWith(403);
    expect(res.send).toBeCalledWith({ success: false, msg: 'Authentication Failed' });
    expect(next).not.toHaveBeenCalled();
  });
});


