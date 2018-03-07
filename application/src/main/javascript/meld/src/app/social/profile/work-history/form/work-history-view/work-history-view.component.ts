import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {WorkHistory} from '../../work-history.interfaces';
import {MeldRouterService} from '../../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-work-history-view',
  templateUrl: 'work-history-view.component.html',
  styleUrls: ['work-history-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class WorkHistoryViewComponent implements OnInit {

  public workHistory: WorkHistory;

  constructor(private router: MeldRouterService) {
  }

  ngOnInit() {
    this.workHistory = this.router.data.workHistory;
  }

  onEdit() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['work', 'history', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile', this.router.param.id]);
  }


}
