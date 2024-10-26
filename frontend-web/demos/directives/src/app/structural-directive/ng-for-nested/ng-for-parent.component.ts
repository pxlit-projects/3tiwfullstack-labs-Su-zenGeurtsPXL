import { Component } from '@angular/core';
import {NgForChildComponent} from "./ng-for-child.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-ng-for-parent',
  standalone: true,
  imports: [NgForChildComponent, NgForOf],
  template: `
    <app-ng-for-child *ngFor="let contactItem of contactList;" [contactChild]="contactItem"></app-ng-for-child>
  `
})

export class NgForParentComponent {
  contactList = ['Su', 'Nicolas', 'Alpkan'];
}
