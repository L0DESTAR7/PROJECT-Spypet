const express = require('express');
const router = express.Router();

router.get('/', (req, res) => {
  const type = req.query.type;
  const date = req.query.date;

  if (!type || !date) {
    // Perhaps separating this if statement into two
    // if's and specifying which argument is missing.
    return res.status(400).json({error: "Missing required parameters"});
  }

  res.status(200).json({type: type, date: date});
})


router.post('/', (req, res) => {
  const {
    type, 
    date, 
    periodicity = "WEEKLY",
    weight,
  } = req.body;

  if (!type || !date || !weight){
    return res.status(400).json({error: "Missing required parameters"});
  }
  
  res.status(201).json({type: type, date: date,
                        periodicity: periodicity,
                        weight: weight});
})


module.exports = router;
