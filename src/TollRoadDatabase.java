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
    private ArrayList<TollRecord> database2;
    public TollRoadDatabase(String eventFileName){
        try (FileInputStream fileStr = new FileInputStream( eventFileName )){
            HashMap database = new HashMap();
            ArrayList<TollRecord> database2 = new ArrayList<>();
            Scanner in = new Scanner( fileStr );
            while ( in.hasNext() ) {
                String part = in.next();
                List<String> TollList = Arrays.asList(part.split(","));
                String tag = TollList.get(1);
                int time = Integer.parseInt(TollList.get(0));
                int exit = Integer.parseInt(TollList.get(2));
                if (database.containsKey(tag)) {
                    TollRecord t = (TollRecord) database.get(tag);
                    database.remove(tag);
                    t.setOffExit(exit,time);
                    database2.add(t);
                }
                else {
                    TollRecord t = new TollRecord(tag, exit, time);
                    database.put(tag,t);
                }
            }
            Collections.sort(database2);
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
            this.database2.add(t);
        }
    }
    public void summaryReport(){
        System.out.println("\n"+this.database2.size() + " completed trips");

    }
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
    public void printBills(){
        System.out.println();
        System.out.println("BILLING INFORMATION");
        System.out.println("===================");
        Double total = 0.0;
        for (int i = 0;i<this.database2.size();i++){
            TollRecord t = this.database2.get(i);
            Double bill = TollSchedule.getFare(t.getOnExit(),t.getOffExit());
            System.out.print(t.report());
            System.out.printf(": $ %.2f",bill);
            System.out.println();
            total += bill;
        }
        System.out.printf("Total: $%.2f\n", total);
        System.out.println();
    }

    public double bill(String tag){
        Double total = 0.0;
        for (TollRecord t: this.database2){
            if (t.getTag().equals(tag)){
                Double bill = TollSchedule.getFare(t.getOnExit(),t.getOffExit());
                System.out.print(t.report());
                System.out.printf(": $ %.2f",bill);
                System.out.println();
                total+=bill;
            }
        }
        return total;
    }

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
                System.out.printf("\t %.1f MpH", speed);
                System.out.println();
            }
        }
    }
    public void printCustSummary(String tag){
        double total = bill(tag);
        System.out.println();
        System.out.printf("Vehicle total due: $ %.2f",total);
        System.out.println();
    }
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
    public static void main(String[] args){
        TollRoadDatabase t = new TollRoadDatabase("C:\\Users\\William\\Desktop\\Git\\lab05-WCJ7833\\data\\random1000.txt");
        t.summaryReport();
        t.onRoadReport();
        t.speederReport();
        t.printBills();
        Scanner in = new Scanner(System.in);
        while (true){
            System.out.println("'b <string>' to see bill for license tag");
            System.out.println("'e <number>' to see activity at exit");
            System.out.println("'q' to quit");
            System.out.print("> ");
            String user = in.next();
            if (user.equals("b")){
                String tag = in.next();
                boolean a = false;
                for (TollRecord c: t.database2){
                    if (c.getTag().equals(tag)){
                        a = true;
                    }
                }
                if (a) {
                    t.printCustSummary(tag);
                    System.out.println();
                }
                else {
                    System.out.println("Illegal command. Try again");
                }
            }
            else if (user.equals("e")){
                int exit = Integer.parseInt(in.next());

                if (TollSchedule.isValid(exit)){
                    t.printExitActivity(exit);
                    System.out.println();
                }
                else {
                    System.out.println("Illegal command. Try again");
                }
            }
            else if (user.equals("q")){
                break;
            }
            else{
                System.out.println("Illegal command. Try again");
            }
        }
    }
}
