import { Socket } from "socket.io";
import WeightData from "../types/WeightData";


export default function small_weight_changed_handler(socket: Socket, room_id: string, data: WeightData) {
  if (data.value == null) {
    socket.emit("server:small-weight-error", "Invalid weight data format. Missing value field.");
    socket.disconnect();
    return;
  }
  if (!data.unit) {
    socket.emit("server:small-weight-error", "Invalid weight data format. Missing unit field.");
    socket.disconnect();
    return;
  }
  socket.to(room_id).emit("raspberry:small-weight-changed", data);
  return;
}
