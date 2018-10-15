import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../service/user.service';
import { User } from '../../../entity/User';
import { CategoryService } from '../../../service/category.service';

@Component({
    selector: 'qy-user-list',
    templateUrl: './user-list.component.html',
    styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
data = [
  ];
  userData: User[] = new Array<User>();
    constructor(private categoryService: CategoryService) {
    }

    ngOnInit() {
      this.loadData();
    }
    loadData() {
      this.categoryService.findAll().subscribe(
        data => {
          console.log(data);
          this.userData = data['content'];
        },
        error => {
          console.log(error);
        }
      );
    }


}
