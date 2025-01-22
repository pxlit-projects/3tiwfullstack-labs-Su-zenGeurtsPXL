import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-ng-class',
  standalone: true,
  imports: [
    FormsModule,
    NgClass
  ],
  template: `
  <span>Voorbeeld tekst</span><br/>
  <span [ngClass]="{bordered: hasBorder, active: isActive}">Test tekst</span><br/>
  Voorzie rand: <input type="checkbox" [(ngModel)]="hasBorder" name="hasBorder" /><br/>
  Actief: <input type="checkbox" [(ngModel)]="isActive" name="isActive" />
  `,
  styles:[`
  .active { color: red; }
  .bordered { border: 2px solid black; }
  `]
})
export class NgClassComponent {
  hasBorder: boolean = false;
  isActive: boolean = false;

  ngOnInit(): void {
  }
}
