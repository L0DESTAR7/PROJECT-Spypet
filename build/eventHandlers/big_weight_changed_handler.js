"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
function big_weight_changed_handler(socket, room_id, data) {
    if (data.value == null) {
        socket.emit("server:big-weight-error", "Invalid weight data format. Missing value field.");
        socket.disconnect();
        return;
    }
    if (!data.unit) {
        socket.emit("server:big-weight-error", "Invalid weight data format. Missing unit field.");
        socket.disconnect();
        return;
    }
    socket.to(room_id).emit("raspberry:big-weight-changed", data);
    return;
}
exports.default = big_weight_changed_handler;
