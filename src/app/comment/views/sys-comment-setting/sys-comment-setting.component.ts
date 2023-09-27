import { Component, OnInit } from '@angular/core';
import { SysCommentSetting } from '../../entity/sys-comment-setting';
import { SysCommentSettingService } from '../../service/sys-comment-setting.service';
import { Page } from '../../../core/entity/page';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
    selector: 'qy-sys-comment-setting',
    templateUrl: './sys-comment-setting.component.html',
    styleUrls: ['./sys-comment-setting.component.scss']
})
export class SysCommentSettingComponent implements OnInit {

  sysCommentsData: SysCommentSetting;
  searchList: any;
  constructor(private fb: FormBuilder,
    private settingService: SysCommentSettingService) {
  }
  validateForm: FormGroup;
  pageIndex = 1;
  loading = false;
  ngOnInit() {
    let obj = this.sysCommentsData || new SysCommentSetting();
    this.validateForm = this.fb.group({
      isEnabled: [ obj.isEnabled || true, []],
      isApproved: [ obj.isApproved || true, []]
    });
    this.loadData();
  }

  loadData() {
    this.loading = true;
    this.settingService.getOne(1).subscribe(
      data => {
        this.loading = false;
        console.log(data);
        this.sysCommentsData = data;
        this.validateForm.setValue({
          isEnabled: data.isEnabled,
          isApproved: true
        });
      },
      error => {
        console.log(error);
        this.loading = false;
      }
    );
  }

  submitForm($event, value) {
    if (this.sysCommentsData && this.sysCommentsData.id) {
      value.id = this.sysCommentsData.id;
    }
    let that = this;
    this.settingService.update(value).subscribe(
        data => {
            console.log('更新成功');
            that.loadData();
        },
        err => {
            console.error(err);
        }
    );
}


}
