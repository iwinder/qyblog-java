import { Component, OnInit } from '@angular/core';
import { BlogArticle } from '../../../entity/blog-article';
import { BlogArticleService } from '../../../service/blog-article.service';

@Component({
    selector: 'qy-blog-article-list',
    templateUrl: './blog-article-list.component.html',
    styleUrls: ['./blog-article-list.component.scss']
})
export class BlogArticleListComponent implements OnInit {

  articlesData: BlogArticle[] = new Array<BlogArticle>();

  constructor(private articleService: BlogArticleService) {
  }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.articleService.findAll().subscribe(
      data => {
        console.log(data);
        this.articlesData = data['content'];
      },
      error => {
        console.log(error);
      }
    );
  }


}
