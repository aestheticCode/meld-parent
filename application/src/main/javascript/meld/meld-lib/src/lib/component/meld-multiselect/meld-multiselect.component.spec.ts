import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldMultiSelectComponent } from './meld-multiselect.component';

describe('MeldMultiselectComponent', () => {
  let component: MeldMultiSelectComponent;
  let fixture: ComponentFixture<MeldMultiSelectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldMultiSelectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldMultiSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
