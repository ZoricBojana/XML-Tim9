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

  getHTML(id: string): Observable<any> {


    return this.http.get(`http://localhost:8000/api/getCoverLetter/HTML/${id}`,{
      headers: new HttpHeaders({
        'Accept': 'text/html',
        'Content-Type': 'application/x-www-form-urlencoded'
      }),
      responseType: 'text'
    });
  }

  getPDF(id: string): Observable<any> {

    const httpOptions = {
      'responseType'  : 'arraybuffer' as 'json'
       // 'responseType'  : 'blob' as 'json'        //This also worked
    };

    return this.http.get(`http://localhost:8000/api/getCoverLetter/PDF/${id}`, httpOptions);
  }
}
