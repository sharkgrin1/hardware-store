import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ShoppingCartRoutingModule} from "./shopping-cart-routing.module";
import {FormsModule} from "@angular/forms";
import {OrderItemService} from "../services/order-item.service";
import {ShoppingCartComponent} from "./shopping-cart.component";
import {OrderService} from "../services/order.service";

@NgModule({
  declarations: [ShoppingCartComponent],
    imports: [
        CommonModule,
        ShoppingCartRoutingModule,
        FormsModule,
    ],
  providers: [OrderItemService, OrderService]
})
export class ShoppingCartModule {

}
