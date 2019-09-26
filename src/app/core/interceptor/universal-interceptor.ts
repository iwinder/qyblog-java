import {Injectable, Inject, Optional} from '@angular/core';
import {HttpInterceptor, HttpHandler, HttpRequest, HttpHeaders} from '@angular/common/http';
import {Request} from 'express';
import {REQUEST} from '@nguniversal/express-engine/tokens';
 
@Injectable()
export class UniversalInterceptor implements HttpInterceptor {
 
  constructor(@Optional() @Inject(REQUEST) protected request: Request) {}
 
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let serverReq: HttpRequest<any> = req;
    if (this.request) {
      console.log("this.request", req.url);
      let newUrl = `${this.request.protocol}://${this.request.get('host')}`;
      if (!req.url.startsWith('/')) {
        newUrl += '/';
      }
      newUrl += req.url;
      // if (req.url.includes("/api/")){
      //   newUrl = "http://localhost:8000/"+req.url;
      // }else {
      //   newUrl += req.url;
      // }
      // console.log("this.newUrl 0 0", newUrl);
      serverReq = req.clone({url: newUrl});
    }
    return next.handle(serverReq);
  }
}