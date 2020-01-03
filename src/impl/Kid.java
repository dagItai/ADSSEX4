package impl;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4753.5a97eca04 modeling language!*/


import java.sql.Date;

// line 26 "system.ump"
// line 95 "system.ump"
public class Kid {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //src.impl.Kid Attributes
    private int ID;
    private String name;
    private int height;
    private int weight;
    private int age;
    private boolean ridingDevice;


    //src.impl.Kid Associations
    private eTicket eTicket;
    private eBand eBand;
    private Device device;
    private Guardian guardian;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Kid(int aID, String aName, int aHeight, int aWeight, int aAge, boolean aRidingDevice, eTicket aETicket, Device aDevice, Guardian aGuardian) {
        ID = aID;
        name = aName;
        height = aHeight;
        weight = aWeight;
        age = aAge;
        ridingDevice = aRidingDevice;
        if (aETicket == null || aETicket.getKid() != null) {
            throw new RuntimeException("Unable to create src.impl.Kid due to aETicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        eTicket = aETicket;
        boolean didAddDevice = setDevice(aDevice);
        if (!didAddDevice) {
            throw new RuntimeException("Unable to create kid due to device. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        boolean didAddGuardian = setGuardian(aGuardian);
        if (!didAddGuardian) {
            throw new RuntimeException("Unable to create kid due to guardian. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    public Kid(int aID, String aName, int aHeight, int aWeight, int aAge, boolean aRidingDevice, int aIdForETicket, Date aExpireDateForETicket, Device aDevice, Guardian aGuardian) {
        ID = aID;
        name = aName;
        height = aHeight;
        weight = aWeight;
        age = aAge;
        ridingDevice = aRidingDevice;
        eTicket = new eTicket(aIdForETicket, aExpireDateForETicket, this);
        boolean didAddDevice = setDevice(aDevice);
        if (!didAddDevice) {
            throw new RuntimeException("Unable to create kid due to device. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        boolean didAddGuardian = setGuardian(aGuardian);
        if (!didAddGuardian) {
            throw new RuntimeException("Unable to create kid due to guardian. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    //Alisa
    public Kid(int aID, String aName,int aAge, Guardian aGuardian) {
        ID = aID;
        name = aName;
        age = aAge;
        boolean didAddGuardian = setGuardian(aGuardian);
        if (!didAddGuardian) {
            throw new RuntimeException("Unable to create kid due to guardian. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }


    //------------------------
    // INTERFACE
    //------------------------

    public boolean setID(int aID) {
        boolean wasSet = false;
        ID = aID;
        wasSet = true;
        return wasSet;
    }

    public boolean setName(String aName) {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boolean setHeight(int aHeight) {
        boolean wasSet = false;
        height = aHeight;
        wasSet = true;
        return wasSet;
    }

    public boolean setWeight(int aWeight) {
        boolean wasSet = false;
        weight = aWeight;
        wasSet = true;
        return wasSet;
    }

    public boolean setAge(int aAge) {
        boolean wasSet = false;
        age = aAge;
        wasSet = true;
        return wasSet;
    }

    public boolean setRidingDevice(boolean aRidingDevice) {
        boolean wasSet = false;
        ridingDevice = aRidingDevice;
        wasSet = true;
        return wasSet;
    }


    public void seteTicket(impl.eTicket eTicket) {
        this.eTicket = eTicket;
    }

    public void seteBand(impl.eBand eBand) {
        this.eBand = eBand;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public boolean getRidingDevice() {
        return ridingDevice;
    }

    /* Code from template association_GetOne */
    public eTicket getETicket() {
        return eTicket;
    }

    /* Code from template association_GetOne */
    public eBand getEBand() {
        return eBand;
    }

    public boolean hasEBand() {
        boolean has = eBand != null;
        return has;
    }

    /* Code from template association_GetOne */
    public Device getDevice() {
        return device;
    }

    /* Code from template association_GetOne */
    public Guardian getGuardian() {
        return guardian;
    }

    /* Code from template association_SetOptionalOneToOne */
    public boolean setEBand(eBand aNewEBand) {
        boolean wasSet = false;
        if (eBand != null && !eBand.equals(aNewEBand) && equals(eBand.getKid())) {
            //Unable to setEBand, as existing src.impl.eBand would become an orphan
            return wasSet;
        }

        eBand = aNewEBand;
        Kid anOldKid = aNewEBand != null ? aNewEBand.getKid() : null;

        if (!this.equals(anOldKid)) {
            if (anOldKid != null) {
                anOldKid.eBand = null;
            }
            if (eBand != null) {
                eBand.setKid(this);
            }
        }
        wasSet = true;
        return wasSet;
    }

    /* Code from template association_SetOneToMany */
    public boolean setDevice(Device aDevice) {
        boolean wasSet = false;
        if (aDevice == null) {
            return wasSet;
        }

        Device existingDevice = device;
        device = aDevice;
        if (existingDevice != null && !existingDevice.equals(aDevice)) {
            existingDevice.removeKid(this);
        }
        device.addKid(this);
        wasSet = true;
        return wasSet;
    }

    /* Code from template association_SetOneToMandatoryMany */
    public boolean setGuardian(Guardian aGuardian) {
        boolean wasSet = false;
        //Must provide guardian to kid
        if (aGuardian == null) {
            return wasSet;
        }

        if (guardian != null && guardian.numberOfKids() <= Guardian.minimumNumberOfKids()) {
            return wasSet;
        }

        Guardian existingGuardian = guardian;
        guardian = aGuardian;
        if (existingGuardian != null && !existingGuardian.equals(aGuardian)) {
            boolean didRemove = existingGuardian.removeKid(this);
            if (!didRemove) {
                guardian = existingGuardian;
                return wasSet;
            }
        }
        guardian.addKid(this);
        wasSet = true;
        return wasSet;
    }

    public void delete() {
        eTicket existingETicket = eTicket;
        eTicket = null;
        if (existingETicket != null) {
            existingETicket.delete();
        }
        eBand existingEBand = eBand;
        eBand = null;
        if (existingEBand != null) {
            existingEBand.delete();
        }
        Device placeholderDevice = device;
        this.device = null;
        if (placeholderDevice != null) {
            placeholderDevice.removeKid(this);
        }
        Guardian placeholderGuardian = guardian;
        this.guardian = null;
        if (placeholderGuardian != null) {
            placeholderGuardian.removeKid(this);
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "ID" + ":" + getID() + "," +
                "name" + ":" + getName() + "," +
                "height" + ":" + getHeight() + "," +
                "weight" + ":" + getWeight() + "," +
                "age" + ":" + getAge() + "," +
                "ridingDevice" + ":" + getRidingDevice() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "src.impl.eTicket = " + (getETicket() != null ? Integer.toHexString(System.identityHashCode(getETicket())) : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "src.impl.eBand = " + (getEBand() != null ? Integer.toHexString(System.identityHashCode(getEBand())) : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "device = " + (getDevice() != null ? Integer.toHexString(System.identityHashCode(getDevice())) : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "guardian = " + (getGuardian() != null ? Integer.toHexString(System.identityHashCode(getGuardian())) : "null");
    }


}