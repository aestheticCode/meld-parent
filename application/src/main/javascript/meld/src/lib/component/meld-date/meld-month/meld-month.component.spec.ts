import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldMonthComponent } from './meld-month.component';

describe('MeldMonthComponent', () => {
  let component: MeldMonthComponent;
  let fixture: ComponentFixture<MeldMonthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldMonthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldMonthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
