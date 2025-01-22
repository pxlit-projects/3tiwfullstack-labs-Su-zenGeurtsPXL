import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-input-binding',
  standalone: true,
  imports: [FormsModule],
  template: `
  <input type="text" placeholder="Name" [(ngModel)]="name">
  <p>Hello, {{name}}</p>
  `
})
export class InputBindingComponent {
  name: string = '';
}
