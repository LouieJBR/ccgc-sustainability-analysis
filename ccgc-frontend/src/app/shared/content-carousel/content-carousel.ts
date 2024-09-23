import {Component} from '@angular/core';
import {NgFor} from "@angular/common";

@Component({
  selector: 'app-content-carousel',
  standalone: true,
  imports: [NgFor],
  templateUrl: './content-carousel.html',
  styleUrl: './content-carousel.css'
})
export class ContentCarouselComponent {
  factList = [
    { fact: 'Recycling one aluminum can saves enough energy to run a TV for three hours.' },
    { fact: 'Data centers consume about 1% of the world’s electricity.' },
    { fact: 'Turning off lights when not in use can reduce your energy bill by up to 10%.' }
  ];
}
