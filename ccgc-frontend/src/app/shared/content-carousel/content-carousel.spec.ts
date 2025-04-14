import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ContentCarouselComponent} from './content-carousel';

describe('ContentSideBarComponent', () => {
  let component: ContentCarouselComponent;
  let fixture: ComponentFixture<ContentCarouselComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContentCarouselComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContentCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
