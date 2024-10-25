import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-newsletter',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './newsletter.component.html',
  styleUrl: './newsletter.component.css'
})
export class NewsletterComponent {
  email: string = '';
  message: string = '';

  submit() {
    this.message = `You subscribed with ${this.email}.`;
    console.log('Subscribed with email:', this.email);
  }
}
