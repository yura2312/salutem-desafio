import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';

export interface IngredienteRequest {
  descricao: string;
  precoUnitario: number;
  adicional: boolean;
}

export interface IngredienteResponse extends IngredienteRequest {
  id: number;
}

@Service()
export class ApiIngrediente {
  private readonly apiUrl = 'http://localhost:8080/api/ingrediente';
  private readonly http = inject(HttpClient);

  save(ingrediente: IngredienteRequest): Observable<IngredienteResponse> {
    return this.http.post<IngredienteResponse>(this.apiUrl, ingrediente);
  }

  get(id: number): Observable<IngredienteResponse> {
    return this.http.get<IngredienteResponse>(`${this.apiUrl}/${id}`);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  update(id: number, ingrediente: IngredienteRequest): Observable<IngredienteResponse> {
    return this.http.put<IngredienteResponse>(`${this.apiUrl}/${id}`, ingrediente);
  }

  getAll(): Observable<IngredienteResponse[]> {
    return this.http.get<IngredienteResponse[]>(`${this.apiUrl}/all`);
  }

  getByDescricao(descricao: string): Observable<IngredienteResponse[]> {
    return this.http.get<IngredienteResponse[]>(this.apiUrl, {
      params: { descricao },
    });
  }
}
