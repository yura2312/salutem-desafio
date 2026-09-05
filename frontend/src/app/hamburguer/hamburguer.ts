import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { form, FormField } from '@angular/forms/signals';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatOption } from '@angular/material/autocomplete';
import { MatSelect } from '@angular/material/select';
import { ApiHamburguer, HamburguerRequest, HamburguerResponse } from '../service/api-hamburguer';
import { ApiIngrediente, IngredienteResponse } from '../service/api-ingrediente';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialog } from '../shared/error-dialog/error-dialog';

@Component({
  imports: [FormField, MatButton, MatFormField, MatInput, MatLabel, MatOption, MatSelect],
  selector: 'app-hamburguer',
  templateUrl: './hamburguer.html',
})
export class Hamburguer implements OnInit {
  private readonly api = inject(ApiHamburguer);
  private readonly apiIngrediente = inject(ApiIngrediente);

  readonly hamburguers = signal<HamburguerResponse[]>([]);
  readonly ingredientes = signal<IngredienteResponse[]>([]);
  readonly editandoId = signal<number | null>(null);

  readonly hamburguerModel = signal<HamburguerRequest>({
    descricao: '',
    valor: 0,
    idIngredientes: [],
  });
  readonly hamburguerForm = form(this.hamburguerModel);

  readonly ultimosHamburguers = computed(() =>
    [...this.hamburguers()].sort((a, b) => Number(b.id) - Number(a.id)).slice(0, 5),
  );

  readonly buscaModel = signal({
    termo: '',
    tipo: 'id' as 'id' | 'descricao',
  });
  readonly buscaForm = form(this.buscaModel);

  private readonly dialog = inject(MatDialog);

  ngOnInit(): void {
    this.carregarHamburguers();
    this.carregarIngredientes();
  }

  carregarHamburguers(): void {
    this.api.getAll().subscribe({
      next: (hamburguers) => this.hamburguers.set(hamburguers),
      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  carregarIngredientes(): void {
    this.apiIngrediente.getAll().subscribe({
      next: (ingredientes) => this.ingredientes.set(ingredientes),
      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  salvar(): void {
    const id = this.editandoId();
    const request = this.hamburguerModel();

    if (id === null) {
      this.api.save(request).subscribe({
        next: (hamburguer) => {
          this.hamburguers.update((lista) => [...lista, hamburguer]);
          this.limparFormulario();
        },
        error: (error) => {
          this.dialog.open(ErrorDialog, {
            data: error.error,
          });
        },
      });
      return;
    }

    this.api.update(id, request).subscribe({
      next: (hamburguerAtualizado) => {
        this.hamburguers.update((lista) =>
          lista.map((hamburguer) => (hamburguer.id === id ? hamburguerAtualizado : hamburguer)),
        );
        this.limparFormulario();
      },
      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  editar(hamburguer: HamburguerResponse): void {
    this.editandoId.set(hamburguer.id);
    this.hamburguerModel.set({
      descricao: hamburguer.descricao,
      valor: hamburguer.valor,
      idIngredientes: hamburguer.ingredientes.map((ingrediente) => ingrediente.id),
    });
  }

  deletar(id: number): void {
    this.api.delete(id).subscribe({
      next: () => {
        this.hamburguers.update((lista) => lista.filter((hamburguer) => hamburguer.id !== id));

        if (this.editandoId() === id) {
          this.limparFormulario();
        }
      },
      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  cancelarEdicao(): void {
    this.limparFormulario();
  }

  pesquisar(): void {
    const busca = this.buscaModel();

    if (!busca.termo.trim()) {
      this.carregarHamburguers();
      return;
    }

    if (busca.tipo === 'id') {
      this.api.get(Number(busca.termo)).subscribe({
        next: (hamburguer) => this.hamburguers.set([hamburguer]),
        error: (error) => {
          this.dialog.open(ErrorDialog, {
            data: error.error,
          });
        },
      });
      return;
    }

    this.api.getByDescricao(busca.termo).subscribe({
      next: (hamburguers) => this.hamburguers.set(hamburguers),
      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  verificarBarraDeBusca(): void {
    if (!this.buscaModel().termo.trim()) {
      this.carregarHamburguers();
    }
  }

  private limparFormulario(): void {
    this.editandoId.set(null);
    this.hamburguerModel.set({
      descricao: '',
      valor: 0,
      idIngredientes: [],
    });
  }
}
