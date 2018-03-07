import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvitationsViewComponent } from './invitations-view.component';

describe('InvitationsViewComponent', () => {
  let component: InvitationsViewComponent;
  let fixture: ComponentFixture<InvitationsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvitationsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvitationsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
