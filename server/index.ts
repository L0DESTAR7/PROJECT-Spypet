import express, { Request, Response } from 'express';
import bodyParser from 'body-parser';
import ordersRouter from './routes/orders';
import dataRouter from './routes/data';
import mediaRouter from './routes/media';

const app = express();
app.use(bodyParser.json());

app.get('/', (req: Request, res: Response) => {
  res.send("SPIN");
});

app.use('/orders', ordersRouter);
app.use('/data', dataRouter);
app.use('/media', mediaRouter);

app.listen(8080, () => {
  console.log('Server is listening on port 8080');
});
