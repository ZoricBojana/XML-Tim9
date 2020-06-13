import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginDto } from '../model/login-dto';
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

  isLoggedIn(): boolean {
    if (!localStorage.getItem('user')) {
      return false;
    }
    return true;
  }
}
