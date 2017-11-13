import {
  Component,
  Input,
  OnInit,
  QueryList,
  TemplateRef,
  ContentChild,
  ViewChild,
  ViewChildren,
  ElementRef, Output, EventEmitter
} from "@angular/core";
import {Items} from "./Items";
import {Params} from "./Params";

@Component({
  selector: 'meld-list',
  templateUrl: 'meld-list.component.html',
  styleUrls: ['meld-list.component.css']
})
export class MeldListComponent implements OnInit {

  @Input()
  index : number = 0;

  @Output()
  indexChange : EventEmitter<number> = new EventEmitter<number>();

  @Input()
  scrollTop : number = 0;

  @Output()
  scrollTopChange : EventEmitter<number> = new EventEmitter<number>();

  window: Array<any>;

  @Output() rendered : EventEmitter<any> = new EventEmitter<any>();

  @Input('items') items: Items;

  @ContentChild(TemplateRef) templateRef: TemplateRef<any>;

  @ViewChild('viewPort') viewPort: ElementRef;

  @ViewChildren('windowElements') windowElements: QueryList<ElementRef>;

  ngOnInit(): void {
    if (this.items) {
      this.items(new Params(this.index, 50), (data: Array<any>) => {
        this.window = data;
      });
    }

    const viewPort: HTMLDivElement = this.viewPort.nativeElement;

    viewPort.onscroll = (event) => {
      const element = event.srcElement;
      if (element.scrollHeight - element.scrollTop === element.clientHeight && this.window.length == 50) {
        this.index += 25;
        this.indexChange.emit(this.index);
        this.items(new Params(this.index, 50), (data: Array<any>) => {
          this.window = data;
        });
        let scrollDelta = 0;
        this.windowElements.forEach((item: ElementRef, index: number) => {
          if (index <= 25) {
            scrollDelta += item.nativeElement.offsetHeight;
          }
        });
        viewPort.scrollTop = element.scrollHeight - scrollDelta - element.clientHeight;
      }

      if (element.scrollTop == 0 && this.index != 0) {
        this.index -= 25;
        this.indexChange.emit(this.index);
        this.items(new Params(this.index, 50), (data: Array<any>) => {
          this.window = data;
        });
        let scrollDelta = 0;
        this.windowElements.forEach((item: ElementRef, index: number) => {
          if (index >= 25) {
            scrollDelta += item.nativeElement.offsetHeight;
          }
        });
        viewPort.scrollTop = scrollDelta;
      }

      if (this.scrollTop != viewPort.scrollTop) {
        this.scrollTop = viewPort.scrollTop;
        this.scrollTopChange.emit(this.scrollTop);
      }
    };
  }

  refresh() {
    if (this.items) {
      this.index = 0;
      this.scrollTop = 0;
      this.indexChange.emit(this.index);
      this.scrollTopChange.emit(this.scrollTop);

      this.items(new Params(this.index, 50), (data: Array<any>) => {
        this.window = data;
      });
    }
  }

  onRendered() {
    const viewPort: HTMLDivElement = this.viewPort.nativeElement;
    viewPort.scrollTop = this.scrollTop;
    this.rendered.emit();
  }

}
