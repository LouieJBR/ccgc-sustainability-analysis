import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {LandingPageComponent} from './landing-page/landing-page.component';
import {HeaderComponent} from "./shared/header/header.component";
import {FooterComponent} from "./shared/footer/footer.component";
import {JumbotronContentComponent} from "./shared/jumbotron-content/jumbotron-content.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LandingPageComponent, HeaderComponent, FooterComponent, JumbotronContentComponent], // Import RouterOutlet and your components directly
  template: `
    <router-outlet></router-outlet> <!-- Router outlet to render routed components -->
    <app-header></app-header>
    <app-jumbotron-content></app-jumbotron-content>
    <app-landing-page></app-landing-page>
    <app-footer></app-footer>
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'CCGC';
}
