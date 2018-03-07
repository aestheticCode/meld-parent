import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldCommentDialogComponent } from './meld-comment-dialog.component';

describe('MeldCommentDialogComponent', () => {
  let component: MeldCommentDialogComponent;
  let fixture: ComponentFixture<MeldCommentDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldCommentDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldCommentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
