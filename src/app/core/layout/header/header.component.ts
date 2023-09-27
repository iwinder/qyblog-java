import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';


@Component({
    selector: 'qy-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeadertComponent implements OnInit {

    constructor(private sanitizer: DomSanitizer) {
    }

    ngOnInit() {

    }


}
