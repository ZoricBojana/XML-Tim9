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

  add(review: string, id: string): Observable<any> {
    return this.http.post(`http://localhost:8000/api/saveReview/${id}`, review, {headers: this.headers, responseType: 'text'});
  }

  getPDFReviews(paperId: string): Observable<any> {
    const httpOptions = {
      'responseType'  : 'arraybuffer' as 'json'
       // 'responseType'  : 'blob' as 'json'        //This also worked
    };

    return this.http.get(`http://localhost:8000/api/getReviewsPDF/${paperId}`, httpOptions);
  }
}
