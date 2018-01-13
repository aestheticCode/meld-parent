import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {WorkHistory} from '../work-history.interfaces';
import {WorkHistoryModel} from '../work-history.classes';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-work-history-view',
  templateUrl: 'work-history-view.component.html',
  styleUrls: ['work-history-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class WorkHistoryViewComponent implements OnInit {

  public workHistory: WorkHistory;

  constructor(private router: MeldRouterService) {
  }

  ngOnInit() {
    this.workHistory = this.router.data.workHistory || new WorkHistoryModel();
  }

  onEdit() {
    this.router.navigate(['social', 'profile', this.router.param.id , {outlets: {profile: ['work', 'history', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile', this.router.param.id]);
  }


}
