const android_io = require('socket.io-client');
const android_socket = android_io("http://localhost:8080", {
  auth: {
    token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQxNjhmOGJhLWNmMTQtNDViOS05OTU3LWRmYzUxYjNlNDIxOSIsImVtYWlsIjoidGFvdWZpa2toYXJpajEzQGdtYWlsLmNvbSIsIm5hbWUiOiJUYXdmdSIsInBhc3N3b3JkIjoiJDJiJDEwJFQ4UVFVTlBEQ1NQVVNpdHpsRlBUak9RckwxMW9kcGV4am9JWjNSelJZVHBGWTAuVlBKeFB5IiwiaWF0IjoxNjg0ODQxNjk0fQ.YNRT1P1BXJVwp9KaGiIft5B-etOEO42fFfXuDwgNtW0"
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
