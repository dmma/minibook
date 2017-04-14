package ua.dp.dmma.model;

/**
 * @author dmma
 */
public enum QuoteType {
    BID("B"), OFFER("O");

    private String quoteCode;

    QuoteType(String quoteCode) {
        this.quoteCode = quoteCode;
    }

    public static QuoteType getQuoteTypeByCode(String quoteCode) {
        for (QuoteType quoteType : values()) {
            if (quoteType.quoteCode.equals(quoteCode)) {
                return quoteType;
            }
        }
        throw new IllegalArgumentException("Unknown quote type:" + quoteCode);
    }
}
