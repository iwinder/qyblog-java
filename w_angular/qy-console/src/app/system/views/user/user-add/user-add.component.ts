import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../service/user.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    selector: 'qy-user-add',
    templateUrl: './user-add.component.html',
    styleUrls: ['./user-add.component.scss']
})
export class UserAddComponent implements OnInit {

    constructor(private userService: UserService,
        private router: Router,
        private route: ActivatedRoute) {

    }

    ngOnInit() {
    }

    save(event) {
        this.userService.created(event.value).subscribe(
            data => {
                console.log('添加成功');
                this.toList();
            },
            err => {
                console.error(err);
            }
        );
    }

    toList() {
        this.router.navigate(['../'], { relativeTo: this.route });
    }
}
