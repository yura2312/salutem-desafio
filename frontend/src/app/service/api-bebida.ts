import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';

export interface BebidaRequest {
  descricao: string;
  precoUnitario: number;
  contemAcucar: boolean;
}

export interface BebidaResponse extends BebidaRequest {
  id: number;
}

@Service()
export class ApiBebida {
  private readonly apiUrl = 'http://localhost:8080/api/bebidas';
  private readonly http = inject(HttpClient);

  save(bebida: BebidaRequest): Observable<BebidaResponse> {
    return this.http.post<BebidaResponse>(this.apiUrl, bebida);
  }

  get(id: number): Observable<BebidaResponse> {
    return this.http.get<BebidaResponse>(`${this.apiUrl}/${id}`);
  }

  update(id: number, bebida: BebidaRequest): Observable<BebidaResponse> {
    return this.http.put<BebidaResponse>(`${this.apiUrl}/${id}`, bebida);
  }

  getAll(): Observable<BebidaResponse[]> {
    return this.http.get<BebidaResponse[]>(`${this.apiUrl}/all`);
  }

  getByDescricao(descricao: string): Observable<BebidaResponse[]> {
    return this.http.get<BebidaResponse[]>(this.apiUrl, { params: { descricao } });
  }

  delete(id: number): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/${id}`);
}
}
