import {Component} from '@angular/core';

@Component({
  selector: 'app-news-carousel',
  template: `
    <div class="carousel-container">
      <h3>{{ newsItems[currentIndex].title }}</h3>
      <p>{{ newsItems[currentIndex].content }}</p>
    </div>
  `,
  standalone: true,
  styles: [`
    .carousel-container {
      text-align: center;
    }
  `]
})
export class NewsCarouselComponent {
  newsItems = [
    { title: 'Sustainability News 1', content: 'Fact 1 about sustainability.' },
    { title: 'Sustainability News 2', content: 'Fact 2 about sustainability.' },
    { title: 'Sustainability News 3', content: 'Fact 3 about sustainability.' }
  ];

  currentIndex = 0;

  constructor() {
    this.startAutoChange();
  }

  startAutoChange() {
    setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.newsItems.length;
    }, 5000); // Change every 5 seconds
  }
}
