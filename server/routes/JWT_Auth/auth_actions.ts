import { Request, Response, NextFunction } from 'express';
import jwt from 'jsonwebtoken';
import { saveUser } from '../../middleware/db_ops/Authentication_ops';
import { compare_password } from '../../middleware/hooks/auth_hooks/password_compare';
import { error } from 'console';
import secret from './config/config'
import User from '../../types/User'


interface SignUpRequestBody {
  email: string;
  name: string;
  password: string;
}

interface SignUpResponseBody {
  success: boolean;
  msg: string;
}

interface SignInResponseBody {
  success: boolean;
  token: string;
}



const functions = {
  signup: async function (req: Request<{}, {}, SignUpRequestBody>, res: Response<SignUpResponseBody>): Promise<void> {
    try {
      const user: User = {
        email: req.body.email,
        password: req.body.password,
        name: req.body.name
      };
      await saveUser(user);
      res.status(200).json({ success: true, msg: 'User Registered Successfully' });
    } catch (error) {
      res.status(500).json({ success: false, msg: 'Unable to register user' });
    }
  },
  signin: async function (req: Request<{}, {}, SignUpRequestBody>, res: Response<SignInResponseBody>): Promise<void> {
    //check if password matches
    const password = req.body.password;
    const isMatch = await compare_password(res.locals.existing_user.password,password)
    if(isMatch ){
       var token = jwt.sign(res.locals.existing_user,secret);
       res.json({success:true, token: token})
    }
    else {
      res.status(403).send({success: false, token: 'Authentication failed, wrong password'})
    }
  }
};

export default functions;
