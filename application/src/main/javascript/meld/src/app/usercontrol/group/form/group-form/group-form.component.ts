import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Http, Response} from "@angular/http";
import {GroupForm} from "./GroupForm";
import {GroupFormModel} from "./GroupFormModel";
import {NgModel} from "@angular/forms";
import {RoleSelect} from '../../../role/multiselect/role-multiselect/RoleSelect';
import {Items} from '../../../../../lib/common/query/Items';

@Component({
  selector: 'app-group-form',
  templateUrl: './group-form.component.html',
  styleUrls: ['./group-form.component.css']
})
export class GroupFormComponent implements OnInit {

  public group: GroupForm;

  @ViewChild('name')
  public name: NgModel;

  @ViewChild('input')
  public input: any;


  constructor(private http: Http,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { group: any }) => {
      this.group = data.group || new GroupFormModel();
    });

    this.name
      .control
      .valueChanges
      .debounceTime(300)
      .subscribe((value: string) => {
        if (value != null) {
          const inputElement = this.input.nativeElement;
          this.http.post('service/usercontrol/group/form/validate',
            {name: value, id: this.group.id})
            .subscribe((res: Response) => {
              let isValid = res.json();
              if (!isValid) {
                inputElement.invalid = true;
              }
            });
        }
      });

  }

  roles : Items<RoleSelect> = (query, response) => {
    this.http.post('service/usercontrol/role/table', query)
      .subscribe((res: Response) => {
        const json = res.json();
        response(json.rows, json.size);
      });
  };

  onSave() {
    this.http.post('service/usercontrol/group/form', this.group)
      .subscribe((res: Response) => {
        this.group = res.json();
      });
  }

  onUpdate() {
    this.http.put(`service/usercontrol/group/${this.group.id}/form`, this.group)
      .subscribe((res: Response) => {
        this.group = res.json();
      });
  }


}
