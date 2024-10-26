import { Component } from '@angular/core';

@Component({
  selector: 'app-ng-switch',
  standalone: true,
  imports: [],
  template: `
   @switch (condition) {
     @case (water) {
       <p>Water</p>
     }
     @case (fire) {
       <p>Fire</p>
     }
     @case (air) {
       <p>Air</p>
     }
     @default {
       <p>Earth</p>
     }
   }
  `
})

export class NgSwitchComponent {
  condition: string = 'nothing';
  water: string = 'water';
  fire: string = 'fire';
  air: string = 'air';
}
