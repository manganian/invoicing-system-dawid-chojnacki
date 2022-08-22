package pl.futurecollars.invoicing.db.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.utils.FilesService;
import pl.futurecollars.invoicing.utils.JsonService;

@AllArgsConstructor
public class FileBasedDatabase implements Database {

  private final Path databasePath;
  private final IdService idService;
  private final FilesService filesService;
  private final JsonService jsonService;

  @Override
  public int save(Invoice invoice) {
    try {
      invoice.setId(idService.getNextIdAndIncreament());
      filesService.appendLineToFile(databasePath, jsonService.toJson(invoice));

      return invoice.getId();
    } catch (IOException ex) {
      throw new RuntimeException("Database failed to save invoice", ex);
    }
  }

  @Override
  public Optional<Invoice> getById(int id) {
    try {
      return filesService.readAllLines(databasePath)
          .stream()
          .filter(line -> containsId(line, id))
          .map(line -> jsonService.toObject(line, Invoice.class))
          .findFirst();
    } catch (IOException ex) {
      throw new RuntimeException("Database failed to get invoice with id: " + id, ex);
    }
  }

  @Override
  public List<Invoice> getAll() {
    try {
      return filesService.readAllLines(databasePath)
          .stream()
          .map(line -> jsonService.toObject(line, Invoice.class))
          .collect(Collectors.toList());
    } catch (IOException ex) {
      throw new RuntimeException("Failed to read invoices from file", ex);
    }
  }

  @Override
  public void update(int id, Invoice updatedInvoice) {
    if (getById(id).isEmpty()) {
      throw new IllegalArgumentException("Id " + id + " does not exist");
    }
    updatedInvoice.setId(id);
    String updatedInvoiceAsString = jsonService.toJson(updatedInvoice).trim();

    try {
      String ivoicesAsString = Files.readAllLines(databasePath)
          .stream()
          .map(invoice -> updatedInvoice(invoice, id, updatedInvoiceAsString))
          .collect(Collectors.joining("\n"));
      Files.writeString(databasePath, ivoicesAsString, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException exception) {
      throw new RuntimeException("Updating invoice failed");
    }
  }

  private boolean findById(String line, int id) {
    return line.contains("\"id\":" + id + ",");
  }

  private String updatedInvoice(String oldInvoiceAsString, int id, String updatedInvoiceAsString) {
    return findById(oldInvoiceAsString, id) ? updatedInvoiceAsString : oldInvoiceAsString;
  }

  @Override
  public void delete(int id) {
    try {
      var updatedList = filesService.readAllLines(databasePath)
          .stream()
          .filter(line -> !containsId(line, id))
          .collect(Collectors.toList());

      filesService.writeLinesToFile(databasePath, updatedList);

    } catch (IOException ex) {
      throw new RuntimeException("Failed to delete invoice with id: " + id, ex);
    }
  }

  private boolean containsId(String line, int id) {
    return line.contains("\"id\":" + id + ",");
  }
}
