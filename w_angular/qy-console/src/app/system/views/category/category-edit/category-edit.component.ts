import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../../service/user.service';
import { User } from '../../../entity/User';
import { CategoryService } from '../../../service/category.service';
import { Category } from '../../../entity/Category';
import { NzFormatEmitEvent } from 'ng-zorro-antd';
import { QyCategoryFormComponent } from '../../../public/category-form/category-form.component';
import { Params, Router, ActivatedRoute } from '@angular/router';

@Component({
    selector: 'qy-category-edit',
    templateUrl: './category-edit.component.html',
    styleUrls: ['./category-edit.component.scss']
})
export class QyCategoryEditComponent implements OnInit {


    category: Category;
    constructor(private categoryService: CategoryService,
      private router: Router,
      private route: ActivatedRoute) {
    }

    ngOnInit() {
      this.route.params.subscribe(
        (params: Params) => {
            let categoryId = +params['categoryId'];
            this.loadData(categoryId);
        }
      );
    }

    loadData(categoryId) {
      this.categoryService.getOne(categoryId).subscribe(
        data => {
            this.category = data;
        },
        error => {
          console.log(error);
        }
      );
    }

    save(event) {
        this.categoryService.created(event.value).subscribe(
            data => {
                console.log('更新成功');
                this.toList();
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
