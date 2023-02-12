import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../model/product";
import {User} from "../model/user";
import {AppComponent} from "../app.component";

@Injectable()
export class ProductService {
  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Product[]> {
    const user: User = AppComponent.getUser();
    return this.http.get<Product[]>(`/api/products`, {
      params: {
        username: user.username,
        token: user.token
      }
    });
  }

  public create(product: Product): Observable<Product> {
    const user: User = AppComponent.getUser();
    return this.http.post<Product>(`/api/products`, product, {
      params: {
        username: user.username,
        token: user.token
      }
    });
  }

  public delete(id: number): Observable<void> {
    const user = AppComponent.getUser();
    return this.http.delete<void>(`/api/products/${id}`, {
      params: {
        username: user.username,
        token: user.token
      }
    });
  }

  update(product: Product): Observable<Product> {
    const user: User = AppComponent.getUser();
    return this.http.put<Product>(`/api/products`, product, {
      params: {
        username: user.username,
        token: user.token
      }
    });
  }
}
