export class Order {
  constructor(public userId: number,
              public total: number,
              public status: boolean,
              public id?: number) {
  }
}
