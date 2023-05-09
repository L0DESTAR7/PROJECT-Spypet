const android_io = require('socket.io-client');
const android_socket = android_io("http://localhost:8080", {
  auth: {
    token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjJlZDk3MzEwLTk3OGItNGUxYi1iZTBmLTdiM2E1ZDExNjIzOCIsImVtYWlsIjoieGFyaWw0ODkzM0Bzb29tYm8uY29tIiwibmFtZSI6IkhhbWlkIiwicGFzc3dvcmQiOiIkMmIkMTAkTWwzM3dVRHI2aDNkYTY1NVJkUHRUdTNxdmVHci9NVnN0RnFKckFzQ2dua0JmUEF3Q0tzZE8iLCJpYXQiOjE2ODM1OTg4MTB9.YPTshd_qZ8ppyYE2-XCn_b6fgbIZJ89V85n7qyRnuC0"
  },
  extraHeaders: {
    "spypet-device-type": "ANDROID"
  }
});


android_socket.on("hello", (arg) => {
  console.log(arg);
});

android_socket.on("room:joined", (arg) => {
  console.log(arg);
});

android_socket.on("room:auth-error", (arg) => {
  console.log(arg);
});

android_socket.on("room:spypet-joined", (arg) => {
  console.log(arg);
})

android_socket.on("raspberry:weight-changed", (arg) => {
  console.log(arg);
})
