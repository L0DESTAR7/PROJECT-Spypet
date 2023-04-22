import express, { Request, Response } from 'express';
import bodyParser from 'body-parser';
import ordersRouter from './routes/orders';
import dataRouter from './routes/data';
import mediaRouter from './routes/media';
import { Server } from 'socket.io';
import { createServer } from 'http';


const app = express();
const httpServer = createServer(app);
const io = new Server(httpServer, {});

app.use(bodyParser.json());

app.get('/', (req: Request, res: Response) => {
  res.send("SPIN");
});

app.use('/orders', ordersRouter);
app.use('/data', dataRouter);
app.use('/media', mediaRouter);

io.on("connection", (socket) => {
  socket.emit("hello", "hamid from server");
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
