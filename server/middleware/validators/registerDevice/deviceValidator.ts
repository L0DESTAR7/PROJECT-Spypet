import { Device } from '@prisma/client';
import { Request, Response, NextFunction } from 'express';
import { verifyDeviceId } from '../../../helpers/verifyDeviceId';
import verifyUserToken from '../../../helpers/verifyUserToken';
import DecodedUser from '../../../types/DecodedUser';
import RegisterDeviceReq from '../../../types/requests/registerDeviceReq';


export const validateRegisterDeviceReq = async (req: Request, res: Response,
  next: NextFunction) => {
  let reqDevice: RegisterDeviceReq = req.body;
  const token: string = req.headers['authorization']!.split(' ')[1];
  // STEP1: Validate and Retrieve user object 
  const decodedUser: DecodedUser | null = verifyUserToken(token);
  if (!decodedUser) {
    return res.status(401).json({ error: "Authorization error. Invalid token" });
  }
  // STEP2: Verify existence of device with id = device.id 
  const device: Device | null = await verifyDeviceId(reqDevice.id);
  console.log(JSON.stringify(device));
  if (!device) { return res.status(400).json({ error: "Device Id was not found." }); }
  // STEP3: Verify device doesn'nt already belong to user 
  if (device.user_id === decodedUser.id) { return res.status(409).json({ error: "Device already belongs to this user." }) }
  // DONE
  res.locals.device = device;
  res.locals.owner = decodedUser.id;
  next();
}
