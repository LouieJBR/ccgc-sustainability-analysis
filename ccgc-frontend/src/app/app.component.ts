import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {LandingPageComponent} from './landing-page/landing-page.component';
import {HeaderComponent} from "./shared/header/header.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {filter, from, switchMap} from "rxjs";
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
    from(this.auth.handleRedirectCallback()).pipe(
      switchMap(() => this.auth.isAuthenticated$),
      filter(isAuthenticated => isAuthenticated)
    ).subscribe(() => {
      this.auth.getAccessTokenSilently().subscribe(token => {
        console.log('Token ready after redirect:', token);
      });
    }, err => {
      console.error('Error during Auth0 redirect callback:', err);
    });
  }

  @ViewChild(UserProfileComponent) popup!: UserProfileComponent;
}
