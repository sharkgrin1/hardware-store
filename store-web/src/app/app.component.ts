import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Role} from "./model/role";
import {User} from "./model/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  @ViewChild("cartLink", {static: true})
  private cartLink: ElementRef;
  @ViewChild("logoutBtn", {static: true})
  private logoutBtn: ElementRef;
  username:string;

  constructor() {
  }

  ngOnInit(): void {
    let userStr: string = localStorage.getItem('user');
    if (!userStr) {
      this.cartLink.nativeElement.hidden = true;
      this.logoutBtn.nativeElement.hidden = true;
      return;
    }
    const user: User = JSON.parse(userStr);
    this.username = user.username;
    if (user.role == Role.ROLE_ADMIN) {
      this.cartLink.nativeElement.hidden = true;
      return;
    }

  }

  logout(): void {
    localStorage.removeItem('user');
    window.location.reload();
  }
}
