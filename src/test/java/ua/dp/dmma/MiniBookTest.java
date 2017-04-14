package ua.dp.dmma;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.dp.dmma.service.QuoteStorageService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @author dmma
 */
public class MiniBookTest {

    private static final String DUMP_DEST_FOLDER = "dump";
    private MiniBook miniBook;

    @BeforeMethod
    public void setUp() {
        miniBook = new MiniBook(new QuoteStorageService());
    }

    @Test
    public void testMiniBook() {
        for (String[] quoteRequest : getTestDataSequence()) {
            miniBook.processQuoteRequest(quoteRequest);
        }
        miniBook.displayBookContent();
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Input tuple can't be null")
    public void testMiniBookNullQuoteTupleFail() {
        miniBook.processQuoteRequest(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Wrong tuple size. Expected 5, but got 0")
    public void testMiniBookWrongQuoteTupleLengthFail() {
        miniBook.processQuoteRequest(new String[0]);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Unknown quote type:BD")
    public void testMiniBookWrongQuoteTypeFail() {
        miniBook.processQuoteRequest(new String[]{"Q1", "BD", "N", "1.31", "1000000"});
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Unknown quote operation:A")
    public void testMiniBookWrongOperationFail() {
        miniBook.processQuoteRequest(new String[]{"Q1", "O", "A", "1.31", "1000000"});
    }

    @Test
    public void testMiniBookDumpCreation() {
        clearDestDirectory();
        for (String[] quoteRequest : getTestDataSequence()) {
            miniBook.processQuoteRequest(quoteRequest);
        }
        File destDirectory = new File(DUMP_DEST_FOLDER);
        assertTrue(destDirectory.mkdir());

        miniBook.createBookDump(DUMP_DEST_FOLDER);

        File bidDump = new File(DUMP_DEST_FOLDER + File.separator + MiniBook.BID_DUMP_FILE);
        assertTrue(bidDump.isFile());
        File offerDump = new File(DUMP_DEST_FOLDER + File.separator + MiniBook.OFFER_DUMP_FILE);
        assertTrue(offerDump.isFile());
    }

    private void clearDestDirectory() {
        File destDirectory = new File(DUMP_DEST_FOLDER);
        if (destDirectory.exists()) {
            for (String fileName : destDirectory.list()) {
                File currentFile = new File(destDirectory.getPath(), fileName);
                currentFile.delete();
            }
        }
        destDirectory.delete();
    }

    private List<String[]> getTestDataSequence() {
        List<String[]> inputQuotas = new ArrayList<>();
        String[] quote = new String[]{"Q1", "O", "N", "1.31", "1000000"};
        inputQuotas.add(quote);
        quote = new String[]{"Q2", "B", "N", "1.21", "1000000"};
        inputQuotas.add(quote);
        quote = new String[]{"Q3", "B", "N", "1.22", "1000000"};
        inputQuotas.add(quote);
        quote = new String[]{"Q4", "B", "N", "1.20", "1000000"};
        inputQuotas.add(quote);
        quote = new String[]{"Q5", "B", "N", "1.20", "1000000"};
        inputQuotas.add(quote);
        quote = new String[]{"Q6", "O", "N", "1.32", "1000000"};
        inputQuotas.add(quote);
        quote = new String[]{"Q7", "O", "N", "1.33", "200000"};
        inputQuotas.add(quote);
        quote = new String[]{"Q5", "B", "U", "1.20", "500000"};
        inputQuotas.add(quote);
        quote = new String[]{"Q7", "O", "U", "1.33", "100000"};
        inputQuotas.add(quote);
        quote = new String[]{"Q7", "O", "D", "0", "0"};
        inputQuotas.add(quote);
        return inputQuotas;
    }
}
