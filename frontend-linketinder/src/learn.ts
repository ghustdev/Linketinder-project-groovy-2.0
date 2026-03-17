// ============================================
// Configuran ambiente, tsconfig e package

console.log("Aprendendo TS");
console.log("Teste 1");
console.log("Teste 2");

// ============================================
// Tipos de dados - any, number, string, enum, array, funçao, tipo função,checagem de tipos automática

let nome = "Gust"
// nome = 25 - erro (não para a compilação)
console.log(nome);


let idade = 20
idade = 20.8
console.log(idade);


let logic = false
// logic = 1 - erro
console.log(logic);

let minhaIdade: number
minhaIdade = 20
// minhaIdade = "20" - erro
console.log(minhaIdade);

let hobbies: any = ["cozinhar", 10]
console.log(typeof hobbies);
hobbies = ["nome"]
hobbies = [100]
console.log(hobbies[0]);

let address: [string, number] = ["Av Principal", 123] // tupla

console.log(address[1]);
console.log(address);

enum Cor {
    verde,
    cinza,
    azul = 10
}
let cor: Cor = Cor.azul
console.log(cor);

let carro: any = "Texto"
carro = {
    marca: "BMW",
}
console.log(carro);


function retorna(): string {
    return nome
}
console.log(retorna());

function oi(): void {
    console.log("Oi");
    
}
console.log(oi());

function soma(a: number, b: number): number {
    return a + b
}
console.log(soma(5, 3));

let calculo: (a: number, b: number) => number
calculo = soma
console.log(calculo(3, 10));

let user: {nome: string, idade: number} = {
    nome: "Gust",
    idade: 20
}
console.log(user);

// ==============================================
// Desafio:

let employee: {
    supervisors: string[], 
    baterPonto(horario: number): string
} = {
    supervisors: ["Ana", "Fernando"],
    baterPonto(horario) {
        if (horario <= 8) {
            return "Ponto normal"
        } else {
            return "Fora do horário"
        }
    }
}
console.log(employee.supervisors[1])
console.log(employee.baterPonto(9))

// ==============================================
// Union Types e Alias - tipos personalizados
type Funcionario = {
    supervisors: string[], 
    baterPonto(horario: number): string
}

let funcionario: Funcionario = {
    supervisors: ["Ana", "Fernando"],
    baterPonto(horario) {
        if (horario <= 8) {
            return "Ponto normal"
        } else {
            return "Fora do horário"
        }
    }
}

let nota: number | string = 10

// ==================================================
// never - esse tipo nunca acontece, usado em  excessões

function falha(msg: string): never {
    throw new Error(msg)
}

const product = {
    name: "Nome",
    price: 4,
    validarProduto() {
        if (!this.name || this.name.trim().length === 0) {
            falha("Precisa ter um nome")
        }
        if (!this.price || this.price <= 0) {
            falha("Precisa ter um preço")
        }
    }
}
product.validarProduto()

console.log("Teste");

// =================================================
// Classes e POO
// TypeScript = procedural, estruturada, OO e funcional

class Data {
    dia: number
    mes: number
    public ano: number

    constructor(dia: number = 1, mes: number = 1, ano: number = 1970) {
        this.dia = dia
        this.mes = mes
        this.ano = ano
    }
}

const data = new Data(14, 12, 2019)
console.log(data);
console.log(data.ano);

// Desafio

class Product {
    private name: string
    private price: number
    private discount: number

    constructor(name: string, price: number, discount: number = 0) {
        this.name = name
        this.price = price
        this.discount = discount
    }

    getName(): string {
        return this.name
    }

    getPrice(): number {
        return this.price
    }

    getPriceWithDiscount(): number {
        return this.price * (1 - this.discount)
    }

    setName(name: string): void {
        this.name = name
    }

    setPrice(price: number): void {
        if (price <= 0) {
            throw new Error("Preço inválido!")
        }
        this.price = price
    }
}

const computer = new Product("Notebook", 1000, 0.05)
const phone = new Product("iPhone", 2000)

console.log(computer);
console.log(phone);

// Namespaces (funções / classes) - espaço reservado que não irá conflitar com escopo global, deve ser exportada

// ///<reference path="./area.ts"/>
// import { areaCircunferencia } from './area'

// import { areaCircunferencia } from './area'

console.log(5);
// console.log(areaCircunferencia(5));

// Baixar dependencia SystemJS para carregar modulos no browser, porém no cenario regal terá alguma ferramete de build para auxiliar isso (webpack / node)

// =================================================
// Interface (exclusivo do TS)

interface Humano {
    nome: string
    idade?: number
    altura?: number
    [prop: string]: any
}

function saudar(pessoa: Humano) {
    console.log('Ola, ' + pessoa.nome);
    
}

const pessoa: Humano = {
    nome: "João",
    idade: 27,
    altura: 1.75
}

saudar(pessoa)

// herdando o compromisso
class Cliente implements Humano {
    nome: string = 'Gustavo'
    idade: number = 1
    altura: number = 1.8
    saudar(sobrenome: string) {
        console.log("Olá, " + this.nome + " " + sobrenome);
    }
}

const cliente = new Cliente()
cliente.saudar("Schmidt")
