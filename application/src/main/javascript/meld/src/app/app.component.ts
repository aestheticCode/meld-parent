import {Component, HostListener, ViewEncapsulation} from '@angular/core';
import {AppService} from "./app.service";
import {Link} from "../lib/common/rest/Link";
import {NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router, RouterEvent} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class AppComponent {

  public sideNavShow: boolean = true;

  public sideNavOption : string = "side";

  public links : Link[];

  public loading : boolean = false;

  constructor(service : AppService,
              router : Router) {
    router.events.subscribe((event: RouterEvent) => {
      if (event instanceof NavigationStart) {
        this.loading = true
      }
      if (event instanceof NavigationEnd) {
        this.loading = false
      }
      // Set loading state to false in both of the below events to hide the spinner in case a request fails
      if (event instanceof NavigationCancel) {
        this.loading = false
      }
      if (event instanceof NavigationError) {
        this.loading = false
      }
    });

    this.links = service.configuration.links;
    service.event.subscribe((linksContainer) => {
      this.links = linksContainer.links;
    });

    let matchMedia = window.matchMedia( "(max-width: 1000px)" );

    if (matchMedia.matches) {
      this.sideNavShow = false;
      this.sideNavOption = "over";
    }

    matchMedia.addListener((listener) => {
      this.sideNavShow = false;
      this.sideNavOption = "over";
    });


    window.matchMedia( "(max-width: 1050px)" ).addListener((listener) => {
      this.sideNavShow = true;
      this.sideNavOption = "side";
    });
  }

  toggleSideNav() {
    this.sideNavShow = !this.sideNavShow;
  }

  closeSideNav() {
    if (this.sideNavOption === "over") {
      this.sideNavShow = false;
    }
  }

}
