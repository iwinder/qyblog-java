import { Component, OnInit } from '@angular/core';
import { Comment } from '../../entity/comment';
import { CommentService } from '../../service/comment.service';
import { Page } from '../../../core/entity/page';

@Component({
    selector: 'qy-comment-list',
    templateUrl: './comment-list.component.html',
    styleUrls: ['./comment-list.component.scss']
})
export class CommentListComponent implements OnInit {

  commentsData: Comment[] = new Array<Comment>();
  data: Page<Comment>;
  searchList: any;
  constructor(private commentService: CommentService) {
  }
  pageIndex = 1;
  loading = false;
  show: Boolean[] = new  Array<Boolean>(false);
  ngOnInit() {
    this.loadData();
  }

  loadData(event?) {
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
    this.commentService.findAll(params).subscribe(
      data => {
        this.loading = false;
        console.log(data);
        this.data = data;
        this.commentsData = data['content'];
      },
      error => {
        console.log(error);
        this.loading = false;
      }
    );
  }

  updateStatus(id, status) {
    let params = {
      id: id,
      status: status
    };
    this.commentService.updateStatus(params).subscribe(
      data => {
        // this.loading = false;
        this.loadData();
        // this.data = data;
        // this.commentsData = data['content'];
      },
      error => {
        this.loadData();
        // this.loading = false;
      }
    );
  }


}
