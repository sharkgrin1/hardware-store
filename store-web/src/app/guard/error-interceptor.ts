import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {Router} from "@angular/router";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(err => {
      if ([401, 403].includes(err.status) && localStorage.getItem('user')) {
        localStorage.removeItem('user');
        this.router.navigate(['/login']);
      }

      const error = err.error?.message || err.statusText;
      console.error(err);
      return throwError(() => error);
    }))
  }

}
