import {Observable} from 'rxjs/Observable';
import {ViewChild} from '@angular/core';
import {FormArray, FormControl, FormGroup, NgForm} from '@angular/forms';

export abstract class AbstractForm<F> {

  @ViewChild('form')
  public ngForm: NgForm;

  public preRequest(): boolean {
    if (this.ngForm) {
      for (let property in this.ngForm.controls) {
        this.ngForm.controls[property].markAsTouched();
      }
      return this.ngForm.valid;
    }
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

  public validateAllFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});
      } else if (control instanceof FormGroup) {
        this.validateAllFields(control);
      } else if (control instanceof FormArray) {
        control.controls.forEach((control) => this.validateAllFields(control as FormGroup));
      }
    });
    return formGroup.valid;
  }




}
