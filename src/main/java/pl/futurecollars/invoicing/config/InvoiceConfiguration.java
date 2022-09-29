package pl.futurecollars.invoicing.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.db.file.IdService;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;
import pl.futurecollars.invoicing.service.InvoiceService;
import pl.futurecollars.invoicing.utils.FilesService;
import pl.futurecollars.invoicing.utils.JsonService;

@Slf4j
@Configuration
public class InvoiceConfiguration {

  private static final String INVOICES_FILE_NAME = "invoices.json";
  private static final String ID_FILE_NAME = "id.txt";
  private static final String DATABASE_LOCATION = "db";

  @Bean
  public JsonService jsonService() {
    log.info("Creating jsonService");
    return new JsonService();
  }

  @Bean
  public FilesService filesService() {
    log.info("Creating filesService");
    return new FilesService();
  }

  @ConditionalOnProperty(name = "database.type", havingValue = "in-memory")
  @Bean
  public Database inMemoryDatabase() {
    log.info("loading in-memory database");
    return new InMemoryDatabase();
  }

  @Bean
  public InvoiceService invoiceService(Database database) {
    return new InvoiceService(database);
  }

  @Bean
  public IdService idService(FilesService filesService) throws IOException {
    Path idFilePath = Files.createTempFile(DATABASE_LOCATION, ID_FILE_NAME);
    return new IdService(idFilePath, filesService);
  }

  @ConditionalOnProperty(name = "database.type", havingValue = "file")
  @Bean
  public Database fileBasedDatabase(IdService idService, FilesService filesService, JsonService jsonService,
                                    @Value("${database.path:/db/defaultDb.json}") String databasePath) throws IOException {
    log.info("loading filebased database");
    log.debug(databasePath);

    Path databaseFilePath = Files.createTempFile(DATABASE_LOCATION, INVOICES_FILE_NAME);
    return new FileBasedDatabase(databaseFilePath, idService, filesService, jsonService);
  }
}
