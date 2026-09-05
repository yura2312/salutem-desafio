import { CurrencyPipe, DatePipe } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { form, FormField } from '@angular/forms/signals';

import { MatButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

import { ApiBebida, BebidaResponse } from '../service/api-bebida';
import { ApiHamburguer, HamburguerResponse } from '../service/api-hamburguer';
import { ApiPedido, PedidoRequest, PedidoResponse } from '../service/api-pedido';
import { ErrorDialog } from '../shared/error-dialog/error-dialog';

type TipoBusca = 'id' | 'descricao';

@Component({
  selector: 'app-pedido',

  imports: [
    CurrencyPipe,
    DatePipe,
    FormField,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    MatSelectModule,
  ],

  templateUrl: './pedido.html',
})
export class Pedido implements OnInit {
  private readonly api = inject(ApiPedido);
  private readonly apiHamburguer = inject(ApiHamburguer);
  private readonly apiBebida = inject(ApiBebida);
  private readonly dialog = inject(MatDialog);

  // =============================
  // PEDIDOS
  // =============================

  readonly pedidos = signal<PedidoResponse[]>([]);
  readonly editandoId = signal<number | null>(null);

  readonly pedidoModel = signal<PedidoRequest>(this.novoPedido());

  readonly pedidoForm = form(this.pedidoModel);

  readonly ultimosPedidos = computed(() =>
    [...this.pedidos()].sort((a, b) => b.id - a.id).slice(0, 5),
  );

  // =============================
  // CARDÁPIO
  // =============================

  readonly hamburguers = signal<HamburguerResponse[]>([]);
  readonly bebidas = signal<BebidaResponse[]>([]);

  // =============================
  // BUSCA DE PEDIDOS
  // =============================

  readonly buscaModel = signal({
    termo: '',
  });

  readonly buscaForm = form(this.buscaModel);

  // =============================
  // BUSCA HAMBÚRGUER
  // =============================

  readonly buscaHamburguerModel = signal<{
    tipo: TipoBusca;
    termo: string;
  }>({
    tipo: 'descricao',
    termo: '',
  });

  readonly buscaHamburguerForm = form(this.buscaHamburguerModel);

  readonly hamburgueresEncontrados = signal<HamburguerResponse[]>([]);

  readonly hamburguerSelecionado = signal<HamburguerResponse | null>(null);

  readonly quantidadeHamburguerSelecionado = signal(1);

  // =============================
  // BUSCA BEBIDA
  // =============================

  readonly buscaBebidaModel = signal<{
    tipo: TipoBusca;
    termo: string;
  }>({
    tipo: 'descricao',
    termo: '',
  });

  readonly buscaBebidaForm = form(this.buscaBebidaModel);

  readonly bebidasEncontradas = signal<BebidaResponse[]>([]);

  readonly bebidaSelecionada = signal<BebidaResponse | null>(null);

  readonly quantidadeBebidaSelecionada = signal(1);


  readonly hamburgueresSelecionados = computed(() => {
    const quantidades = this.pedidoModel().idHamburguerQuantidade;

    return this.hamburguers()
      .filter((hamburguer) => quantidades[hamburguer.id] !== undefined)
      .map((hamburguer) => ({
        ...hamburguer,
        quantidade: quantidades[hamburguer.id],
      }));
  });

  readonly bebidasSelecionadas = computed(() => {
    const quantidades = this.pedidoModel().idBebidaQuantidade;

    return this.bebidas()
      .filter((bebida) => quantidades[bebida.id] !== undefined)
      .map((bebida) => ({
        ...bebida,
        quantidade: quantidades[bebida.id],
      }));
  });



  ngOnInit(): void {
    this.carregarPedidos();
    this.carregarCardapio();
  }

  carregarPedidos(): void {
    this.api.getAll().subscribe({
      next: (pedidos) => this.pedidos.set(pedidos),

      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  carregarCardapio(): void {
    this.apiHamburguer.getAll().subscribe({
      next: (hamburguers) => this.hamburguers.set(hamburguers),

      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });

    this.apiBebida.getAll().subscribe({
      next: (bebidas) => this.bebidas.set(bebidas),

      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }


  salvar(): void {
    const id = this.editandoId();
    const request = this.pedidoModel();

    if (id === null) {
      this.api.save(request).subscribe({
        next: (pedido) => {
          this.pedidos.update((lista) => [...lista, pedido]);

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
      next: (pedidoAtualizado) => {
        this.pedidos.update((lista) =>
          lista.map((pedido) => (pedido.id === id ? pedidoAtualizado : pedido)),
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

  editar(pedido: PedidoResponse): void {
    this.editandoId.set(pedido.id);

    this.pedidoModel.set({
      descricao: pedido.descricao,
      clienteNome: pedido.clienteNome,
      clienteEndereco: pedido.clienteEndereco,
      clienteTelefone: pedido.clienteTelefone,

      idHamburguerQuantidade: this.itensParaQuantidades(pedido.hamburgueres),

      idBebidaQuantidade: this.itensParaQuantidades(pedido.bebidas),

      observacoes: pedido.observacoes ?? '',
    });
  }

  deletar(id: number): void {
    this.api.delete(id).subscribe({
      next: () => {
        this.pedidos.update((lista) => lista.filter((pedido) => pedido.id !== id));

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

  // =============================
  // PESQUISA PEDIDO
  // =============================

  pesquisar(): void {
    const termo = this.buscaModel().termo.trim();

    if (!termo) {
      this.carregarPedidos();
      return;
    }

    this.api.get(Number(termo)).subscribe({
      next: (pedido) => this.pedidos.set([pedido]),

      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  verificarBarraDeBusca(): void {
    if (!this.buscaModel().termo.trim()) {
      this.carregarPedidos();
    }
  }

  // =============================
  // PESQUISA HAMBÚRGUER
  // =============================

  pesquisarHamburguer(): void {
    const busca = this.buscaHamburguerModel();

    const termo = busca.termo.trim();

    if (!termo) {
      this.hamburgueresEncontrados.set([]);
      return;
    }

    if (busca.tipo === 'id') {
      this.apiHamburguer.get(Number(termo)).subscribe({
        next: (hamburguer) => {
          this.hamburgueresEncontrados.set([hamburguer]);
        },

        error: (error) => {
          this.dialog.open(ErrorDialog, {
            data: error.error,
          });
        },
      });

      return;
    }

    this.apiHamburguer.getByDescricao(termo).subscribe({
      next: (hamburguers) => {
        this.hamburgueresEncontrados.set(hamburguers);
      },

      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  selecionarHamburguer(hamburguer: HamburguerResponse): void {
    this.hamburguerSelecionado.set(hamburguer);

    this.quantidadeHamburguerSelecionado.set(1);
  }

  adicionarHamburguer(): void {
    const hamburguer = this.hamburguerSelecionado();

    const quantidade = this.quantidadeHamburguerSelecionado();

    if (!hamburguer || quantidade <= 0) {
      return;
    }

    const id = hamburguer.id;
    this.pedidoModel.update((pedido) => {
      const quantidadeAtual = pedido.idHamburguerQuantidade[id] ?? 0;

      return {
        ...pedido,

        idHamburguerQuantidade: {
          ...pedido.idHamburguerQuantidade,

          [id]: quantidadeAtual + quantidade,
        },
      };
    });

    this.hamburguerSelecionado.set(null);

    this.quantidadeHamburguerSelecionado.set(1);

    this.buscaHamburguerModel.update((busca) => ({
      ...busca,
      termo: '',
    }));

    this.hamburgueresEncontrados.set([]);
  }

  removerHamburguer(id: number): void {
    this.pedidoModel.update((pedido) => {
      const quantidades = {
        ...pedido.idHamburguerQuantidade,
      };

      delete quantidades[id];

      return {
        ...pedido,
        idHamburguerQuantidade: quantidades,
      };
    });
  }
  //Bebida
  pesquisarBebida(): void {
    const busca = this.buscaBebidaModel();

    const termo = busca.termo.trim();

    if (!termo) {
      this.bebidasEncontradas.set([]);
      return;
    }

    if (busca.tipo === 'id') {
      this.apiBebida.get(Number(termo)).subscribe({
        next: (bebida) => {
          this.bebidasEncontradas.set([bebida]);
        },

        error: (error) => {
          this.dialog.open(ErrorDialog, {
            data: error.error,
          });
        },
      });

      return;
    }

    this.apiBebida.getByDescricao(termo).subscribe({
      next: (bebidas) => {
        this.bebidasEncontradas.set(bebidas);
      },

      error: (error) => {
        this.dialog.open(ErrorDialog, {
          data: error.error,
        });
      },
    });
  }

  selecionarBebida(bebida: BebidaResponse): void {
    this.bebidaSelecionada.set(bebida);

    this.quantidadeBebidaSelecionada.set(1);
  }

  adicionarBebida(): void {
    const bebida = this.bebidaSelecionada();

    const quantidade = this.quantidadeBebidaSelecionada();

    if (!bebida || quantidade <= 0) {
      return;
    }

    const id = bebida.id;

    this.pedidoModel.update((pedido) => {
      const quantidadeAtual = pedido.idBebidaQuantidade[id] ?? 0;

      return {
        ...pedido,

        idBebidaQuantidade: {
          ...pedido.idBebidaQuantidade,

          [id]: quantidadeAtual + quantidade,
        },
      };
    });

    this.bebidaSelecionada.set(null);

    this.quantidadeBebidaSelecionada.set(1);

    this.buscaBebidaModel.update((busca) => ({
      ...busca,
      termo: '',
    }));

    this.bebidasEncontradas.set([]);
  }

  removerBebida(id: number): void {
    this.pedidoModel.update((pedido) => {
      const quantidades = {
        ...pedido.idBebidaQuantidade,
      };

      delete quantidades[id];

      return {
        ...pedido,
        idBebidaQuantidade: quantidades,
      };
    });
  }

  private novoPedido(): PedidoRequest {
    return {
      descricao: '',
      clienteNome: '',
      clienteEndereco: '',
      clienteTelefone: '',
      idHamburguerQuantidade: {},
      idBebidaQuantidade: {},
      observacoes: '',
    };
  }

  private limparFormulario(): void {
    this.editandoId.set(null);

    this.pedidoModel.set(this.novoPedido());

    this.hamburguerSelecionado.set(null);
    this.bebidaSelecionada.set(null);

    this.hamburgueresEncontrados.set([]);
    this.bebidasEncontradas.set([]);

    this.quantidadeHamburguerSelecionado.set(1);

    this.quantidadeBebidaSelecionada.set(1);
  }

  private itensParaQuantidades(
    itens: Array<{
      id: number;
      quantidade: number;
    }>,
  ): Record<string, number> {
    return Object.fromEntries(itens.map((item) => [String(item.id), item.quantidade]));
  }
}