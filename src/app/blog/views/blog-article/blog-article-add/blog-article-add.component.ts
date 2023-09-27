import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BlogArticleService } from '../../../service/blog-article.service';

@Component({
    selector: 'qy-blog-article-add',
    templateUrl: './blog-article-add.component.html',
    styleUrls: ['./blog-article-add.component.scss']
})
export class BlogArticleAddComponent implements OnInit {

    constructor(private articleService: BlogArticleService,
        private router: Router,
        private route: ActivatedRoute) {

    }

    ngOnInit() {
    }

    save(event) {
        this.articleService.created(event.value).subscribe(
            data => {
                console.log('添加成功');
                this.toList();
            },
            err => {
                console.error(err);
            }
        );
    }

    toList() {
        this.router.navigate(['../'], { relativeTo: this.route });
    }
}
