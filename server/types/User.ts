import Device from "./Device";

export default interface User {
  email: string,
  password: string,
  name: string,
  devices?: Device[]
}
