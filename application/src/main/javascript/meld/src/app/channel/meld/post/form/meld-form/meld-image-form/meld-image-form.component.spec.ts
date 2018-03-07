import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldImageFormComponent } from './meld-image-form.component';

describe('MeldImageFormComponent', () => {
  let component: MeldImageFormComponent;
  let fixture: ComponentFixture<MeldImageFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldImageFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldImageFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
