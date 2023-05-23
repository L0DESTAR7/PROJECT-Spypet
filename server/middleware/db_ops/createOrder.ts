import { Request, Response, NextFunction } from 'express';
import { PrismaClient, Prisma } from '@prisma/client';
import Order from '../../types/order';
import DecodedUser from '../../types/DecodedUser';


const prisma = new PrismaClient();

export const createOrder = async (req: Request, res: Response,
  next: NextFunction) => {
  try {
    console.log("A7aa");
    const order: Order = res.locals.order;
    const user: DecodedUser | null = res.locals.user;
    let userID: string;
    if (user == null) { userID = "8db72ec5-acfd-44c4-b467-df81d668a996" }
    else { userID = user.id }
    console.log(`createOrder received following order object: ${JSON.stringify(order)}`);
    let orderCreateInput: Prisma.OrderCreateInput;
    orderCreateInput = {
      type: order.type.toString(),
      params: order.params.toString(),
      programedAt: order.programmedAt,
      periodicity: order.periodicity.toString(),
      author: {
        connect: { id: userID }
      },
      device: {
        connect: { id: "9a3e041f-1fe3-4be7-b6d7-39a13e82680b" }
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
