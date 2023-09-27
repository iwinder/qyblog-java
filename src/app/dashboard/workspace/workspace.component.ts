import { Component, OnInit } from '@angular/core';
import { User } from 'app/system/entity/user';
import { AuthService } from '../../core/AuthGuard';

@Component({
  selector: 'qy-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.scss']
})
export class WorkspaceComponent implements OnInit {
  user: User = new User();

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.authService.getCurrentUser().subscribe(
      data => {
        let userToken = data['result'];
        this.user.username = userToken ? userToken["username"] : '';
        this.user.nickname = userToken ? userToken["nickname"] : '';
        this.user.avatar = userToken ? userToken["avatar"] : '';
      }
    );
  }

}
