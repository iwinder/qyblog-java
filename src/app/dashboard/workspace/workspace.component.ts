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
    this.user.username = this.authService.userToken ? this.authService.userToken.username : '';
    // this.authService.getCurrentUser().subscribe(user => this.user = user);
  }

}
