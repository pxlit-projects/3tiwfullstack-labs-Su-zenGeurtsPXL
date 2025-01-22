import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-event-binding',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './event-binding.component.html',
  styleUrl: './event-binding.component.css'
})
export class EventBindingComponent {
  name: string = '';
  password: string = '';
  message: string = 'Please enter login details!';

  click(): void {
    console.log('Er is op de knop geklikt!');
    this.message = this.name + 'has logged in!'
  }
}
