import { Component, ViewChild, Input, AfterViewInit, ElementRef, NgZone, Output, EventEmitter, OnDestroy, forwardRef } from '@angular/core';
import { EditorConfig } from '../../../editor/model/editor-config';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

declare var editormd: any;

const UEDITOR_VALUE_ACCESSOR = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => EditorMdComponent),
  multi: true
};

@Component({
  selector: 'qy-editor-md',
  templateUrl: './editor-md.component.html',
  styleUrls: ['./editor-md.component.scss'],
  providers: [UEDITOR_VALUE_ACCESSOR]
})
export class EditorMdComponent implements AfterViewInit, OnDestroy, ControlValueAccessor {



  @Input() editormdConfig: EditorConfig; // 配置选项

  // tslint:disable-next-line:no-output-on-prefix
  @Output() onReady = new EventEmitter();
  // tslint:disable-next-line:no-output-on-prefix
  @Output() onValueChange = new EventEmitter();
  // tslint:disable-next-line:no-output-on-prefix
  @Output() onFocus = new EventEmitter();

  @ViewChild('host') host;
  private mdeditor: any;
  private value: string;
  // conf = new EditorConfig();
  // markdown = '测试语句';
  onChange: Function = () => { };
  onTouched: Function = () => { };
  constructor(
    private el: ElementRef,
    private ngZone: NgZone
) {

}

  ngAfterViewInit(): void {
    this.init();
    // 当编辑器内容改变时，触发textarea的change事件
    // this.editor.on('change', function () {
    //   out.emit(textarea.val());
    // });
  }

  ngOnDestroy(): void {
    this.destroy();
  }
  writeValue(value: string): void {
    this.value = value;
    if (this.mdeditor) {
        this.mdeditor.setMarkdown(this.value);
    }
  }
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }
  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }
  setDisabledState?(isDisabled: boolean): void {
    this.editormdConfig.readOnly = isDisabled;
    if (isDisabled) {
      this.mdeditor.setDisabled();
  } else {
      this.mdeditor.setEnabled();
  }
  }

  init() {
    if (typeof editormd === 'undefined') {
      console.error('UEditor is missing');
      return;
    }

    console.log( 'this.host.nativeElement', this.host.nativeElement.id);
    this.mdeditor = editormd(this.host.nativeElement.id, this.editormdConfig); // 创建编辑器
    // this.mdeditor.addListener('ready', () => {
    //   if (this.value) {
    //       this.mdeditor.setContent(this.value);
    //   }
    //   this.onReady.emit();

    //   this.mdeditor.addEvent();
    // });

  }

  addEvent() {
    this.mdeditor.addListener('onchange', () => {
        this.updateValue(this.mdeditor.getMarkdown());
    });
    this.mdeditor.addListener('focus', () => {
        this.onFocus.emit();
    });
}

  updateValue(value: string) {
    this.ngZone.run(() => {
        this.value = value;

        this.onChange(this.value);
        this.onTouched();

        this.onValueChange.emit(this.value);
    });
 }

 destroy() {
  if (this.mdeditor) {
      this.mdeditor.removeListener('ready');
      this.mdeditor.removeListener('contentChange');
      this.mdeditor.editor.remove();
      this.mdeditor.destroy();
      this.mdeditor = null;
  }

}


  getMarkContent(): string {
    return this.mdeditor.getMarkdown();
  }

  getHtmlContent(): string {
    return this.mdeditor.getHTML();
  }
}
