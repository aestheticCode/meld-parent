import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkHistoryViewComponent } from './work-history-view.component';

describe('WorkHistoryViewComponent', () => {
  let component: WorkHistoryViewComponent;
  let fixture: ComponentFixture<WorkHistoryViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkHistoryViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkHistoryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
