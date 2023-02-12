import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../model/product";
import {User} from "../model/user";

@Injectable()
export class ProductService {
  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Product[]> {
    const user: User = JSON.parse(localStorage.getItem('user'));
    return this.http.get<Product[]>(`/api/products`, {
      params: {
        username: user.username,
        token: user.token
      }
    });
  }
}
