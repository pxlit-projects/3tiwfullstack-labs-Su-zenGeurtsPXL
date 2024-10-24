import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import {Customer} from "./shared/models/customer.model";
import {NavbarComponent} from "./core/navbar/navbar.component";
import {CustomerListComponent } from "./core/customers/customer-list/customer-list.component";

@Component({
  selector: 'app-hallo',
  standalone: true,
  imports: [RouterOutlet, CommonModule, NavbarComponent, CustomerListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Web expert!';
  name = 'Su-zen Geurts';

  constructor() {
    const c1 = new Customer('Dries Swinnen', 'dries@d-ries.be','Pelt','mystreet','Belgium',21);
    const c2 = new Customer('John Doe', 'john@doe.com','New York','5th Avenue','USA',6, 'john.png');
    c2.isLoyal = true;

    console.log(c1);
    console.log(c2);
  }
}
