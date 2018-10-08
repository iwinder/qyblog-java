import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../entity/User';
import { Page } from '../../core/entity/page';
import { Injectable } from '@angular/core';

@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    findAll( options?: {}): Observable<Page<User>> {
        return this.http.get<Page<User>>(`api/users`, options);
    }

    created(user: User, options?: {}): Observable<User>  {
        return this.http.put<User>(`api/users`, user, options);
    }
    checkUser(username) {
        return this.http.get(`api/users/checkUser`, {responseType: 'text', params: {"username": username}});
    }

    sayHello(name): Observable<string>  {
        return this.http.get(`api/test/sayHello`, {responseType: 'text', params: {"name": name}});
    }
}
