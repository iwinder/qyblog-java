import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
@Component({
    selector: 'qy-nav-left',
    templateUrl: './nav-left.component.html',
    styleUrls: ['./nav-left.component.scss']
})
export class NavLeftComponent implements OnInit {
    isCollapsed = false;

    constructor() {
    }

    ngOnInit() {
    }
}
