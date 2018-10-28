import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    Validators,
    FormControl
} from '@angular/forms';
import { UserService } from '../../service/user.service';
import { catchError, map } from 'rxjs/operators';
import { UploadFile, NzFormatEmitEvent, NzTreeNode } from 'ng-zorro-antd';
import { User } from '../../entity/user';
import { Category } from '../../entity/Category';
import { CategoryService } from '../../service/category.service';

@Component({
    selector: 'qy-category-form',
    templateUrl: './category-form.component.html',
    styleUrls: ['./category-form.component.scss']
})
export class QyCategoryFormComponent implements OnInit {
    @Output() save: EventEmitter<any> = new EventEmitter();
    validateForm: FormGroup;
    @Input()  category: Category;
    // tslint:disable-next-line:no-inferrable-types
    disabledValue: boolean = true;
    constructor(private fb: FormBuilder,
        private categoryService: CategoryService) {
    }

    expandKeys = [];
    value;
    nodes: NzTreeNode[] = [];
    node: NzTreeNode;
    ngOnInit() {

        let obj = this.category || new Category();

        this.validateForm = this.fb.group({
            title: [ obj.title, [ Validators.required ]],
            key: [obj.key],
            parent: [obj.parent ? obj.parent.key : null],
            keyWord: [obj.keyWord, [Validators.required]],
            description: [obj.description],
            type: [obj.type],
            displayOrder: [obj.displayOrder],
            hasChildren: [obj.hasChildren, []]
        });

        this.loadAllNode().then(data => {
            data.forEach( o => {
                this.node = new NzTreeNode(o);
                this.nodes.push( this.node);
            });
            if (this.category) {
                if (this.category.parent) {
                    // this.getOne();
                    this.expandKeys = [this.category.parent.key];
                    this.getFormControl('parent').setValue(this.category.parent.key);
                }
            }
        });

    }

    getOne() {
        let ids = this.category.idPath.split(",");
        const target = this.nodes.find(a => a.key === ids[0]);
        this.getOne2(0, ids, target);
    }
    getOne2(i, ids, target) {
        if (i < ids.length - 1) {
            let tmpnodes;
            let tmpnode;
            let j = 0;
            this.loadNode({ parentId: ids[i] }).then(data => {
                tmpnodes = [];
                j = 0;
                data.forEach( o => {
                    tmpnode = new NzTreeNode(o);
                    tmpnodes.push(tmpnode);
                    if (o.key !== ids[i + 1]  ) {
                        j++;
                    }
                });
                target.children = tmpnodes;
                i++;
                this.getOne2(i, ids, target.children[j]);
            });
        } else {
            this.expandKeys = [this.category.parent.key];
            this.getFormControl('parent').setValue(this.category.parent.key);
        }
    }
    markAsDirty() {
        for (let key of Object.keys(this.validateForm.controls)) {
            this.validateForm.controls[key].markAsDirty();
        }
    }
    submitForm = ($event, value) => {
        $event.preventDefault();
        for (let key of Object.keys(this.validateForm.controls)) {
          this.validateForm.controls[ key ].markAsDirty();
          this.validateForm.controls[ key ].updateValueAndValidity();
        }
        let values = this.validateForm.value;
        if (this.category && this.category.id) {
            values.id =  this.category.id;
        }
        if (this.getFormControl('parent').value != null) {
            let category = new Category();
            category.id = this.getFormControl('parent').value;
            values.parent = category;
        }
        this.save.emit({ originalEvent: event, value: values });
        console.log(value);
    }

    getFormControl(name) {
        return this.validateForm.controls[name];
    }
    // getHtmlValue(event) {
    //     console.log('getHtmlValue', event.value);
    // }

    // uploadChange(event) {
    //     if (event.type === 'success') {
    //         this.previewImage = event.file.response.relativePath;
    //         this.getFormControl('avatar').setValue(this.previewImage);
    //     }
    // }

    handlePreview = (file: UploadFile) => {
        // this.previewImage = file.url || file.thumbUrl;
        // this.previewVisible = true;
    }


      updateConfirmValidator() {
        /** wait for refresh value */
        setTimeout(_ => {
            this.validateForm.controls['checkPassword'].updateValueAndValidity();
        });
    }

    validateConfirmPassword(): void {
        setTimeout(() => this.validateForm.controls.checkPassword.updateValueAndValidity());
      }

    confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
        if (!control.value) {
            return { required: true };
        } else if (control.value !== this.validateForm.controls['password'].value) {
            return { confirm: true, error: true };
        }
    }
    userNameAsyncValidator = (control: FormControl) => {
        let param = {
            username : control.value
        };
        // return this.userService.checkUser(control.value).pipe(
        //         map( data => {
        //             // tslint:disable-next-line:radix
        //             if (data && parseInt(data) > 0) {
        //                 return { error: true, duplicated: true };
        //             }
        //             return;
        //         },
        //         catchError( err => {
        //             console.log(err);
        //             return null;
        //             // return { error: true, duplicated: true };
        //         }))
        //     );

    }

    resetForm(e?: MouseEvent): void {
        if (e) {
            e.preventDefault();
        }
        this.validateForm.reset();
        for (let key of Object.keys(this.validateForm.controls)) {
          this.validateForm.controls[ key ].markAsPristine();
          this.validateForm.controls[ key ].updateValueAndValidity();
        }
    }

    getCaptcha(e: MouseEvent) {
        e.preventDefault();
    }


    onExpandChange(e: NzFormatEmitEvent): void {
        console.log(" e.node 0",  e.node );
        // this.onModelChange(e.node);
        console.log(" e.node ",  e.node );
        if (e.node && e.node.getChildren().length === 0 && e.node.isExpanded) {
          this.loadNode({ parentId: e.node.key }).then(data => {
            e.node.addChildren(data);
          });
        }
      }

      loadNode(params?: any): Promise<Category[]> {
        return this.categoryService.findAllOfPromise(params);
      }

      loadAllNode(): Promise<Category[]> {
        return this.categoryService.findAllNodeOfPromise();
      }

      public refresh(e) {
       this.resetForm(e);
    }
}
