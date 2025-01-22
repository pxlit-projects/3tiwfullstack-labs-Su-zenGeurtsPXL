import { Component } from '@angular/core';

@Component({
  selector: 'app-ng-if',
  standalone: true,
  imports: [],
  template: `
    @if (isLoggedIn) {
      <p>Welcome back, Friend</p>
    }
  `
})

export class NgIfComponent {
  isLoggedIn: boolean = true;
}
