package ua.dp.dmma.service;

import ua.dp.dmma.model.Quote;
import ua.dp.dmma.model.QuoteType;

import java.util.*;

/**
 * @author dmma
 */
public class QuoteStorageService {

    private static final String DELETE_ALL_CODE = "0";

    private Map<String, Quote> offerStorage = new HashMap<>();
    private Map<String, Quote> bidStorage = new HashMap<>();

    public void addQuote(Quote quote) {
        Map<String, Quote> storage = getStorage(quote.getQuoteType());
        storage.put(quote.getQuoteId(), quote);
    }

    public void updateQuote(Quote quote) {
        Map<String, Quote> storage = getStorage(quote.getQuoteType());
        Quote oldQuote = storage.get(quote.getQuoteId());
        if (oldQuote != null) {
            quote.setQuoteCreationTime(oldQuote.getQuoteCreationTime());
            storage.put(quote.getQuoteId(), quote);
        }
    }

    public void deleteQuote(Quote quote) {
        Map<String, Quote> storage = getStorage(quote.getQuoteType());
        if (DELETE_ALL_CODE.equals(quote.getQuoteId())) {
            clearStorage(quote.getQuoteType());
        } else {
            storage.remove(quote.getQuoteId());
        }
    }

    public Map<String, Quote> getStoredOffers() {
        return offerStorage;
    }

    public Map<String, Quote> getStoredBids() {
        return bidStorage;
    }

    private Map<String, Quote> getStorage(QuoteType quoteType) {
        return QuoteType.BID == quoteType ? bidStorage : offerStorage;
    }

    private void clearStorage(QuoteType quoteType) {
        if (QuoteType.BID == quoteType) {
            bidStorage = new HashMap<>();
        } else {
            offerStorage = new HashMap<>();
        }
    }
}
