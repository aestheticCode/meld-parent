import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldDialogComponent } from './meld-dialog.component';

describe('MeldDialogComponent', () => {
  let component: MeldDialogComponent;
  let fixture: ComponentFixture<MeldDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
