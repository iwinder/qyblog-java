import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';

@Component({
    selector: 'qy-contnet',
    templateUrl: './content.component.html',
    styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit {
    isCollapsed = false;
    triggerTemplate = null;
    @ViewChild('trigger') customTrigger: TemplateRef<void>;

    constructor() {
    }

    ngOnInit() {
        console.log('ContentComponent:', 22222);
    }

        /** custom trigger can be TemplateRef **/
        changeTrigger(): void {
            this.triggerTemplate = this.customTrigger;
          }

    choseIsCollapsed(isCollapsed) {
        this.isCollapsed = isCollapsed;
    }
}
