import { Component, inject, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '@auth0/auth0-angular';
import { ProfilingResult } from '../models/profiling-result.model';
import {FooterComponent} from "../shared/footer/footer.component";
import {CommonModule, DatePipe} from "@angular/common";
import {JumbotronContentComponent} from "../shared/jumbotron-content/jumbotron-content.component"; // Make sure this exists

@Component({
  selector: 'app-landing-page',
  standalone: true,
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css'],
  imports: [
    CommonModule,
    FooterComponent,
    DatePipe,
    JumbotronContentComponent
  ]
})
export class LandingPageComponent implements OnInit {
  latestResult: ProfilingResult | null = null;

  private http = inject(HttpClient);
  private auth = inject(AuthService);

  ngOnInit(): void {
    this.auth.getAccessTokenSilently().subscribe(token => {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });

      this.http.get<ProfilingResult[]>('http://localhost:8080/api/analyze/user', { headers })
        .subscribe({
          next: (results) => {
            this.latestResult = results.length > 0 ? results[results.length - 1] : null;
          },
          error: err => {
            console.error('Failed to fetch profiling results', err);
          }
        });
    });
  }
  getCarbonFootprintRating(intensity?: number): string {
    if (intensity == null) return 'N/A';
    if (intensity <= 150) return 'Low';
    if (intensity <= 400) return 'Moderate';
    return 'High';
  }

  getCpuLoadRating(cpuTimeMs?: number): string {
    if (cpuTimeMs == null) return 'N/A';
    if (cpuTimeMs < 500) return 'Low';
    if (cpuTimeMs < 1500) return 'Moderate';
    return 'High';
  }
}
