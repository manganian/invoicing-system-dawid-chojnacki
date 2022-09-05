package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import spock.lang.Specification

import static pl.futurecollars.invoicing.TestHelpers.invoice

class InvoiceServiceUnitTest extends Specification {

    private InvoiceService service
    private Database database

    def setup() {
        database = Mock()
        service = new InvoiceService(database)
    }

    def "calling save() should delegate to database save() method"() {
        given:
        def invoice = invoice(1)
        when:
        service.save(invoice)
        then:
        1 * database.save(invoice)
    }
    def "calling delete method should return true and delegate to database delete method if invoice exists in database"() {
        given:
        database.getById(1) >> Optional.of(invoice(1))
        when:
        service.delete(1)
        then:
        1 * database.delete(1)
        true
    }
    def "calling deleteInvoice() should return false if invoice to update does not exists in database"() {
        given:
        database.getById(123) >> Optional.empty()
        when:
        def result = service.delete(123)
        then:
        !result
    }
    def "calling getById() should delegate to database getById() method"() {
        given:
        def invoiceId = 321
        when:
        service.getById(invoiceId)
        then:
        1 * database.getById(invoiceId)
    }
    def "calling getAll() should delegate to database getAll() method"() {
        when:
        service.getAll()
        then:
        1 * database.getAll()
    }
    def "calling update method should return true and delegate to database update method if invoice to update exists in database"() {
        given:
        database.getById(2) >> Optional.of(invoice(2))
        when:
        service.update(2, invoice(3))
        then:
        1 * database.update(2, invoice(3))
        true
    }
    def "calling update method should return false if invoice to update does not exists in database"() {
        given:
        database.getById(4) >> Optional.empty()
        when:
        def result = service.update(4, invoice(5))
        then:
        !result
    }
}
