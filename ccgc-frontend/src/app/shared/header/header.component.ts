import {Component, Inject} from '@angular/core';
import {DOCUMENT, NgIf} from "@angular/common";
import {AuthService} from "@auth0/auth0-angular";
import {UserProfileComponent} from "../../user-profile/user-profile.component";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf, UserProfileComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isLoggedIn = false;
  constructor(@Inject(DOCUMENT) public document: Document, public auth: AuthService) {
    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      this.isLoggedIn = isAuthenticated;
    });
  }

  //fires onclick of login button
  login() {
    this.auth.loginWithRedirect();
  }}
