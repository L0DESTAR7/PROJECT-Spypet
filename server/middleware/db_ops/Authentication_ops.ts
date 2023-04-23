import User from '../../types/User';
import { PrismaClient, Prisma } from "@prisma/client"
import prisma from '../hooks/auth_hooks/password_crypting'


async function saveUser(user: User): Promise<User> {
  let userCreateInput: Prisma.UserCreateInput;
  userCreateInput = {
    email: user.email,
    name: user.name,
    password: user.password
  }
  const createdUser = await prisma.user.create({
    data: {
      email: user.email,
      name: user.name,
      password: user.password
    },
  });
  return createdUser;
}

async function check_for_user(email: User["email"]) {
  return prisma.user.findUnique({
    where: {
      email: email,
    },
  }
  )
}


export { saveUser, check_for_user };

