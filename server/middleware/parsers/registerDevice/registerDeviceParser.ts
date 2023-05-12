import { Response, Request, NextFunction } from 'express';
import verifyUserToken from '../../../helpers/verifyUserToken';
import RegisterDeviceReq from '../../../types/requests/registerDeviceReq';

export const parseDeviceRegReq = (req: Request, res: Response,
  next: NextFunction) => {

  let device: RegisterDeviceReq = req.body;
  console.log(`Beginning parsing of ${JSON.stringify(device)}`);
  // STEP1: Verify device name field
  if (!device.name) { return res.status(400).json({ error: "Missing device name argument." }); }
  // STEP2: Verify header contains authorization bearer token 
  const authHeader = req.headers["authorization"];
  if (!authHeader) { return res.status(401).json({ error: "Authorization header is missing." }); }
  // DONE
  next();
}
