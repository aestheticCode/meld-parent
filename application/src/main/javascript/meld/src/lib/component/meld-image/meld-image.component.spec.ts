/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MeldImageComponent } from './meld-image.component';

describe('MeldImageComponent', () => {
  let component: MeldImageComponent;
  let fixture: ComponentFixture<MeldImageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldImageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
