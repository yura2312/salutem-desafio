import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { form, FormField } from '@angular/forms/signals';
import { MatCheckbox } from '@angular/material/checkbox';
import { ApiBebida, BebidaRequest, BebidaResponse } from '../service/api-bebida';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';

@Component({
  imports: [FormField,
    MatFormField,
    MatLabel,
    MatInput,
    MatCheckbox,
    MatButton],
  selector: 'app-bebida',
  templateUrl: './bebida.html',
})
export class Bebida implements OnInit {

  private api = inject(ApiBebida);

  bebidas = signal<BebidaResponse[]>([]);

  editandoId = signal<number | null>(null);

  bebidaModel = signal<BebidaRequest>({
    descricao: '',
    precoUnitario: 0,
    contemAcucar: false
  });

  bebidaForm = form(this.bebidaModel);

  ultimasBebidas = computed(() =>
    [...this.bebidas()]
      .sort((a, b) => b.id - a.id)
      .slice(0, 5)
  );

  ngOnInit(): void {
    this.carregarBebidas();
  }

  carregarBebidas(): void {
    this.api.getAll().subscribe({
      next: bebidas => {
        this.bebidas.set(bebidas);
      },
      error: erro => {
        console.error('Erro ao carregar bebidas', erro);
      }
    });
  }

  salvar(): void {
    const id = this.editandoId();

    if (id === null) {
      this.api.save(this.bebidaModel()).subscribe({
        next: bebida => {
          this.bebidas.update(lista => [...lista, bebida]);
          this.limparFormulario();
        }
      });

      return;
    }

    this.api.update(id, this.bebidaModel()).subscribe({
      next: bebidaAtualizada => {
        this.bebidas.update(lista =>
          lista.map(bebida =>
            bebida.id === id ? bebidaAtualizada : bebida
          )
        );

        this.limparFormulario();
      }
    });
  }

  editar(bebida: BebidaResponse): void {
    this.editandoId.set(bebida.id);

    this.bebidaModel.set({
      descricao: bebida.descricao,
      precoUnitario: bebida.precoUnitario,
      contemAcucar: bebida.contemAcucar
    });
  }

  deletar(id: number): void {
    this.api.delete(id).subscribe({
      next: () => {
        this.bebidas.update(lista =>
          lista.filter(bebida => bebida.id !== id)
        );
      }
    });
  }

  cancelarEdicao(): void {
    this.limparFormulario();
  }

  private limparFormulario(): void {
    this.editandoId.set(null);

    this.bebidaModel.set({
      descricao: '',
      precoUnitario: 0,
      contemAcucar: false
    });
  }
}