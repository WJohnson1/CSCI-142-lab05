/* A few useful items are provided to you. You must write the rest. */

public class TollRoadDatabase {
    /**
     * For printing floating point values in dollar/cents format. Example:
     * System.out.println( String.format( DOLLAR_FORMAT, 10.5 );  // $10.50
     */
    private static final String DOLLAR_FORMAT = "$%5.2f";
    private static final String SPEED_FORMAT = "%5.1f MpH";

    /**
     * Universal new line
     */
    private static final String NL = System.lineSeparator();

    /**
     * Conversion constant from minutes to hours
     */
    public static final double MINUTES_PER_HOUR = 60.0;

    /**
     * This toll road's speed limit, in miles per hour
     */
    public static final double SPEED_LIMIT = 65.0;
    public TollRoadDatabase(String eventFileName){

    }
    public void enterEvent(String tag, int exit, int time){

    }
    public void summaryReport(){

    }
    public void onRoadReport(){

    }
    public void printBills(){

    }
    public double bill(String tag){
        return 0.0;
    }
    public void speederReport(){

    }
    public void printCustSummary(String tag){

    }
    public void printExitActivity(int exit){

    }
}
