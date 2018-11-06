import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { BlogArticleService } from '../../service/blog-article.service';
import { BlogArticle } from '../../entity/blog-article';
import { Page } from '../../../core/entity/page';



@Component({
    selector: 'qy-blog-list-index',
    templateUrl: './blog-list-index.component.html',
    styleUrls: ['./blog-list-index.component.scss']
})
export class BlogListIndexComponent implements OnInit {

    // tslint:disable-next-line:max-line-length
    dangerousVideoUrl  = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNTAgMjUwIj4KICAgIDxwYXRoIGZpbGw9IiNERDAwMzEiIGQ9Ik0xMjUgMzBMMzEuOSA2My4ybDE0LjIgMTIzLjFMMTI1IDIzMGw3OC45LTQzLjcgMTQuMi0xMjMuMXoiIC8+CiAgICA8cGF0aCBmaWxsPSIjQzMwMDJGIiBkPSJNMTI1IDMwdjIyLjItLjFWMjMwbDc4LjktNDMuNyAxNC4yLTEyMy4xTDEyNSAzMHoiIC8+CiAgICA8cGF0aCAgZmlsbD0iI0ZGRkZGRiIgZD0iTTEyNSA1Mi4xTDY2LjggMTgyLjZoMjEuN2wxMS43LTI5LjJoNDkuNGwxMS43IDI5LjJIMTgzTDEyNSA1Mi4xem0xNyA4My4zaC0zNGwxNy00MC45IDE3IDQwLjl6IiAvPgogIDwvc3ZnPg==";
    imagSrc;
    page = 1;


    articlesData: Page<BlogArticle>;
    searchList: any;
    pageIndex = 1;
    loading = false;
    constructor(private sanitizer: DomSanitizer,
        private articleService: BlogArticleService) {
    }

    ngOnInit() {
        this.imagSrc = this.sanitizer.bypassSecurityTrustUrl(this.dangerousVideoUrl);
        this.loadData();
    }

    loadData(event?) {
        console.log("loda event", event);
        console.log("loda  this.pageIndex",  this.pageIndex);
        if ( this.articlesData) {
          this.articlesData.number = this.pageIndex ? this.pageIndex - 1  : this.articlesData.number;
        }

        let params = {
          ...this.searchList,
          page: this.articlesData ? this.articlesData.number  : 0,
          size: this.articlesData ? this.articlesData.size : 3,
          // sort: page && page.sort ? page.sort : null
      };
      this.loading = true;
        this.articleService.findAll(params).subscribe(
          data => {
            this.loading = false;
            this.articlesData = data;
          },
          error => {
            console.log(error);
            this.loading = false;
          }
        );
      }

     getNewUrl(urlStrng) {
         if (!urlStrng) {
           return this.imagSrc;
         }
        return this.sanitizer.bypassSecurityTrustUrl(urlStrng);
    }

}
