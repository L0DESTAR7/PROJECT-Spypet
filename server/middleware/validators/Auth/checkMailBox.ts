import net from 'net';

export type typecheckMail = {
    connected : boolean,
    mailValid : boolean
}


export async function checkMailBox(smtpHost: string, email: string) : Promise<typecheckMail> {
    return new Promise((resolve, reject)=>{
        
        const socket_to_smtp =  net.createConnection(25, smtpHost);
        let [current_state, rightCode, next_state, command] = ["Check_Connection", '', '', ''];
        let result = {connected : false, mailValid : false};

        socket_to_smtp.on('data',(data: Buffer)=>{
            const response = data.toString('utf-8');
            console.log('---> ' + response);
            
            switch(current_state){
                case "Check_Connection": {
                    rightCode = '220';
                    next_state = "Send EHLO";
                    command = "EHLO mail.example.org\r\n"

                    if(!(response.startsWith(rightCode))){
                      socket_to_smtp.end();
                      return resolve(result);
                    }

                    socket_to_smtp.write(command,()=>{
                        console.log("---> " + command)
                       current_state = next_state;
                    });
                }

                break;
                case "Send EHLO" : {
                    rightCode = '250';
                    next_state = "Check Mail Box From";
                    command = "MAIL FROM:<name@example.org>\r\n"

                    result.connected = true;

                    if(!(response.startsWith(rightCode))){
                        socket_to_smtp.end();
                        return resolve(result);
                    };

                    socket_to_smtp.write(command,()=>{
                        console.log("---> " + command)
                        current_state = next_state;
                     });
                    }
                break;
                case "Check Mail Box From" : {
                    rightCode = '250';
                    next_state = "RECEPIENT Sent to";
                    command = `RCPT TO:<${email}>\r\n>`

                    if(!(response.startsWith(rightCode))){
                        socket_to_smtp.end();
                        return resolve(result);
                    };


                    socket_to_smtp.write(command,()=>{
                        console.log("---> " + command)
                        current_state = next_state;
                     });
                    }
                break;
                case "RECEPIENT Sent to": {
                    rightCode = '250';
                    command = 'QUIT\r\n>'

                    if(!(response.startsWith(rightCode))){
                        console.log("---> " + command)
                        socket_to_smtp.end();
                        return resolve(result);
                    };

                    result.mailValid = true;

                    socket_to_smtp.write(command,()=>{
                        socket_to_smtp.end();
                        return resolve(result);
                     });
                break;
                    }
                }
                },
        )
                socket_to_smtp.on('error',(err: Error)=>{
                    console.error(err);
                    reject(err);
                })
                socket_to_smtp.on('connect',()=>{
                    console.log("connected to :" + smtpHost);
                })

            }
    )
        }

