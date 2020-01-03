package impl;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4753.5a97eca04 modeling language!*/


// line 47 "system.ump"
// line 111 "system.ump"
public class eBand {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    private static int bandID = 0;
    //src.impl.eBand Attributes
    private int ID;

    //src.impl.eBand Associations
    private Kid kid;

    //------------------------
    // CONSTRUCTOR
    //------------------------



    public eBand() {
        ID = bandID;
        bandID++;
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

    public int getID() {
        return ID;
    }

    /* Code from template association_GetOne */
    public Kid getKid() {
        return kid;
    }

    /* Code from template association_SetOneToOptionalOne */
    public boolean setKid(Kid aNewKid) {
        boolean wasSet = false;
        if (aNewKid == null) {
            //Unable to setKid to null, as src.impl.eBand must always be associated to a kid
            return wasSet;
        }

        eBand existingEBand = aNewKid.getEBand();
        if (existingEBand != null && !equals(existingEBand)) {
            //Unable to setKid, the current kid already has a src.impl.eBand, which would be orphaned if it were re-assigned
            return wasSet;
        }

        Kid anOldKid = kid;
        kid = aNewKid;
        kid.setEBand(this);

        if (anOldKid != null) {
            anOldKid.setEBand(null);
        }
        wasSet = true;
        return wasSet;
    }

    public void delete() {
        Kid existingKid = kid;
        kid = null;
        if (existingKid != null) {
            existingKid.setEBand(null);
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "ID" + ":" + getID() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "kid = " + (getKid() != null ? Integer.toHexString(System.identityHashCode(getKid())) : "null");
    }
}