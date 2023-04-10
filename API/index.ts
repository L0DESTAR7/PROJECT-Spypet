import { initTRPC } from '@trpc/server';

const t = initTRPC.create();


const router = t.router;
const publicProcedute = t.procedure;

const appRouter = router({}); // CONTINUE CODE
