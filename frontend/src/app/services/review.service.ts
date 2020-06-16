import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private baseUrl = 'http://localhost:8000/api';
  private headers = new HttpHeaders({'Content-Type': 'application/xml'});

  constructor(private http: HttpClient) { }

  add(review: string): Observable<any> {
    return this.http.post('http://localhost:8000/api/saveReview', review, {headers: this.headers, responseType: 'text'});
  }
}
