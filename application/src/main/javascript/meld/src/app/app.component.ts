import { Component } from '@angular/core';
import {AppService} from "./app.service";
import {Link} from "../lib/common/rest/Link";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css']
})
export class AppComponent {

  public sideNavShow: boolean = true;

  public links : Link[];

  constructor(service : AppService) {
    this.links = service.configuration.links;
    service.event.subscribe((linksContainer) => {
      this.links = linksContainer.links;
    })
  }

  toggleSideNav() {
    this.sideNavShow = !this.sideNavShow;
  }

}
