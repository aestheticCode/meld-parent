import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldItemComponent } from './meld-item.component';

describe('MeldItemComponent', () => {
  let component: MeldItemComponent;
  let fixture: ComponentFixture<MeldItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
