import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldTextItemComponent } from './meld-text-item.component';

describe('MeldTextItemComponent', () => {
  let component: MeldTextItemComponent;
  let fixture: ComponentFixture<MeldTextItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldTextItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldTextItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
