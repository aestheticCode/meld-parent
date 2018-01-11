import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

export abstract class AbstractForm<F> {

  constructor(public http: HttpClient) {
  }

  public preRequest(): boolean {
    return true;
  }

  public postRequest(form: F) {
  }

  public abstract saveRequest(): Observable<F>;

  public abstract updateRequest(): Observable<F>

  public abstract deleteRequest(): Observable<F>

  onSave() {
    if (this.preRequest()) {
      this.saveRequest()
        .subscribe((res: F) => {
          this.postRequest(res);
        });
    }
  }

  onUpdate() {
    if (this.preRequest()) {
      this.updateRequest()
        .subscribe((res: F) => {
          this.postRequest(res);
        });
    }
  }

  onCancel() {
    this.preRequest();
    this.postRequest(null);
  }

  onDelete() {
    if (this.preRequest()) {
      this.deleteRequest()
        .subscribe(() => {
          this.postRequest(null);
        });
    }
  }


}
