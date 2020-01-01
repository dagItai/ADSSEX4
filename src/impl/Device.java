package impl;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4753.5a97eca04 modeling language!*/


import java.util.*;

// line 52 "model.ump"
// line 103 "model.ump"
public class Device
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Device Attributes
    private int ID;
    private String name;
    private boolean isBroken;
    private boolean isOpen;
    private boolean isInUse;
    private boolean isExtreme;
    private int minHeight;
    private int minWeight;
    private int minAge;

    //Device Associations
    private List<Kid> kids;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Device(int aID, String aName, boolean aIsBroken, boolean aIsOpen, boolean aIsInUse, boolean aIsExtreme, int aMinHeight, int aMinWeight, int aMinAge)
    {
        ID = aID;
        name = aName;
        isBroken = aIsBroken;
        isOpen = aIsOpen;
        isInUse = aIsInUse;
        isExtreme = aIsExtreme;
        minHeight = aMinHeight;
        minWeight = aMinWeight;
        minAge = aMinAge;
        kids = new ArrayList<Kid>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setID(int aID)
    {
        boolean wasSet = false;
        ID = aID;
        wasSet = true;
        return wasSet;
    }

    public boolean setName(String aName)
    {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boolean setIsBroken(boolean aIsBroken)
    {
        boolean wasSet = false;
        isBroken = aIsBroken;
        wasSet = true;
        return wasSet;
    }

    public boolean setIsOpen(boolean aIsOpen)
    {
        boolean wasSet = false;
        isOpen = aIsOpen;
        wasSet = true;
        return wasSet;
    }

    public boolean setIsInUse(boolean aIsInUse)
    {
        boolean wasSet = false;
        isInUse = aIsInUse;
        wasSet = true;
        return wasSet;
    }

    public boolean setIsExtreme(boolean aIsExtreme)
    {
        boolean wasSet = false;
        isExtreme = aIsExtreme;
        wasSet = true;
        return wasSet;
    }

    public boolean setMinHeight(int aMinHeight)
    {
        boolean wasSet = false;
        minHeight = aMinHeight;
        wasSet = true;
        return wasSet;
    }

    public boolean setMinWeight(int aMinWeight)
    {
        boolean wasSet = false;
        minWeight = aMinWeight;
        wasSet = true;
        return wasSet;
    }

    public boolean setMinAge(int aMinAge)
    {
        boolean wasSet = false;
        minAge = aMinAge;
        wasSet = true;
        return wasSet;
    }

    public int getID()
    {
        return ID;
    }

    public String getName()
    {
        return name;
    }

    public boolean getIsBroken()
    {
        return isBroken;
    }

    public boolean getIsOpen()
    {
        return isOpen;
    }

    public boolean getIsInUse()
    {
        return isInUse;
    }

    public boolean getIsExtreme()
    {
        return isExtreme;
    }

    public int getMinHeight()
    {
        return minHeight;
    }

    public int getMinWeight()
    {
        return minWeight;
    }

    public int getMinAge()
    {
        return minAge;
    }
    /* Code from template association_GetMany */
    public Kid getKid(int index)
    {
        Kid aKid = kids.get(index);
        return aKid;
    }

    public List<Kid> getKids()
    {
        List<Kid> newKids = Collections.unmodifiableList(kids);
        return newKids;
    }

    public int numberOfKids()
    {
        int number = kids.size();
        return number;
    }

    public boolean hasKids()
    {
        boolean has = kids.size() > 0;
        return has;
    }

    public int indexOfKid(Kid aKid)
    {
        int index = kids.indexOf(aKid);
        return index;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfKids()
    {
        return 0;
    }
    /* Code from template association_AddManyToOne */
    public Kid addKid(int aID, String aName, int aHeight, int aWeight, int aAge, boolean aRidingDevice, eTicket aETicket, Guardian aGuardian)
    {
        return new Kid(aID, aName, aHeight, aWeight, aAge, aRidingDevice, aETicket, this, aGuardian);
    }

    public boolean addKid(Kid aKid)
    {
        boolean wasAdded = false;
        if (kids.contains(aKid)) { return false; }
        Device existingDevice = aKid.getDevice();
        boolean isNewDevice = existingDevice != null && !this.equals(existingDevice);
        if (isNewDevice)
        {
            aKid.setDevice(this);
        }
        else
        {
            kids.add(aKid);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeKid(Kid aKid)
    {
        boolean wasRemoved = false;
        //Unable to remove aKid, as it must always have a device
        if (!this.equals(aKid.getDevice()))
        {
            kids.remove(aKid);
            wasRemoved = true;
        }
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addKidAt(Kid aKid, int index)
    {
        boolean wasAdded = false;
        if(addKid(aKid))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfKids()) { index = numberOfKids() - 1; }
            kids.remove(aKid);
            kids.add(index, aKid);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveKidAt(Kid aKid, int index)
    {
        boolean wasAdded = false;
        if(kids.contains(aKid))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfKids()) { index = numberOfKids() - 1; }
            kids.remove(aKid);
            kids.add(index, aKid);
            wasAdded = true;
        }
        else
        {
            wasAdded = addKidAt(aKid, index);
        }
        return wasAdded;
    }

    public void delete()
    {
        for(int i=kids.size(); i > 0; i--)
        {
            Kid aKid = kids.get(i - 1);
            aKid.delete();
        }
    }


    public String toString()
    {
        return super.toString() + "["+
                "ID" + ":" + getID()+ "," +
                "name" + ":" + getName()+ "," +
                "isBroken" + ":" + getIsBroken()+ "," +
                "isOpen" + ":" + getIsOpen()+ "," +
                "isInUse" + ":" + getIsInUse()+ "," +
                "isExtreme" + ":" + getIsExtreme()+ "," +
                "minHeight" + ":" + getMinHeight()+ "," +
                "minWeight" + ":" + getMinWeight()+ "," +
                "minAge" + ":" + getMinAge()+ "]";
    }
}


