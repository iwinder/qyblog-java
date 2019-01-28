import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../core/entity/page';
import { Injectable } from '@angular/core';
import { FormDataUtil } from '../../core/utils/form-data-util';
import { Comment } from '../entity/comment';

@Injectable()
export class CommetsService {
    url: string  = '/api/web/comments';
    constructor(private http: HttpClient) { }

    findAll( params?): Observable<Page<Comment>> {
        return this.http.get<Page<Comment>>(`${this.url}/${params.agentTargetId}`, {params: params});
    }

    findAllReplies( params?): Observable<Page<Comment>> {
        return this.http.get<Page<Comment>>(`${this.url}/${params.agentTargetId}/${params.parentId}/replies`, {params: params});
    }

    created(comment: Comment, options?: {}): Observable<Comment>  {
        let formData = FormDataUtil.covert(comment);
        return this.http.post<Comment>(`${this.url}/${comment.agentTargetId}/add`, formData);
    }
    createdReply(comment: Comment, options?: {}) {
        let formData = FormDataUtil.covert(comment);
        return this.http.post(`${this.url}/${comment.agentTargetId}//${comment.parentId}/replies/add`, formData);
    }

    getOne(aticleId: number): Observable<Comment>  {
        return this.http.get<Comment>(`${this.url}/${aticleId}`);
    }
}
