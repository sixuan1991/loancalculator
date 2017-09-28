package entity;

public class LenderOffer implements Comparable<LenderOffer> {
    private String lender;
    private double rate;
    private int amount;

    public LenderOffer(String lender, double rate, int amount) {
        this.lender = lender;
        this.rate = rate;
        this.amount = amount;
    }

    public String getLender() {
        return lender;
    }

    public double getRate() {
        return rate;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int compareTo(LenderOffer otherOffer) {
        return Double.valueOf(this.rate).compareTo(otherOffer.getRate());
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof LenderOffer) {
            LenderOffer otherOffer = (LenderOffer) o;

            return this.lender.equals(otherOffer.getLender())
                    && this.rate == otherOffer.getRate()
                    && this.amount == otherOffer.getAmount();
        } else {
            return false;
        }
    }
}
