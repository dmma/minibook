package ua.dp.dmma.util;

import ua.dp.dmma.model.Quote;

import java.util.Comparator;

/**
 * @author dmma
 */
public class OfferComparator implements Comparator<Quote> {
    @Override
    public int compare(Quote o1, Quote o2) {
        if (!o1.getQuotePrice().equals(o2.getQuotePrice())) {
            return o1.getQuotePrice().compareTo(o2.getQuotePrice());
        }
        if (!o1.getQuoteVolume().equals(o2.getQuoteVolume())) {
            return o2.getQuoteVolume().compareTo(o1.getQuoteVolume());
        }
        return o1.getQuoteCreationTime().compareTo(o2.getQuoteCreationTime());
    }
}
