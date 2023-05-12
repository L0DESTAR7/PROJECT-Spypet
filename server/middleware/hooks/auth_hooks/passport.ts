import { Strategy, ExtractJwt } from 'passport-jwt';
import { PrismaClient} from '@prisma/client';
import secret from '../../../routes/JWT_Auth/config/config';

const prisma = new PrismaClient();

interface JwtPayload {
  id: string;
}

export default function pass (passport: any) {
  const opts = {} as any;
  opts.secretOrKey = secret;
  opts.jwtFromRequest = ExtractJwt.fromAuthHeaderAsBearerToken();

  passport.use(new Strategy(opts, async function (jwt_payload: JwtPayload, done: any) {
    try {
      const user = await prisma.user.findUnique({
        where: {
          id: jwt_payload.id
        }
      });
      if (user) {
        return done(null, user);
      } else {
        return done(null, false);
      }
    } catch (err) {
      return done(err, false);
    }
  }));
}
