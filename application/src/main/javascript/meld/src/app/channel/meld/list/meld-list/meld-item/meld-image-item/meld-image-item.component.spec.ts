import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldImageItemComponent } from './meld-image-item.component';

describe('MeldImageItemComponent', () => {
  let component: MeldImageItemComponent;
  let fixture: ComponentFixture<MeldImageItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldImageItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldImageItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
