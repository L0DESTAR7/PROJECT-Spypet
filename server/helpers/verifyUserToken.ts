import jwt, { VerifyErrors } from 'jsonwebtoken';
import DecodedUser from '../types/DecodedUser';

export default function verifyUserToken(token: string): DecodedUser | null {
  let result: DecodedUser | null = null;
  const secret = process.env.SECRET;
  if (!secret) {
    throw new Error("SECRET env variable does not exist");
  }
  jwt.verify(token, secret, function(err: VerifyErrors | null, decoded: any) {
    if (err) {
      console.log('Authentication error: Invalid token !');
    } else {
      result = decoded;
    }
  });

  return result;
}

