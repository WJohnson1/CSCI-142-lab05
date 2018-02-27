
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
/**
 * A class that creates a database for the TollRoad
 *
 * @author William Johnson
 */
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

    /**
     * A HashMap that holds all not completed trips
     */
    private HashMap database1;

    /**
     * An ArrayList that holds all completed trips
     */
    private ArrayList<TollRecord> database2;

    /**
     * Raises FileNotFoundException if the file cannot be opened, because one of the methods you write might raise it.
     * This method reads the event file and build all data structures needed later.
     * @param eventFileName the file that holds the data for the TollRoad
     */
    public TollRoadDatabase(String eventFileName){
        try (FileInputStream fileStr = new FileInputStream( eventFileName )){
            this.database1 = new HashMap();
            this.database2 = new ArrayList<>();
            Scanner in = new Scanner( fileStr );
            while ( in.hasNext() ) {
                String part = in.next();
                List<String> TollList = Arrays.asList(part.split(","));
                String tag = TollList.get(1);
                int time = Integer.parseInt(TollList.get(0));
                int exit = Integer.parseInt(TollList.get(2));
                this.enterEvent(tag,exit,time);
            }
            Collections.sort(this.database2);
        }
        catch( FileNotFoundException ioe ) {
            System.err.println( "Could not find file " + eventFileName );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If the tag is the database of incomplete trips, the TollRecord with that tag will have its offExit and offRime values declared
     * Else, a TollRecord is created with the input values and added to the database of incomplete trips
     * @param tag the tag value of a vehicle
     * @param exit the onExit or offExit value of a vehicle
     * @param time the onTime or offTime value of a vehicle
     */
    public void enterEvent(String tag, int exit, int time){
        if (this.database1.containsKey(tag)){
            TollRecord t = (TollRecord) this.database1.get(tag);
            this.database1.remove(tag);
            t.setOffExit(exit,time);
            this.database2.add(t);
        }
        else{
            TollRecord t = new TollRecord(tag, exit, time);
            this.database1.put(tag,t);
        }
    }

    /**
     * Print out the number of completed trips
     */
    public void summaryReport(){
        System.out.println("\n"+this.database2.size() + " completed trips");
    }

    /**
     * Print out a report listing the vehicles that are still on the toll road. The vehicles will be printed in order based on license tag.
     */
    public void onRoadReport(){
        System.out.println();
        System.out.println("On-road Report");
        System.out.println("==============");
        Map<String,TollRecord> map = new TreeMap<String,TollRecord>(this.database1);
        for (Object tag : map.keySet()){
            TollRecord t = (TollRecord) this.database1.get(tag);
            System.out.println(t.report());
        }
        System.out.println();
    }

    /**
     * Print out a billing report for the vehicles that completed trips on the toll road. The report lists the trips first by vehicle tag and then by the entry time for the vehicle's trip.
     */
    public void printBills(){
        System.out.println();
        System.out.println("BILLING INFORMATION");
        System.out.println("===================");
        Double total = 0.0;
        for (int i = 0;i<this.database2.size();i++){
            TollRecord t = this.database2.get(i);
            Double bill = t.getFare();
            System.out.print(t.report()+": ");
            System.out.printf(DOLLAR_FORMAT,bill);
            System.out.println();
            total += bill;
        }
        System.out.print("Total: ");
        System.out.printf(DOLLAR_FORMAT, total);
        System.out.println();
        System.out.println();
    }

    /**
     * Returns the bill value for a certain tag value of a TollRecord
     * @param tag the tag value of a TollRecord
     * @return the bill value
     */
    public double bill(String tag){
        Double total = 0.0;
        for (TollRecord t: this.database2){
            if (t.getTag().equals(tag)){
                Double bill = t.getFare();
                System.out.print(t.report() + ": ");
                System.out.printf(DOLLAR_FORMAT,bill);
                System.out.println();
                total+=bill;
            }
        }
        return total;
    }

    /**
     * List cars going above the speed limit
     */
    public void speederReport(){
        System.out.println();
        System.out.println("SPEEDER REPORT");
        System.out.println("==============");
        for (int i = 0; i<this.database2.size();i++) {
            TollRecord t = this.database2.get(i);
            Double distance = TollSchedule.getLocation(t.getOnExit()) - TollSchedule.getLocation(t.getOffExit());
            int time = t.getOffTime() - t.getOnTime();
            Double speed = Math.abs(distance / time * MINUTES_PER_HOUR);
            if (speed > SPEED_LIMIT) {
                System.out.println("Vehicle " + t.getTag() + ", starting at time " + t.getOnTime());
                System.out.println("\tfrom " + TollSchedule.getInterchange(t.getOnExit()));
                System.out.println("\tto " + TollSchedule.getInterchange(t.getOffExit()));
                System.out.printf("\t" + SPEED_FORMAT, speed);
                System.out.println();
            }
        }
    }

    /**
     * Print the summary information for a single customer, specified by license tag. The toll records are printed in order of time entered.
     * The complete trips will be listed in the same format as the complete billing listing, with the fare of each trip listed at the end of the line.
     * A total due is printed at the end.
     * @param tag the specific license tag
     */
    public void printCustSummary(String tag){
        double total = bill(tag);
        System.out.println();
        System.out.printf("Vehicle total due: " + DOLLAR_FORMAT,total);
        System.out.println();
    }

    /**
     * Print all toll records that include a specific exit as their on or off point. Records are listed completed first,
     * in order by vehicle tag and then by entry time; afterwards incomplete trips are listed in the same ordering.
     * @param exit the specific exit
     */
    public void printExitActivity(int exit){
        System.out.println();
        System.out.println("EXIT " + exit + " REPORT");
        System.out.print("==============\n");
        for (TollRecord t: this.database2){
            if (t.getOnExit() == exit || t.getOffExit() == exit){
                System.out.println(t.report());
            }
        }
        for (Object tag : this.database1.keySet()){
            TollRecord t = (TollRecord) this.database1.get(tag);
            if (t.getOnExit() == exit || t.getOffExit() == exit){
                System.out.println(t.report());
            }
        }
    }

    /**
     * Returns the database of completed trips
     * @return the database of completed trips
     */
    public ArrayList<TollRecord> getDatabase2() {
        return database2;
    }
}
