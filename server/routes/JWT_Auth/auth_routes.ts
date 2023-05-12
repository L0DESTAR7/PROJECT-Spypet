import {check_user_sign_up, check_user_sign_in} from "../../middleware/validators/Auth/user_check"
import express from 'express'
import Auth_actions from './auth_actions';
import { emailDomainChecker } from "../../middleware/validators/Auth/emailController";

const router = express.Router()

//Add a new user
router.post('/signup', check_user_sign_up, emailDomainChecker, Auth_actions.signup);

//Authenticate User
router.post('/signin' ,check_user_sign_in, Auth_actions.signin);

//Reset Password
//router.post('/reset_password')


export default router;