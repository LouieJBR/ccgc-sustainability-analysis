import {Component, Inject} from '@angular/core';
import {DOCUMENT, NgIf} from "@angular/common";
import {AuthService} from "@auth0/auth0-angular";
import {UserProfileComponent} from "../../user-profile/user-profile.component";
import {HttpClient} from "@angular/common/http";

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
    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      this.isLoggedIn = isAuthenticated;

      if (isAuthenticated) {
        this.auth.user$.subscribe(user => {
          // ✅ Sync with backend
          if (user) {
            const payload = {
              auth0Id: user.sub,
              name: user.name,
              email: user.email
            };

            this.http.post('http://localhost:8080/api/auth/oauth', payload).subscribe({
              next: () => console.log('User synced with backend'),
              error: err => console.error('Failed to sync user:', err)
            });
          }
        });
      }
    });
  }

  login() {
    this.auth.loginWithRedirect();
  }
}
