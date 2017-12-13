import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldPhotoFormComponent } from './meld-photo-form.component';

describe('MeldPhotoFormComponent', () => {
  let component: MeldPhotoFormComponent;
  let fixture: ComponentFixture<MeldPhotoFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldPhotoFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldPhotoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
