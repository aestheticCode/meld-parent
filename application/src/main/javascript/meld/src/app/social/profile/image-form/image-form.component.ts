import { Component, OnInit } from '@angular/core';
import {AppService} from "../../../app.service";

@Component({
  selector: 'app-image-form',
  templateUrl: './image-form.component.html',
  styleUrls: ['./image-form.component.css']
})
export class ImageFormComponent {

  image = this.service.configuration.user.image;

  constructor(private service : AppService) {}


}
