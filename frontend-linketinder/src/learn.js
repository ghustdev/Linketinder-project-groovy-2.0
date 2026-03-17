// ============================================
// Configuran ambiente, tsconfig e package
console.log("Aprendendo TS");
console.log("Teste 1");
console.log("Teste 2");
// ============================================
// Tipos de dados - any, number, string, enum, array, funçao, tipo função,checagem de tipos automática
var nome = "Gust";
// nome = 25 - erro (não para a compilação)
console.log(nome);
var idade = 20;
idade = 20.8;
console.log(idade);
var logic = false;
// logic = 1 - erro
console.log(logic);
var minhaIdade;
minhaIdade = 20;
// minhaIdade = "20" - erro
console.log(minhaIdade);
var hobbies = ["cozinhar", 10];
console.log(typeof hobbies);
hobbies = ["nome"];
hobbies = [100];
console.log(hobbies[0]);
var address = ["Av Principal", 123]; // tupla
console.log(address[1]);
console.log(address);
var Cor;
(function (Cor) {
    Cor[Cor["verde"] = 0] = "verde";
    Cor[Cor["cinza"] = 1] = "cinza";
    Cor[Cor["azul"] = 10] = "azul";
})(Cor || (Cor = {}));
var cor = Cor.azul;
console.log(cor);
var carro = "Texto";
carro = {
    marca: "BMW",
};
console.log(carro);
function retorna() {
    return nome;
}
console.log(retorna());
function oi() {
    console.log("Oi");
}
console.log(oi());
function soma(a, b) {
    return a + b;
}
console.log(soma(5, 3));
var calculo;
calculo = soma;
console.log(calculo(3, 10));
var user = {
    nome: "Gust",
    idade: 20
};
console.log(user);
// ==============================================
// Desafio:
var employee = {
    supervisors: ["Ana", "Fernando"],
    baterPonto: function (horario) {
        if (horario <= 8) {
            return "Ponto normal";
        }
        else {
            return "Fora do horário";
        }
    }
};
console.log(employee.supervisors[1]);
console.log(employee.baterPonto(9));
var funcionario = {
    supervisors: ["Ana", "Fernando"],
    baterPonto: function (horario) {
        if (horario <= 8) {
            return "Ponto normal";
        }
        else {
            return "Fora do horário";
        }
    }
};
var nota = 10;
// ==================================================
// never - esse tipo nunca acontece, usado em  excessões
function falha(msg) {
    throw new Error(msg);
}
var product = {
    name: "Nome",
    price: 4,
    validarProduto: function () {
        if (!this.name || this.name.trim().length === 0) {
            falha("Precisa ter um nome");
        }
        if (!this.price || this.price <= 0) {
            falha("Precisa ter um preço");
        }
    }
};
product.validarProduto();
console.log("Teste");
// =================================================
// Classes e POO
// TypeScript = procedural, estruturada, OO e funcional
var Data = /** @class */ (function () {
    function Data(dia, mes, ano) {
        if (dia === void 0) { dia = 1; }
        if (mes === void 0) { mes = 1; }
        if (ano === void 0) { ano = 1970; }
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }
    return Data;
}());
var data = new Data(14, 12, 2019);
console.log(data);
console.log(data.ano);
// Desafio
var Product = /** @class */ (function () {
    function Product(name, price, discount) {
        if (discount === void 0) { discount = 0; }
        this.name = name;
        this.price = price;
        this.discount = discount;
    }
    Product.prototype.getName = function () {
        return this.name;
    };
    Product.prototype.getPrice = function () {
        return this.price;
    };
    Product.prototype.getPriceWithDiscount = function () {
        return this.price * (1 - this.discount);
    };
    Product.prototype.setName = function (name) {
        this.name = name;
    };
    Product.prototype.setPrice = function (price) {
        if (price <= 0) {
            throw new Error("Preço inválido!");
        }
        this.price = price;
    };
    return Product;
}());
var computer = new Product("Notebook", 1000, 0.05);
var phone = new Product("iPhone", 2000);
console.log(computer);
console.log(phone);
// Namespaces (funções / classes) - espaço reservado que não irá conflitar com escopo global, deve ser exportada
// ///<reference path="./area.ts"/>
// import { areaCircunferencia } from './area'
// import { areaCircunferencia } from './area'
console.log(5);
function saudar(pessoa) {
    console.log('Ola, ' + pessoa.nome);
}
var pessoa = {
    nome: "João",
    idade: 27,
    altura: 1.75
};
saudar(pessoa);
// herdando o compromisso (obriga a implementar o que tem nele, semelhante ao abstratc, porém orbigando a usar os mesmos métodos)
var Cliente = /** @class */ (function () {
    function Cliente() {
        this.nome = 'Gustavo';
        this.idade = 1;
        this.altura = 1.8;
    }
    Cliente.prototype.saudar = function (sobrenome) {
        console.log("Olá, " + this.nome + " " + sobrenome);
    };
    return Cliente;
}());
var cliente = new Cliente();
cliente.saudar("Schmidt");
var AB = /** @class */ (function () {
    function AB() {
    }
    return AB;
}());
