import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldTableFilterDialogComponent } from './meld-table-filter-dialog.component';

describe('MeldTableFilterDialogComponent', () => {
  let component: MeldTableFilterDialogComponent;
  let fixture: ComponentFixture<MeldTableFilterDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldTableFilterDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldTableFilterDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
