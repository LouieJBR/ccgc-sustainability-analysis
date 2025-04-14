import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {LandingPageComponent} from './landing-page/landing-page.component';
import {HeaderComponent} from "./shared/header/header.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {AuthService} from "@auth0/auth0-angular";

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
export class AppComponent implements OnInit {
  title = 'CCGC';

  private auth = inject(AuthService);

  ngOnInit(): void {
    const params = new URLSearchParams(window.location.search);
    const hasCode = params.has('code');
    const hasState = params.has('state');

    if (hasCode && hasState) {
      this.auth.handleRedirectCallback().subscribe({
        next: () => {
          console.log('Redirect callback handled');
          // Optional: remove query string
          window.history.replaceState({}, '', window.location.pathname);
        },
        error: (err) => {
          console.error('Redirect handling failed:', err);
        }
      });
    }
  }

  @ViewChild(UserProfileComponent) popup!: UserProfileComponent;
}
