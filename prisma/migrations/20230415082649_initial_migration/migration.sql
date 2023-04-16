-- CreateTable
CREATE TABLE "User" (
    "USER_PRIMARY_KEY" SERIAL NOT NULL,
    "email" TEXT NOT NULL,
    "name" TEXT NOT NULL,
    "device_id" INTEGER NOT NULL,

    CONSTRAINT "User_pkey" PRIMARY KEY ("USER_PRIMARY_KEY")
);

-- CreateTable
CREATE TABLE "Order" (
    "ORDER_PRIMARY_KEY" SERIAL NOT NULL,
    "type" TEXT NOT NULL,
    "date" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "Order_pkey" PRIMARY KEY ("ORDER_PRIMARY_KEY")
);

-- CreateIndex
CREATE UNIQUE INDEX "User_email_key" ON "User"("email");

-- CreateIndex
CREATE UNIQUE INDEX "User_device_id_key" ON "User"("device_id");
