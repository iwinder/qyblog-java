import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../entity/User';
import { Page } from '../../core/entity/page';
import { Injectable } from '@angular/core';
import { FormDataUtil } from '../../core/utils/form-data-util';

@Injectable()
export class UserService {

    url: string  = '/api/users';
    constructor(private http: HttpClient) { }

    findAll( options?: {}): Observable<Page<User>> {
        return this.http.get<Page<User>>(`${this.url}`, options);
    }

    created(user: User, options?: {}): Observable<User>  {
        return this.http.put<User>(`${this.url}`, user, options);
    }
    checkUser(username) {
        return this.http.get(`${this.url}/checkUser`, {responseType: 'text', params: {"username": username}});
    }

    getOne(userId: number) {
        return this.http.get<User>(`${this.url}/${userId}`);
    }

    update(user: User, options?: {}) {
        let params = new HttpParams();
        // params.append();
        if (!options) {
            options = {};
        }
        let formData = FormDataUtil.covert(user);
        // options["params"] = user;
        return this.http.post(`${this.url}/${user.id}`, formData);
    }

    sayHello(name): Observable<string>  {
        return this.http.get(`${this.url}/sayHello`, {responseType: 'text', params: {"name": name}});
    }
}
