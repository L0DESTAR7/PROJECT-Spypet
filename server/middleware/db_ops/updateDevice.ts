import { Request, Response, NextFunction } from 'express';
import { PrismaClient, Prisma } from '@prisma/client';
import { Device } from '@prisma/client';

const prisma = new PrismaClient();

export const updateDevice = async (req: Request, res: Response,
  next: NextFunction) => {
  try {
    const device: Device = res.locals.device;
    const updatedDevice: Device = await prisma.device.update({
      where: { id: device.id },
      data: {
        user_id: res.locals.owner,
        name: res.locals.name,
      }
    });
    console.log(`Updated device: ${JSON.stringify(updatedDevice)}`);
    next();
  } catch (error) {

  } finally {

  }
}
