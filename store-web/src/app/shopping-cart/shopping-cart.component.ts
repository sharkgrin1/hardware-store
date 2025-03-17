import {Component, OnInit} from '@angular/core';
import {OrderItemService} from "../services/order-item.service";
import {OrderItemDisplay} from "../model/order-item-display";
import {Order} from "../model/order";
import {AppComponent} from "../app.component";
import {OrderService} from "../services/order.service";

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css'],
  standalone: false
})
export class ShoppingCartComponent implements OnInit {

  items: OrderItemDisplay[] = [];

  constructor(private orderItemService: OrderItemService, private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.orderItemService.findUnpaidByUserId().subscribe({
      next: (value) => this.items = value,
      error: (error) => console.error(error),
    });
  }

  delete(id: number): void {
    this.orderItemService.deleteItem(id).subscribe({
      next: () =>
        this.items.splice(this.items.findIndex(x => x.id === id), 1),
      error: (error) => console.error(error)
    });
  }

  countOrder(): number {
    return this.items.map(x => x.total).reduce((sum, val) => sum + val, 0);
  }

  makeOrder(): void {
    const order = new Order(
      AppComponent.getUser().id,
      this.countOrder(),
      false
    );
    const itemIds = this.items.map(x => x.id);
    this.orderService.create(order, itemIds).subscribe({
      next: () => this.items = [],
      error: (error) => console.error(error)
    });
  }
}
