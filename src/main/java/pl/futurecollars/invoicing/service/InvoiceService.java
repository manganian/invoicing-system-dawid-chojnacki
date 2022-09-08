package pl.futurecollars.invoicing.service;

import java.util.List;
import java.util.Optional;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InvoiceService {

  private final Database database;

  public InvoiceService(Database database) {
    this.database = database;
  }

  public int save(Invoice invoice) {
    return database.save(invoice);
  }

  public Optional<Invoice> getById(int id) {
    return database.getById(id);
  }

  public List<Invoice> getAll() {
    return database.getAll();
  }

  public boolean update(int id, Invoice updatedInvoice) {
    if (database.getById(id).isPresent()) {
      database.update(id, updatedInvoice);
      return true;
    }
    return false;
  }

  public boolean delete(int id) {
    if (database.getById(id).isPresent()) {
      database.delete(id);
      return true;
    }
    return false;
  }
}

