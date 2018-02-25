/* A few useful items are provided to you. You must write the rest. */

public class TollRecord implements Comparable{

    /**
     * For printing toll records in reports
     * using {@link String#format(String, Object...)}
     */
    private static final String TOLL_RECORD_FORMAT = "[%11s] on #%2d, time %5d";
    private static final String OFF_FORMAT = "; off #%2d, time %5d";
    private String tag;
    private int onExit;
    private int offExit = -1;
    private int onTime;
    private int offTime = -1;
    /**
     * Value of uninitialized integer fields in this record
     */
    public static final int UNINITIALIZED = -1;
    public TollRecord(String tag, int onExit, int onTime){
        this.tag = tag;
        this.onExit = onExit;
        this.onTime = onTime;
    }

    public void setOffExit(int offExit, int offTime){
        this.offExit = offExit;
        this.offTime = offTime;
    }
    public String getTag(){
        return this.tag;
    }
    public int getOnExit(){
        return this.onExit;
    }
    public int getOnTime(){
        return this.onTime;
    }
    public int getOffExit(){
        return this.offExit;
    }
    public int getOffTime(){
        return this.offTime;
    }
    public double getFare(){
        return TollSchedule.getFare(this.getOnExit(),this.getOffExit());
    }
    public boolean equals(Object o){
        if (o instanceof TollRecord){
            TollRecord t = (TollRecord) o;
            return t.getOnExit()==this.getOnExit() && t.getOffExit()==this.getOffExit() &&
                    t.getOnTime()==this.getOnTime() && t.getOffTime()==this.getOffTime();
        }
        return false;
    }
    @Override
    public String toString() {
        if (this.getOffExit() != -1) {
            return "[" + this.tag + "]{(" + this.getOnExit() + "," + this.getOnTime() + "),(" + this.getOffExit() + "," + this.getOffTime() + ")}";
        }
        else{
            return "[" + this.tag + "]{(" + this.getOnExit() + "," + this.getOnTime() + ")}";
        }
    }
    public String report(){
        String f = String.format(TOLL_RECORD_FORMAT,this.getTag(),this.getOnExit(),this.getOnTime());
        if (this.offExit != -1) {
            String l = String.format(OFF_FORMAT, this.getOffExit(), this.getOffTime());
            return f + l;
        }
        else {
            return f;
        }
    }

    public int hashCode(){
        return this.getTag().charAt(0)*100 + this.getTag().charAt(1);
    }

    @Override
    public int compareTo(Object o) {
        TollRecord t = (TollRecord) o;
        if (this.getTag().equals(t.getTag())){
            return this.getOnTime() - t.getOnTime();
        }
        return this.hashCode()-t.hashCode();
    }

    public static void main(String[] args){

    }
}
