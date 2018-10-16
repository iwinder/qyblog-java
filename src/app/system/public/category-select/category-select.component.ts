import { Component, OnInit, Output, EventEmitter, Input, forwardRef, ChangeDetectorRef } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    Validators,
    FormControl,
    NG_VALUE_ACCESSOR,
    ControlValueAccessor
} from '@angular/forms';
import { UserService } from '../../service/user.service';
import { catchError, map } from 'rxjs/operators';
import { UploadFile, NzFormatEmitEvent, NzTreeNode } from 'ng-zorro-antd';
import { User } from '../../entity/user';
import { CategoryService } from '../../service/category.service';
import { Observable } from 'rxjs';
import { Page } from '../../../core/entity/page';
import { Category } from '../../entity/Category';

export const SELECT_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => QyCategorySelectComponent),
    multi: true
};

@Component({
    selector: 'qy-category-select',
    templateUrl: './category-select.component.html',
    styleUrls: ['./category-select.component.scss'],
    providers: [SELECT_VALUE_ACCESSOR]
})
export class QyCategorySelectComponent implements OnInit, ControlValueAccessor {

    @Output() save: EventEmitter<any> = new EventEmitter();
    @Input()  category:  Category;

    value: any;


    constructor(private fb: FormBuilder,
        private categoryService: CategoryService, private cd: ChangeDetectorRef) {
    }

    expandKeys = [];

    nodes = [];

    onChange: Function = () => { };
    onTouched: Function = () => { };

    onExpandChange(e: NzFormatEmitEvent): void {
      console.log(" e.node 0",  e.node );
      // this.onChange(e.node);
      console.log(" e.node ",  e.node );
      if (e.node.getChildren().length === 0 && e.node.isExpanded) {
        this.loadNode({ parentId: e.node.key }).subscribe(data => {
          e.node.addChildren(data);
        });
      }
    }

    loadNode(params?: any): Observable<Category[]> {
      return this.categoryService.findAll(params);
    }



    ngOnInit() {

      // <!-- [(ngModel)]="value" -->
      this.loadNode().subscribe(data => {
        data.forEach( o => {
          this.nodes.push(new NzTreeNode(o));
      });
        console.log(" this.nodes ",  this.nodes );
        if (this.category) {
          console.log(" this.category ",  this.category );
          this.expandKeys = [this.category.key];
        }
        console.log(" this.expandKeys ",  this.expandKeys );
      });
    }

    writeValue(obj: any): void {
      console.log("writeValue e.node obj",  obj );
      if (obj != null) {
        this.value = obj;
        this.save.emit(this.value);
        this.cd.markForCheck();
      }

    }
    registerOnChange(fn: any): void {
      this.onChange = fn;
    }
    registerOnTouched(fn: any): void {
      this.onTouched = fn;
    }
    setDisabledState?(isDisabled: boolean): void {
      throw new Error("Method not implemented.");
    }



}
