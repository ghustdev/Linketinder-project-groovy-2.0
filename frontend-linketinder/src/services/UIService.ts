import { StorageService } from "../repositories/Storage.js";

export class UIService {
  static renderizarVagas(): void {
    const vagas = StorageService.obterVagas();
    const empresas = StorageService.obterEmpresas();
    const feed = document.getElementById("vagas-feed");

    if (!feed) return;
    feed.innerHTML = "";

    if (vagas.length === 0) {
      feed.innerHTML = "<p>Nenhuma vaga disponível no momento.</p>";
      return;
    }

    vagas.forEach((vaga) => {
      const empresa = empresas.find((e) => e.cnpj === vaga.cnpj);
      const card = document.createElement("div");
      card.className = "card";
      card.innerHTML = `
                <h3>${vaga.titulo}</h3>
                <p><strong>Requisitos:</strong> ${vaga.competencias.join(", ")}</p>
                <p><strong>Localização:</strong> ${empresa?.pais}, ${empresa?.estado}</p>
            `;
      feed.appendChild(card);
    });
  }

  static renderizarCandidatos(): void {
    const candidatos = StorageService.obterCandidatos();
    const feed = document.getElementById("candidatos-feed");

    if (!feed) return;
    feed.innerHTML = "";

    if (candidatos.length === 0) {
      feed.innerHTML = "<p>Nenhum candidato disponível no momento.</p>";
      return;
    }

    candidatos.forEach((candidato) => {
      const card = document.createElement("div");
      card.className = "card";
      card.innerHTML = `
                <p><strong>Competências:</strong> ${candidato.competencias.join(", ")}</p>
                <p><strong>Descrição:</strong> ${candidato.descricao}</p>
                <p><strong>Formação:</strong> ${candidato.formacao}</p>
                <p><strong>Estado:</strong> ${candidato.estado}</p>
            `;
      feed.appendChild(card);
    });
  }

  static renderizarGrafico(): void {
    const candidatos = StorageService.obterCandidatos();
    const competenciasMap: { [key: string]: number } = {};

    candidatos.forEach((candidato) => {
      candidato.competencias.forEach((comp) => {
        competenciasMap[comp] = (competenciasMap[comp] || 0) + 1;
      });
    });

    const labels = Object.keys(competenciasMap);
    const data = Object.values(competenciasMap);

    const canvas = document.getElementById(
      "chart-competencias",
    ) as HTMLCanvasElement;
    if (!canvas) return;

    new (window as any).Chart(canvas, {
      type: "bar",
      data: {
        labels: labels,
        datasets: [
          {
            label: "Quantidade de Candidatos",
            data: data,
            backgroundColor: "#FD2B7B",
            borderColor: "#00000",
            borderWidth: 1,
          },
        ],
      },
      options: {
        responsive: true,
        scales: {
          y: { beginAtZero: true },
        },
      },
    });
  }

  static listarCandidatos(): void {
    const candidatos = StorageService.obterCandidatos();
    const lista = document.getElementById("lista-cadastros");

    if (!lista) return;
    lista.innerHTML = "<h3>Candidatos Cadastrados</h3>";

    candidatos.forEach((candidato) => {
      const item = document.createElement("div");
      item.className = "item-lista";
      item.innerHTML = `
                <span>${candidato.nome} - ${candidato.email} - ${candidato.cpf}</span>
                <button data-id="${candidato.id}" class="btn-excluir-candidato">Excluir</button>
            `;
      lista.appendChild(item);
    });
  }

  static listarEmpresas(): void {
    const empresas = StorageService.obterEmpresas();
    const lista = document.getElementById("lista-cadastros");

    if (!lista) return;
    lista.innerHTML = "<h3>Empresas Cadastradas</h3>";

    empresas.forEach((empresa) => {
      const item = document.createElement("div");
      item.className = "item-lista";
      item.innerHTML = `
                <span>${empresa.nome} - ${empresa.cnpj}</span>
                <button data-cnpj="${empresa.cnpj}" data-id="${empresa.id}" class="btn-excluir-empresa">Excluir</button>
            `;
      lista.appendChild(item);
    });
  }

  static listarVagas(): void {
    const vagas = StorageService.obterVagas();
    const lista = document.getElementById("lista-cadastros");

    if (!lista) return;
    lista.innerHTML = "<h3>Vagas Cadastradas</h3>";

    vagas.forEach((vagas) => {
      const item = document.createElement("div");
      item.className = "item-lista";
      item.innerHTML = `
                <span>${vagas.titulo} - ${vagas.id} - ${vagas.cnpj}</span>
                <button data-id="${vagas.id}" class="btn-excluir-vaga">Excluir</button>
            `;
      lista.appendChild(item);
    });
  }

  static readonly regex = {
    nome: /[a-zA-Z]+/,
    email: /[\w._%+-]+@[\w.-]+\.[a-zA-Z]{2,4}/,
    cpf: /\d{3}\.\d{3}\.\d{3}-\d{2}/,
    cnpj: /\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}/,
    skill: /[a-zA-Z0-9#+.\-]{1,20}/,
    linkedin: /(https:\/\/)?(www\.)?linkedin\.com\/in\/[a-zA-Z0-9_-]+\/?/,
    cep: /\d{5}-\d{3}/,
  };
}
