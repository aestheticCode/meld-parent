import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RoleForm} from "./RoleForm";
import {Http, Response} from "@angular/http";
import {ActivatedRoute} from "@angular/router";
import {RoleFormModel} from "./RoleFormModel";
import {NgModel} from "@angular/forms";
import {Items} from "../../../lib/common/query/Items";
import {PermissionRow} from "./PermissionRow";

@Component({
  selector: 'app-role-form',
  templateUrl: 'role-form.component.html',
  styleUrls: ['role-form.component.css']
})
export class RoleFormComponent implements OnInit {

  public role: RoleForm;

  @ViewChild('name')
  private name: NgModel;

  @ViewChild('input')
  private input: ElementRef;


  constructor(private http: Http,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { role: any }) => {
      this.role = data.role || new RoleFormModel();
    });

    this.name
      .control
      .valueChanges
      .debounceTime(300)
      .subscribe((value: string) => {
        if (value != null) {
          const inputElement = this.input.nativeElement;
          this.http.post('service/usercontrol/role/form/validate',
            {name: value, id: this.role.id})
            .subscribe((res: Response) => {
              let isValid = res.json();
              if (!isValid) {
                inputElement.invalid = true;
              }
            });
        }
      });

  }


  permissions : Items<PermissionRow> = (query, response) => {
    this.http.post('service/usercontrol/permission/table', query)
      .subscribe((res: Response) => {
        const json = res.json();
        response(json.rows, json.size);
      });
  };

  onSave() {
    this.http.post('service/usercontrol/role/form', this.role)
      .subscribe((res: Response) => {
        this.role = res.json();
      });
  }

  onUpdate() {
    this.http.put(`service/usercontrol/role/${this.role.id}/form`, this.role)
      .subscribe((res: Response) => {
        this.role = res.json();
      });
  }

}
