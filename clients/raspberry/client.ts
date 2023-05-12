const raspberry_io = require('socket.io-client');


const raspberry_socket = raspberry_io("http://localhost:8080", {
  extraHeaders: {
    "spypet-device-type": "RASPBERRY",
    "spypet-device-identifier": "fc07c56e-9a22-4113-a150-804ef08f6761"
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
    value: 0,
    unit: "g",
  });
  console.log("sending weight...");
}, 5000);
