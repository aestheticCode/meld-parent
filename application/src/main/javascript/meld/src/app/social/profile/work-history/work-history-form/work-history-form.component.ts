import { Component, OnInit } from '@angular/core';
import {Http, Response} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {WorkHistory} from '../work-history.interfaces';
import {WorkHistoryModel} from '../work-history.classes';
import {CompanyModel} from '../company.classes';
import {Company} from '../company.interfaces';
import {Strings} from '../../../../../lib/common/utils/Strings';
import {Location} from '@angular/common';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-work-history-form',
  templateUrl: 'work-history-form.component.html',
  styleUrls: ['work-history-form.component.css']
})
export class WorkHistoryFormComponent implements OnInit {

  public workHistory: WorkHistory;

  constructor(private http: Http,
              private router : MeldRouterService) {
  }

  ngOnInit() {
    this.workHistory = this.router.data.workHistory || new WorkHistoryModel();
  }

  onCreateAddress() {
    this.workHistory.companies.push(new CompanyModel())
  }

  onDeleteAddress(address: Company) {
    if (this.workHistory.companies.length > 0) {
      let indexOf = this.workHistory.companies.indexOf(address);
      this.workHistory.companies.splice(indexOf, 1);
    }
  }

  onEdit() {
    if (this.workHistory.companies.length === 0) {
      this.workHistory.companies.push(new CompanyModel());
    }
  }

  onSave() {
    this.filterEmpty();
    this.http.post("service/social/user/current/work/history", this.workHistory)
      .subscribe((res: Response) => {
        this.workHistory = res.json();
        this.router.navigate(['social', 'profile', this.router.param.id,{outlets: {profile: ['work', 'history', 'view']}}]);
      })
  }

  onUpdate() {
    this.filterEmpty();
    this.http.put("service/social/user/current/work/history", this.workHistory)
      .subscribe((res: Response) => {
        this.workHistory = res.json();
        this.router.navigate(['social', 'profile', this.router.param.id,{outlets: {profile: ['work', 'history', 'view']}}]);
      })
  }

  onCancel() {
    this.filterEmpty();
    this.router.navigate(['social', 'profile', this.router.param.id,{outlets: {profile: ['work', 'history', 'view']}}]);
  }

  private filterEmpty() {
    this.workHistory.companies
      = this.workHistory.companies.filter((company) => {
      return Strings.isNotEmpty(company.name)
        || Strings.isNotEmpty(company.description)
        || Strings.isNotEmpty(company.start)
        || Strings.isNotEmpty(company.end)
    })
  }

}
