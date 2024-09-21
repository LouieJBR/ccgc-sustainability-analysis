import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import {HeaderComponent} from "./shared/header/header.component"; // Import your landing page component

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LandingPageComponent, HeaderComponent], // Import RouterOutlet and your components directly
  template: `
    <app-header></app-header>
    <app-landing-page></app-landing-page>
    <router-outlet></router-outlet> <!-- Router outlet to render routed components -->
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'CCGC';
}
