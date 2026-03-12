// ============================================
// Configuran ambiente, tsconfig e package
console.log("Aprendendo TS");
console.log("Teste 1");
console.log("Teste 2");
// ============================================
// Tipos de dados - any, number, string, enum, array, funçao, tipo função,checagem de tipos automática
let nome = "Gust";
// nome = 25 - erro (não para a compilação)
console.log(nome);
let idade = 20;
idade = 20.8;
console.log(idade);
let logic = false;
// logic = 1 - erro
console.log(logic);
let minhaIdade;
minhaIdade = 20;
// minhaIdade = "20" - erro
console.log(minhaIdade);
let hobbies = ["cozinhar", 10];
console.log(typeof hobbies);
hobbies = ["nome"];
hobbies = [100];
console.log(hobbies[0]);
let address = ["Av Principal", 123]; // tupla
console.log(address[1]);
console.log(address);
var Cor;
(function (Cor) {
    Cor[Cor["verde"] = 0] = "verde";
    Cor[Cor["cinza"] = 1] = "cinza";
    Cor[Cor["azul"] = 10] = "azul";
})(Cor || (Cor = {}));
let cor = Cor.azul;
console.log(cor);
let carro = "Texto";
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
let calculo;
calculo = soma;
console.log(calculo(3, 10));
let user = {
    nome: "Gust",
    idade: 20
};
console.log(user);
// ==============================================
// Desafio:
let employee = {
    supervisors: ["Ana", "Fernando"],
    baterPonto(horario) {
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
let funcionario = {
    supervisors: ["Ana", "Fernando"],
    baterPonto(horario) {
        if (horario <= 8) {
            return "Ponto normal";
        }
        else {
            return "Fora do horário";
        }
    }
};
let nota = 10;
// ==================================================
// never - esse tipo nunca acontece, usado em  excessões
function falha(msg) {
    throw new Error(msg);
}
const product = {
    name: "Nome",
    price: 4,
    validarProduto() {
        if (!this.name || this.name.trim().length === 0) {
            falha("Precisa ter um nome");
        }
        if (!this.price || this.price <= 0) {
            falha("Precisa ter um preço");
        }
    }
};
product.validarProduto();
export {};
//# sourceMappingURL=learn.js.map