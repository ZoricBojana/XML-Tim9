import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceSaService {

  private baseUrl = 'http://localhost:8000/api';
  private headers = new HttpHeaders({'Content-Type': 'application/xml'});

  constructor(
    private http: HttpClient
  ) { }

  add(newArticle: string): Observable<any> {
    return this.http.post('http://localhost:8000/api/saveScientificArticle', newArticle, {headers: this.headers, responseType: 'text'});
  }

  searchByText(value: string): Observable<any> {
    return this.http.post('http://localhost:8000/api/searchArticles', value, {headers: this.headers, responseType: 'json'});
  }
}
