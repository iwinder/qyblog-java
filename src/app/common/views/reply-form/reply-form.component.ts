import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { CommentAgent } from 'app/common/entity/comment-agent';
import { Comment } from 'app/common/entity/comment';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';




@Component({
    selector: 'qy-reply-form',
    templateUrl: './reply-form.component.html',
    styleUrls: ['./reply-form.component.scss']
})
export class ReplyFormComponent implements OnInit {

    @Input() target: CommentAgent;
    @Input() parent: Comment;
    @Input() comment: Comment;
    @Output() save: EventEmitter<any> = new EventEmitter();
    @Output() clearn: EventEmitter<any> = new EventEmitter();
    validateForm: FormGroup;
    page = 1;
    textFiltering: string = "暂无";


    constructor(private sanitizer: DomSanitizer,
        private fb: FormBuilder) {
    }

    ngOnInit() {
        let obj = this.comment || new Comment();
        this.validateForm = this.fb.group({
            author: [ obj.author, [ Validators.required ]],
            email: [obj.email, [ Validators.required, Validators.email ]],
            url: [ obj.url],
            content: [obj.content, [ Validators.required ]]
        });
    }

    submitForm($event) {
        $event.preventDefault();
        for (let key of Object.keys(this.validateForm.controls)) {
          this.validateForm.controls[ key ].markAsDirty();
          this.validateForm.controls[ key ].updateValueAndValidity();
        }
        if (this.validateForm.invalid) {
            return;
        }
        let link = this.getFormControl('url').value;
        if (link) {
            this.getFormControl('url').setValue (/http:\/\/|https:\/\//ig.test(link) ? link : "http://" + link);
        }
        let values = this.validateForm.value;
        values["agent"] = this.target;
        values["agentTargetId"] = this.target.id;
        if (this.parent != null) {
            values["parent"] = this.parent;
            values["parentId"] = this.parent.id;
        }
        this.save.emit({ originalEvent: event, value: values });
    }
    clearnForm($event) {
        this.getFormControl('content').setValue(null);
        this.clearn.emit({ originalEvent: event});
    }

    markAsDirty() {
        for (let key of Object.keys(this.validateForm.controls)) {
            this.validateForm.controls[key].markAsDirty();
        }
    }
    getFormControl(name) {
        return this.validateForm.controls[name];
    }
}
