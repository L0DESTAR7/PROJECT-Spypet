import { Request, Response, NextFunction } from 'express';
import { PrismaClient, Prisma } from '@prisma/client';
import { Device } from '@prisma/client';

const prisma = new PrismaClient();

export const getDevices = async (req: Request, res: Response) => {
  try {
    const userID: string = res.locals.userID;
    console.log(`Looking up devices of user of id = ${userID}`);
    // QUITE A DILEMA, SHOULD WE RETRIEVE THE DEVICE ID OR NOT?
    // SHOULD WE RETRIEVE USER ID OR NOT?
    // ANSWERS TO THESE QUESTIONS DEPEND ON HOW THE LITE ANDROID
    // CLIENT WISHES TO USE THE DATA.
    const userWithDevices = await prisma.user.findUnique({
      where: { id: userID },
      include: { Devices: true },
    });

    console.log(`Look up result: ${JSON.stringify(userWithDevices?.Devices)}`);
    if (userWithDevices == null) {
      return res.status(401).json({ error: "userDevices is null" });
    }
    return res.status(200).json({ devices: userWithDevices.Devices });
  } catch (error) {
    throw error;
  } finally {

  }
}
