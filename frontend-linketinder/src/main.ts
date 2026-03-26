import { Candidato } from "./models/Candidato.js";
import { Empresa } from "./models/Empresa.js";
import { Vaga } from "./models/Vaga.js";
import { UIService } from "./services/UIService.js";
import { StorageService } from "./repository/StorageService.js";
const { regex } = UIService;

const navButtons = document.querySelectorAll(".nav-btn");
const tabs = document.querySelectorAll(".tab");

navButtons.forEach((btn) => {
  btn.addEventListener("click", () => {
    const targetTab = btn.getAttribute("data-tab");

    navButtons.forEach((b) => b.classList.remove("active"));
    tabs.forEach((t) => t.classList.remove("active"));

    btn.classList.add("active");
    document.getElementById(targetTab!)?.classList.add("active");

    if (targetTab === "vagas-tab") UIService.renderizarVagas();
    if (targetTab === "candidatos-tab") UIService.renderizarCandidatos();
  });
});

const formButtons = document.querySelectorAll(".form-tab");
const forms = document.querySelectorAll(".form");

formButtons.forEach((btn) => {
  btn.addEventListener("click", () => {
    const formId = btn.id.replace("btn-", "");

    formButtons.forEach((b) => b.classList.remove("active"));
    forms.forEach((f) => f.classList.remove("active"));

    btn.classList.add("active");
    document.getElementById(formId)?.classList.add("active");
  });
});

document.getElementById('form-candidato')?.addEventListener('submit', (e) => {
  e.preventDefault();
  const nome = (document.getElementById('candidato-nome') as HTMLInputElement).value;
  const email = (document.getElementById('candidato-email') as HTMLInputElement).value;
  const cpf = (document.getElementById('candidato-cpf') as HTMLInputElement).value;
  const linkedin = (document.getElementById('candidato-linkedin') as HTMLInputElement).value;
  const cep = (document.getElementById('candidato-cep') as HTMLInputElement).value;
  const competencias = (document.getElementById('candidato-competencias') as HTMLInputElement).value.split(',').map(c => c.trim());

  if (!regex.nome.test(nome)) { alert('Nome inválido. Use apenas letras.'); return; }
  if (!regex.email.test(email)) { alert('E-mail inválido.'); return; }
  if (!regex.cpf.test(cpf)) { alert('CPF inválido. Use o formato 000.000.000-00.'); return; }
  if (!regex.linkedin.test(linkedin)) { alert('LinkedIn inválido. Ex: linkedin.com/in/seu-perfil'); return; }
  if (!regex.cep.test(cep)) { alert('CEP inválido. Use o formato 00000-000.'); return; }
  if (!competencias.every(c => regex.skill.test(c))) { alert('Competência inválida. Use apenas letras, números e # + . -'); return; }

  StorageService.adicionarCandidato(new Candidato(
    nome, email, cpf,
    parseInt((document.getElementById('candidato-idade') as HTMLInputElement).value),
    (document.getElementById('candidato-estado') as HTMLInputElement).value,
    cep,
    (document.getElementById('candidato-descricao') as HTMLTextAreaElement).value,
    (document.getElementById('candidato-formacao') as HTMLInputElement).value,
    competencias,
    linkedin
  ));
  alert('Candidato cadastrado com sucesso!');
  (e.target as HTMLFormElement).reset();
});

document.getElementById('form-empresa')?.addEventListener('submit', (e) => {
  e.preventDefault();
  const nome = (document.getElementById('empresa-nome') as HTMLInputElement).value;
  const email = (document.getElementById('empresa-email') as HTMLInputElement).value;
  const cnpj = (document.getElementById('empresa-cnpj') as HTMLInputElement).value;
  const cep = (document.getElementById('empresa-cep') as HTMLInputElement).value;

  if (!regex.nome.test(nome)) { alert('Nome inválido. Use apenas letras.'); return; }
  if (!regex.email.test(email)) { alert('E-mail inválido.'); return; }
  if (!regex.cnpj.test(cnpj)) { alert('CNPJ inválido. Use o formato 00.000.000/0000-00.'); return; }
  if (!regex.cep.test(cep)) { alert('CEP inválido. Use o formato 00000-000.'); return; }

  StorageService.adicionarEmpresa(new Empresa(
    nome, email, cnpj,
    (document.getElementById('empresa-pais') as HTMLInputElement).value,
    (document.getElementById('empresa-estado') as HTMLInputElement).value,
    cep,
    (document.getElementById('empresa-descricao') as HTMLTextAreaElement).value
  ));
  alert('Empresa cadastrada com sucesso!');
  (e.target as HTMLFormElement).reset();
});

document.getElementById('form-vaga')?.addEventListener('submit', (e) => {
  e.preventDefault();
  const cnpj = (document.getElementById('vaga-cnpj') as HTMLInputElement).value;
  const competencias = (document.getElementById('vaga-competencias') as HTMLInputElement).value.split(',').map(c => c.trim());

  if (!regex.cnpj.test(cnpj)) { alert('CNPJ inválido. Use o formato 00.000.000/0000-00.'); return; }
  if (!StorageService.empresaExiste(cnpj)) { alert('Empresa com esse CNPJ não encontrada. Cadastre a empresa primeiro.'); return; }
  if (!competencias.every(c => regex.skill.test(c))) { alert('Competência inválida. Use apenas letras, números e # + . -'); return; }

  StorageService.adicionarVaga(new Vaga(
    (document.getElementById('vaga-titulo') as HTMLInputElement).value,
    (document.getElementById('vaga-descricao') as HTMLTextAreaElement).value,
    cnpj,
    competencias
  ));
  alert('Vaga cadastrada com sucesso!');
  (e.target as HTMLFormElement).reset();
});

document.getElementById("btn-listar-candidatos")
  ?.addEventListener("click", () => {
    UIService.listarCandidatos();
  });

document.getElementById("btn-listar-vagas")?.addEventListener("click", () => {
  UIService.listarVagas();
});

document
  .getElementById("btn-listar-empresas")
  ?.addEventListener("click", () => {
    UIService.listarEmpresas();
  });

// Botões de excluir
document.getElementById('lista-cadastros')?.addEventListener('click', (e) => {
  const target = e.target as HTMLElement;
  const id = target.getAttribute('data-id');
  if (!id) return;

  if (target.classList.contains('btn-excluir-candidato')) 
  { 
    StorageService.excluirCandidato(id); UIService.listarCandidatos(); 
  }

  if (target.classList.contains('btn-excluir-empresa')) 
  { 
    StorageService.excluirEmpresa(id, target.getAttribute('data-cnpj')!); UIService.listarEmpresas(); 
  }

  if (target.classList.contains('btn-excluir-vaga')) { 
    StorageService.excluirVaga(id); UIService.listarVagas(); 
  }
});

// Modal do gráfico
const modal = document.getElementById("modal-grafico");
const btnGrafico = document.getElementById("btn-grafico");
const closeModal = document.querySelector(".close");

btnGrafico?.addEventListener("click", () => {
  modal?.classList.add("active");
  UIService.renderizarGrafico();
});

closeModal?.addEventListener("click", () => {
  modal?.classList.remove("active");
});

window.addEventListener("click", (e) => {
  if (e.target === modal) {
    modal?.classList.remove("active");
  }
});

UIService.renderizarVagas();
