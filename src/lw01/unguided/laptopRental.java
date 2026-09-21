package lw01.unguided;

public class laptopRental extends Rental {
    public laptopRental(String id, int days) {
        super(id, days);
    }

    @Override
    public int calculateCharge() {
        return getDays() * 40000 + 10000;
    }

    @Override
    public String label() {
        return "Laptop";
    }
}
