import { HttpRequest, HttpInterceptor, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

// @Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const idToken = this.getToken();
    console.log("intercept");
    if (idToken) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', idToken)
      });

      return next.handle(cloned);
    } else {
      return next.handle(req);
    }
  }

  getToken(): string {
    const userStr = localStorage.getItem('currentUser');
    return userStr ? JSON.parse(userStr).token : '';
  }
}
