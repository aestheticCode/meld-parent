import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldYoutubeFormComponent } from './meld-youtube-form.component';

describe('MeldYoutubeFormComponent', () => {
  let component: MeldYoutubeFormComponent;
  let fixture: ComponentFixture<MeldYoutubeFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldYoutubeFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldYoutubeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
