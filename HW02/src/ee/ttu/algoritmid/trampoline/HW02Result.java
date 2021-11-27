package ee.ttu.algoritmid.trampoline;

import java.util.List;

public class HW02Result implements Result{

    private List<String> jumps;
    private int totalFine;

    public void setJumps(List<String> jumps) {
        this.jumps = jumps;
    }

    public void setTotalFine(int totalFine) {
        this.totalFine = totalFine;
    }

    @Override
    public List<String> getJumps() {
        return this.jumps;
    }

    @Override
    public int getTotalFine() {
        return this.totalFine;
    }
}
