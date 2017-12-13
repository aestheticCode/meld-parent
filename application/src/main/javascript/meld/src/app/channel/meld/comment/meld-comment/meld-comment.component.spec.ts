import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldCommentComponent } from './meld-comment.component';

describe('MeldCommentComponent', () => {
  let component: MeldCommentComponent;
  let fixture: ComponentFixture<MeldCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
