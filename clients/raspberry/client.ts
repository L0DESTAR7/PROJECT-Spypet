const raspberry_io = require('socket.io-client');


const raspberry_socket = raspberry_io("http://localhost:8080", {
  extraHeaders: {
    "spypet-device-type": "RASPBERRY",
    "spypet-device-identifier": "4869bbd1-73f8-4476-98e8-739e184822b8"
  }
});

raspberry_socket.on("hello", (arg) => {
  console.log(arg);
});

raspberry_socket.on("order:created", (arg) => {
  console.log(arg);
});

raspberry_socket.on("room:android-joined", (arg) => {
  console.log(arg);
});

setInterval(() => {
  raspberry_socket.emit("raspberry:weight-changed", {
    weight: 0,
    unit: "g",
  });
  console.log("sending weight...");
}, 5000);
