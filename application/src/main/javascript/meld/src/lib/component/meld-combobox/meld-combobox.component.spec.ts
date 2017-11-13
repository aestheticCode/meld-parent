import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldComboBoxComponent } from './meld-combobox.component';

describe('MeldComboBoxComponent', () => {
  let component: MeldComboBoxComponent;
  let fixture: ComponentFixture<MeldComboBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldComboBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldComboBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
