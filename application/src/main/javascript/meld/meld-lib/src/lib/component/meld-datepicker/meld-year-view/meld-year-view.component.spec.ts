import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldYearViewComponent } from './meld-year-view.component';

describe('MeldYearViewComponent', () => {
  let component: MeldYearViewComponent;
  let fixture: ComponentFixture<MeldYearViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldYearViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldYearViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
