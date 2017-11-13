import {ChangeDetectorRef, Component, Input} from '@angular/core';
import {Link} from "../../common/rest/Link";

@Component({
  selector: 'meld-image',
  templateUrl: 'meld-image.component.html',
  styleUrls: ['meld-image.component.css']
})
export class MeldImageComponent {

  @Input()
  public src : Link;

  public visible : boolean = true;

  constructor(private changeDetectorRefs:ChangeDetectorRef) {}

  reload() {
    this.visible = false;
    this.changeDetectorRefs.detectChanges();
    this.visible = true;
    this.changeDetectorRefs.detectChanges();
  }

}
