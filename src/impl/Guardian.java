package impl;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4753.5a97eca04 modeling language!*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// line 6 "system.ump"
// line 80 "system.ump"
public class Guardian {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //src.impl.Guardian Attributes
    private int ID;
    private String name;
    private int creditCard;


    //src.impl.Guardian Associations
    private Account account;
    private WebUser webUser;
    private List<Kid> kids;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Guardian(int aID, String aName, int aCreditCard, Account aAccount, WebUser aWebUser) {
        ID = aID;
        name = aName;
        creditCard = aCreditCard;
        if (aAccount == null || aAccount.getGuardian() != null) {
            throw new RuntimeException("Unable to create src.impl.Guardian due to aAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        account = aAccount;
        if (aWebUser == null || aWebUser.getGuardian() != null) {
            throw new RuntimeException("Unable to create src.impl.Guardian due to aWebUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        webUser = aWebUser;
        kids = new ArrayList<Kid>();
    }

    public Guardian(int aID, String aName, int aCreditCard, int aCreditCardForAccount, int aBalanceForAccount, CreditCompany aCreditCompanyForAccount, String aUserNameForWebUser, String aPasswordForWebUser) {
        ID = aID;
        name = aName;
        creditCard = aCreditCard;
        account = new Account(aCreditCardForAccount, aBalanceForAccount, aCreditCompanyForAccount, this);
        webUser = new WebUser(aUserNameForWebUser, aPasswordForWebUser, this);
        kids = new ArrayList<Kid>();
    }

    //Alisa
    public Guardian(int aID, String aName, int aCreditCard){
        ID = aID;
        name = aName;
        creditCard = aCreditCard;
        kids = new ArrayList<Kid>();
    }

    public void setAccount(Account newAccount) {
        account=newAccount;
    }

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
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

    public boolean setCreditCard(int aCreditCard) {
        boolean wasSet = false;
        creditCard = aCreditCard;
        wasSet = true;
        return wasSet;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getCreditCard() {
        return creditCard;
    }

    /* Code from template association_GetOne */
    public Account getAccount() {
        return account;
    }

    /* Code from template association_GetOne */
    public WebUser getWebUser() {
        return webUser;
    }

    /* Code from template association_GetMany */
    public Kid getKid(int index) {
        Kid aKid = kids.get(index);
        return aKid;
    }

    public List<Kid> getKids() {
        List<Kid> newKids = Collections.unmodifiableList(kids);
        return newKids;
    }

    public int numberOfKids() {
        int number = kids.size();
        return number;
    }

    public boolean hasKids() {
        boolean has = kids.size() > 0;
        return has;
    }

    public int indexOfKid(Kid aKid) {
        int index = kids.indexOf(aKid);
        return index;
    }

    /* Code from template association_IsNumberOfValidMethod */
    public boolean isNumberOfKidsValid() {
        boolean isValid = numberOfKids() >= minimumNumberOfKids();
        return isValid;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfKids() {
        return 1;
    }

    /* Code from template association_AddMandatoryManyToOne */
    public Kid addKid(int aID, String aName, int aHeight, int aWeight, int aAge, boolean aRidingDevice, eTicket aETicket, Device aDevice) {
        Kid aNewKid = new Kid(aID, aName, aHeight, aWeight, aAge, aRidingDevice, aETicket, aDevice, this);
        return aNewKid;
    }

    public boolean addKid(Kid aKid) {
        boolean wasAdded = false;
        if (kids.contains(aKid)) {
            return false;
        }
        Guardian existingGuardian = aKid.getGuardian();
        boolean isNewGuardian = existingGuardian != null && !this.equals(existingGuardian);

        if (isNewGuardian && existingGuardian.numberOfKids() <= minimumNumberOfKids()) {
            return wasAdded;
        }
        if (isNewGuardian) {
            aKid.setGuardian(this);
        } else {
            kids.add(aKid);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeKid(Kid aKid) {
        boolean wasRemoved = false;
        //Unable to remove aKid, as it must always have a guardian
        if (! this.equals(aKid.getGuardian())) {
            return wasRemoved;
        }

        //guardian already at minimum (1)
        if (numberOfKids() <= minimumNumberOfKids()) {
            return wasRemoved;
        }

        kids.remove(aKid);
        wasRemoved = true;
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addKidAt(Kid aKid, int index) {
        boolean wasAdded = false;
        if (addKid(aKid)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfKids()) {
                index = numberOfKids() - 1;
            }
            kids.remove(aKid);
            kids.add(index, aKid);
            wasAdded = true;
        }
        return wasAdded;
    }

    public void delete() {
        Account existingAccount = account;
        account = null;
        if (existingAccount != null) {
            existingAccount.delete();
        }
        WebUser existingWebUser = webUser;
        webUser = null;
        if (existingWebUser != null) {
            existingWebUser.delete();
        }
        for (int i = kids.size(); i > 0; i--) {
            Kid aKid = kids.get(i - 1);
            aKid.delete();
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "ID" + ":" + getID() + "," +
                "name" + ":" + getName() + "," +
                "creditCard" + ":" + getCreditCard() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "account = " + (getAccount() != null ? Integer.toHexString(System.identityHashCode(getAccount())) : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "webUser = " + (getWebUser() != null ? Integer.toHexString(System.identityHashCode(getWebUser())) : "null");
    }

}