const { PrismaClient } = require('@prisma/client');

const prisma = new PrismaClient();

async function main() {
  const newDevice = await prisma.device.create({
    data: {
      name: 'Spypet#7042',
      model: 'MK1',
    }
  });

  console.log(`Created device with ID: ${newDevice.id}`);
}

main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
