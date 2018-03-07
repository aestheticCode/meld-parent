import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldFormComponent } from './meld-form.component';

describe('MeldFormComponent', () => {
  let component: MeldFormComponent;
  let fixture: ComponentFixture<MeldFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
