import {Component} from '@angular/core';
import {NewsCarouselComponent} from "../news-carousel/news-carousel";
import {ContentCarouselComponent} from "../content-carousel/content-carousel";

@Component({
  selector: 'app-jumbotron-content',
  standalone: true,
  imports: [NewsCarouselComponent, ContentCarouselComponent],
  templateUrl: './jumbotron-content.component.html',
  styleUrl: './jumbotron-content.component.css'
})
export class JumbotronContentComponent {

}
