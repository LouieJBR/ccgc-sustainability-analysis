import {Component, ViewChild} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {LandingPageComponent} from './landing-page/landing-page.component';
import {HeaderComponent} from "./shared/header/header.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LandingPageComponent, HeaderComponent],
  template: `
    <router-outlet></router-outlet>
    <app-header></app-header><!-- Router outlet to render routed components -->
    <app-landing-page></app-landing-page>

  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'CCGC';

  @ViewChild(UserProfileComponent) popup!: UserProfileComponent;
}
