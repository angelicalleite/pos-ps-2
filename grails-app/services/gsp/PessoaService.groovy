package gsp

import grails.transaction.Transactional

@Transactional
class PessoaService {

    def serviceMethod() {
    }

    def save(Pessoa pessoa){
        pessoa.save()
        return pessoa
    }
}
