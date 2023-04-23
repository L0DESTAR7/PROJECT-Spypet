import { Request, Response, NextFunction } from "express";
import { resolveMx } from "./domainValidator";
import { checkMailBox, typecheckMail  } from "./checkMailBox";

interface SignUpRequestBody {
  email: string;
}

const emailDomainChecker = async (req: Request<{}, {}, SignUpRequestBody>, res: Response, next: NextFunction) => {

    const [email, domain] = [req.body.email, req.body.email.split('@')[1]];
    const sortedMxRecords = (await resolveMx(domain)).sort((a,b)=> a.priority - b.priority)
    let hostIndex = 0;
    let smtpResult : typecheckMail = {connected : false, mailValid : false};

    while(hostIndex < sortedMxRecords.length){
        try{
            smtpResult  = await checkMailBox(sortedMxRecords[hostIndex].exchange, email);
            if(!smtpResult.mailValid){
            hostIndex++;
            }
            else {
                break;
            }
        }
        catch(error){
            console.error(error);
        }
    }

    if(!smtpResult.mailValid){
        res.status(400).json({ success : false, msg: "Not A valid mailbox" })
    }
    else {
        next()
    }
    
}

export {emailDomainChecker};
