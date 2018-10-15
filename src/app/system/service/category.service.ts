import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../core/entity/page';
import { Injectable } from '@angular/core';
import { FormDataUtil } from '../../core/utils/form-data-util';
import { Category } from '../entity/Category';

@Injectable()
export class CategoryService {

    url: string  = 'api/category';
    constructor(private http: HttpClient) { }

    findAll( params?: any): Observable<Category[]> {
        return this.http.get<Category[]>(`${this.url}`, {params: params});
    }

    created(category: Category, options?: {}): Observable<Category>  {
        return this.http.put<Category>(`${this.url}`, category, options);
    }
    // checkcategory(categoryname) {
    //     return this.http.get(`${this.url}/checkcategory`, {responseType: 'text', params: {"categoryname": categoryname}});
    // }

    getOne(categoryId: number) {
        return this.http.get<Category>(`${this.url}/${categoryId}`);
    }

    update(category: Category, options?: {}) {
        let params = new HttpParams();
        // params.append();
        if (!options) {
            options = {};
        }
        let formData = FormDataUtil.covert(category);
        // options["params"] = category;
        return this.http.post(`${this.url}/${category.id}`, formData);
    }

    // sayHello(name): Observable<string>  {
    //     return this.http.get(`${this.url}/sayHello`, {responseType: 'text', params: {"name": name}});
    // }
}
