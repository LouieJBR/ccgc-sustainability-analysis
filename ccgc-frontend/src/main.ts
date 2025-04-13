import {bootstrapApplication} from '@angular/platform-browser';
import {AppComponent} from './app/app.component';
import {provideAuth0} from "@auth0/auth0-angular";
import {provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";

bootstrapApplication(AppComponent, {
  providers: [
  provideHttpClient(withInterceptorsFromDi()),
    provideAuth0({
      domain: 'dev-m1vbm7mjkcjugfu4.uk.auth0.com',
      clientId: 'VpE5HKtJzTdhP3gj5lMHeCM9awyh2Kgo',
      authorizationParams: {
        redirect_uri: window.location.origin,
        audience: 'https://ccgc-api', // <-- must match backend expected audience
      },
      cacheLocation: 'localstorage',
      useRefreshTokens: true,
    }),
  ]
}).catch((err) => console.error(err));
