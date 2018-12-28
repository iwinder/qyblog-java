import { Component, OnInit, OnChanges, SimpleChanges, AfterViewInit, AfterContentInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { BlogArticleService } from '../../service/blog-article.service';
import { BlogArticle } from '../../entity/blog-article';
import { Page } from '../../../core/entity/page';
import { ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import * as hljs from 'highlight.js/lib/highlight';
import javascript from 'highlight.js/lib/languages/javascript';
import java from 'highlight.js/lib/languages/java';
hljs.registerLanguage('javascript', javascript);
hljs.registerLanguage('java', java);

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
    searchList: any;
    pageIndex = 1;
    loading = false;
    thumbnail;

    constructor(private sanitizer: DomSanitizer,
        private articleService: BlogArticleService,
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
          },
          error => {
            this.loading = false;
          }
        );
    }

    ngAfterContentInit(): void {
      console.log("ngAfterContentInit not implemented.");
    }
    ngAfterViewInit(): void {
      console.log("ngAfterViewInit not implemented.");
    }
    // getNewUrlStr(urlStrng) {
    //   if (!urlStrng) {
    //     urlStrng =  this.imagSrc;
    //  }

    //  return this.sanitizer.bypassSecurityTrustStyle(`background-image:url(${urlStrng}); background-size: cover;`);
    // }

    getNewHTML(contentHtml) {
      if (!contentHtml) {
        contentHtml = this.textFiltering;
      }

      // $('pre code').each(function(i, block) {
      //   console.log(i, block);
      //   let s = $(block).attr("class");
      //   console.log('class', s.substr(s.lastIndexOf("-") + 1));
      //   $(block).addClass(s.substr(s.lastIndexOf("-") + 1));
      //   $(block).removeClass(s);
      //   console.log(i, block);
      //   hljs.highlightBlock(block);
      // });

      console.log("contentHtml", this.textFiltering);
      let codes = contentHtml.match(/(<code)([\s\S^(<pre>)]*?)(<\/code>)/g);
      let newCodes = [];
      codes.forEach(function(block, i) {
          console.log(i, block);
          let targ = $(block);
          let s = targ.attr("class");
          console.log('class', s.substr(s.lastIndexOf("-") + 1));
          targ.addClass(s.substr(s.lastIndexOf("-") + 1));
          targ.removeClass(s);
          console.log(i, block);
          hljs.highlightBlock(targ[0]);
          console.log("targ", targ);
          console.log("targ[0]", targ[0]);
          newCodes.push(targ[0]);
        });
      console.log("a", newCodes);
      return this.sanitizer.bypassSecurityTrustHtml(contentHtml);
    }

}
