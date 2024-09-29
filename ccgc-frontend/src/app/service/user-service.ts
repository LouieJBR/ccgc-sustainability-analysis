// user.service.ts
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userLoggedInSubject = new Subject<void>();
  userLoggedIn$ = this.userLoggedInSubject.asObservable();

  emitUserLoggedIn() {
    this.userLoggedInSubject.next();
  }
}
