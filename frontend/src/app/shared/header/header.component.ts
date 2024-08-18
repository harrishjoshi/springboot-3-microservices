import {Component, inject, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})

export class HeaderComponent implements OnInit {
  constructor(private oidcSecurityService: OidcSecurityService) {}
  isAuthenticated: boolean = false;
  username: string = '';

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
      }
    )

    this.oidcSecurityService.userData$.subscribe(
      ({userData}) => {
        this.username = userData?.preferred_username.toUpperCase();
      }
    )
  }

  login(): void {
    this.oidcSecurityService.authorize();
  }

  logout(): void {
    this.oidcSecurityService.logoff()
      .subscribe((result) => console.log({result}));
  }
}
