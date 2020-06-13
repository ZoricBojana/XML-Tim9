import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';

import { RegisterDto } from '../model/register-dto';
import { Router } from '@angular/router';

import { HttpClient } from '@angular/common/http';
import { PersonService } from '../services/person.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;

 

  constructor( 
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient,
    private personService: PersonService
  
  ) {}
  ngOnInit() {
    this.registerForm=this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
      firstname: ['',[Validators.required]],
      lastname: ['',[Validators.required]],
     email: ['',[Validators.required]]
  

    });
  }
  onSubmit():void {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }
    const dto = new RegisterDto();
    this.loading = true;
    this.personService.register(this.registerForm.value as RegisterDto).subscribe(
      result => {
        console.log('register' + result);
        if (localStorage.getItem('user') !== null) {
          localStorage.removeItem('user');
        }
        localStorage.setItem('user', JSON.stringify(result));
        this.router.navigate(['login']);
      },
      error => {
          console.log(error.error);
          this.loading = false;
      }
    );
  }

}


