import {Component, Inject} from '@angular/core';
import {DOCUMENT, NgIf} from "@angular/common";
import {AuthService} from "@auth0/auth0-angular";
import {UserProfileComponent} from "../../user-profile/user-profile.component";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {combineLatest, take} from 'rxjs';


@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf, UserProfileComponent,],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isLoggedIn = false;


constructor(
  @Inject(DOCUMENT) public document: Document,
  public auth: AuthService,
  private http: HttpClient
) {
  combineLatest([this.auth.isAuthenticated$, this.auth.user$])
    .pipe(take(1))
    .subscribe(([isAuthenticated, user]) => {
      this.isLoggedIn = isAuthenticated;

      if (isAuthenticated && user) {
        const payload = {
          auth0Id: user.sub,
          name: user.name,
          email: user.email
        };

        this.http.post('https://ccgc-backend-dxdqfmcaexa3a2c3.uksouth-01.azurewebsites.net/api/auth/oauth', payload).subscribe({
          next: () => console.log('User connected with backend'),
          error: err => console.error('Failed to connect user:', err)
        });
      }
    });
}

login() {
    this.auth.loginWithRedirect({
      authorizationParams: {
        audience: environment.auth.audience,
        scope: 'openid profile email offline_access',
        redirect_uri: environment.auth.redirectUri
      }
    });
  }
}
