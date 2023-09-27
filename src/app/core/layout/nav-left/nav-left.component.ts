import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
@Component({
    selector: 'qy-nav-left',
    templateUrl: './nav-left.component.html',
    styleUrls: ['./nav-left.component.scss']
})
export class NavLeftComponent implements OnInit {
    @Input()
    navItems: any;
    @Input()
    isCollapsed: boolean;
    @Input()
    isSubMenu = false;

    constructor() {
    }

    ngOnInit() {
    }

    hasChildren(item) {
        return item.children ? item.children.length : item.children;
    }
}
