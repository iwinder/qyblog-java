import { Component, OnInit } from '@angular/core';
import { User } from 'app/system/entity/user';
import { AuthService } from '../../core/AuthGuard';

@Component({
  selector: 'qy-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.scss']
})
export class WorkspaceComponent implements OnInit {
  user: User;

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
    // this.authService.getCurrentUser().subscribe(user => this.user = user);
  }

}
