package impl;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4753.5a97eca04 modeling language!*/


// line 43 "system.ump"
// line 75 "system.ump"
public class Entry {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //src.impl.Entry Attributes
    private int deviceID;

    //src.impl.Entry Associations
    private eTicket eTicket;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Entry(int aDeviceID, eTicket aETicket) {
        deviceID = aDeviceID;
        boolean didAddETicket = setETicket(aETicket);
        if (!didAddETicket) {
            throw new RuntimeException("Unable to create entry due to src.impl.eTicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setDeviceID(int aDeviceID) {
        boolean wasSet = false;
        deviceID = aDeviceID;
        wasSet = true;
        return wasSet;
    }

    public int getDeviceID() {
        return deviceID;
    }

    /* Code from template association_GetOne */
    public eTicket getETicket() {
        return eTicket;
    }

    /* Code from template association_SetOneToMany */
    public boolean setETicket(eTicket aETicket) {
        boolean wasSet = false;
        if (aETicket == null) {
            return wasSet;
        }

        eTicket existingETicket = eTicket;
        eTicket = aETicket;
        if (existingETicket != null && !existingETicket.equals(aETicket)) {
            existingETicket.removeEntry(this);
        }
        eTicket.addEntry(this);
        wasSet = true;
        return wasSet;
    }

    public void delete() {
        eTicket placeholderETicket = eTicket;
        this.eTicket = null;
        if (placeholderETicket != null) {
            placeholderETicket.removeEntry(this);
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "deviceID" + ":" + getDeviceID() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "src.impl.eTicket = " + (getETicket() != null ? Integer.toHexString(System.identityHashCode(getETicket())) : "null");
    }
}