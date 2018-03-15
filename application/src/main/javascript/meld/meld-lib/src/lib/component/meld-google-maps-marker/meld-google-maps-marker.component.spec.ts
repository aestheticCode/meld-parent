import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldGoogleMapsMarkerComponent } from './meld-google-maps-marker.component';

describe('MeldGoogleMapsMarkerComponent', () => {
  let component: MeldGoogleMapsMarkerComponent;
  let fixture: ComponentFixture<MeldGoogleMapsMarkerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldGoogleMapsMarkerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldGoogleMapsMarkerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
