import { Component, ViewChild, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { HeaderComponent } from "./shared/header/header.component";
import { FooterComponent } from "./shared/footer/footer.component";
import { JumbotronContentComponent } from "./shared/jumbotron-content/jumbotron-content.component";
import { UserProfileComponent } from "./user-profile/user-profile.component";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { AuthService } from "@auth0/auth0-angular";
import { take, tap } from "rxjs";
import { UserService } from './service/user-service'; // Adjust the path as needed

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LandingPageComponent, HeaderComponent, FooterComponent, JumbotronContentComponent, HttpClientModule],
  template: `
    <router-outlet></router-outlet>
    <app-header></app-header>
    <app-landing-page></app-landing-page>
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'CCGC';

  constructor(private http: HttpClient, public auth: AuthService, private userService: UserService) {}

  ngOnInit() {
    // Subscribe to user logged in event
    this.userService.userLoggedIn$.subscribe(() => {
      this.getUserProfile();
    });
  }

  getUserProfile() {
    this.auth.idTokenClaims$.pipe(
      take(1),
      tap((token) => {
        if (token) {
          console.log(token)
          this.http.post('/api/users/register', { auth0_id: token['auth0UserId'], email: token.email }).subscribe();
        }
      })
    ).subscribe();
  }

  @ViewChild(UserProfileComponent) popup!: UserProfileComponent; // Reference to the popup component
}
