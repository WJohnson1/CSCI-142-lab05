/**
 * A class that creates a TollRecord which stores the data of a vehicle
 *
 * @author William Johnson
 */

public class TollRecord implements Comparable{

    /**
     * For printing toll records in reports
     * using {@link String#format(String, Object...)}
     */
    private static final String TOLL_RECORD_FORMAT = "[%11s] on #%2d, time %5d";
    private static final String OFF_FORMAT = "; off #%2d, time %5d";

    /**
     *
     */
    private String tag;
    private int onExit;
    private int offExit;
    private int onTime;
    private int offTime;

    /**
     * Value of uninitialized integer fields in this record
     */
    public static final int UNINITIALIZED = -1;

    /**
     * Create a new TollRecord given the tag of the vehicle and incoming exit number and time. The off exit and off time for the TollRecord are initialized as -1
     * @param tag the tag of the vehicle
     * @param onExit the exit that the vehicle gets on
     * @param onTime the time that the vehicle gets on
     */
    public TollRecord(String tag, int onExit, int onTime){
        this.tag = tag;
        this.onExit = onExit;
        this.onTime = onTime;
        this.offExit = UNINITIALIZED;
        this.offTime = UNINITIALIZED;
    }

    /**
     * Record the exit number and time at which this vehicle left the highway.
     * @param offExit the exit that the vehicle leaves the highway
     * @param offTime the time that the vehicle leaves the highway
     */
    public void setOffExit(int offExit, int offTime){
        this.offExit = offExit;
        this.offTime = offTime;
    }

    /**
     * Returns the tag value for the TollRecord
     * @return the tag value for the TollRecord
     */
    public String getTag(){
        return this.tag;
    }

    /**
     * Returns the onExit value for the TollRecord
     * @return the onExit value for the TollRecord
     */
    public int getOnExit(){
        return this.onExit;
    }

    /**
     * Returns  the onTime value for the TollRecord
     * @return  the onTime value for the TollRecord
     */
    public int getOnTime(){
        return this.onTime;
    }

    /**
     * Returns the offExit value for the TollRecord
     * @return the offExit value for the TollRecord
     */
    public int getOffExit(){
        return this.offExit;
    }

    /**
     * Returns the offTime value for the TollRecord
     * @return the offTime value for the TollRecord
     */
    public int getOffTime(){
        return this.offTime;
    }

    /**
     * Compute the toll paid by this vehicle
     * @return the fare of the TollRecord
     */
    public double getFare(){
        return TollSchedule.getFare(this.getOnExit(),this.getOffExit());
    }

    /**
     * Two TollRecord objects are equal if all of their inner values are the same.
     * @param o object that is compared to the TollRecord
     * @return true if the object is equal to the TollRecord, else false
     */
    public boolean equals(Object o){
        if (o instanceof TollRecord){
            TollRecord t = (TollRecord) o;
            return t.getOnExit()==this.getOnExit() && t.getOffExit()==this.getOffExit() &&
                    t.getOnTime()==this.getOnTime() && t.getOffTime()==this.getOffTime() &&
                    t.hashCode() == this.hashCode();
        }
        return false;
    }

    /**
     * Returns a string representation of this object
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        if (this.getOffExit() != -1) {
            return "[" + this.tag + "]{(" + this.getOnExit() + "," + this.getOnTime() + "),(" + this.getOffExit() + "," + this.getOffTime() + ")}";
        }
        else{
            return "[" + this.tag + "]{(" + this.getOnExit() + "," + this.getOnTime() + ")}";
        }
    }

    /**
     * Returns a string representation of this object suitable for display in a report
     * @return a string representation of this object suitable for display in a report
     */
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

    /**
     * Returns a hash code value for the object
     * @return a hash code value for the object
     */
    public int hashCode(){
        return this.getTag().charAt(0)*100 + this.getTag().charAt(1);
    }

    /**
     * The natural order comparison for TollRecords
     * @param o the object that the TollRecord is being compared to
     * @return the natural order comparison for the TollRecords
     */
    @Override
    public int compareTo(Object o) {
        TollRecord t = (TollRecord) o;
        if (this.getTag().equals(t.getTag())){
            return this.getOnTime() - t.getOnTime();
        }
        return this.hashCode()-t.hashCode();
    }

}
