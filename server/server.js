const express = require('express');
const bodyParser = require('body-parser');
const ordersRouter = require('./routes/orders');
const dataRouter = require('./routes/data');
const mediaRouter = require('./routes/media');

const app = express();
app.use(bodyParser.json());

app.get('/', (req, res) => {
  res.send("SPIN");
});

app.use('/orders', ordersRouter);
app.use('/data', dataRouter);
app.use('/media', mediaRouter);

app.listen(8080, () => {
  console.log('Server is listening on port 8080');
});
