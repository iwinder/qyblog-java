import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    Validators,
    FormControl
} from '@angular/forms';
import { UserService } from '../../service/user.service';
import { catchError, map } from 'rxjs/operators';
import { UploadFile, NzFormatEmitEvent } from 'ng-zorro-antd';
import { User } from '../../entity/user';
import { CategoryService } from '../../service/category.service';
import { Observable } from 'rxjs';
import { Page } from '../../../core/entity/page';
import { Category } from '../../entity/Category';

@Component({
    selector: 'qy-category-tree-select',
    templateUrl: './category-tree-select.component.html',
    styleUrls: ['./category-tree-select.component.scss']
})
export class QyUserFormComponent implements OnInit {
    @Output() save: EventEmitter<any> = new EventEmitter();
    @Input()  user: User;

    constructor(private fb: FormBuilder,
        private categoryService: CategoryService) {
    }

    expandKeys = [ '0-0' ];
    value: string;
    nodes = [ {
      title   : 'Node1',
      value   : '0-0',
      key     : '0-0',
      children: [ {
        title: 'Child Node1',
        value: '0-0-1',
        key  : '0-0-1'
      }, {
        title: 'Child Node2',
        value: '0-0-2',
        key  : '0-0-2'
      } ]
    }, {
      title: 'Node2',
      value: '0-1',
      key  : '0-1'
    } ];

    onExpandChange(e: NzFormatEmitEvent): void {
      if (e.node.getChildren().length === 0 && e.node.isExpanded) {
        this.loadNode().subscribe(data => {
          e.node.addChildren(data.content);
        });
      }
    }

    loadNode(): Observable<Page<Category>> {
      return this.categoryService.findAll();
    }



    ngOnInit() {

    }



}
