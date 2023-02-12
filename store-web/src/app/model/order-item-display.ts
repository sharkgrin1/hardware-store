export class OrderItemDisplay {
  constructor(public id: number,
              public userId: number,
              public productName: string,
              public total: number,
              public quantity: number) {
  }
}
