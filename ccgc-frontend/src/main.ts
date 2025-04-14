import {bootstrapApplication} from '@angular/platform-browser';
import {AppComponent} from './app/app.component';
import {provideAuth0} from '@auth0/auth0-angular';
import {provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {environment} from './environments/environment';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptorsFromDi()),
    provideAuth0({
      domain: environment.auth.domain,
      clientId: environment.auth.clientId,
      authorizationParams: {
        redirect_uri: environment.auth.redirectUri,
        audience: environment.auth.audience,
        scope: 'openid profile email offline_access',
      },
      cacheLocation: 'localstorage',
      useRefreshTokens: true,
    }),
  ]
}).catch(err => console.error(err));
