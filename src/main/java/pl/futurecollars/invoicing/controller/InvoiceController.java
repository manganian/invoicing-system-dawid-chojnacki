package pl.futurecollars.invoicing.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
@RequestMapping("invoices")
class InvoiceController {

  InvoiceService invoiceService = new InvoiceService(new InMemoryDatabase());

  @GetMapping
  public ResponseEntity<List<Invoice>> getAll() {
    return ResponseEntity.ok(invoiceService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Invoice> getById(@PathVariable int id) {
    return ResponseEntity.of(invoiceService.getById(id));
  }

  @PostMapping
  public ResponseEntity<Integer> saveInvoice(@RequestBody Invoice invoice) {
    invoiceService.save(invoice);
    return ResponseEntity.status(201).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable int id) {
    if (invoiceService.delete(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable int id, @RequestBody Invoice invoice) {
    if (invoiceService.update(id, invoice)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
