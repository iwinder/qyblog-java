import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../service/user.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { User } from '../../../entity/user';

@Component({
    selector: 'qy-user-edit',
    templateUrl: './user-edit.component.html',
    styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

    user: User;
    constructor(private userService: UserService,
        private router: Router,
        private route: ActivatedRoute) {

    }

    ngOnInit() {
        this.route.params.subscribe(
            (params: Params) => {
                let userId = +params['userId'];
                this.loadData(userId);
            }
        );
    }
    loadData(userId) {
        console.log("this.userId ", userId );
        this.userService.getOne(userId).subscribe(
            user => this.user = user
          );
    }

    save(event) {
        this.userService.update(event.value).subscribe(
            data => {
                console.log('更新成功');
                // this.toList();
            },
            err => {
                console.error(err);
            }
        );
    }

    toList() {
        this.router.navigate(['../../'], { relativeTo: this.route });
    }
}
