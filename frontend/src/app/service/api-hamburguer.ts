import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { IngredienteResponse } from './api-ingrediente';
import { Observable } from 'rxjs';

export interface HamburguerRequest {
  descricao: string;
  valor: number;
  idIngredientes: number[];
}

export interface HamburguerResponse {
  id: string;
  descricao: string;
  valor: number;
  ingredientes: IngredienteResponse[];
}

@Service()
export class ApiHamburguer {
  private readonly apiUrl = 'http://localhost:8080/api/hamburguers';

  private http = inject(HttpClient);

  save(hamburguer: HamburguerRequest): Observable<HamburguerResponse> {
    return this.http.post<HamburguerResponse>(this.apiUrl, hamburguer);
  }

  get(id: number): Observable<HamburguerResponse> {
    return this.http.get<HamburguerResponse>(`${this.apiUrl}/${id}`);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  update(id: number, hamburguer: HamburguerRequest): Observable<HamburguerResponse> {
    return this.http.put<HamburguerResponse>(`${this.apiUrl}/${id}`, hamburguer);
  }

  getAll(): Observable<HamburguerResponse[]> {
    return this.http.get<HamburguerResponse[]>(`${this.apiUrl}/all`);
  }

  getByDescricao(descricao: string): Observable<HamburguerResponse[]> {
    return this.http.get<HamburguerResponse[]>(this.apiUrl, {
      params: { descricao },
    });
  }
}
