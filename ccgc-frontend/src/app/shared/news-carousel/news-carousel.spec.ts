import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewsCarousel} from './news-carousel';

describe('NewsSideBarComponent', () => {
  let component: NewsCarousel;
  let fixture: ComponentFixture<NewsCarousel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsCarousel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsCarousel);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
