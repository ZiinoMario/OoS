package bank;

public class ImportantTransfer extends Transfer {
    private int prio;
    public ImportantTransfer(String dat, double am, String desc, String send, String rec, int pr) {
        super(dat,am,desc,send,rec);
        prio = pr;
    }
    @Override
    public String toString() {
        return "ACHTUNG WICHTIG" + "Prio: " + prio + super.toString();
    }

}
