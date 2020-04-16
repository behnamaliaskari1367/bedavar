import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MyErrorStateMatcher} from "../../model/error-state-matcher";
import {LoginFa} from "../../model/lang.fa";
import {BackServiceAuthService} from "@fartak/backservice-common";

@Component({
  selector: 'bs-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginFa = new LoginFa();
  loginForm: FormGroup;
  username = '';
  password = '';
  matcher = new MyErrorStateMatcher();

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: BackServiceAuthService) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      'username': [null, Validators.required],
      'password': [null, Validators.required]
    });
  }

  onFormSubmit(form: NgForm) {
    this.authService.login(form);
  }
}
