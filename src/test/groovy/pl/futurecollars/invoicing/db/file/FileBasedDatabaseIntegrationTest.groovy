package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.db.AbstractDatabaseTest
import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.TestHelpers
import pl.futurecollars.invoicing.utils.FilesService
import pl.futurecollars.invoicing.utils.JsonService

import java.nio.file.Files

class FileBasedDatabaseIntegrationTest extends AbstractDatabaseTest {

    def dbPath

    @Override
    Database getDatabaseInstance() {
        def filesService = new FilesService()

        def idPath = File.createTempFile('ids', '.txt').toPath()
        def idService = new IdService(idPath, filesService)

        dbPath = File.createTempFile('invoices', '.json').toPath()
        return new FileBasedDatabase(dbPath, idService, filesService, new JsonService())
    }

    def "file based database writes invoices to correct file2"() {
        given:
        def db = getDatabaseInstance()

        when:
        db.save(TestHelpers.invoice(4))

        then:
        1 == Files.readAllLines(dbPath).size()

        when:
        db.save(TestHelpers.invoice(5))

        then:
        2 == Files.readAllLines(dbPath).size()
    }

    def "should return exception when can't save invoice"() {

        given:
        def db = getDatabaseInstance()
        Files.deleteIfExists(dbPath)

        when:
        db.save(TestHelpers.invoice(5))

        then:
        RuntimeException exception = thrown(RuntimeException)
        exception.message == "Database failed to save invoice"
    }

    def "should return exception when can't get invoice"() {

        given:
        def db = getDatabaseInstance()
        Files.deleteIfExists(dbPath)

        when:
        db.getById(2)

        then:
        RuntimeException exception2 = thrown(RuntimeException)
        exception2.message == "Database failed to get invoice with id: 2"
    }

    def "should return exception when can't get all invoices"() {

        given:
        def db = getDatabaseInstance()
        Files.deleteIfExists(dbPath)

        when:
        db.getAll()

        then:
        RuntimeException exception3 = thrown(RuntimeException)
        exception3.message == "Failed to read invoices from file"
    }

    def "should return exception when can't delete invoice"() {

        given:
        def db = getDatabaseInstance()
        Files.deleteIfExists(dbPath)

        when:
        db.delete(82)

        then:
        RuntimeException exception4 = thrown(RuntimeException)
        exception4.message == "Failed to delete invoice with id: 82"
    }
}
