import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldTableMenuDialogComponent } from './meld-table-menu-dialog.component';

describe('MeldTableMenuDialogComponent', () => {
  let component: MeldTableMenuDialogComponent;
  let fixture: ComponentFixture<MeldTableMenuDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldTableMenuDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldTableMenuDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
