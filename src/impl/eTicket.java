package impl;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4753.5a97eca04 modeling language!*/



import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

// line 38 "system.ump"
// line 106 "system.ump"
public class eTicket {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //src.impl.eTicket Attributes
    private int id;
    private Date expireDate;

    //src.impl.eTicket Associations
    private List<Entry> entries;



    private Kid kid;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public eTicket(int aId, Date aExpireDate, Kid aKid) {
        id = aId;
        expireDate = aExpireDate;
        entries = new ArrayList<Entry>();
        if (aKid == null || aKid.getETicket() != null) {
            throw new RuntimeException("Unable to create src.impl.eTicket due to aKid. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        kid = aKid;
    }

    public eTicket(int aId, Date aExpireDate, int aIDForKid, String aNameForKid, int aHeightForKid, int aWeightForKid, int aAgeForKid, boolean aRidingDeviceForKid, Device aDeviceForKid, Guardian aGuardianForKid) {
        id = aId;
        expireDate = aExpireDate;
        entries = new ArrayList<Entry>();
        kid = new Kid(aIDForKid, aNameForKid, aHeightForKid, aWeightForKid, aAgeForKid, aRidingDeviceForKid, this, aDeviceForKid, aGuardianForKid);
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setId(int aId) {
        boolean wasSet = false;
        id = aId;
        wasSet = true;
        return wasSet;
    }
    public void setKid(Kid kid) {
        this.kid = kid;
    }

    public boolean setExpireDate(Date aExpireDate) {
        boolean wasSet = false;
        expireDate = aExpireDate;
        wasSet = true;
        return wasSet;
    }

    public int getId() {
        return id;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    /* Code from template association_GetMany */
    public Entry getEntry(int index) {
        Entry aEntry = entries.get(index);
        return aEntry;
    }

    public List<Entry> getEntries() {
        List<Entry> newEntries = Collections.unmodifiableList(entries);
        return newEntries;
    }

    public int numberOfEntries() {
        int number = entries.size();
        return number;
    }

    public boolean hasEntries() {
        boolean has = entries.size() > 0;
        return has;
    }

    public int indexOfEntry(Entry aEntry) {
        int index = entries.indexOf(aEntry);
        return index;
    }

    /* Code from template association_GetOne */
    public Kid getKid() {
        return kid;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfEntries() {
        return 0;
    }

    /* Code from template association_AddManyToOne */
    public Entry addEntry(int aDeviceID) {
        return new Entry(aDeviceID, this);
    }

    public boolean addEntry(Entry aEntry) {
        boolean wasAdded = false;
        if (entries.contains(aEntry)) {
            return false;
        }
        eTicket existingETicket = aEntry.getETicket();
        boolean isNewETicket = existingETicket != null && !this.equals(existingETicket);
        if (isNewETicket) {
            aEntry.setETicket(this);
        } else {
            entries.add(aEntry);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeEntry(Entry aEntry) {
        boolean wasRemoved = false;
        //Unable to remove aEntry, as it must always have a src.impl.eTicket
        if (this.equals(aEntry.getETicket())) {
            entries.remove(aEntry);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public Entry getEntryByID(Kid kid, Integer entryID){
        for (Entry entry : kid.getETicket().getEntries()) {
            if (entry.getDeviceID() == entryID)
                return entry;
        }
        return null;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addEntryAt(Entry aEntry, int index) {
        boolean wasAdded = false;
        if (addEntry(aEntry)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfEntries()) {
                index = numberOfEntries() - 1;
            }
            entries.remove(aEntry);
            entries.add(index, aEntry);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveEntryAt(Entry aEntry, int index) {
        boolean wasAdded = false;
        if (entries.contains(aEntry)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfEntries()) {
                index = numberOfEntries() - 1;
            }
            entries.remove(aEntry);
            entries.add(index, aEntry);
            wasAdded = true;
        } else {
            wasAdded = addEntryAt(aEntry, index);
        }
        return wasAdded;
    }

    public void delete() {
        while (entries.size() > 0) {
            Entry aEntry = entries.get(entries.size() - 1);
            aEntry.delete();
            entries.remove(aEntry);
        }

        Kid existingKid = kid;
        kid = null;
        if (existingKid != null) {
            existingKid.delete();
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "expireDate" + "=" + (getExpireDate() != null ? !getExpireDate().equals(this) ? getExpireDate().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "kid = " + (getKid() != null ? Integer.toHexString(System.identityHashCode(getKid())) : "null");
    }
}