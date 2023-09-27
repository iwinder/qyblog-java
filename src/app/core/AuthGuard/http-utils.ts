import { Http, Response, ConnectionBackend, Request, RequestOptionsArgs } from '@angular/http';

import { Injectable } from '@angular/core';
import { Router, } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';


export class HttpUtils {
    public static extractData(res: Response) {
        let body: any = {};
        try {
            body = res.json();
        } catch (e) {
        }

        return body.data || body || {};
    }

    public static handleError(error: HttpErrorResponse | any) {
        // In a real world app, you might use a remote logging infrastructure
        let errMsg: string;
        if (error instanceof HttpResponse) {
            let body: any = '';
            try {
                body = error.body;
            } catch (e) {
                body = error.statusText || '';
            }
            let err = body.message || body.error || JSON.stringify(body);
            if (!err || err === 'No message available') {
                err = body.error || JSON.stringify(body);
                errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
            } else {
                errMsg = body.message;
            }
        } else {
            let errMsgOne = error.error ? error.error.message : error.message;
            errMsg = errMsgOne  ? errMsgOne : JSON.stringify(error);
        }
         console.log("handleError", errMsg);
        return throwError(errMsg);
    }
}
