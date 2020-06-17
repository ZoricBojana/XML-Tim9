import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginDto } from '../model/login-dto';
import { RegisterDto } from '../model/register-dto';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private http: HttpClient
  ) { }

  login(dto: LoginDto): Observable<any> {
    return this.http.post('http://localhost:8000/api/login', dto, {headers: this.headers, responseType: 'text'});
  }

  logout(): Observable<any> {
    return this.http.get('http://localhost:8000/api/logOut', {headers: this.headers, responseType: 'text'});
  }

  register(dto: RegisterDto): Observable<any> {
    return this.http.post('http://localhost:8000/api/savePerson', dto, {headers: this.headers, responseType: 'text'});
  }


  getReviewers(): Observable<any> {
    return this.http.get('http://localhost:8000/api/getReviewers',
    {headers: new HttpHeaders({'Content-Type': 'application/xml'}), responseType: 'json'});
  }

  isLoggedIn(): boolean {
    if (!localStorage.getItem('user')) {
      return false;
    }
    return true;
  }
}
