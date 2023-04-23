import { Request, Response, NextFunction } from 'express';
import { check_for_user } from '../../db_ops/Authentication_ops';

interface AuthenticatedRequest extends Request {
  existing_user?: any;
}

export async function check_user_sign_up(req: Request, res: Response, next: NextFunction): Promise<void> {

  const { email, password, name } = req.body;

  if (!email || !password || !name) {
    res.status(400).json({ success: false, msg: 'Please enter all fields' });
  } else {
    // Check if the Email is already registered
    const existingUser = await check_for_user(email);
    if (existingUser) {
      res.status(409).json({ success: false, msg: 'Email already exists, Try again using another email' });
    } else {
      //check if the mail box exists
      next()
    }
  }
}

export async function check_user_sign_in(req: AuthenticatedRequest, res: Response, next: NextFunction) {

  const { email } = req.body;

  const existing_user = await check_for_user(email);

  if (!existing_user) {
    res.status(403).send({ success: false, msg: "Authentication Failed" });
  }
  else {
    res.locals.existing_user = existing_user;
    next();
  }

}
