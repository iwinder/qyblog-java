import { Component, ViewChild, Input, AfterViewInit, ElementRef, NgZone, Output, EventEmitter, OnDestroy, forwardRef } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { EditorConfig } from './editor-config';

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
  onChange: Function = () => { };
  onTouched: Function = () => { };
  constructor(
    private el: ElementRef,
    private ngZone: NgZone
) {

}

  ngAfterViewInit(): void {
    this.init();
  }

  ngOnDestroy(): void {
    this.destroy();
  }
  writeValue(value: string): void {
    this.value = value;
    console.log('value', value);
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
    // this.mdeditor.readOnly = isDisabled;
    console.log('isDisabled', isDisabled);
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
    this.editormdConfig = new EditorConfig();
    this.editormdConfig.onload = () => {
      if (this.value) {
        this.mdeditor.setMarkdown(this.value);
      }
      this.onReady.emit();
    };
    this.editormdConfig.onchange = () => {
      this.updateValue(this.mdeditor.getMarkdown());
    };


    this.mdeditor = editormd(this.host.nativeElement.id, this.editormdConfig); // 创建编辑器

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
