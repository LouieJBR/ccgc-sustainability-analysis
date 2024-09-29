import {Component, inject, Inject} from '@angular/core';
import {DOCUMENT, NgIf} from "@angular/common";
import {AuthService} from "@auth0/auth0-angular";
import {UserProfileComponent} from "../../user-profile/user-profile.component";
import {AppComponent} from "../../app.component";
import {UserService} from "../../service/user-service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf, UserProfileComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isLoggedIn = false;
  constructor(@Inject(DOCUMENT) public document: Document, public auth: AuthService, private userService: UserService)
  {
    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      this.isLoggedIn = isAuthenticated;
      console.log(this.auth)

    });
  }

  //fires onclick of login button
  login() {
    this.auth.loginWithRedirect();
    this.userService.emitUserLoggedIn(); // Emit the user logged in event
}
}
