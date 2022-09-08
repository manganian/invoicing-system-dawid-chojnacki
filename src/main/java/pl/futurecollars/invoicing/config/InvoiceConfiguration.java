package pl.futurecollars.invoicing.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.db.file.IdService;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;
import pl.futurecollars.invoicing.service.InvoiceService;
import pl.futurecollars.invoicing.utils.FilesService;
import pl.futurecollars.invoicing.utils.JsonService;

@Configuration
public class InvoiceConfiguration {

  private static final String INVOICES_FILE_NAME = "invoices.json";
  private static final String ID_FILE_NAME = "id.txt";
  private static final String DATABASE_LOCATION = "db";

  @Bean
  public JsonService jsonService() {
    return new JsonService();
  }

  @Bean
  public FilesService fileService() {
    return new FilesService();
  }

  @Bean
  public InMemoryDatabase inMemoryDatabase() {
    return new InMemoryDatabase();
  }

  @Bean
  public InvoiceService invoiceService(@Qualifier("fileBasedDatabase") Database database) {
    return new InvoiceService(database);
  }

  @Bean
  public IdService idService(FilesService filesService) throws IOException {
    Path idFilePath = Files.createTempFile(DATABASE_LOCATION, ID_FILE_NAME);
    return new IdService(idFilePath, filesService);
  }

  @Bean
  public Database fileBasedDatabase(IdService idService, FilesService filesService, JsonService jsonService) throws IOException {
    Path databaseFilePath = Files.createTempFile(DATABASE_LOCATION, INVOICES_FILE_NAME);
    return new FileBasedDatabase(databaseFilePath, idService, filesService, jsonService);
  }
}
