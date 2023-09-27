import { Component, OnInit, OnChanges, SimpleChanges, AfterViewInit, AfterContentInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { BlogArticleService } from '../../service/blog-article.service';
import { BlogArticle } from '../../entity/blog-article';
import { Page } from '../../../core/entity/page';
import { ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { CommentAgent } from 'app/common/entity/comment-agent';
import { CookieService } from 'ngx-cookie-service';
import { Comment } from 'app/common/entity/comment';

declare var $: any;
@Component({
    selector: 'qy-blog-info',
    templateUrl: './blog-info.component.html',
    styleUrls: ['./blog-info.component.scss']
})
export class BlogInfoComponent implements OnInit, AfterViewInit, AfterContentInit {



    // tslint:disable-next-line:max-line-length
    dangerousVideoUrl  = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNTAgMjUwIj4KICAgIDxwYXRoIGZpbGw9IiNERDAwMzEiIGQ9Ik0xMjUgMzBMMzEuOSA2My4ybDE0LjIgMTIzLjFMMTI1IDIzMGw3OC45LTQzLjcgMTQuMi0xMjMuMXoiIC8+CiAgICA8cGF0aCBmaWxsPSIjQzMwMDJGIiBkPSJNMTI1IDMwdjIyLjItLjFWMjMwbDc4LjktNDMuNyAxNC4yLTEyMy4xTDEyNSAzMHoiIC8+CiAgICA8cGF0aCAgZmlsbD0iI0ZGRkZGRiIgZD0iTTEyNSA1Mi4xTDY2LjggMTgyLjZoMjEuN2wxMS43LTI5LjJoNDkuNGwxMS43IDI5LjJIMTgzTDEyNSA1Mi4xem0xNyA4My4zaC0zNGwxNy00MC45IDE3IDQwLjl6IiAvPgogIDwvc3ZnPg==";
    imagSrc;
    page = 1;
    articleId;
    textFiltering: string = "暂无";

    articlesData: BlogArticle;
    commentAgent: CommentAgent;
    comment: Comment = new Comment();
    searchList: any;
    pageIndex = 1;
    loading = false;
    thumbnail;

    constructor(private sanitizer: DomSanitizer,
        private articleService: BlogArticleService,
        private activeRouter: ActivatedRoute,
        private cookieService: CookieService,
        private titleService: Title) {
        this.articleId = activeRouter.snapshot.paramMap.get('articleId');
    }

    ngOnInit() {
        this.imagSrc = this.sanitizer.bypassSecurityTrustUrl(this.dangerousVideoUrl);
        this.loadData();
    }

    loadData(event?) {
      this.loading = true;
        this.articleService.getOne(this.articleId).subscribe(
          data => {
            this.loading = false;
            this.articlesData = data;
            this.thumbnail = data.thumbnail;
            this.commentAgent = data.commentAgent;
            this.titleService.setTitle(this.articlesData.title);
            // this.getCookieComemntAuthor();
          },
          error => {
            this.loading = false;
          }
        );
    }
    getDateTime(date) {
      return new Date(date);
    }
    ngAfterContentInit(): void {
    }
    ngAfterViewInit(): void {
    }

    getCookieComemntAuthor() {
      this.comment.author = this.cookieService.get("comment_remember_author");
      if (this.comment.author) {
          this.comment.email =  this.cookieService.get("comment_remember_mail");
          this.comment.url =   this.cookieService.get("comment_remember_url");
      }
      console.log("this.comment", this.comment);
  }
}
