import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldDatePickerComponent } from './meld-datepicker.component';

describe('MeldDateickerComponent', () => {
  let component: MeldDatePickerComponent;
  let fixture: ComponentFixture<MeldDatePickerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldDatePickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldDatePickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
