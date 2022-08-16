package pl.futurecollars.invoicing

import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat

import java.time.LocalDate

class TestHelpers {

    static company(int id) {
        new Company(("$id").repeat(10),
                "ul. Morska 1/$id 00-000 Poznań, Polska",
                "Ladybird")
    }

    static product(int id) {
        new InvoiceEntry("Butter $id", BigDecimal.valueOf(id * 10), BigDecimal.valueOf(id * 10 * 0.23), Vat.VAT_23)
    }

    static invoice(int id) {
        new Invoice(LocalDate.now(), company(id), company(id), List.of(product(id)))
    }
}
