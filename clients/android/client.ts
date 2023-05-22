const android_io = require('socket.io-client');
const android_socket = android_io("http://localhost:8080", {
  auth: {
    token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU1YzdkNmUxLTU2YTktNDc2NC1hZDc0LWU1M2QxNjM3OGFiNCIsImVtYWlsIjoid2FsaWQubGFtMDlAZ21haWwuY29tIiwibmFtZSI6IldhbGlkIExhbWtvdXRhciIsInBhc3N3b3JkIjoiJDJiJDEwJHM3MkM2VVRpUmdVbmdidkZkY2o5bC5CZjRqL1NmcWV5TU9QLnRkcnNULmFqSmo3ZnpibHFHIiwiaWF0IjoxNjgzOTMzMDIzfQ.7gMvj27JeJeQ3ZH8BGqV5-XEp4M1Ew_ueSsWKzRoT0E"
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

android_socket.on("raspberry:small-weight-changed", (arg) => {
  console.log(`small-weight-changed: ${JSON.stringify(arg)}`);
})

android_socket.on("raspberry:big-weight-changed", (arg) => {
  console.log(`big-weight-changed: ${JSON.stringify(arg)}`);
})
