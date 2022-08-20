package pl.futurecollars.invoicing.utils

import com.fasterxml.jackson.databind.ObjectMapper
import pl.futurecollars.invoicing.TestHelpers
import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Specification

class JsonServiceTest extends Specification {

    def "can convert object to json and read it back"() {
        given:
        def jsonService = new JsonService()
        def invoice = TestHelpers.invoice(12)

        when:
        def invoiceAsString = jsonService.toJson(invoice)

        and:
        def invoiceFromJson = jsonService.toObject(invoiceAsString, Invoice)

        then:
        invoice == invoiceFromJson
    }

    def "return exception when parsing wrong json string"() {
        given:
        def jsonService = new JsonService()
        ObjectMapper objectMapper = new ObjectMapper()
        def invoice3 = TestHelpers.invoice(3)
        def jsonString = objectMapper.writeValueAsString(invoice3)
        jsonString = jsonString.replace('[', 'z')

        when:
        jsonService.toObject(jsonString, Invoice)

        then:
        RuntimeException exception = thrown(RuntimeException)
        exception.message == "Failed to parse JSON"
    }
}
