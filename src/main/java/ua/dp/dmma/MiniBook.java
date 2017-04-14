package ua.dp.dmma;

import ua.dp.dmma.model.OperationType;
import ua.dp.dmma.model.Quote;
import ua.dp.dmma.model.QuoteType;
import ua.dp.dmma.service.QuoteStorageService;
import ua.dp.dmma.util.BidComparator;
import ua.dp.dmma.util.OfferComparator;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author dmma
 */
public class MiniBook {

    public static final String OFFER_DUMP_FILE = "offer.book";

    public static final String BID_DUMP_FILE = "bid.book";

    private QuoteStorageService quoteStorageService;

    public MiniBook(QuoteStorageService quoteStorageService) {
        this.quoteStorageService = quoteStorageService;
    }

    public void processQuoteRequest(String[] quoteRequest) {
        validateQuoteRequest(quoteRequest);
        OperationType operationType = OperationType.getQuoteTypeByCode(quoteRequest[2]);
        Quote quote = convertRequest(quoteRequest);
        processOperationForQuote(quote, operationType);
    }

    public void displayBookContent() {
        Set<Quote> offers = new TreeSet<>(new OfferComparator());
        offers.addAll(quoteStorageService.getStoredOffers().values());
        System.out.println("OFFER");
        offers.forEach(System.out::println);

        Set<Quote> bids = new TreeSet<>(new BidComparator());
        bids.addAll(quoteStorageService.getStoredBids().values());
        System.out.println("BID");
        bids.forEach(System.out::println);
    }

    public void createBookDump(String destinationFolder) {
        serializeStorage(destinationFolder + File.separator + BID_DUMP_FILE, new HashSet<>(quoteStorageService.getStoredOffers().values()));
        serializeStorage(destinationFolder + File.separator + OFFER_DUMP_FILE, new HashSet<>(quoteStorageService.getStoredOffers().values()));
    }

    private void serializeStorage(String destinationPath, Set<Quote> storage) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(destinationPath))) {
            objectOutputStream.writeObject(storage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processOperationForQuote(Quote quote, OperationType operationType) {
        switch (operationType) {
            case NEW:
                quoteStorageService.addQuote(quote);
                break;
            case UPDATE:
                quoteStorageService.updateQuote(quote);
                break;
            case DELETE:
                quoteStorageService.deleteQuote(quote);
        }
    }

    private void validateQuoteRequest(String[] quoteRequest) {
        if (quoteRequest == null) {
            throw new IllegalArgumentException("Input tuple can't be null");
        }
        if (quoteRequest.length != 5) {
            throw new IllegalArgumentException("Wrong tuple size. Expected 5, but got " + quoteRequest.length);
        }
    }

    private Quote convertRequest(String[] quoteRequest) {
        return new Quote(quoteRequest[0], QuoteType.getQuoteTypeByCode(quoteRequest[1]), new BigDecimal(quoteRequest[3]), Integer.valueOf(quoteRequest[4]));
    }
}
