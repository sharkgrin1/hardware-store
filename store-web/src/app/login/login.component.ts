import {Component, OnInit} from '@angular/core';
import {LoginService} from "../services/login.service";
import {Router} from "@angular/router";
import {User} from "../model/user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private loginService: LoginService, private router: Router) {
  }

  public ngOnInit(): void {

  }

  login(): void {
    this.loginService.get(this.username, this.password).subscribe(
      value => {
        let user = new User(value.id, this.username, value.role, value.token);
        localStorage.setItem('user', JSON.stringify(user))
        this.router.navigate(['/']).then(() => window.location.reload());
      },
      (error: any) => console.error(error)
    );
  }
}
