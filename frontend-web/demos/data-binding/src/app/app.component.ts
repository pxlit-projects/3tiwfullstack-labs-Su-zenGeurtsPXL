import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {DataBindingComponent} from "./one-way/data-binding/data-binding.component";
import {OutputBindingComponent} from "./one-way/output-binding/output-binding.component";
import {InputBindingComponent} from "./two-way/input-binding/input-binding.component";
import {EventBindingComponent} from "./two-way/event-binding/event-binding.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, DataBindingComponent, OutputBindingComponent, InputBindingComponent, EventBindingComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'data-binding';
}
