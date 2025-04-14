import {Component, inject, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthService} from '@auth0/auth0-angular';
import {ProfilingResult} from '../models/profiling-result.model';
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
  allResults: ProfilingResult[] = [];
  selectedResult: ProfilingResult | null = null;

  private http = inject(HttpClient);
  private auth = inject(AuthService);

  ngOnInit(): void {
    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      if (!isAuthenticated) {
        return;
      }

      this.auth.getAccessTokenSilently().subscribe({
        next: (token) => {
          const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`
          });

          this.http.get<ProfilingResult[]>(
            'https://ccgc-backend-dxdqfmcaexa3a2c3.uksouth-01.azurewebsites.net/api/analyze/user',
            { headers }
          ).subscribe({
            next: (results) => {
              this.allResults = results;
              this.latestResult = results.length > 0 ? results[results.length - 1] : null;
              this.selectedResult = this.latestResult;
            },
            error: err => {
              console.error('Failed to fetch profiling results', err);
            }
          });
        },
        error: err => {
          console.error('Failed to get access token silently', err);
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

  onResultSelected(event: Event): void {
    const index = (event.target as HTMLSelectElement).value;
    this.selectedResult = this.allResults[+index];
  }

  deleteSelectedResult(event: Event): void {
    event.preventDefault();

    if (!this.selectedResult || !this.selectedResult.id) {
      alert('No run selected to delete.');
      return;
    }

    this.auth.getAccessTokenSilently().subscribe(token => {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });

      this.http.delete(`https://ccgc-backend-dxdqfmcaexa3a2c3.uksouth-01.azurewebsites.net/api/analyze/${this.selectedResult!.id}`, { headers })
        .subscribe({
          next: () => {
            this.allResults = this.allResults.filter(r => r.id !== this.selectedResult!.id);
            this.selectedResult = this.allResults.length > 0 ? this.allResults[this.allResults.length - 1] : null;
            alert('Run deleted successfully.');
          },
          error: err => {
            if (err.status !== 404 || err.status !== 200) {
              console.error('Failed to delete run:', err);
              alert('Failed to delete run. See console for details.');
            } else {
              console.warn('Result not found. It may have already been deleted.');
            }
          }

        });
    });
  }

}
