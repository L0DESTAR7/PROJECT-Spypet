import { Device } from "@prisma/client"
import { PrismaClient, Prisma } from '@prisma/client';

const prisma = new PrismaClient();

export const verifyDeviceId = async (id: string): Promise<Device | null> => {
  console.log(`Received id: ${id}`);
  try {
    const device: Device | null = await prisma.device.findUnique({
      where: {
        id: id,
      }
    })
    return device;
  } catch (error) {
    console.error('Error fetching device:', error);
    throw new Error('Failed to fetch device');
  } finally {
    prisma.$disconnect;
  }
}
