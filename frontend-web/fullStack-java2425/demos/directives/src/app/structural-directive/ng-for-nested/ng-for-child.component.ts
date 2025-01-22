import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-ng-for-child',
  standalone: true,
  imports: [],
  template: `
    <p>{{contactChild}}</p>
  `
})

export class NgForChildComponent {
  @Input() contactChild: string = '';
}
