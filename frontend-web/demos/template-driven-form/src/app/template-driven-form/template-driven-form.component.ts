import { Component } from '@angular/core';
import {FormsModule, NgForm} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-template-driven-form',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  template: `
<!--    TODO: Why is NgForm not used??? -->
    <form #myForm="ngForm" (ngSubmit)="onSubmit(myForm)" ngNativeValidate >
      user: <input #name="ngModel" type="text" minlength="3" maxlength="20" name="username" [(ngModel)]="model.username" required/>
      e-mail: <input type="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$" name="email" [(ngModel)]="model.email"/>
      <button type="submit">Submit</button>

      <p>
        Form valid: {{myForm.form.valid}} <br/>
        Form untouched: {{myForm.form.untouched}}
      </p>

      <div *ngIf="name.valid && (name.dirty || name.touched)">
        Name is good
      </div>
<!--      TODO: Solve messages with these errors -->
      <div *ngIf="name.errors?.required">
        Name is required!
      </div>
        <div *ngIf="name.errors?.minLength">
          Name must be at least 3 characters!
        </div>

    </form>
  `
})

export class TemplateDrivenFormComponent {
  model: User = new User('', '');

  onSubmit(data: Object): void {
    console.log(data);
  }
}

interface User {
  username: string;
  email: string;
}

class User {
  username: string;
  email: string;

  constructor(username: string, email: string) {
    this.username = username;
    this.email = email;
  }
}
