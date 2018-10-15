import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    Validators,
    FormControl
} from '@angular/forms';
import { UserService } from '../../service/user.service';
import { catchError, map } from 'rxjs/operators';
import { UploadFile } from 'ng-zorro-antd';
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



    ngOnInit() {
        let obj = this.category || new Category();
        console.log("this.user ", this.category );

        this.validateForm = this.fb.group({
            title: [ obj.title, [ Validators.required ], [  ] ],
            key: [obj.key],
            parent: [obj.parent],
            keyWord: [obj.keyWord, [Validators.required]],
            description: [obj.description],
            type: [obj.type],
            displayOrder: [obj.displayOrder],
            hasChildren: [obj.hasChildren, []]
        });



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

    resetForm(e: MouseEvent): void {
        e.preventDefault();
        this.validateForm.reset();
        for (let key of Object.keys(this.validateForm.controls)) {
          this.validateForm.controls[ key ].markAsPristine();
          this.validateForm.controls[ key ].updateValueAndValidity();
        }
    }

    getCaptcha(e: MouseEvent) {
        e.preventDefault();
    }
}
