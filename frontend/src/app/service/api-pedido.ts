import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';

export interface PedidoRequest {
  descricao: string;
  clienteNome: string;
  clienteEndereco: string;
  clienteTelefone: string;
  idHamburguerQuantidade: Record<string, number>;
  idBebidaQuantidade: Record<string, number>;
  observacoes: string;
}

export interface ItemPedidoResponse {
  id: number;
  nome: string;
  quantidade: number;
  precoVenda: number;
}

export interface PedidoResponse {
  id: number;
  data: string;
  descricao: string;
  clienteNome: string;
  clienteEndereco: string;
  clienteTelefone: string;
  hamburgueres: ItemPedidoResponse[];
  bebidas: ItemPedidoResponse[];
  observacoes: string | null;
  valorTotal: number;
}

@Service()
export class ApiPedido {
  private readonly apiUrl = 'http://localhost:8080/api/pedidos';
  private readonly http = inject(HttpClient);

  save(pedido: PedidoRequest): Observable<PedidoResponse> {
    return this.http.post<PedidoResponse>(this.apiUrl, pedido);
  }

  get(id: number): Observable<PedidoResponse> {
    return this.http.get<PedidoResponse>(`${this.apiUrl}/${id}`);
  }

  update(id: number, pedido: PedidoRequest): Observable<PedidoResponse> {
    return this.http.put<PedidoResponse>(`${this.apiUrl}/${id}`, pedido);
  }

  getAll(): Observable<PedidoResponse[]> {
    return this.http.get<PedidoResponse[]>(`${this.apiUrl}/all`);
  }
}
