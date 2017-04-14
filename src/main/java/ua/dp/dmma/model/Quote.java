package ua.dp.dmma.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dmma
 */
public class Quote implements Serializable {

    private String quoteId;
    private QuoteType quoteType;
    private BigDecimal quotePrice;
    private Integer quoteVolume;
    private LocalDateTime quoteCreationTime;

    public Quote(String quoteId, QuoteType quoteType, BigDecimal quotePrice, Integer quoteVolume) {
        this.quoteId = quoteId;
        this.quoteType = quoteType;
        this.quotePrice = quotePrice;
        this.quoteVolume = quoteVolume;
        this.quoteCreationTime = LocalDateTime.now();
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public QuoteType getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(QuoteType quoteType) {
        this.quoteType = quoteType;
    }

    public BigDecimal getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(BigDecimal quotePrice) {
        this.quotePrice = quotePrice;
    }

    public Integer getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(Integer quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public LocalDateTime getQuoteCreationTime() {
        return quoteCreationTime;
    }

    public void setQuoteCreationTime(LocalDateTime quoteCreationTime) {
        this.quoteCreationTime = quoteCreationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        return quoteId.equals(quote.quoteId);
    }

    @Override
    public int hashCode() {
        return quoteId.hashCode();
    }

    @Override
    public String toString() {
        return quoteId + "/" + quotePrice +
                "/" + quoteVolume;
    }
}
