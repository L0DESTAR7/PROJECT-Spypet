import { Socket } from "socket.io";

const spawn = require('child_process').spawn;


export default function startWeightWatch(socket: Socket) {
  const deltaCalculatorProg = spawn('./delta.exe');

  deltaCalculatorProg.stdout.on('data', (data) => {
    console.log(`${data.toString()}`);
    socket.emit("raspberry:weight-changed", {
      weight: data,
      unit: "g",
    });
    console.log("sending weight...");
  });

  deltaCalculatorProg.stdout.on('error', (err) => {
    console.error(`Failed to start C++ program: ${err}`);
  });

  deltaCalculatorProg.on('close', (code) => {
    console.log(`C++ program exited with code ${code}`);
  });

}
