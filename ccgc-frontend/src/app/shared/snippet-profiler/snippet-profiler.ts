import {Component, inject} from '@angular/core';
import {ProfilingResult} from "../../models/profiling-result.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "@auth0/auth0-angular";
import {FormsModule} from "@angular/forms";


@Component({
  selector: 'app-snippet-profiler',
  templateUrl: './snippet-profiler.html',
  standalone: true,
  imports: [
    FormsModule
  ],
  styleUrls: ['snippet-profiler.css']
})
export class SnippetProfilerComponent {
  code = '';
  language = 'python';
  fileNameHint = 'snippet';
  result: ProfilingResult | null = null;

  private http = inject(HttpClient);
  private auth = inject(AuthService);

  submitCode() {
    this.auth.getAccessTokenSilently().subscribe(token => {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      });

      const payload = {
        code: this.code,
        language: this.language,
        fileNameHint: this.fileNameHint
      };

      this.http.post<ProfilingResult>('http://localhost:8080/api/analyze', payload, { headers })
        .subscribe({
          next: res => this.result = res,
          error: err => {
            console.error('Error:', err);
            alert('Code analysis failed. See console.');
          }
        });
    });
  }
  handleFileUpload(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    const file = input.files[0];
    this.fileNameHint = file.name.split('.')[0];
    const reader = new FileReader();
    reader.onload = () => {
      this.code = reader.result as string;
    };
    reader.readAsText(file);
  }
}
