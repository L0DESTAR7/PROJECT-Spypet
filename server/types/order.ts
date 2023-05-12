
export default interface Order {
  id: number,
  type: String,
  params: String,
  createdAt: Date,
  updatedAt: Date,
  programmedAt: Date,
  periodicity: String,
  forwarded: boolean,
  author_id: number,
  device_id: number,
}
