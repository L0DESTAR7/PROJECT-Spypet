"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.checkMailBox = void 0;
const net_1 = __importDefault(require("net"));
function checkMailBox(smtpHost, email) {
    return __awaiter(this, void 0, void 0, function* () {
        return new Promise((resolve, reject) => {
            const socket_to_smtp = net_1.default.createConnection(25, smtpHost);
            let [current_state, rightCode, next_state, command] = ["Check_Connection", '', '', ''];
            let result = { connected: false, mailValid: false };
            socket_to_smtp.on('data', (data) => {
                const response = data.toString('utf-8');
                console.log('---> ' + response);
                switch (current_state) {
                    case "Check_Connection":
                        {
                            rightCode = '220';
                            next_state = "Send EHLO";
                            command = "EHLO mail.example.org\r\n";
                            if (!(response.startsWith(rightCode))) {
                                socket_to_smtp.end();
                                return resolve(result);
                            }
                            socket_to_smtp.write(command, () => {
                                console.log("---> " + command);
                                current_state = next_state;
                            });
                        }
                        break;
                    case "Send EHLO":
                        {
                            rightCode = '250';
                            next_state = "Check Mail Box From";
                            command = "MAIL FROM:<name@example.org>\r\n";
                            result.connected = true;
                            if (!(response.startsWith(rightCode))) {
                                socket_to_smtp.end();
                                return resolve(result);
                            }
                            ;
                            socket_to_smtp.write(command, () => {
                                console.log("---> " + command);
                                current_state = next_state;
                            });
                        }
                        break;
                    case "Check Mail Box From":
                        {
                            rightCode = '250';
                            next_state = "RECEPIENT Sent to";
                            command = `RCPT TO:<${email}>\r\n>`;
                            if (!(response.startsWith(rightCode))) {
                                socket_to_smtp.end();
                                return resolve(result);
                            }
                            ;
                            socket_to_smtp.write(command, () => {
                                console.log("---> " + command);
                                current_state = next_state;
                            });
                        }
                        break;
                    case "RECEPIENT Sent to": {
                        rightCode = '250';
                        command = 'QUIT\r\n>';
                        if (!(response.startsWith(rightCode))) {
                            console.log("---> " + command);
                            socket_to_smtp.end();
                            return resolve(result);
                        }
                        ;
                        result.mailValid = true;
                        socket_to_smtp.write(command, () => {
                            socket_to_smtp.end();
                            return resolve(result);
                        });
                        break;
                    }
                }
            });
            socket_to_smtp.on('error', (err) => {
                console.error(err);
                reject(err);
            });
            socket_to_smtp.on('connect', () => {
                console.log("connected to :" + smtpHost);
            });
        });
    });
}
exports.checkMailBox = checkMailBox;
