import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'qy-contnet',
    templateUrl: './content.component.html',
    styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit {
    isCollapsed = false;

    constructor() {
    }

    ngOnInit() {
    }

    choseIsCollapsed(isCollapsed) {
        this.isCollapsed = isCollapsed;
    }
}
