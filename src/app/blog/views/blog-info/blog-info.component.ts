import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { BlogArticleService } from '../../service/blog-article.service';
import { BlogArticle } from '../../entity/blog-article';
import { Page } from '../../../core/entity/page';
import { ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
// import * as hljs from 'highlight.js';
import * as hljs from 'highlight.js/lib/highlight';
// import hljs from 'highlight.js/lib/highlight';
import javascript from 'highlight.js/lib/languages/javascript';
import java from 'highlight.js/lib/languages/java';
hljs.registerLanguage('javascript', javascript);
hljs.registerLanguage('java', java);
// import 'highlight.js/styles/monokai-sublime.css';
declare var hljs: any;

declare var $: any;

@Component({
    selector: 'qy-blog-info',
    templateUrl: './blog-info.component.html',
    styleUrls: ['./blog-info.component.scss']
})
export class BlogInfoComponent implements OnInit {

    // tslint:disable-next-line:max-line-length
    dangerousVideoUrl  = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNTAgMjUwIj4KICAgIDxwYXRoIGZpbGw9IiNERDAwMzEiIGQ9Ik0xMjUgMzBMMzEuOSA2My4ybDE0LjIgMTIzLjFMMTI1IDIzMGw3OC45LTQzLjcgMTQuMi0xMjMuMXoiIC8+CiAgICA8cGF0aCBmaWxsPSIjQzMwMDJGIiBkPSJNMTI1IDMwdjIyLjItLjFWMjMwbDc4LjktNDMuNyAxNC4yLTEyMy4xTDEyNSAzMHoiIC8+CiAgICA8cGF0aCAgZmlsbD0iI0ZGRkZGRiIgZD0iTTEyNSA1Mi4xTDY2LjggMTgyLjZoMjEuN2wxMS43LTI5LjJoNDkuNGwxMS43IDI5LjJIMTgzTDEyNSA1Mi4xem0xNyA4My4zaC0zNGwxNy00MC45IDE3IDQwLjl6IiAvPgogIDwvc3ZnPg==";
    imagSrc;
    page = 1;
    articleId;
    textFiltering: string = "暂无";

    articlesData: BlogArticle;
    searchList: any;
    pageIndex = 1;
    loading = false;
    thumbnail;
    constructor(private sanitizer: DomSanitizer,
        private articleService: BlogArticleService,
        // private route: ActivatedRouteSnapshot
        private activeRouter: ActivatedRoute) {
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
            // hljs.initHighlightingOnLoad();

          },
          error => {
            console.log(error);
            this.loading = false;
          }
        );
    }

    get getNewUrl() {
        if (!this.thumbnail) {
           return this.imagSrc;
        }
        return this.sanitizer.bypassSecurityTrustUrl(this.thumbnail);
    }

    getNewUrlStr(urlStrng) {
      if (!urlStrng) {
        urlStrng =  this.imagSrc;
     }

     return this.sanitizer.bypassSecurityTrustStyle(`background-image:url(${urlStrng}); background-size: cover;`);
    }

    getNewHTML(contentHtml) {
      if (!contentHtml) {
        contentHtml = this.textFiltering;
      }
      $('pre code').each(function(i, block) {
        let s = $(block).attr("class");
        $(block).addClass(s.substr(s.lastIndexOf("-") + 1));
        $(block).removeClass(s);

        hljs.highlightBlock(block);
      });
      return this.sanitizer.bypassSecurityTrustHtml(contentHtml);
    }


}
