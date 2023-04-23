import { User } from '@prisma/client';
import bcrypt from 'bcrypt';

export async function compare_password(user_passw: User["password"], password: string){
    const isMatch = await bcrypt.compare(password, user_passw);
    return isMatch;
}
