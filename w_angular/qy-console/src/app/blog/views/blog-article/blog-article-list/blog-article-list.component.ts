import { Component, OnInit } from '@angular/core';
import { BlogArticle } from '../../../entity/blog-article';
import { BlogArticleService } from '../../../service/blog-article.service';
import { Page } from '../../../../core/entity/page';

@Component({
    selector: 'qy-blog-article-list',
    templateUrl: './blog-article-list.component.html',
    styleUrls: ['./blog-article-list.component.scss']
})
export class BlogArticleListComponent implements OnInit {

  articlesData: BlogArticle[] = new Array<BlogArticle>();
  data: Page<BlogArticle>;
  searchList: any;
  constructor(private articleService: BlogArticleService) {
  }
  pageIndex = 1;
  loading = false;
  ngOnInit() {
    this.loadData();
  }

  loadData(event?) {
    console.log("loda event", event);
    console.log("loda  this.pageIndex",  this.pageIndex);
    if ( this.data) {
      this.data.number = this.pageIndex ? this.pageIndex - 1  : this.data.number;
    }

    let params = {
      ...this.searchList,
      page: this.data ? this.data.number  : 0,
      size: this.data ? this.data.size : 3,
      // sort: page && page.sort ? page.sort : null
  };
  this.loading = true;
    this.articleService.findAll(params).subscribe(
      data => {
        this.loading = false;
        console.log(data);
        this.data = data;
        this.articlesData = data['content'];
      },
      error => {
        console.log(error);
        this.loading = false;
      }
    );
  }


}
