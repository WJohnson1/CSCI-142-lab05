/* A few useful items are provided to you. You must write the rest. */

public class TollRecord {

    /**
     * For printing toll records in reports
     * using {@link String#format(String, Object...)}
     */
    private static final String TOLL_RECORD_FORMAT = "[%11s] on #%2d, time %5d";
    private static final String OFF_FORMAT = "; off #%2d, time %5d";

    /**
     * Value of uninitialized integer fields in this record
     */
    public static final int UNINITIALIZED = -1;
    public TollRecord(String tag, int onExit, int onTime){

    }
    public void setOffExit(int offExit, int offTime){

    }
    public String getTag(){
        return "";
    }
    public int getOnExit(){
        return 0;
    }
    public int getOnTime(){
        return 0;
    }
    public int getOffExit(){
        return 0;
    }
    public int getOffTime(){
        return 0;
    }
    public double getFare(){
        return 0.0;
    }
    public boolean equals(Object o){
        return true;
    }
    @Override
    public String toString() {
        return super.toString();
    }
    public String report(){
        return "";
    }
    public int hashCode(){
        return 0;
    }
    public int compareTo(TollRecord o){
        return 0;
    }
    public static void main(String[] args){

    }
}
