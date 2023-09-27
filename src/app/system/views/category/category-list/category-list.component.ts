import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../../service/user.service';
import { User } from '../../../entity/User';
import { CategoryService } from '../../../service/category.service';
import { Category } from '../../../entity/Category';
import { NzFormatEmitEvent } from 'ng-zorro-antd';
import { QyCategoryFormComponent } from '../../../public/category-form/category-form.component';

@Component({
    selector: 'qy-category-list',
    templateUrl: './category-list.component.html',
    styleUrls: ['./category-list.component.scss']
})
export class QyCategoryListComponent implements OnInit {
data = [
  ];
  expandDataCache = {};
  categoryData: Category[] = new Array<Category>();
  @ViewChild("cateForm") cateForm: QyCategoryFormComponent;
    constructor(private categoryService: CategoryService) {
    }
    nodes = [];
    ngOnInit() {
      this.loadData();
    }

    loadData() {
      this.categoryService.findAllOfTree().subscribe(
        data => {
          this.categoryData = data;
          let parma;
          this.categoryData.forEach(item => {
            this.expandDataCache[ item.key ] = this.convertTreeToList(item);
          });
        },
        error => {
          console.log(error);
        }
      );
    }

    save(event) {
        this.categoryService.created(event.value).subscribe(
            data => {
                console.log('添加成功');
                this.cateForm.resetForm();
                this.loadData();
            },
            err => {
                console.error(err);
            }
        );
    }


    collapse(array: Category[], data: Category, $event: boolean): void {
        if ($event === false) {
          if (data.hasChildren) {
            data.children.forEach(d => {
              const target = array.find(a => a.key === d.key);
              target.expand = false;
              this.collapse(array, target, false);
            });
          } else {
            return;
          }
        }
      }

      convertTreeToList(root: object): Category[] {
        const stack = [];
        const array = [];
        const hashMap = {};
        stack.push({ ...root, level: 0, expand: false });
        while (stack.length !== 0) {
          const node = stack.pop();
          this.visitNode(node, hashMap, array);
          if (node.hasChildren) {

            for (let i = node.children.length - 1; i >= 0; i--) {
              stack.push({ ...node.children[ i ], level: node.level + 1, expand: false, parent: node });
            }
          }
        }
        return array;
      }

      visitNode(node: Category, hashMap: object, array: Category[]): void {
        if (!hashMap[ node.key ]) {
          hashMap[ node.key ] = true;
          array.push(node);
        }
      }
}
