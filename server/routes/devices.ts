
import express, { NextFunction, Request, Response } from 'express';
import verifyUserToken from '../helpers/verifyUserToken';
import io from '../index';
import { getDevices } from '../middleware/db_ops/getDevices';
import DecodedUser from '../types/DecodedUser';

const router = express.Router();

router.get('/', (req: Request, res: Response) => {
  const token: string = req.headers['authorization']!.split(' ')[1];
  const decodedUser: DecodedUser | null = verifyUserToken(token);
  // STEP1: Validate and Retrieve user object 
  if (!decodedUser) {
    return res.status(401).json({ error: "Authorization error. Invalid token" });
  }
  res.locals.userID = decodedUser.id;
  console.log(`Decoded user: ${JSON.stringify(decodedUser)}`);
  // STEP2: Get User devices and send them back.
  getDevices(req, res);
});

export default router;
