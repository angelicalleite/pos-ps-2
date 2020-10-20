package gsp

class Pessoa {

    String nome
    int idade
    Date dataDeNascimento

    static hasMany = [carros: Carro]

    static constraints = {
    }
}