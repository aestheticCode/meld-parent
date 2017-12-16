import {Component, HostListener} from '@angular/core';
import {AppService} from "./app.service";
import {Link} from "../lib/common/rest/Link";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css']
})
export class AppComponent {

  public sideNavShow: boolean = true;

  public sideNavOption : string = "side";

  public links : Link[];

  constructor(service : AppService) {
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

}
