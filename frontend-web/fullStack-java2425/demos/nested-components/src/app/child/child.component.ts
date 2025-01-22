import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-child',
  standalone: true,
  imports: [],
  template: `
  <p>{{receivedMessage}}</p>

  <button (click)="onClick()">Click me!</button>
  `
})
export class ChildComponent {
  @Input() receivedMessage: string = '';

  messageChild: string = 'From child to parent';
  @Output() notify: EventEmitter<string> = new EventEmitter<string>();
  onClick(): void{
    this.notify.emit(this.messageChild);
  }
}
