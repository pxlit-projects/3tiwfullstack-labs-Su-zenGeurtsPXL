import { Component } from '@angular/core';
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-output-binding',
  standalone: true,
  imports: [DatePipe],
  template: `
  <p>Er zijn {{days}} dagen in een week</p>
  <p>Voorbeeld van een expressie: 2 + 2 = {{2 + 2}}</p>
  <p>Voorbeeld van een functie: {{getDays()}}</p>
  <p>Voorbeeld van een datum met pipe: {{today | date:'dd/MM/yyyy'}}</p>
  `
})
export class OutputBindingComponent {
  days: number = 7;
  today: Date = new Date();

  getDays(): number {
    return this.days;
  }

}
