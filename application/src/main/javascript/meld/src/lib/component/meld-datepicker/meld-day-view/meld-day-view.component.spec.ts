import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldDayViewComponent } from './meld-day-view.component';

describe('MeldDayViewComponent', () => {
  let component: MeldDayViewComponent;
  let fixture: ComponentFixture<MeldDayViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldDayViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldDayViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
