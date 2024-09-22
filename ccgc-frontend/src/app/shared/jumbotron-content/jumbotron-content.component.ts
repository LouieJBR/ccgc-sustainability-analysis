import {Component} from '@angular/core';
import {NewsCarousel} from "../news-carousel/news-carousel";
import {ContentCarousel} from "../content-carousel/content-carousel";

@Component({
  selector: 'app-jumbotron-content',
  standalone: true,
  imports: [NewsCarousel, ContentCarousel],
  templateUrl: './jumbotron-content.component.html',
  styleUrl: './jumbotron-content.component.css'
})
export class JumbotronContentComponent {

}
