import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginModule} from "./login/login.module";
import {AuthGuard} from "./guard/auth-guard.component";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {HomeModule} from "./home/home.module";
import {ErrorInterceptor} from "./guard/error-interceptor";
import {ShoppingCartModule} from "./shopping-cart/shopping-cart.module";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    LoginModule,
    HomeModule,
    ShoppingCartModule
  ],
  providers: [
    AuthGuard,
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
