package gsp

class Carro {

    String nome

    static belongsTo = [pessoa: Pessoa]

    static constraints = {
    }
}
