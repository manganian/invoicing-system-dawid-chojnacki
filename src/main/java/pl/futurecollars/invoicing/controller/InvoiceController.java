package pl.futurecollars.invoicing.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
@AllArgsConstructor
public class InvoiceController implements InvoiceApi {

  private final InvoiceService invoiceService;

  @Override
  public ResponseEntity<List<Invoice>> getAll() {
    return ResponseEntity.ok(invoiceService.getAll());
  }

  @Override
  public ResponseEntity<Invoice> getById(@PathVariable int id) {
    return ResponseEntity.of(invoiceService.getById(id));
  }

  @Override
  public ResponseEntity<Integer> saveInvoice(@RequestBody Invoice invoice) {
    return new ResponseEntity<Integer>(invoiceService.save(invoice), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<?> delete(@PathVariable int id) {
    if (invoiceService.delete(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  public ResponseEntity<?> update(@PathVariable int id, @RequestBody Invoice invoice) {
    if (invoiceService.update(id, invoice)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
