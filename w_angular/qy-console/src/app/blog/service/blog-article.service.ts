import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../core/entity/page';
import { Injectable } from '@angular/core';
import { BlogArticle } from '../entity/blog-article';
import { FormDataUtil } from '../../core/utils/form-data-util';

@Injectable()
export class BlogArticleService {
    url: string  = '/api/articles';
    constructor(private http: HttpClient) { }

    findAll( params?): Observable<Page<BlogArticle>> {
        return this.http.get<Page<BlogArticle>>(`${this.url}`, {params: params});
    }

    created(aticle: BlogArticle, options?: {}): Observable<BlogArticle>  {
        return this.http.put<BlogArticle>(`${this.url}`, aticle, options);
    }
    update(aticle: BlogArticle, options?: {}) {
        let formData = FormDataUtil.covert(aticle);
        return this.http.post(`${this.url}/${aticle.id}`, formData);
    }

    getOne(aticleId: number): Observable<BlogArticle>  {
        return this.http.get<BlogArticle>(`${this.url}/${aticleId}`);
    }
}
