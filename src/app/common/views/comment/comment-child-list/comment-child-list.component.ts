// tslint:disable-next-line:max-line-length
import { Component, OnInit, OnChanges, AfterViewInit, AfterContentInit, Input } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { CommentAgent } from 'app/common/entity/comment-agent';
import { CommetsService } from 'app/common/service/comment-list.service';
import { Comment } from 'app/common/entity/comment';





@Component({
    selector: 'qy-comment-child-list',
    templateUrl: './comment-child-list.component.html',
    styleUrls: ['./comment-child-list.component.scss']
})
export class CommentChildListComponent implements OnInit {

    @Input() target: CommentAgent;
    @Input()  parent: Comment;
    // tslint:disable-next-line:max-line-length
    dangerousVideoUrl  = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNTAgMjUwIj4KICAgIDxwYXRoIGZpbGw9IiNERDAwMzEiIGQ9Ik0xMjUgMzBMMzEuOSA2My4ybDE0LjIgMTIzLjFMMTI1IDIzMGw3OC45LTQzLjcgMTQuMi0xMjMuMXoiIC8+CiAgICA8cGF0aCBmaWxsPSIjQzMwMDJGIiBkPSJNMTI1IDMwdjIyLjItLjFWMjMwbDc4LjktNDMuNyAxNC4yLTEyMy4xTDEyNSAzMHoiIC8+CiAgICA8cGF0aCAgZmlsbD0iI0ZGRkZGRiIgZD0iTTEyNSA1Mi4xTDY2LjggMTgyLjZoMjEuN2wxMS43LTI5LjJoNDkuNGwxMS43IDI5LjJIMTgzTDEyNSA1Mi4xem0xNyA4My4zaC0zNGwxNy00MC45IDE3IDQwLjl6IiAvPgogIDwvc3ZnPg==";
    imagSrc;
    page = 1;
    articleId;
    textFiltering: string = "暂无";
    searchList: any;
    pageIndex = 1;
    loading = false;
    thumbnail;

    constructor(private sanitizer: DomSanitizer,
        activeRouter: ActivatedRoute,
        private commetsService: CommetsService) {
        // this.articleId = activeRouter.snapshot.paramMap.get('articleId');
    }

    ngOnInit() {
        this.imagSrc = this.sanitizer.bypassSecurityTrustUrl(this.dangerousVideoUrl);

    }




    // getNewHTML(contentHtml) {
    //   if (!contentHtml) {
    //     contentHtml = this.textFiltering;
    //   }
    //   return this.sanitizer.bypassSecurityTrustHtml(contentHtml);
    // }

    findAll() {
        // let params = {
        //     ...this.searchList,
        //     agentTargetId: this.agent.id,
        //     page: this.articlesData ? this.articlesData.number  : 0,
        //     size: this.articlesData ? this.articlesData.size : 3,
        //     // sort: page && page.sort ? page.sort : null
        // };
        // this.commetsService.findAll(params).subscribe(
        //     data => {
        //       this.loading = false;
        //       this.articlesData = data;
        //     },
        //     error => {
        //       console.log(error);
        //       this.loading = false;
        //     }
        //   );
    }

}
