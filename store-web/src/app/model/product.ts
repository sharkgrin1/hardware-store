export class Product {
  public bought?: boolean;
  public paid?:boolean;

  constructor(readonly id: number,
              public name: string,
              public price: number,
              public quantity: number,
              public hidden: boolean) {
  }
}
