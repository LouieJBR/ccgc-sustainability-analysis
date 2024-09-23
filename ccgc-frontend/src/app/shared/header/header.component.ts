import {Component, Inject} from '@angular/core';
import {DOCUMENT, NgIf} from "@angular/common";
import {AuthService} from "@auth0/auth0-angular";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isLoggedIn = false;
  userName: string | null = null;


  //The isLoggedIn variable tracks whether the user is authenticated by subscribing to auth.isAuthenticated$
  constructor(@Inject(DOCUMENT) public document: Document, public auth: AuthService) {
    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      this.isLoggedIn = isAuthenticated;

      if (isAuthenticated) {
        this.auth.user$.subscribe(user => {
            this.userName = user?.name || null;
            console.log(user)
        });
      } else {
        this.userName = null; // Reset when logged out
      }
    });
  }

  //fires onclick of login button
  login() {
    this.auth.loginWithRedirect();
  }}
