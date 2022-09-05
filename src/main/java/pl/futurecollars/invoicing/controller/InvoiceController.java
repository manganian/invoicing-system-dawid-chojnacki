package pl.futurecollars.invoicing.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
@RequestMapping("invoices")
class InvoiceController {

  private final InvoiceService invoiceService;

  @Autowired
  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping(produces = { "application/json;charset=UTF-8" })
  public ResponseEntity<List<Invoice>> getAll() {
    return ResponseEntity.ok(invoiceService.getAll());
  }

  @GetMapping(value = "/{id}", produces = { "application/json;charset=UTF-8" })
  public ResponseEntity<Invoice> getById(@PathVariable int id) {
    return ResponseEntity.of(invoiceService.getById(id));
  }

  @PostMapping
  public ResponseEntity<Integer> saveInvoice(@RequestBody Invoice invoice) {
    return new ResponseEntity<Integer>(invoiceService.save(invoice), HttpStatus.CREATED);
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
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
