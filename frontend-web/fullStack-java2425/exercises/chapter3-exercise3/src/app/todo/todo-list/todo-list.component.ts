import { Component } from '@angular/core';
import {TodoItemComponent} from "../todo-item/todo-item.component";

@Component({
  selector: 'app-todo-list',
  standalone: true,
  imports: [TodoItemComponent],
  template: `
    <div class="md:container md:mx-auto container mx-auto mt-10">
      <div class="grid grid-cols-3 gap-6">
        <app-todo-item></app-todo-item>
        <app-todo-item></app-todo-item>
        <app-todo-item></app-todo-item>
      </div>
    </div>
  `
})
export class TodoListComponent {

}
