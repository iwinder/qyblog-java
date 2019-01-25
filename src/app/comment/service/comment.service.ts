import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../core/entity/page';
import { Injectable } from '@angular/core';
import { Comment } from '../entity/comment';
import { FormDataUtil } from '../../core/utils/form-data-util';

@Injectable()
export class CommentService {
    url: string  = '/api/comments';
    constructor(private http: HttpClient) { }

    findAll( params?): Observable<Page<Comment>> {
        return this.http.get<Page<Comment>>(`${this.url}`, {params: params});
    }

    created(comment: Comment, options?: {}): Observable<Comment>  {
        return this.http.put<Comment>(`${this.url}`, comment, options);
    }
    update(comment: Comment, options?: {}) {
        let formData = FormDataUtil.covert(comment);
        return this.http.post(`${this.url}/${comment.id}`, formData);
    }

    updateStatus(params: any, options?: {}) {
        let formData = FormDataUtil.covert(params);
        return this.http.post(`${this.url}/${params.id}/updateStatus`, formData);
    }

    getOne(commentId: number): Observable<Comment>  {
        return this.http.get<Comment>(`${this.url}/${commentId}`);
    }
}
