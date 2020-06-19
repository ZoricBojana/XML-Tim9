import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, from } from 'rxjs';
import { SearchDTO } from '../model/search-dto';
import { Filter } from '../dto/filter';


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

  filter(value: Filter) {
    return this.http.post('http://localhost:8000/api/searchArticlesMetadata', value, 
    {headers: new HttpHeaders({'Content-Type': 'application/json'}), responseType: 'json'});
  }

  searchByKeyword(value: string): Observable<any> {

    const dto = new SearchDTO();
    dto.key_word = value;
    console.log(dto);
    return this.http.post('http://localhost:8000/api/searchArticlesMetadata', dto,
    {headers: new HttpHeaders({'Content-Type': 'application/json'}), responseType: 'json'});
  }

  searchByAuthorUsername(value: string): Observable<any> {
    return this.http.post('http://localhost:8000/api/searchAuthorsArticles', value, {headers: this.headers, responseType: 'json'});
  }

  getAuthorsPapers(): Observable<any> {
    return this.http.get('http://localhost:8000/api/searchAllAuthorsArticles', {headers: this.headers, responseType: 'json'});
  }

  getPapersForReview(): Observable<any> {
    return this.http.get('http://localhost:8000/api/getAllForReview', {headers: this.headers, responseType: 'json'});

  }

  getPapersForReviewer(): Observable<any> {
    return this.http.get('http://localhost:8000/api/getAllForReviewer', {headers: this.headers, responseType: 'json'});

  }

  getHTML(id: string): Observable<any> {


    return this.http.get(`http://localhost:8000/api/getScientificArticle/HTML/${id}`,{
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

    return this.http.get(`http://localhost:8000/api/getScientificArticle/PDF/${id}`, httpOptions);
  }

  delete(id: string): Observable<any> {
    return this.http.delete(`http://localhost:8000/api/deleteScientificArticle/${id}`);
  }
}
