import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Order} from "../model/order";
import {User} from "../model/user";
import {AppComponent} from "../app.component";

@Injectable()
export class OrderService {
  constructor(private http: HttpClient) {
  }

  public create(order: Order, itemIds: number[]): Observable<void> {
    const user: User = AppComponent.getUser();
    return this.http.post<void>('/api/orders', order, {
      params: {
        username: user.username,
        token: user.token,
        items: itemIds.toString()
      }
    });
  }
}
