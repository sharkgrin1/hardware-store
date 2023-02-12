export class Product {
  public bought?:boolean;
  constructor(readonly id: number,
              public name: string,
              public price: number,
              public quantity: number) {
  }
}
