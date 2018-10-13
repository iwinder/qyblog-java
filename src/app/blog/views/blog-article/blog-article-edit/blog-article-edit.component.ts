import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { BlogArticleService } from '../../../service/blog-article.service';
import { BlogArticle } from '../../../entity/blog-article';

@Component({
    selector: 'qy-blog-article-edit',
    templateUrl: './blog-article-edit.component.html',
    styleUrls: ['./blog-article-edit.component.scss']
})
export class BlogArticleEditComponent implements OnInit {

    article: BlogArticle;
    constructor(private articleService: BlogArticleService,
        private router: Router,
        private route: ActivatedRoute) {

    }

    ngOnInit() {
        this.route.params.subscribe(
            (params: Params) => {
                let articleId = +params['articleId'];
                this.loadData(articleId);
            }
        );
    }

    loadData(articleId) {
        console.log("this.userId ", articleId );
        this.articleService.getOne(articleId).subscribe(
            article => this.article = article
          );
    }

    save(event) {
        this.articleService.update(event.value).subscribe(
            data => {
                console.log('更新成功');
                this.toList();
            },
            err => {
                console.error(err);
            }
        );
    }

    toList() {
        this.router.navigate(['../../'], { relativeTo: this.route });
    }
}
