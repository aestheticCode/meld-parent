import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeldYoutubeItemComponent } from './meld-youtube-item.component';

describe('MeldYoutubeItemComponent', () => {
  let component: MeldYoutubeItemComponent;
  let fixture: ComponentFixture<MeldYoutubeItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeldYoutubeItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeldYoutubeItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
