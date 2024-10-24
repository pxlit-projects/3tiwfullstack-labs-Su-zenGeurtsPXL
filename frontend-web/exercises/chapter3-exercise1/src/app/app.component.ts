import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {LogoComponent} from "./core/logo/logo.component";
import {NieuwsbriefComponent} from "./core/nieuwsbrief/nieuwsbrief.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LogoComponent, NieuwsbriefComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'chapter3-exercise1';
}
