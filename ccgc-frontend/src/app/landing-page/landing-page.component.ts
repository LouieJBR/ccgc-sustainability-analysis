import {Component} from '@angular/core';
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {RouterLink} from "@angular/router";
import {JumbotronContentComponent} from "../shared/jumbotron-content/jumbotron-content.component";

@Component({
  selector: 'app-landing-page',
  standalone: true,
  templateUrl: './landing-page.component.html',
  imports: [
    MatTab,
    MatTabGroup,
    RouterLink,
    JumbotronContentComponent
  ],
  styleUrls: ['./landing-page.component.css'] // Fixed 'styleUrl' to 'styleUrls'
})
export class LandingPageComponent {
  // You can add properties and methods here
}
