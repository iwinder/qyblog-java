import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'qy-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
    @Input() isCollapsed = false;
    @Output() doSubmit = new EventEmitter();
    constructor() {
    }

    ngOnInit() {
    }

    onIsCollapsed() {
        this.isCollapsed = !this.isCollapsed;
        this.doSubmit.emit(this.isCollapsed);
    }
}
