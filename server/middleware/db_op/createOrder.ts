import { Request, Response, NextFunction } from 'express';
import { PrismaClient, Prisma } from '@prisma/client';
import Order from '../../types/order';


const prisma = new PrismaClient();

export const createOrder = async (req: Request, res: Response,
  next: NextFunction) => {
  try {
    console.log("A7aa");
    const order: Order = res.locals.order;
    console.log(`createOrder received following order object: ${JSON.stringify(order)}`);
    let orderCreateInput: Prisma.OrderCreateInput;
    orderCreateInput = {
      type: order.type.toString(),
      params: order.params.toString(),
      programedAt: order.programmedAt,
      periodicity: order.periodicity.toString(),
      author: {
        connect: { id: 0 }
      },
      device: {
        connect: { id: 0 }
      }
    }
    const createOrder = await prisma.order.create({
      data: orderCreateInput
    })
    next();
  } catch (error) {
    console.error('Error creating order:', error);
    throw new Error('Failed to create order');
  } finally {
    await prisma.$disconnect();
  }
}
