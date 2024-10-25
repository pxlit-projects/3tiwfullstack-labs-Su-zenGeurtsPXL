import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  name: string = '';
  password: string = '';
  message: string = 'Please enter login details!';

  process(): void{
    this.message = `Hello ${this.name}! Your password is ${this.password}`;
    console.log(this.name, this.password)
  }
}
