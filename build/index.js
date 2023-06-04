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
const express_1 = __importDefault(require("express"));
const body_parser_1 = __importDefault(require("body-parser"));
const orders_1 = __importDefault(require("./routes/orders"));
const registerDevice_1 = __importDefault(require("./routes/registerDevice"));
const media_1 = __importDefault(require("./routes/media"));
const devices_1 = __importDefault(require("./routes/devices"));
const socket_io_1 = require("socket.io");
const http_1 = require("http");
const auth_routes_1 = __importDefault(require("./routes/JWT_Auth/auth_routes"));
const passport_1 = __importDefault(require("passport"));
const passport_2 = __importDefault(require("./middleware/hooks/auth_hooks/passport"));
const connection_handler_1 = __importDefault(require("./eventHandlers/connection_handler"));
const small_weight_changed_handler_1 = __importDefault(require("./eventHandlers/small_weight_changed_handler"));
const big_weight_changed_handler_1 = __importDefault(require("./eventHandlers/big_weight_changed_handler"));
const app = (0, express_1.default)();
const httpServer = (0, http_1.createServer)(app);
const io = new socket_io_1.Server(httpServer, {});
app.use(body_parser_1.default.json());
app.use(passport_1.default.initialize());
(0, passport_2.default)(passport_1.default);
app.get('/', (req, res) => {
    res.send("SPIN");
});
app.use('/orders', passport_1.default.authenticate('jwt', { session: false }), orders_1.default);
app.use('/registerDevice', passport_1.default.authenticate('jwt', { session: false }), registerDevice_1.default);
app.use('/media', passport_1.default.authenticate('jwt', { session: false }), media_1.default);
app.use('/devices', passport_1.default.authenticate('jwt', { session: false }), devices_1.default);
app.use(auth_routes_1.default);
io.on("connection", (socket) => __awaiter(void 0, void 0, void 0, function* () {
    const room_id = yield (0, connection_handler_1.default)(socket);
    console.log("HAMID 3");
    socket.on("raspberry:small-weight-changed", (data) => {
        console.log("Hamid");
        if (!room_id) {
            socket.disconnect();
        }
        (0, small_weight_changed_handler_1.default)(socket, room_id, data);
    });
    // Code duplication !!! Should be improved ASAP.
    socket.on("raspberry:big-weight-changed", (data) => {
        console.log("Hamid");
        if (!room_id) {
            socket.disconnect();
        }
        (0, big_weight_changed_handler_1.default)(socket, room_id, data);
    });
}));
/*
app.get("/device", (req, res) => {
  res.setHeader("Content-Type", "text/event-stream");
  res.write("Connected: " + "hamid\n\n");
  send(res);
})

var i: number = 0;
function send(res: Response) {
  res.write(`Hello hamid ${i++}.`);
  setTimeout(() => send(res), 1000);
}
*/
httpServer.listen(8080, () => {
    console.log('Server is listening on port 8080');
});
exports.default = io;
