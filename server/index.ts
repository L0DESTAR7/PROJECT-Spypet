import express, { Request, Response } from 'express';
import bodyParser from 'body-parser';
import ordersRouter from './routes/orders';
import registerDeviceRouter from './routes/registerDevice';
import mediaRouter from './routes/media';
import devicesRouter from './routes/devices';
import { Server } from 'socket.io';
import { createServer } from 'http';
import auth_routes from './routes/JWT_Auth/auth_routes';
import passport from 'passport';
import pass from './middleware/hooks/auth_hooks/passport';
import connectionHandler from './eventHandlers/connection_handler';
import small_weight_changed_handler from './eventHandlers/small_weight_changed_handler';
import big_weight_changed_handler from './eventHandlers/big_weight_changed_handler';


const app = express();
const httpServer = createServer(app);
const io = new Server(httpServer, {});

app.use(bodyParser.json());

app.use(passport.initialize());
pass(passport);

app.get('/', (req: Request, res: Response) => {
  res.send("SPIN");
});

app.use('/orders', passport.authenticate('jwt', { session: false }), ordersRouter);
app.use('/registerDevice', passport.authenticate('jwt', { session: false }), registerDeviceRouter);
app.use('/media', passport.authenticate('jwt', { session: false }), mediaRouter);
app.use('/devices', passport.authenticate('jwt', { session: false }), devicesRouter);
app.use(auth_routes);

io.on("connection", async (socket) => {
  const room_id: string | null = await connectionHandler(socket);
  console.log("HAMID 3");
  socket.on("raspberry:small-weight-changed", (data) => {
    console.log("Hamid");
    if (!room_id) {
      socket.disconnect();
    }
    small_weight_changed_handler(socket, room_id!, data);
  });

  // Code duplication !!! Should be improved ASAP.
  socket.on("raspberry:big-weight-changed", (data) => {
    console.log("Hamid");
    if (!room_id) {
      socket.disconnect();
    }
    big_weight_changed_handler(socket, room_id!, data);
  })
});

/*
app.get("/device", (req, res) => {
  res.setHeader("Content-Type", "text/event-stream");
  res.write("Connected: " + "hamid\n\n");
  send(res);
})

var i: number = 0;
function send(res: Response) {
  res.write(`Hello hamid ${i++}.`);
  setTimeout(() => send(res), 1000);
}
*/

httpServer.listen(8080, () => {
  console.log('Server is listening on port 8080');
});

export default io;
