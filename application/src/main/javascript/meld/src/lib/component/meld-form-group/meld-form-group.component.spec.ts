import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldFormGroupComponent } from './meld-form-group.component';

describe('MeldFormGroupComponent', () => {
  let component: MeldFormGroupComponent;
  let fixture: ComponentFixture<MeldFormGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldFormGroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldFormGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
