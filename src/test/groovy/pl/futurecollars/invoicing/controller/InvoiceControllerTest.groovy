package pl.futurecollars.invoicing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.TestHelpers
import pl.futurecollars.invoicing.utils.JsonService
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Unroll
class InvoiceControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private JsonService jsonService

    private static final ENDPOINT = "/invoices"

    def cleanup() {
        deleteAllInvoices()
    }

    private int addOneInvoice(Invoice invoice) {
        def invoiceAsJsonString = jsonService.toJson(invoice)

        String id = mockMvc.perform(post(ENDPOINT)
                .content(invoiceAsJsonString)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andReturn()
                .response
                .getContentAsString()

        return id as int
    }

    private List<Invoice> addInvoices(int howMany) {
        (1..howMany).collect({ id ->
            def invoice = TestHelpers.invoice(id)
            invoice.id = addOneInvoice(invoice)
            return invoice
        })
    }

    private List<Invoice> getAllInvoices() {
        def response = mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .getContentAsString()

        return jsonService.toObject(response, Invoice[])
    }

    private Invoice getInvoiceById(int id) {
        def response = mockMvc.perform(get("$ENDPOINT/$id"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .getContentAsString()
        return jsonService.toObject(response, Invoice)
    }

    private void deleteInvoice(int id) {
        mockMvc.perform(delete("$ENDPOINT/$id"))
                .andExpect(status().isNoContent())
    }

    private void deleteAllInvoices() {
        getAllInvoices().each { invoice -> deleteInvoice(invoice.id) }
    }

    def "empty array is returned when no invoices were created"() {
        expect:
        getAllInvoices() == []
    }

    def "one invoice is successfully added to the database"() {
        given:
        Invoice invoiceToAdd = TestHelpers.invoice(5)

        when:
        def id = addOneInvoice(invoiceToAdd)
        invoiceToAdd.id = id

        then:
        getAllInvoices().size() > 0
        getAllInvoices().get(0) == invoiceToAdd
    }

    def "more than one invoice is successfully added to the database"() {
        when:
        List<Invoice> invoicesToAdd = addInvoices(8)

        then:
        invoicesToAdd.size() == getAllInvoices().size()
        invoicesToAdd == getAllInvoices()
    }

    def "returns correct ids when invoices added"() {
        given:
        def invoiceToAdd = TestHelpers.invoice(1)

        expect:
        def id = addOneInvoice(invoiceToAdd)
        addOneInvoice(invoiceToAdd) == id + 1
        addOneInvoice(invoiceToAdd) == id + 2
        addOneInvoice(invoiceToAdd) == id + 3
        addOneInvoice(invoiceToAdd) == id + 4
        addOneInvoice(invoiceToAdd) == id + 5
    }

    def "getting existing invoice by id returns appropriate invoice"() {
        when:
        List<Invoice> invoicesToAdd = addInvoices(11)
        Invoice invoiceToFind = invoicesToAdd.get(7)

        then:
        getInvoiceById(invoiceToFind.getId()) == invoiceToFind
    }

    def "returns 404 not found status when trying to get invoice by non-existent id [#id]"() {
        given:
        addInvoices(9)

        expect:
        mockMvc.perform(get("$ENDPOINT/$id"))
                .andExpect(status().isNotFound())

        where:
        id << [-88, -1, 0, 27, 119]
    }

    def "one invoice is successfully deleted from the database"() {
        given:
        List<Invoice> invoicesToAdd = addInvoices(100)
        Invoice invoiceTODelete = invoicesToAdd.get(0)
        getAllInvoices().size() == 100

        when:
        deleteInvoice(invoiceTODelete.getId())

        then:
        getAllInvoices().size() == 99
    }

    def "all invoices are successfully deleted from the database"() {
        given:
        addInvoices(40)
        getAllInvoices().size() == 40

        when:
        deleteAllInvoices()

        then:
        getAllInvoices().size() == 0
    }

    def "returns 404 not found status when trying to delete invoice by non-existent id [#id]"() {
        given:
        addInvoices(8)

        expect:
        mockMvc.perform(delete("$ENDPOINT/$id"))
                .andExpect(status().isNotFound())

        where:
        id << [-99, -1, 0, 123, 228]
    }

    def "invoice is successfully updated"() {
        given:
        def id = addOneInvoice(TestHelpers.invoice(1))
        def updatedInvoice = TestHelpers.invoice(52)
        updatedInvoice.id = id
        def updatedInvoiceAsJson = jsonService.toJson(updatedInvoice)

        expect:
        mockMvc.perform(put("$ENDPOINT/$id")
                .content(updatedInvoiceAsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())

        getInvoiceById(id) == updatedInvoice
    }

    def "returns 404 not found status when trying to update invoice by non-existent id [#id]"() {
        given:
        addInvoices(10)
        def updatedInvoice = jsonService.toJson(TestHelpers.invoice(9))

        expect:
        mockMvc.perform(put("$ENDPOINT/$id")
                .content(updatedInvoice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())

        where:
        id << [-77, -1, 0, 303, 410]
    }
}
