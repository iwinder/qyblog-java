import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../entity/User';
import { Page } from '../../core/entity/page';
import { Injectable } from '@angular/core';
import { tap, catchError } from 'rxjs/operators';
import { HttpUtils } from '../../core/AuthGuard/http-utils';

@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    findAll( options?: {}): Observable<Page<User>> {
        return this.http.get<Page<User>>(`api/users`, options);
    }

    created(user: User, options?: {}): Observable<User>  {
        return this.http.put<User>(`api/users`, user, options);
    }

    sayHello(name): Observable<string>  {
           let param = new HttpParams();
           param.append("name", name);


         let parameter = {
            responseType: 'text'
        };
        return this.http.get(`api/test/sayHello`, {responseType: 'text', params: {"name": name}});
    }
}
