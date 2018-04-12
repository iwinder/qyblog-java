import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent {
  title = 'app';
  searchName;
  isPublishedOptions = [
    { value: '', label: '全部' },
    { value: '0', label: '未发布' },
    { value: '1', label: '已发布' },
  ];
  isPublished = this.isPublishedOptions[0];
}
