import { HttpRequest, HttpInterceptor, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, throwError } from "rxjs";
import { mergeMap, catchError } from "rxjs/operators";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError(this.handleData)
    );
  }

  private handleData(event: HttpResponse<any> | HttpErrorResponse): Observable<any> {
        // 业务处理：一些通用操作
        switch (event.status) {
          case 200:
            break;
          case 401: // 未登录状态码
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
