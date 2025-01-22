import { Component } from '@angular/core';

@Component({
  selector: 'app-todo-item',
  standalone: true,
  imports: [],
  template: `
    <div class="bg-lime-200 max-w-md mx-auto rounded-xl overflow-hidden shadow-md">
      <div class="p-6 flex flex-wrap items-center justify-start">
        <input class="checked:bg-lime-400 py-2 ml-2 mr-5" type="checkbox"/>
        <h2 class="font-bold text-xl mb-1">Taak</h2>
      </div>
    </div>


  `
})
export class TodoItemComponent {

}
