package exceptions;

public class QuoteUnavailableException extends RuntimeException {
    public QuoteUnavailableException (int requestedAmount) {
        super(String.format("Sorry, we are not able provide a quote for the request amount: £%d",
                requestedAmount));
    }
}
