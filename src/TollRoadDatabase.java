/* A few useful items are provided to you. You must write the rest. */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        try (FileInputStream fileStr = new FileInputStream( eventFileName )){
            ArrayList< TollRecord > database = new ArrayList<>();
            Scanner in = new Scanner( fileStr );
            while ( in.hasNext() ) {
                String part = in.next();
                System.out.println(part);
                List<String> TollList = Arrays.asList(part.split(","));
                int time =  Integer.parseInt(TollList.get(0));
                String tag = TollList.get(1);
                int exit =  Integer.parseInt(TollList.get(2));
                TollRecord t = new TollRecord(tag,exit,time);
                database.add(t);
            }
        }
        catch( IOException ioe ) {
            System.err.println( "Could not open file " + eventFileName );
        }
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
    public static void main(String[] args){
        TollRoadDatabase t = new TollRoadDatabase("C:\\Users\\William\\Desktop\\Git\\lab05-WCJ7833\\data\\5guys.txt");
    }
}
