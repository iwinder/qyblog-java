// tslint:disable-next-line:max-line-length
import { Component, OnInit, OnChanges, AfterViewInit, AfterContentInit, Input, AfterViewChecked } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { BlogArticle } from '../../entity/blog-article';
import { ActivatedRoute } from '@angular/router';
// import {hljsa} from './highlight-config';

// declare var $: any;


@Component({
    selector: 'qy-posts-contents',
    templateUrl: './posts-contents.component.html',
    styleUrls: ['./posts-contents.component.scss']
})
export class PostsContentsComponent implements OnInit, OnChanges, AfterViewInit, AfterContentInit, AfterViewChecked {


    @Input() contentHtml: String = "暂无";
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
        activeRouter: ActivatedRoute) {
        this.articleId = activeRouter.snapshot.paramMap.get('articleId');
    }

    ngOnInit() {
        this.imagSrc = this.sanitizer.bypassSecurityTrustUrl(this.dangerousVideoUrl);

    }


    ngOnChanges() {
      // this.updateComponent();
    }

    ngAfterContentInit(): void {
      // this.updateComponent();
    }
    ngAfterViewInit(): void {
      // this.updateComponent();
    }
    ngAfterViewChecked(): void {
      this.updateComponent();
    }


    getNewHTML(contentHtml) {
      if (!contentHtml) {
        contentHtml = this.textFiltering;
      }
      return this.sanitizer.bypassSecurityTrustHtml(contentHtml);
    }

    updateComponent() {

      // document.addEventListener('DOMContentLoaded', (event) => {
      //   let elems =  document.querySelectorAll('pre code');
      //   [].forEach.call(elems, function(block) {
      //     hljsa.highlightBlock(block);
      // });
      // });
      //  $('pre code').each(function(i, block) {
      //   let s = $(block).attr("class");
      //   console.log("s", s);
      //   $(block).addClass(s.substr(s.lastIndexOf("-") + 1));
      //   $(block).removeClass(s);
      //   hljsa.highlightBlock(block);
      // });
    }

}
