FROM node:19-alpine

WORKDIR /server

COPY package.json ./

RUN npm install

COPY . .

RUN npm start

