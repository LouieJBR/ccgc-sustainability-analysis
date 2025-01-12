import {Component, Inject} from '@angular/core';
import { DOCUMENT, NgIf } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';
import {HttpClient} from '@angular/common/http';
import { UserProfileComponent } from '../../user-profile/user-profile.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf, UserProfileComponent],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  isLoggedIn = false;

  constructor(
    @Inject(DOCUMENT) public document: Document,
    public auth: AuthService,
    private http: HttpClient
  ) {
    // Subscribe to the authentication status
    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      this.isLoggedIn = isAuthenticated;
    });
  }

  // Fires onclick of login button
  login() {
    this.auth.loginWithRedirect();
  }

  // Handles the token after login and sends it to the backend
  async sendTokenToBackend() {
    try {
      // Get the access token from Auth0
      const token = this.auth.getAccessTokenSilently();
      console.log('Access Token:', token); // Log the token to verify it's being retrieved

      if (token) {
        // Create a payload to send to the backend
        const tokenPayload = {
          token,
          email: this.auth.user$.pipe(),  // Get user email from Auth0
          name:  this.auth.user$.pipe(),   // Get user name from Auth0
          provider: 'Auth0',  // Provider is Auth0
          providerId: this.auth.user$.pipe() // Auth0 user ID
        };

        // Send the token to the backend
        this.http.post('/api/auth/oauth', tokenPayload).subscribe(response => {
          console.log('User authenticated and data saved successfully:', response);
          // Optionally, store JWT token from the backend if needed
        });
      }
    } catch (error) {
      console.error('Error getting access token or sending it to the backend', error);
    }
  }
}
