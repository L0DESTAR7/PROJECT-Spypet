const io = require('socket.io-client');
const socket = io("https://39db-196-74-197-36.ngrok-free.app");

socket.on("hello", (arg) => {
  console.log(arg);
});

socket.on("order:created", (arg) => {
  console.log(arg);
})
