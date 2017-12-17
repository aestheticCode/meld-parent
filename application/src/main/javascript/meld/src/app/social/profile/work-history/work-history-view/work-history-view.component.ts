import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {WorkHistory} from '../work-history.interfaces';
import {WorkHistoryModel} from '../work-history.classes';

@Component({
  selector: 'app-work-history-view',
  templateUrl: 'work-history-view.component.html',
  styleUrls: ['work-history-view.component.css']
})
export class WorkHistoryViewComponent implements OnInit {

  public workHistory: WorkHistory;

  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { workHistory: WorkHistory }) => {
      this.workHistory = data.workHistory || new WorkHistoryModel();
    });
  }

  onEdit() {
    this.route.parent.params.subscribe((params) => {
      this.router.navigate(['social', 'profile', params['id'] , {outlets: {profile: ['work', 'history', 'edit']}}]);
    });
  }

  onCancel() {
    this.route.parent.params
      .subscribe((param) => {
        this.router.navigate(['social', 'profile', param['id']]);
      });
  }


}
