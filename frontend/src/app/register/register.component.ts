import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';

import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm:FormGroup;
  

  
  imports: [

    ReactiveFormsModule
    ]

    
  constructor( 
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient
  
  ) {}
  ngOnInit() {
    this. registerForm=this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
      firstname: ['',[Validators.required]],
      lastname: ['',[Validators.required]],
     email: ['',[Validators.required]]
  

    });
  }

 


  
}


