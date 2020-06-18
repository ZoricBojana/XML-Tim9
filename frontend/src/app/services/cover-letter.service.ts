import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoverLetterService {

  private headers = new HttpHeaders({'Content-Type': 'application/xml'});

  constructor(private http: HttpClient) { }

  add(coverLetter: string, id: string): Observable<any> {
    return this.http.post(`http://localhost:8000/api/saveCoverLetter/${id}`, coverLetter, {headers: this.headers, responseType: 'text'});
  }
}
