// tslint:disable-next-line:max-line-length
import { Component, OnInit, Input } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { CommentAgent } from 'app/common/entity/comment-agent';
import { CommetsService } from 'app/common/service/comment-list.service';
import { Page } from 'app/core/entity/page';
import { Comment } from 'app/common/entity/comment';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';




@Component({
    selector: 'qy-comment-list',
    templateUrl: './comment-list.component.html',
    styleUrls: ['./comment-list.component.scss']
})
export class CommentListComponent implements OnInit {

    @Input() target: CommentAgent;
    validateForm: FormGroup;
    comment: Comment;
    // tslint:disable-next-line:max-line-length
    dangerousVideoUrl  = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNTAgMjUwIj4KICAgIDxwYXRoIGZpbGw9IiNERDAwMzEiIGQ9Ik0xMjUgMzBMMzEuOSA2My4ybDE0LjIgMTIzLjFMMTI1IDIzMGw3OC45LTQzLjcgMTQuMi0xMjMuMXoiIC8+CiAgICA8cGF0aCBmaWxsPSIjQzMwMDJGIiBkPSJNMTI1IDMwdjIyLjItLjFWMjMwbDc4LjktNDMuNyAxNC4yLTEyMy4xTDEyNSAzMHoiIC8+CiAgICA8cGF0aCAgZmlsbD0iI0ZGRkZGRiIgZD0iTTEyNSA1Mi4xTDY2LjggMTgyLjZoMjEuN2wxMS43LTI5LjJoNDkuNGwxMS43IDI5LjJIMTgzTDEyNSA1Mi4xem0xNyA4My4zaC0zNGwxNy00MC45IDE3IDQwLjl6IiAvPgogIDwvc3ZnPg==";
    imagSrc;
    page = 1;
    articleId;
    textFiltering: string = "暂无";
    commentData: Page<Comment>;
    searchList: any;
    pageIndex = 1;
    loading = false;
    thumbnail;

    constructor(private sanitizer: DomSanitizer,
        private fb: FormBuilder,
        activeRouter: ActivatedRoute,
        private commetsService: CommetsService) {
        // this.articleId = activeRouter.snapshot.paramMap.get('articleId');
    }

    ngOnInit() {
        let obj = this.comment || new Comment();
        this.imagSrc = this.sanitizer.bypassSecurityTrustUrl(this.dangerousVideoUrl);
        this.validateForm = this.fb.group({
            author: [ obj.author, [ Validators.required ]],
            email: [obj.email, [ Validators.required, Validators.email ]],
            url: [ obj.url],
            content: [obj.content, [ Validators.required ]]
        });
        if (this.target && this.target.commentCount > 0 ) {
            this.loadData();
        }
    }

    getNewUrl(urlStrng) {
        if (!urlStrng) {
          return this.imagSrc;
        }
       return this.sanitizer.bypassSecurityTrustUrl(urlStrng);
   }



    loadData() {
        let params = {
            ...this.searchList,
            agentTargetId: this.target.id,
            page: this.commentData ? this.commentData.number  : 0,
            size: this.commentData ? this.commentData.size : 3,
            // sort: page && page.sort ? page.sort : null
        };
        this.commentData = new Page<Comment>();
        this.commetsService.findAll(params).subscribe(
            data => {
              this.loading = false;
              this.commentData = data;
            },
            error => {
              console.log(error);
              this.loading = false;
            }
          );
    }
    markAsDirty() {
        for (let key of Object.keys(this.validateForm.controls)) {
            this.validateForm.controls[key].markAsDirty();
        }
    }
    getFormControl(name) {
        return this.validateForm.controls[name];
    }
    submitForm($event) {
        $event.preventDefault();
        for (let key of Object.keys(this.validateForm.controls)) {
          this.validateForm.controls[ key ].markAsDirty();
          this.validateForm.controls[ key ].updateValueAndValidity();
        }
        if (this.validateForm.invalid) {
            return;
        }
        let link = this.getFormControl('url').value;
        if (link) {
            this.getFormControl('url').setValue (/http:\/\/|https:\/\//ig.test(link) ? link : "http://" + link);
        }
        let values = this.validateForm.value;
        values["agent"] = this.target;
        values["agentTargetId"] = this.target.id;

        console.log("dd", values);
        this.commetsService.created(values).subscribe(
            data => {
                console.log("保存成功：data", data);
                this.loadData();
            },
            error => {
                console.log("保存失败：data", error);
            }
        );
    }

}
