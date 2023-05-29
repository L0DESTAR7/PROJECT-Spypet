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
Object.defineProperty(exports, "__esModule", { value: true });
exports.emailDomainChecker = void 0;
const domainValidator_1 = require("./domainValidator");
const checkMailBox_1 = require("./checkMailBox");
const emailDomainChecker = (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    const [email, domain] = [req.body.email, req.body.email.split('@')[1]];
    const sortedMxRecords = (yield (0, domainValidator_1.resolveMx)(domain)).sort((a, b) => a.priority - b.priority);
    let hostIndex = 0;
    let smtpResult = { connected: false, mailValid: false };
    while (hostIndex < sortedMxRecords.length) {
        try {
            smtpResult = yield (0, checkMailBox_1.checkMailBox)(sortedMxRecords[hostIndex].exchange, email);
            if (!smtpResult.mailValid) {
                hostIndex++;
            }
            else {
                break;
            }
        }
        catch (error) {
            console.error(error);
        }
    }
    if (!smtpResult.mailValid) {
        res.status(400).json({ success: false, msg: "Not A valid mailbox" });
    }
    else {
        next();
    }
});
exports.emailDomainChecker = emailDomainChecker;
