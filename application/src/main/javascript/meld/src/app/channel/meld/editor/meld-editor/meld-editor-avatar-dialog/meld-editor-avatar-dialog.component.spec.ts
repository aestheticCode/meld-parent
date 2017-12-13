import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldEditorAvatarDialogComponent } from './meld-editor-avatar-dialog.component';

describe('MeldEditorAvatarDialogComponent', () => {
  let component: MeldEditorAvatarDialogComponent;
  let fixture: ComponentFixture<MeldEditorAvatarDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldEditorAvatarDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldEditorAvatarDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
