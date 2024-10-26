import { Component } from '@angular/core';

@Component({
  selector: 'app-ng-for',
  standalone: true,
  imports: [],
  template: `
    @for (os of operatingSystems; track os.id; let idx = $index, e = $even) {
      <p>OS at index {{idx}}: {{os.name}}</p>
    }
  `
})

export class NgForComponent {
  operatingSystems = [{id: 'win', name: 'Windows'}, {id: 'linux', name: 'Linux'}];
}
