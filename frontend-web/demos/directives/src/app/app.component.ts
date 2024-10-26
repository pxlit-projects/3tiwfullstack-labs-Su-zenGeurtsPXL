import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NgClassComponent} from "./attribute-directive/ng-class/ng-class.component";
import {SourceImageComponent} from "./attribute-directive/source-image/source-image.component";
import {NgIfComponent} from "./structural-directive/ng-if/ng-if.component";
import {NgForComponent} from "./structural-directive/ng-for/ng-for.component";
import {NgSwitchComponent} from "./structural-directive/ng-switch/ng-switch.component";
import {NgForParentComponent} from "./structural-directive/ng-for-nested/ng-for-parent.component";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgClassComponent, SourceImageComponent, NgIfComponent,NgForComponent, NgSwitchComponent, NgForParentComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'directives';
}
