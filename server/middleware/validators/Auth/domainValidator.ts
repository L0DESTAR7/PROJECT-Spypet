import {promises, MxRecord} from 'dns';

export async function resolveMx(domain: string) : Promise<MxRecord []>{
     try{
        return await promises.resolveMx(domain);
     }
     catch (error) {
        console.error(error);
        return [];
     }
}

