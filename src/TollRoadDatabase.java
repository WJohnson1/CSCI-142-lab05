/* A few useful items are provided to you. You must write the rest. */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

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
    private HashMap database1;
    private HashMap database2;
    public TollRoadDatabase(String eventFileName){
        try (FileInputStream fileStr = new FileInputStream( eventFileName )){
            HashMap database = new HashMap();
            HashMap database2 = new HashMap();
            Scanner in = new Scanner( fileStr );
            while ( in.hasNext() ) {
                String part = in.next();
                List<String> TollList = Arrays.asList(part.split(","));
                String tag = TollList.get(1);
                if (database.containsKey(tag)) {
                    int exit_time = Integer.parseInt(TollList.get(0));
                    int off_exit = Integer.parseInt(TollList.get(2));
                    TollRecord t = (TollRecord) database.get(tag);
                    database.remove(tag);
                    t.setOffExit(off_exit,exit_time);
                    database2.put(tag,t);
                }
                else {
                    int time = Integer.parseInt(TollList.get(0));
                    int exit = Integer.parseInt(TollList.get(2));
                    TollRecord t = new TollRecord(tag, exit, time);
                    database.put(tag,t);
                }
            }
            this.database2 = database2;
            this.database1 = database;
        }
        catch( IOException ioe ) {
            System.err.println( "Could not open file " + eventFileName );
        }

    }
    public void enterEvent(String tag, int exit, int time){
        if (this.database1.containsKey(tag)){
            TollRecord t = (TollRecord) this.database1.get(tag);
            this.database1.remove(tag);
            t.setOffExit(exit,time);
            this.database2.put(tag,t);
        }
    }
    public void summaryReport(){
        System.out.println(database2.values());
    }
    public void onRoadReport(){
        for(TollRecord t: this.database2.values()){

        }
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
    public static void main(String[] args){
        TollRoadDatabase t = new TollRoadDatabase("C:\\Users\\William\\Desktop\\Git\\lab05-WCJ7833\\data\\5guys.txt");
        t.summaryReport();
    }
}
