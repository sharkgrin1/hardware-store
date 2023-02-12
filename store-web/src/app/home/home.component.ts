import {Component, OnInit} from '@angular/core';
import {ProductService} from "../services/product.service";
import {Product} from "../model/product";
import {FormBuilder, Validators} from "@angular/forms";
import {User} from "../model/user";
import {BehaviorSubject} from "rxjs";
import {OrderItem} from "../model/order-item";
import {OrderItemService} from "../services/order-item.service";
import {AppComponent} from "../app.component";
import {Role} from "../model/role";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  products: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);
  user: User;
  form = this.fb.group({
    id: [null],
    name: [null, [Validators.required]],
    price: [null, [Validators.required, Validators.min(0.01)]],
    quantity: [null, [Validators.required, Validators.min(0)]],
    hidden: [false],
    paid: [null]
  });

  constructor(private productService: ProductService, private orderItemService: OrderItemService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user'));

    this.productService.findAll().subscribe(value => {
        if (this.user.role === Role.ROLE_CUSTOMER) {
          value = value.filter(x => !x.hidden);
        }
        this.products.next(value);
      },
      error => console.error(error),
      () => {
        const productArray = this.products.value;
        if (this.user.role == Role.ROLE_ADMIN) {
          const ids = productArray.map(x => x.id);
          this.orderItemService.findPaidByProductIds(ids).subscribe(value => {
            value.forEach(x => {
              const product = productArray.find(pr => pr.id === x.productId);
              product.paid = true;
            });
          });
          return;
        }
        this.orderItemService.findUnpaidByUserId().subscribe(value => {
            productArray.forEach(x => {
              const item = value.find(item => item.productName === x.name);
              x.bought = !!item;
            })
          },
          error => console.error(error))
      });
  }

  onSubmit(): void {
    const product = this.getProduct();
    this.productService.create(product).subscribe(value => {
        this.products.value.push(value);
        this.form.reset();
      },
      error => console.error(error)
    );
  }

  delete(productId: number) {
    this.productService.delete(productId).subscribe(value => {
        const productArray = this.products.value;
        let index = productArray.findIndex(x => x.id == productId);
        productArray.splice(index, 1);
        delete productArray[index];
        this.products.next(productArray);
      },
      error => console.error(error)
    );
  }

  onSubmitModify(): void {
    const product = this.getProduct();
    this.productService.update(product).subscribe(value => {
        const productArray = this.products.value;
        let index = productArray.findIndex(x => x.id == product.id);
        productArray[index] = value;
        this.form.reset();
      },
      error => console.error(error)
    );
  }

  private getProduct() {
    return new Product(
      this.form.get('id').value,
      this.form.get('name').value,
      this.form.get('price').value,
      this.form.get('quantity').value,
      this.form.get('hidden').value
    );
  }

  setForm(product: Product): void {
    this.form.reset(JSON.parse(JSON.stringify(product)));
  }

  closeModify() {
    this.form.reset();
  }

  buyItem(value: string, product: Product): void {
    const user = AppComponent.getUser();
    const item = new OrderItem(
      user.id,
      product.id,
      Number.parseInt(value)
    );
    this.orderItemService.createItem(item).subscribe(value => {
        product.bought = true;
        product.quantity = product.quantity - item.quantity;
      },
      error => console.error(error)
    );
  }
}
