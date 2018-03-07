import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldTextFormComponent } from './meld-text-form.component';

describe('MeldTextFormComponent', () => {
  let component: MeldTextFormComponent;
  let fixture: ComponentFixture<MeldTextFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldTextFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldTextFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
