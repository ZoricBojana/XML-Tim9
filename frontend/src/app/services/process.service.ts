import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ProcessDto } from '../dto/process-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  sendProcessData(process: ProcessDto): Observable<any> {
    return this.http.post('http://localhost:8000/api/saveBussinessProcess', process, {headers: this.headers});
  }
}
