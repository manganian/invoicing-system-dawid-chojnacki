package pl.futurecollars.invoicing.controller.invoice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.futurecollars.invoicing.model.Invoice;

@RequestMapping ("invoices")
@Api (tags = {"invoice-controller"})
public interface InvoiceApi {

  @ApiOperation(value = "Add new invoice to system")
  @GetMapping(produces = {"application/json;charset=UTF-8"})
  ResponseEntity<List<Invoice>> getAll();

  @ApiOperation(value = "Get invoice by id")
  @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
  ResponseEntity<Invoice> getById(@PathVariable int id);

  @ApiOperation(value = "Add new invoice to system")
  @PostMapping
  ResponseEntity<Integer> saveInvoice(@RequestBody Invoice invoice);

  @ApiOperation(value = "Delete invoice with given id")
  @DeleteMapping("/{id}")
  ResponseEntity<?> delete(@PathVariable int id);

  @ApiOperation(value = "Update invoice with given id")
  @PutMapping("/{id}")
  ResponseEntity<?> update(@PathVariable int id, @RequestBody Invoice invoice);
}
