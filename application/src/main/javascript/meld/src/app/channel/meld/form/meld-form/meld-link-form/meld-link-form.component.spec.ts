import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldLinkFormComponent } from './meld-link-form.component';

describe('MeldLinkFormComponent', () => {
  let component: MeldLinkFormComponent;
  let fixture: ComponentFixture<MeldLinkFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldLinkFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldLinkFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
