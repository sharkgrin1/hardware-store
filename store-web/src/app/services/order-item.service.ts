import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {OrderItem} from "../model/order-item";
import {AppComponent} from "../app.component";
import {OrderItemDisplay} from "../model/order-item-display";

@Injectable()
export class OrderItemService {
  constructor(private http: HttpClient) {
  }

  public findUnpaidByUserId(): Observable<OrderItemDisplay[]> {
    const user = AppComponent.getUser();
    return this.http.get<OrderItemDisplay[]>('/api/items', {
      params: {
        username: user.username,
        token: user.token,
        userId: user.id.toString()
      }
    });
  }

  public findPaidByProductIds(productIds:number[]): Observable<OrderItem[]> {
    const user = AppComponent.getUser();
    return this.http.get<OrderItem[]>('/api/items/paid', {
      params: {
        username: user.username,
        token: user.token,
        productIds: productIds.toString()
      }
    });
  }

  public createItem(item: OrderItem): Observable<void> {
    const user = AppComponent.getUser();
    return this.http.post<void>('/api/items', item, {
      params: {
        username: user.username,
        token: user.token
      }
    });
  }

  public deleteItem(id: number): Observable<void> {
    const user = AppComponent.getUser();
    return this.http.delete<void>(`/api/items/${id}`, {
      params: {
        username: user.username,
        token: user.token
      }
    });
  }
}
