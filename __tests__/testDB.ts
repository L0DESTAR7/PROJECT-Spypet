import { PrismaClient } from '@prisma/client';

//connectiong to the testing database
const prisma = new PrismaClient({ datasources: { db: { url: 'postgresql://postgres:tsathamid@localhost:5432/testDB' }} })

export default prisma;
