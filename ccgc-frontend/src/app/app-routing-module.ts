import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LandingPageComponent} from './landing-page/landing-page.component';
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {CodeProfilerComponent} from "./code-profiler/code-profiler.component";

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: '/user-profile', component: UserProfileComponent},
  { path: '/analyze', component: CodeProfilerComponent },
  // Add more routes here if needed
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
