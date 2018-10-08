import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../../AuthGuard';

@Component({
    selector: 'qy-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
    @Input() isCollapsed = false;
    @Output() doSubmit = new EventEmitter();
        constructor( private authService: AuthService) {
    }

    username;

    ngOnInit() {
        this.username = this.authService.userToken;
        console.log('HeaderComponent', this.username );
    }

    onIsCollapsed() {
        this.isCollapsed = !this.isCollapsed;
        this.doSubmit.emit(this.isCollapsed);
    }
}
