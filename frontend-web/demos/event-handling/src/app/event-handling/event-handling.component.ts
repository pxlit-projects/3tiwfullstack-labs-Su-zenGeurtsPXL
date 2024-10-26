import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-event-handling',
  standalone: true,
  imports: [FormsModule],
  template: `
  <input id="txt1" type="text" (keydown)="toggle('txt1')" [(ngModel)]="voornaam"/><br/><br/>
  <button id="btn1" (click)="toggle('btn1')">Toggle</button><br/><br/>
  <div id="div1" (mouseover)="toggle('div1')" (mouseleave)="toggle('div1')">Hover over me!</div>
  `
})
export class EventHandlingComponent {
  voornaam: string = '';

  toggle(id: string){
    let e = document.getElementById(id);
    let bc = e!.style.backgroundColor;
    bc = (bc == 'red') ? 'green' : 'red';
    e!.style.backgroundColor = bc;
  }
}

