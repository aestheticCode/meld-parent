import {Component, ContentChildren, ElementRef, EventEmitter, Input, Output, QueryList} from '@angular/core';
import {Router, RouterLink, RouterLinkWithHref} from '@angular/router';

@Component({
  selector: 'meld-tab',
  templateUrl: 'meld-tab.component.html',
  styleUrls: ['meld-tab.component.css']
})
export class MeldTabComponent {

  @Input('label')
  label: string;

  @ContentChildren(RouterLink, {descendants: true})
  links: QueryList<RouterLink>;

  @Output("activeChange")
  activeChange : EventEmitter<boolean> = new EventEmitter();

  @Input()
  routerLinkActiveOptions: {exact: boolean} = {exact: false};

  constructor(private elementRef: ElementRef,
              private router : Router) {
    router.events.subscribe(() => this.activeChange.emit(this.active))
  }

  set width(value: number) {
    this.elementRef.nativeElement.style.width = value + 'px';
  }

  get active() : boolean {
    if (this.links) {
      return this.isLinkActive(this.router)(this.links.first);
    }
    return false;
  }

  private isLinkActive(router: Router): (link: (RouterLink|RouterLinkWithHref)) => boolean {
    return (link: RouterLink | RouterLinkWithHref) =>
      router.isActive(link.urlTree, this.routerLinkActiveOptions.exact);
  }

}
