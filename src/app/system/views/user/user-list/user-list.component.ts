import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../service/user-service';
import { User } from '../../../entity/User';

@Component({
    selector: 'qy-user-list',
    templateUrl: './user-list.component.html',
    styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
data = [
    {
      key    : '1',
      name   : 'John Brown',
      age    : 32,
      address: 'New York No. 1 Lake Park',
    }, {
      key    : '2',
      name   : 'Jim Green',
      age    : 42,
      address: 'London No. 1 Lake Park',
    }, {
      key    : '3',
      name   : 'Joe Black',
      age    : 32,
      address: 'Sidney No. 1 Lake Park',
    }
  ];
  userData: User[];
    constructor(private userService: UserService) {
    }

    ngOnInit() {
      this.loadData();
    }
    loadData() {
      this.userService.findAll().subscribe(
        data => {
          console.log(data);
          this.userData = data['content'];
        },
        error => {
          console.log(error);
        }
      );
    }

    sayHello() {
      this.userService.sayHello("admin").subscribe(
        data => {
          console.log(data);
        },
        error => {
          console.log(error);
        }
      );
    }
}
