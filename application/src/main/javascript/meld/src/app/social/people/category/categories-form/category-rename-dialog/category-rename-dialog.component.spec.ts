import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryRenameDialogComponent } from './category-rename-dialog.component';

describe('CategoryRenameDialogComponent', () => {
  let component: CategoryRenameDialogComponent;
  let fixture: ComponentFixture<CategoryRenameDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoryRenameDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryRenameDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
