package lw01.unguided;

public class ProjectorRental extends Rental {
    public ProjectorRental(String id, int days) {
        super(id, days);
    }

    @Override
    public int calculateCharge() {
        int d = getDays();
        int firstDays = Math.min(d, 3);
        int extraDays = Math.max(d - 3, 0);
        return firstDays * 60000 + extraDays * 45000 + 20000;
    }

    @Override
    public String label() {
        return "Projector";
    }
}
