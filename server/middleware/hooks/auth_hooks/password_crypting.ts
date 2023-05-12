import { PrismaClient, User } from '@prisma/client';
import bcrypt from 'bcrypt';


const prisma = new PrismaClient();

export default prisma;

prisma.$use(async (params, next) => {
  if (params.model === 'User' && params.action === 'create') {
    const userData = params.args.data as User;
    const saltRounds = 10;
    const hashedPassword = await bcrypt.hash(userData.password, saltRounds);
    params.args.data = { ...userData, password: hashedPassword };
  }

  return next(params);
});

