import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldEditorNameDialogComponent } from './meld-editor-name-dialog.component';

describe('MeldEditorDialogComponent', () => {
  let component: MeldEditorNameDialogComponent;
  let fixture: ComponentFixture<MeldEditorNameDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldEditorNameDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldEditorNameDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
