import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../core/entity/page';
import { Injectable } from '@angular/core';
import { SysCommentSetting } from '../entity/sys-comment-setting';
import { FormDataUtil } from '../../core/utils/form-data-util';

@Injectable()
export class SysCommentSettingService {
    url: string  = '/api/comments/sysCommentSetting';
    constructor(private http: HttpClient) { }

    findAll( params?): Observable<Page<SysCommentSetting>> {
        return this.http.get<Page<SysCommentSetting>>(`${this.url}`, {params: params});
    }

    created(setting: SysCommentSetting, options?: {}): Observable<SysCommentSetting>  {
        return this.http.put<SysCommentSetting>(`${this.url}`, setting, options);
    }
    update(setting: SysCommentSetting, options?: {}) {
        let formData = FormDataUtil.covert(setting);
        return this.http.post(`${this.url}/${setting.id}`, formData);
    }

    updateStatus(setting: SysCommentSetting, options?: {}) {
        let formData = FormDataUtil.covert(setting);
        return this.http.post(`${this.url}/${setting.id}/updateStatus`, formData);
    }

    getOne(settingId: number): Observable<SysCommentSetting>  {
        return this.http.get<SysCommentSetting>(`${this.url}/${settingId}`);
    }
}
