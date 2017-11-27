import {
  AfterContentInit,
  Component,
  ContentChildren,
  ElementRef,
  HostListener,
  QueryList,
  ViewChild
} from '@angular/core';
import {MeldTabComponent} from "./meld-tab/meld-tab.component";

@Component({
  selector: 'meld-tabbar',
  templateUrl: 'meld-tabbar.component.html',
  styleUrls: ['meld-tabbar.component.css']
})
export class MeldTabBarComponent implements AfterContentInit {

  @ViewChild("slider")
  slider: ElementRef;

  @ViewChild("container")
  container: ElementRef;

  @ContentChildren(MeldTabComponent)
  tabBars: QueryList<MeldTabComponent>;

  index : number;

  ngAfterContentInit(): void {
    this.onDocumentResize();

    this.tabBars.first.enable();

    this.tabBars.forEach((tab1, index1) => tab1.activeChange.subscribe((value) => {
      this.tabBars.forEach((tab2, index2) => {
        if (value) {
          let containerWidth = this.container.nativeElement.offsetWidth;
          let sliderWidth = containerWidth / this.tabBars.length;
          this.index = index1;
          this.slider.nativeElement.style.left = this.index * sliderWidth + 'px'
        }
        if (index1 !== index2) {
          tab2.disable();
        }
      })
    }))
  }

  @HostListener('window:resize')
  onDocumentResize() {
    let containerWidth = this.container.nativeElement.offsetWidth;
    let sliderWidth = containerWidth / this.tabBars.length;
    this.slider.nativeElement.style.width = sliderWidth + 'px';
    this.slider.nativeElement.style.left = this.index * sliderWidth + 'px'
    this.tabBars.forEach((tab1) => {
      tab1.width = sliderWidth;
    });
  }

}
