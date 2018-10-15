import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    Validators,
    FormControl
} from '@angular/forms';
import { catchError, map } from 'rxjs/operators';
import { BlogArticleService } from '../../service/blog-article.service';
import { BlogArticle } from '../../entity/blog-article';
import { UploadFile } from 'ng-zorro-antd';

@Component({
    selector: 'qy-blog-article-form',
    templateUrl: './blog-article-form.component.html',
    styleUrls: ['./blog-article-form.component.scss']
})
export class QyBlogArticleFormComponent implements OnInit {
    @Output() save: EventEmitter<any> = new EventEmitter();
    validateForm: FormGroup;
    @Input()  article: BlogArticle;

    previewImage = '';
    previewVisible = false;
    fileList = [];
    // tslint:disable-next-line:no-inferrable-types
    disabledValue: boolean = true;
    constructor(private fb: FormBuilder,
        private articleService: BlogArticleService) {
    }

    // updateConfirmValidator() {
    //     /** wait for refresh value */
    //     setTimeout(_ => {
    //         this.validateForm.controls['checkPassword'].updateValueAndValidity();
    //     });
    // }

    // validateConfirmPassword(): void {
    //     setTimeout(() => this.validateForm.controls.checkPassword.updateValueAndValidity());
    //   }

    // confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
    //     if (!control.value) {
    //         return { required: true };
    //     } else if (control.value !== this.validateForm.controls['password'].value) {
    //         return { confirm: true, error: true };
    //     }
    // }
    // TODO: 用于后期链接校验
    // userNameAsyncValidator = (control: FormControl) => {
    //     let param = {
    //         username : control.value
    //     };
    //     return this.userService.checkUser(control.value).pipe(
    //             map( data => {
    //                 // tslint:disable-next-line:radix
    //                 if (data && parseInt(data) > 0) {
    //                     return { error: true, duplicated: true };
    //                 }
    //                 return;
    //             },
    //             catchError( err => {
    //                 console.log(err);
    //                 return null;
    //                 // return { error: true, duplicated: true };
    //             }))
    //         );

    // }

    // resetForm(e: MouseEvent): void {
    //     e.preventDefault();
    //     this.validateForm.reset();
    //     for (let key of Object.keys(this.validateForm.controls)) {
    //       this.validateForm.controls[ key ].markAsPristine();
    //       this.validateForm.controls[ key ].updateValueAndValidity();
    //     }
    // }

    // getCaptcha(e: MouseEvent) {
    //     e.preventDefault();
    // }

    ngOnInit() {
        let obj = this.article || new BlogArticle();
        console.log("this.article", this.article);
        this.validateForm = this.fb.group({
            title: [ obj.title, [ Validators.required ]],
            // email: [null, [Validators.email]],
            publishedDate: [new Date(obj.publishedDate)],
            isPublished: [obj.isPublished ],
            content: [obj.content, [Validators.required]],
            contentHtml: [obj.contentHtml],
            thumbnail: [obj.thumbnail]
        });
        if ( this.article && this.article.thumbnail) {
            this.fileList.push({url: this.article.thumbnail});
            this.previewImage = this.article.thumbnail;
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
        if ( this.article && this.article.id) {
            values['id'] = this.article.id;
        }
        this.save.emit({ originalEvent: event, value: values });
        console.log(value);
    }

    getFormControl(name) {
        return this.validateForm.controls[name];
    }
    getHtmlValue(event) {
        // console.log('getHtmlValue', event.value);
        this.validateForm.controls['contentHtml'].setValue(event.value);
    }
    uploadChange(event) {
        if (event.type === 'success') {
            this.previewImage = event.file.response.relativePath;
            this.getFormControl('thumbnail').setValue(this.previewImage);
        }
    }

    handlePreview = (file: UploadFile) => {
        // this.previewImage = file.url || file.thumbUrl;
        this.previewVisible = true;
    }

    updateStatus(data) {
        console.log('updateStatus', data);
        if (data) {
            if (!this.getFormControl('publishedDate').value) {
                this.getFormControl('publishedDate').setValue(new Date());
            }
        } else {
            this.getFormControl('publishedDate').setValue(null);
        }
    }
}
