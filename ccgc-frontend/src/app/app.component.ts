import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component'; // Import your landing page component

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LandingPageComponent], // Import RouterOutlet and your components directly
  template: `
    <router-outlet>
      <app-landing-page></app-landing-page>
    </router-outlet> <!-- Router outlet to render routed components -->
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'CCGC';
}
