import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'qy-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class QyLoginComponent implements OnInit {
  validateForm: FormGroup;
  error: string;

  constructor(private fb: FormBuilder,
      private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [ null, [ Validators.required ] ],
      password: [ null, [ Validators.required ] ],
      remember: [ true ]
    });
  }

  markAsDirty() {
    for (let key of Object.keys(this.validateForm.controls)) {
        this.validateForm.controls[key].markAsDirty();
    }
 }

 getFormControl(name) {
  return this.validateForm.controls[name];
 }

  submitForm(): void {
    for (let key of Object.keys(this.validateForm.controls)) {
      this.validateForm.controls[ key ].markAsDirty();
      this.validateForm.controls[ key ].updateValueAndValidity();
    }
    let username = this.getFormControl("username").value;
    let passwd = this.getFormControl("password").value;
    this.authService.login(username, passwd).subscribe(
      isLoggedIn => {
        console.log("isLoggedIn",  isLoggedIn);
        if (isLoggedIn) {
          this.goHome();
        } else {
          this.error = '登录失败！';
        }
      },
      error => this.error = error
    );
  }


  goHome() {
    // Error let pathFromRoot = this.route.pathFromRoot;
    this.router.navigateByUrl('/');
  }
}
