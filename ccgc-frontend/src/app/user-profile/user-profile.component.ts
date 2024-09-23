import {Component, Inject} from '@angular/core';
import {DOCUMENT, NgIf} from "@angular/common";
import {AuthService, User} from "@auth0/auth0-angular";

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent {
  isLoggedIn = false;
  isVisible = false;

  user: User | null | undefined = User
  open() {
    this.isVisible = true;
  }

  close() {
    this.isVisible = false;
  }

  //The isLoggedIn variable tracks whether the user is authenticated by subscribing to auth.isAuthenticated$
  constructor(@Inject(DOCUMENT) public document: Document, public auth: AuthService) {
    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      this.isLoggedIn = isAuthenticated;

      if (isAuthenticated) {
        this.auth.user$.subscribe(user => {
          this.user = user;
        });
      } else {
        this.user = null; // Reset when logged out
      }
    });
  }
}
