import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Auth} from "../model/auth";

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) {
  }

  public get(username: string, password: string): Observable<Auth> {
    return this.http.post<Auth>('/api/login', {
      username: username,
      password: password
    });
  }
}
