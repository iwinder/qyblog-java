import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../core/entity/page';
import { Injectable } from '@angular/core';
import { BlogArticle } from '../entity/blog-article';

@Injectable()
export class BlogArticleService {
    url: string  = 'api/articles';
    constructor(private http: HttpClient) { }

    findAll( options?: {}): Observable<Page<BlogArticle>> {
        return this.http.get<Page<BlogArticle>>(`${this.url}`, options);
    }

    created(aticle: BlogArticle, options?: {}): Observable<BlogArticle>  {
        return this.http.put<BlogArticle>(`${this.url}`, aticle, options);
    }
    update(aticle: BlogArticle, options?: {}) {
        return this.http.post(`${this.url}/${aticle.id}`, aticle, options);
    }

    getOne(aticleId: number): Observable<BlogArticle>  {
        return this.http.get<BlogArticle>(`${this.url}/${aticleId}`);
    }
}
