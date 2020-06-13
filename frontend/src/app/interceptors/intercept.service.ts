import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class InterceptService implements HttpInterceptor {

  constructor() { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const item = localStorage.getItem('user');
    const decodedItem = JSON.parse(item);
    if (item) {
      console.log(item);
      const cloned = req.clone({
        setHeaders: {
          Authorization: `Bearer ${item.slice(1, -1)}`
        }
    });
      return next.handle(cloned);
    } else {
      return next.handle(req);
    }
  }
}
