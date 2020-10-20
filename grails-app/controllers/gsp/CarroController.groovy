package gsp

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CarroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Carro.list(params), model: [carroCount: Carro.count()]
    }

    def show(Carro carro) {
        respond carro
    }

    def create() {
        println Pessoa.list()
        respond new Carro(params), model: [pessoas: Pessoa.list()]
    }

    @Transactional
    def save(Carro carro) {
        carro.save()
        params.id = carro.id
        redirect(action: "show", params: params)
    }

    def edit(Carro carro) {
        respond carro
    }

    @Transactional
    def update(Carro carro) {
        if (carro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (carro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond carro.errors, view: 'edit'
            return
        }

        carro.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'carro.label', default: 'Carro'), carro.id])
                redirect carro
            }
            '*' { respond carro, [status: OK] }
        }
    }

    @Transactional
    def delete(Carro carro) {

        if (carro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        carro.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'carro.label', default: 'Carro'), carro.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'carro.label', default: 'Carro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
