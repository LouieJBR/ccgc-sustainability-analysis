import { Component } from '@angular/core';
import {MatTab, MatTabGroup} from "@angular/material/tabs";

@Component({
  selector: 'app-landing-page',
  standalone: true,
  templateUrl: './landing-page.component.html',
  imports: [
    MatTab,
    MatTabGroup
  ],
  styleUrls: ['./landing-page.component.css'] // Fixed 'styleUrl' to 'styleUrls'
})
export class LandingPageComponent {
  // You can add properties and methods here
}
