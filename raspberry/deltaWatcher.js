
import { spawn } from "child_process";


export const startWeightWatch = (socket) => {
  const deltaCalculatorProg = spawn('./loadcell_zaid/hx711/bin/simplehx711test 5 6 111 411544', [], {stdio: 'pipe', shell: true});

  deltaCalculatorProg.stdout.on('data', (data) => {
    console.log(`${data.toString()}`);
    socket.emit("raspberry:weight-changed", {
      value: Number(data.toString()),
      unit: 'g',
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