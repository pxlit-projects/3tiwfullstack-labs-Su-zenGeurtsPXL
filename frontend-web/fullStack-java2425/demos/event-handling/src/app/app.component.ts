import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {EventHandlingComponent} from "./event-handling/event-handling.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, EventHandlingComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'event-handling';
}
