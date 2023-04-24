FROM node:19-alpine

WORKDIR /server

COPY package.json ./

RUN npm install

COPY . .

COPY ./server_startup.sh ./server_startup.sh

RUN sed -i 's/\r$//' server_startup.sh # This deals with annoying pesky dog windows carriage returns >:(

ENTRYPOINT [ "./server_startup.sh" ]
