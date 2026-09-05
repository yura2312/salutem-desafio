import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from "./header/header";
import { Card } from "./card/card";
import { CrudActions } from "./shared/crud-actions/crud-actions";
import { Bebida } from './bebida/bebida';

@Component({
  imports: [RouterOutlet, Header, Bebida],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('frontend');
}
