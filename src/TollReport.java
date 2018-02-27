import java.util.Scanner;

/**
 * A class that creates a TollRoadDatabase based on information from a file
 *
 * @author William Johnson
 */
public class TollReport {
    /**
     * If the number of arguments is correct, the argument is guaranteed to be a valid event file. A TollRoadDatabase
     * will be created and will continuly ask the user for input until the user quits the program
     * @param args contains the filename that will be used to create a TollRoadDatabase
     */
    public static void main(String[] args){
        if (args.length == 1) {
            TollRoadDatabase t = new TollRoadDatabase(args[0]);
            t.summaryReport();
            t.onRoadReport();
            t.speederReport();
            t.printBills();
            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.println("'b <string>' to see bill for license tag");
                System.out.println("'e <number>' to see activity at exit");
                System.out.println("'q' to quit");
                System.out.print("> ");
                String user = in.next();
                if (user.equals("b")) {
                    String tag = in.next();
                    boolean a = false;
                    for (TollRecord c : t.getDatabase2()) {
                        if (c.getTag().equals(tag)) {
                            a = true;
                        }
                    }
                    if (a) {
                        t.printCustSummary(tag);
                        System.out.println();
                    } else {
                        System.out.println("Illegal command. Try again");
                    }
                } else if (user.equals("e")) {
                    int exit = Integer.parseInt(in.next());

                    if (TollSchedule.isValid(exit)) {
                        t.printExitActivity(exit);
                        System.out.println();
                    } else {
                        System.out.println("Illegal command. Try again");
                    }
                } else if (user.equals("q")) {
                    break;
                } else {
                    System.out.println("Illegal command. Try again");
                }
            }
        }
        else {
            System.out.println("Invalid number of arguments");
        }
    }
}
