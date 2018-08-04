import { Component } from '@angular/core';
// import { EditorConfig } from './editor/model/editor-config';

@Component({
  selector: 'qy-root',
  // templateUrl: './app.component.html',
  template: '<router-outlet></router-outlet>',
  styleUrls: []
  // styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'qy';


  // conf = new EditorConfig();
  // markdown = '测试语句';

  // // 同步属性内容
  // syncModel(str): void {
  //   this.markdown = str;
  // }
}
