import { Request, Response } from 'express';
import functions from '../../../server/routes/JWT_Auth/auth_actions';
import { saveUser } from '../../../server/middleware/db_ops/Authentication_ops';
import { compare_password } from '../../../server/middleware/hooks/auth_hooks/password_compare';
import jwt from 'jsonwebtoken';

interface SignUpRequestBody {
  email: string;
  name: string;
  password: string;
}

interface SignUpResponseBody {
  success: boolean;
  msg: string;
}

interface SignInResponseBody {
  success: boolean;
  token: string;
}


jest.mock('../../middleware/db_ops/Authentication_ops');
jest.mock('../../middleware/hooks/auth_hooks/password_compare');

describe('Signup', () => {
  it('should register user and return success message with 200 status code', async () => {
    const req: Request<{}, {}, SignUpRequestBody> = {
      body: {
        email: 'example@example.com',
        password: 'password123',
        name: 'John Doe'
      }
    } as Request<{}, {}, SignUpRequestBody>;

    const res: Response<SignUpResponseBody> = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn()
    } as unknown as Response<SignUpResponseBody>;

    const saveUserMock = saveUser as jest.MockedFunction<typeof saveUser>;
    saveUserMock.mockResolvedValueOnce(undefined);

    await functions.signup(req, res);

    expect(saveUserMock).toHaveBeenCalledWith({
      email: 'example@example.com',
      password: 'password123',
      name: 'John Doe'
    });
    expect(res.status).toHaveBeenCalledWith(200);
    expect(res.json).toHaveBeenCalledWith({ success: true, msg: 'User Registered Successfully' });
  });

  it('should return error message with 500 status code when unable to register user', async () => {
    const req: Request<{}, {}, SignUpRequestBody> = {
      body: {
        email: 'example@example.com',
        password: 'password123',
        name: 'John Doe'
      }
    } as Request<{}, {}, SignUpRequestBody>;

    const res: Response<SignUpResponseBody> = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn()
    } as unknown as Response<SignUpResponseBody>;

    const saveUserMock = saveUser as jest.MockedFunction<typeof saveUser>;
    saveUserMock.mockRejectedValueOnce(new Error());

    await functions.signup(req, res);

    expect(res.status).toHaveBeenCalledWith(500);
    expect(res.json).toHaveBeenCalledWith({ success: false, msg: 'Unable to register user' });
  });
});

describe('Signin', () => {
  it('should authenticate user and return token with success message when given correct password', async () => {
    const req: Request<{}, {}, SignUpRequestBody> = {
      body: {
        email: 'example@example.com',
        password: 'password123',
        name: 'John Doe'
      }
    } as Request<{}, {}, SignUpRequestBody>;

    const res: Response<SignInResponseBody> = {
      status: jest.fn().mockReturnThis(),
      send: jest.fn(),
      json: jest.fn()
    } as unknown as Response<SignInResponseBody>;

    const comparePasswordMock = compare_password as jest.MockedFunction<typeof compare_password>;
    comparePasswordMock.mockResolvedValueOnce(true);

    jest.spyOn(jwt, 'sign').mockReturnValueOnce('eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c');

    res.locals = {
      existing_user: {
        email: 'example@example.com',
        password: 'password123',
        name: 'John Doe'
      }
    };

    await functions.signin(req, res);

    expect(comparePasswordMock).toHaveBeenCalledWith('password123', 'password123');
    expect(res.json).toHaveBeenCalledWith({ success: true, token: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'});
    expect(res.status).toHaveBeenCalledWith(200);
});

  it('should return error message with 401 status code when given incorrect password', async () => {
     const req: Request<{}, {}, SignUpRequestBody> = {
        body: {
           email: 'example@example.com',
           password: 'password123',
           name: 'John Doe'
           }} as Request<{}, {}, SignUpRequestBody>;
           const res: Response<SignInResponseBody> = {
            status: jest.fn().mockReturnThis(),
            send: jest.fn(),
            json: jest.fn()
          } as unknown as Response<SignInResponseBody>;
          
          const comparePasswordMock = compare_password as jest.MockedFunction<typeof compare_password>;
          comparePasswordMock.mockResolvedValueOnce(false);
          
          await functions.signin(req, res);
          
          expect(comparePasswordMock).toHaveBeenCalledWith('password123', undefined);
          expect(res.json).toHaveBeenCalledWith({ success: false, msg: 'Invalid Email or Password' });
          expect(res.status).toHaveBeenCalledWith(401);
        }
      );
    });          