import { Component, OnInit } from '@angular/core';
import {WorkHistory} from "./work-history.interfaces";
import {ActivatedRoute, Router} from "@angular/router";
import {WorkHistoryModel} from "./work-history.classes";

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
    this.router.navigate(['social', 'profile', {outlets: {profile: ['work', 'history', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile']);
  }


}
