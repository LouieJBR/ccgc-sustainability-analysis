import {Component} from '@angular/core';
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {RouterLink} from "@angular/router";
import {JumbotronContentComponent} from "../shared/jumbotron-content/jumbotron-content.component";
import {HeaderComponent} from "../shared/header/header.component";
import {FooterComponent} from "../shared/footer/footer.component";

@Component({
  selector: 'app-landing-page',
  standalone: true,
  templateUrl: './landing-page.component.html',
  imports: [
    MatTab,
    MatTabGroup,
    RouterLink,
    JumbotronContentComponent,
    HeaderComponent,
    FooterComponent
  ],
  styleUrls: ['./landing-page.component.css'] // Fixed 'styleUrl' to 'styleUrls'
})
export class LandingPageComponent {
  // You can add properties and methods here
}
