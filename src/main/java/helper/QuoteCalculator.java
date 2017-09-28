package helper;

import java.util.List;

import entity.LenderOffer;
import entity.Quote;

public class QuoteCalculator {

    private int loanPeriodInMonth;

    public QuoteCalculator(int loanPeriodInMonth) {
        this.loanPeriodInMonth = loanPeriodInMonth;
    }


    public Quote calculateQuote(int requestedAmount, List<LenderOffer> selectedOffers) {
        if (selectedOffers.isEmpty()) {
            return null;
        }

        double annualRate = calculateAnnualRate(requestedAmount, selectedOffers);
        double monthlyRepayment = calculateMonthlyRepayment(requestedAmount, annualRate);
        double totalRepayment = calculateTotalRepayment(monthlyRepayment);

        return new Quote(requestedAmount, annualRate, monthlyRepayment, totalRepayment);
    }

    private double calculateAnnualRate(int requestedAmount, List<LenderOffer> selectedOffers) {
        double annualRate = 0;

        for (LenderOffer lendOffer : selectedOffers) {
            annualRate += (lendOffer.getAmount() * 1.0 / requestedAmount) * lendOffer.getRate();
        }

        return annualRate;
    }

    private double calculateMonthlyRepayment(int requestedAmount, double annualRate) {
        double monthlyRate = Math.pow(annualRate + 1, 1.0/12) - 1;
        return (requestedAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, - loanPeriodInMonth));
    }

    private double calculateTotalRepayment(double monthlyRepayment) {
        return monthlyRepayment * loanPeriodInMonth;
    }
}
