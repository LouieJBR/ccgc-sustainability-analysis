import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ContentCarousel} from './content-carousel';

describe('ContentSideBarComponent', () => {
  let component: ContentCarousel;
  let fixture: ComponentFixture<ContentCarousel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContentCarousel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContentCarousel);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
