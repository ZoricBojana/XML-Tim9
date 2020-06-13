import { Component, OnInit } from '@angular/core';

import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { LoginDto } from '../model/login-dto';
import { PersonService } from '../services/person.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private personService: PersonService
    ) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit() {
    const dto = new LoginDto();
    this.personService.login(this.loginForm.value as LoginDto).subscribe(
      result => {
        console.log('login ' + result);
        if (localStorage.getItem('user') !== null) {
          localStorage.removeItem('user');
          console.log('removec');
        }
        localStorage.setItem('user', JSON.stringify(result));
        this.router.navigate(['home']);
      },
      error => {
          console.log(error.error);
      }
    );
  }
}
