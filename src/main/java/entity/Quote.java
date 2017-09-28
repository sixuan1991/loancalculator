package entity;

import java.math.BigDecimal;

public class Quote {
    private int requestedAmount;
    private BigDecimal rate;
    private BigDecimal monthlyRepayment;
    private BigDecimal totalRepayment;

    public Quote(int requestedAmount, double rate, double monthlyRepayment, double totalRepayment) {
        this.requestedAmount = requestedAmount;
        this.rate = formatPercentageRate(rate);
        this.monthlyRepayment = formatCurrency(monthlyRepayment);
        this.totalRepayment = formatCurrency(totalRepayment);
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public BigDecimal getTotalRepayment() {
        return totalRepayment;
    }

    private BigDecimal formatPercentageRate(double rate) {
        return new BigDecimal(rate * 100).setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal formatCurrency(double currency) {
        return new BigDecimal(currency).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Quote) {
            Quote otherQuote = (Quote) o;

            return this.requestedAmount == (otherQuote.getRequestedAmount())
                    && this.rate.equals(otherQuote.getRate())
                    && this.monthlyRepayment.equals(otherQuote.getMonthlyRepayment())
                    && this.totalRepayment.equals(otherQuote.getTotalRepayment());
        } else {
            return false;
        }
    }
}