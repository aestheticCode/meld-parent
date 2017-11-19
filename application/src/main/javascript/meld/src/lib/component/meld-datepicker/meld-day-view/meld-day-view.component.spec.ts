import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldMonthViewComponent } from './meld-month-view.component';

describe('MeldDayViewComponent', () => {
  let component: MeldMonthViewComponent;
  let fixture: ComponentFixture<MeldMonthViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldMonthViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldMonthViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
