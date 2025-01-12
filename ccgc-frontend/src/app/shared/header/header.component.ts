import { Component, Inject, OnInit } from '@angular/core';
import { DOCUMENT, NgIf } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';
import { HttpClient } from '@angular/common/http';
import { UserProfileComponent } from '../../user-profile/user-profile.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf, UserProfileComponent],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
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

  ngOnInit(): void {
    // Check if we're in a redirect state
    this.auth.handleRedirectCallback().subscribe({
      next: async (result) => {
        console.log('Redirect callback handled successfully:', result);
        try {
          // Send token to backend after redirect
          await this.sendTokenToBackend();
        } catch (error) {
          console.error('Error sending token to backend after redirect', error);
        }
      },
      error: (error) => {
        console.error('Error handling redirect callback', error);
      }
    });
  }

  // Fires onclick of login button
  login() {
    // Trigger login with redirect
    this.auth.loginWithRedirect();
  }

  // Handles the token after login and sends it to the backend
  async sendTokenToBackend() {
    try {
      // Wait for the access token from Auth0
      const token = this.auth.getAccessTokenSilently();
      console.log('Access Token:', token); // Log the token to verify it's being retrieved

      if (token) {
        const tokenPayload = {
          token,
          email: this.auth.user$.pipe(), // Get user email from Auth0
          name: this.auth.user$.pipe(),  // Get user name from Auth0
          provider: 'Auth0',  // Provider is Auth0
          providerId: this.auth.user$.pipe() // Auth0 user ID
        };

        // Send the token to the backend
        this.http.post('/api/auth/oauth', tokenPayload).subscribe({
          next: (response) => {
            console.log('User authenticated and data saved successfully:', response);
          },
          error: (error) => {
            console.error('Error sending token to backend', error);
          }
        });
      }
    } catch (error) {
      console.error('Error getting access token or sending it to the backend', error);
    }
  }
}
