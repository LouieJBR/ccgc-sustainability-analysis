import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import {HeaderComponent} from "./shared/header/header.component";
import {FooterComponent} from "./shared/footer/footer.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LandingPageComponent, HeaderComponent, FooterComponent], // Import RouterOutlet and your components directly
  template: `
    <app-header></app-header>
    <app-landing-page></app-landing-page>
    <app-footer></app-footer>
    <router-outlet></router-outlet> <!-- Router outlet to render routed components -->
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'CCGC';
}
