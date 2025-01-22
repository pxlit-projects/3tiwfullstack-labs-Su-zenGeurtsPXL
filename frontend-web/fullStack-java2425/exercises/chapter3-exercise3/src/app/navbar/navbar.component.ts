import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  template: `
    <nav class="bg-lime-400 dark:bg-red-950">
      <div class="max-w-screen-xl flex flex-wrap items-center justify-center mx-auto p-4">
        <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">TODO</span>
      </div>
    </nav>
  `
})
export class NavbarComponent {

}
