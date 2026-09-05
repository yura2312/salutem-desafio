import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { form, FormField } from '@angular/forms/signals';
import { MatButton } from '@angular/material/button';
import { MatCheckbox } from '@angular/material/checkbox';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatOption } from '@angular/material/autocomplete';
import { MatDialog } from '@angular/material/dialog';
import { MatSelect } from '@angular/material/select';
import {
  ApiIngrediente,
  IngredienteRequest,
  IngredienteResponse,
} from '../service/api-ingrediente';
import { ErrorDialog } from '../shared/error-dialog/error-dialog';

@Component({
  imports: [
    FormField,
    MatButton,
    MatCheckbox,
    MatFormField,
    MatInput,
    MatLabel,
    MatOption,
    MatSelect,
  ],
  selector: 'app-ingrediente',
  templateUrl: './ingrediente.html',
})
export class Ingrediente implements OnInit {
  private readonly api = inject(ApiIngrediente);
  private readonly dialog = inject(MatDialog);

  readonly ingredientes = signal<IngredienteResponse[]>([]);
  readonly editandoId = signal<number | null>(null);

  readonly ingredienteModel = signal<IngredienteRequest>({
    descricao: '',
    precoUnitario: 0,
    adicional: false,
  });
  readonly ingredienteForm = form(this.ingredienteModel);

  readonly ultimosIngredientes = computed(() =>
    [...this.ingredientes()].sort((a, b) => b.id - a.id).slice(0, 5),
  );

  readonly buscaModel = signal({
    termo: '',
    tipo: 'id' as 'id' | 'descricao',
  });
  readonly buscaForm = form(this.buscaModel);

  ngOnInit(): void {
    this.carregarIngredientes();
  }

  carregarIngredientes(): void {
    this.api.getAll().subscribe({
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
    const request = this.ingredienteModel();

    if (id === null) {
      this.api.save(request).subscribe({
        next: (ingrediente) => {
          this.ingredientes.update((lista) => [...lista, ingrediente]);
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
      next: (ingredienteAtualizado) => {
        this.ingredientes.update((lista) =>
          lista.map((ingrediente) => (ingrediente.id === id ? ingredienteAtualizado : ingrediente)),
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

  editar(ingrediente: IngredienteResponse): void {
    this.editandoId.set(ingrediente.id);
    this.ingredienteModel.set({
      descricao: ingrediente.descricao,
      precoUnitario: ingrediente.precoUnitario,
      adicional: ingrediente.adicional,
    });
  }

  deletar(id: number): void {
    this.api.delete(id).subscribe({
      next: () => {
        this.ingredientes.update((lista) => lista.filter((ingrediente) => ingrediente.id !== id));

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
      this.carregarIngredientes();
      return;
    }

    if (busca.tipo === 'id') {
      this.api.get(Number(busca.termo)).subscribe({
        next: (ingrediente) => this.ingredientes.set([ingrediente]),
        error: (error) => {
          this.dialog.open(ErrorDialog, {
            data: error.error,
          });
        },
      });
      return;
    }

    this.api.getByDescricao(busca.termo).subscribe({
      next: (ingredientes) => this.ingredientes.set(ingredientes),
      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  verificarBarraDeBusca(): void {
    if (!this.buscaModel().termo.trim()) {
      this.carregarIngredientes();
    }
  }

  private limparFormulario(): void {
    this.editandoId.set(null);
    this.ingredienteModel.set({
      descricao: '',
      precoUnitario: 0,
      adicional: false,
    });
  }
}
