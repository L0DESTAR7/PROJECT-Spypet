import express, { Request, Response } from 'express'
import io from '../index';
import { updateDevice } from '../middleware/db_ops/updateDevice';
import { parseDeviceRegReq } from '../middleware/parsers/registerDevice/registerDeviceParser';
import { validateRegisterDeviceReq } from '../middleware/validators/registerDevice/deviceValidator';


const router = express.Router();

router.get('/', (req: Request, res: Response) => {
  res.send("This is /registerDevice");
})

router.post('/',
  parseDeviceRegReq,
  validateRegisterDeviceReq,
  updateDevice,
  (req: Request, res: Response) => {
    res.status(200).json({ success: true, status: "Device registered!" });
  });

export default router;
