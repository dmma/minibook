package ua.dp.dmma.model;

/**
 * @author dmma
 */
public enum OperationType {
    NEW("N"), UPDATE("U"), DELETE("D");

    private String quoteCode;

    OperationType(String quoteCode) {
        this.quoteCode = quoteCode;
    }

    public static OperationType getQuoteTypeByCode(String quoteCode) {
        for (OperationType quoteType : values()) {
            if (quoteType.quoteCode.equals(quoteCode)) {
                return quoteType;
            }
        }
        throw new IllegalArgumentException("Unknown quote operation:" + quoteCode);
    }
}
