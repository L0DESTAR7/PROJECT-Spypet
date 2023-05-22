const raspberry_io = require('socket.io-client');


const raspberry_socket = raspberry_io("http://localhost:8080", {
  extraHeaders: {
    "spypet-device-type": "RASPBERRY",
    "spypet-device-identifier": "9a3e041f-1fe3-4be7-b6d7-39a13e82680b"
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
  raspberry_socket.emit("raspberry:small-weight-changed", {
    value: 15,
    unit: "g",
  });
  console.log("sending weight...");
}, 5000);

setInterval(() => {
  raspberry_socket.emit("raspberry:big-weight-changed", {
    value: 250,
    unit: "g",
  });
  console.log("sending weight...");
}, 3000);
