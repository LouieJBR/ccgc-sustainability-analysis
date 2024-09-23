import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LandingPageComponent} from './landing-page/landing-page.component';
import {UserProfileComponent} from "./user-profile/user-profile.component";

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: '/user-profile', component: UserProfileComponent}
  // Add more routes here if needed
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
