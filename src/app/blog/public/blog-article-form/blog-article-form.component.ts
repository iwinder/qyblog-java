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
import { UploadFile, NzFormatEmitEvent, NzTreeNode } from 'ng-zorro-antd';
import { CategoryService } from '../../../system/service/category.service';
import { Category } from '../../../system/entity/Category';

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
    nodes: NzTreeNode[] = [];
    node: NzTreeNode;
    // tslint:disable-next-line:no-inferrable-types
    disabledValue: boolean = true;
    listOfOption = [];
    constructor(private fb: FormBuilder,
        private articleService: BlogArticleService,
        private categoryService: CategoryService) {
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
            category: [obj.category ? obj.category.key : null],
            publishedDate: [ obj.publishedDate ? new Date(obj.publishedDate) : new Date() ],
            isPublished: [obj.isPublished || false ],
            content: [obj.content, [Validators.required]],
            contentHtml: [obj.contentHtml],
            thumbnail: [obj.thumbnail],
            tagStrings:  [obj.tagStrings],
            summary: [obj.summary],
        });
        if ( this.article && this.article.thumbnail) {
            this.fileList.push({url: this.article.thumbnail});
            this.previewImage = this.article.thumbnail;
        }

        this.loadAllNode().then(data => {
            data.forEach( o => {
                this.node = new NzTreeNode(o);
                this.nodes.push( this.node);
            });
            if (this.article && this.article.category) {
                // this.expandKeys = [this.article.category.key];
                this.getFormControl('category').setValue(this.article.category.key);
            }
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
        if (this.validateForm.invalid) {
            return;
        }
        let values = this.validateForm.value;
        if ( this.article && this.article.id) {
            values['id'] = this.article.id;
        }
        console.log("!this.article.isPublished", !values.isPublished);
        if ( !values.isPublished ) {
            values.publishedDate = null;
            console.log("values1", values);
        }
        console.log("values2", values);
        if (this.getFormControl('category').value != null) {
            let category = new Category();
            category.id = this.getFormControl('category').value;
            values.category = category;
        }
        this.save.emit({ originalEvent: event, value: values });
        console.log(value);
    }

    getFormControl(name) {
        return this.validateForm.controls[name];
    }
    getHtmlValue(event) {
        this.validateForm.controls['contentHtml'].setValue(event.value);
        let summary = this.delHtmlTag(event.value);
        if (summary.length > 100) {
            summary = summary.substring(0, 100);
        }
        this.validateForm.controls['summary'].setValue(summary);
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


    loadAllNode(): Promise<Category[]> {
        return this.categoryService.findAllNodeOfPromise();
    }
    delHtmlTag(str) {
        return str.replace(/<[^>]+>/g, "");
    }


}
