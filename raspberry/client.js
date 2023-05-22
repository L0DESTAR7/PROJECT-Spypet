import { Socket } from "socket.io-client";
import { startWeightWatch } from '../PROJECT-Spypet/deltaWatcher.js';
import io from 'socket.io-client';
import { createSpinTask } from '../PROJECT-Spypet/order_spin.js';

const raspberry_socket = io("https://5739-196-75-203-166.ngrok-free.app/", {
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
    arg = JSON.parse(arg);
    console.log(arg.programmedAt);
    createSpinTask(arg.programmedAt, arg.periodicity, './servo.py');
});

raspberry_socket.on("room:android-joined", (arg) => {
    console.log(arg);
});
  
raspberry_socket.on("room:joined", (arg) => {
    startWeightWatch(raspberry_socket);
});
