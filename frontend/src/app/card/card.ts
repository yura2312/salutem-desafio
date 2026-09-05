import { Component, input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from "@angular/router";

@Component({
  imports: [MatCardModule, MatButtonModule, RouterLink],
  selector: 'app-card',
  styleUrl: './card.css',
  templateUrl: './card.html',
})
export class Card {

  readonly cards = [
  { titulo: 'Bebidas', rota: '/bebidas' },
  { titulo: 'Ingredientes', rota: '/ingredientes' },
  { titulo: 'Hambúrgueres', rota: '/hamburgueres' },
  { titulo: 'Pedidos', rota: '/pedidos' }
];
}
