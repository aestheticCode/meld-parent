import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldTabBarComponent } from './meld-tabbar.component';

describe('MeldTabBarComponent', () => {
  let component: MeldTabBarComponent;
  let fixture: ComponentFixture<MeldTabBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldTabBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldTabBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
