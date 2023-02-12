export class OrderItem {
  constructor(public userId: number,
              public productId: number,
              public quantity: number,
              public id?: number,
              public orderId?: number) {
  }
}
