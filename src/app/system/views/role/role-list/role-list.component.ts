import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'qy-role-list',
    templateUrl: './role-list.component.html',
    styleUrls: ['./role-list.component.scss']
})
export class RoleListComponent implements OnInit {
data = [
    {
      key    : '1',
      name   : '超级管理员',
      age    : 32,
      address: 'New York No. 1 Lake Park',
    }, {
      key    : '2',
      name:  '管理员',
      age    : 42,
      address: 'London No. 1 Lake Park',
    }, {
      key    : '3',
      name   : '用户',
      age    : 32,
      address: 'Sidney No. 1 Lake Park',
    }
  ];
    constructor() {
    }

    ngOnInit() {
    }
}
