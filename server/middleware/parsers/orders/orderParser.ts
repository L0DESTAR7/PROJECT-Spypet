import { Response, Request, NextFunction } from 'express';
import Order from '../../../types/order';


// WARNING: Many Nest if-else blocks !!! This should be
//          replaced later on with seperate functions.
export const parseOrderReq = (req: Request, res: Response,
  next: NextFunction) => {

  console.log("I got into parseOrderReq");
  let order: Order = req.body;
  console.log(`Beginning parsing of ${JSON.stringify(order)}`);
  // STEP1: Parse order type.
  //        Enums should be implemented to improve this part.
  if (order.type === "SPIN") {
    console.log(`Parsed type`);
    // STEP2: Parse order params. For now only numbers are allowed.
    //        Perhaps later, when orders require different params
    //        we should adapt the parsing logic.
    let params: String = order.params;
    if (/^[0-9]+$/.test(params.toString())) {
      // STEP3: Parse order programmedAt.
      console.log(`Parsed params`);
      const date = new Date(order.programmedAt);

      if (!isNaN(date.getTime())) {
        const now: Date = new Date();

        if (date > now) {
          console.log(`Parsed programmedAt`);
          // STEP4 (LAST STEP): Parse order periodicty.
          let periodicity: String = order.periodicity;

          // This is an double check. Normally we can skip it, but it doesn't hurt
          // to recheck.
          if (periodicity === "") {
            order.periodicity = "WEEKLY";
            console.log(`Parsed periodicity`);
            res.locals.order = order;
            next();
          } else if (periodicity === "DAILY" || periodicity === "MOUNTHLY" || periodicity === "YEARLY") {
            // END OF PARSE !
            console.log(`Parsed periodicity HAMID`);
            res.locals.order = order;
            next();
          } else {
            return res.status(400).json({ error: "Invalid order.periodicity argument." });
          }
        } else {
          return res.status(400).json({ error: "Invalid order.programmedAt argument. Date in the past." });
        }
      } else {
        return res.status(400).json({ error: "Invalid order.programmedAt argument." });
      }
    } else {
      return res.status(400).json({ error: "Invalid order.params argument for order of type: SPIN." });
    }
  } else {
    return res.status(400).json({ error: "Invalid order.type argument." });
  }
}
