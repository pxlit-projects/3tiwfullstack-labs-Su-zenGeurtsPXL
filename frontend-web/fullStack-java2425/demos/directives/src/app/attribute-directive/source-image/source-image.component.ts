import {Component} from '@angular/core';

@Component({
  selector: 'app-source-image',
  standalone: true,
  imports: [
  ],
  template: `
    <img [src]="imageUrl"/><br/>
    <input type="button" value="wijzig" (click)="toggleImage()"/>
  `
})
export class SourceImageComponent {
  imageUrl: string = 'element1.png';
  index: number = 1;

  constructor() {  }

  toggleImage(): void {
    this.index = this.index + 1;
    this.index = (this.index < 5) ? this.index : 1;
    this.imageUrl = 'element' + this.index + '.png';
  }
}
