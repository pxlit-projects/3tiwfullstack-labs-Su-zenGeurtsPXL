import {ChildComponent} from "../child/child.component";
import { Component } from '@angular/core';

@Component({
  selector: 'app-parent',
  standalone: true,
  imports: [ChildComponent],
  template: `
  <app-child [receivedMessage]="messageParent" (notify)="catchNotification($event)"></app-child>
  `
})
export class ParentComponent {
  messageParent: string = 'From parent to child';

  catchNotification(message: string): void {
    console.log(message);
  }
}
