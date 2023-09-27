import { HttpRequest, HttpInterceptor, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, throwError } from "rxjs";
import { mergeMap, catchError } from "rxjs/operators";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  reqUrl: String;
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.reqUrl = req.url;
    return next.handle(req).pipe(
      catchError( res => {
        return this.handleData(res, this.reqUrl);
      })
    );
  }

  private handleData(event: HttpResponse<any> | HttpErrorResponse , reqUrl: String): Observable<any> {
        // 业务处理：一些通用操作
        switch (event.status) {
          case 200:
            break;
          case 401: // 未登录状态码
            if ( reqUrl.match(/api\/status/) && event.url.match(/api\/unauth/) ) {
              return throwError('cancelled');
            }
            // tslint:disable-next-line:max-line-length
            window['location']['href'] = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port : '') + '/login';
            return throwError('cancelled');
          case 404:
          case 500:
          default:
          return throwError(event);
      }
  }
}
