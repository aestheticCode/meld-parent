import {
  AfterContentInit, Component, ContentChildren, ElementRef, HostListener, QueryList, ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {MeldTabComponent} from './meld-tab/meld-tab.component';

@Component({
  selector: 'meld-tabbar',
  templateUrl: 'meld-tabbar.component.html',
  styleUrls: ['meld-tabbar.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldTabBarComponent implements AfterContentInit {

  @ViewChild('slider')
  slider: ElementRef;

  @ViewChild('container')
  container: ElementRef;

  @ContentChildren(MeldTabComponent)
  tabBars: QueryList<MeldTabComponent>;

  ngAfterContentInit(): void {
    this.onDocumentResize();

    this.tabBars.forEach((tab, index, array) => {
      this.setCursorPosition(tab, index, array);

      tab.activeChange.subscribe((value) => {
        this.setCursorPosition(tab, index, array);
      });
    });
  }

  @HostListener('window:resize')
  onDocumentResize() {
    this.tabBars.forEach((tab, index, array) => {
      this.setCursorPosition(tab, index, array);
    });
  }

  private setCursorPosition(tab, index, array : MeldTabComponent[]) {
    let containerWidth = this.container.nativeElement.offsetWidth;
    let sliderWidth = containerWidth / this.tabBars.length;

    if (array.find((tab) => tab.active)) {
      if (tab.active) {
        this.slider.nativeElement.style.width = sliderWidth + 'px';
        this.slider.nativeElement.style.left = index * sliderWidth + 'px';
      }
    } else {
      this.slider.nativeElement.style.width = 0;
    }
    tab.width = sliderWidth;
  }

}
