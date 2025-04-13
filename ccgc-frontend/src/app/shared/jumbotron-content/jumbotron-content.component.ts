import {Component} from '@angular/core';
import {SnippetProfilerComponent} from "../snippet-profiler/snippet-profiler";
import {ContentCarouselComponent} from "../content-carousel/content-carousel";

@Component({
  selector: 'app-jumbotron-content',
  standalone: true,
  imports: [SnippetProfilerComponent, ContentCarouselComponent],
  templateUrl: './jumbotron-content.component.html',
  styleUrl: './jumbotron-content.component.css'
})
export class JumbotronContentComponent {

}
