package impl;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4753.5a97eca04 modeling language!*/

// line 15 "system.ump"
// line 85 "system.ump"
public class Account {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //src.impl.Account Attributes
    private int creditCard;
    private int balance;

    //src.impl.Account Associations
    private CreditCompany creditCompany;
    private Guardian guardian;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Account(int aCreditCard, int aBalance, CreditCompany aCreditCompany, Guardian aGuardian) {
        creditCard = aCreditCard;
        balance = aBalance;
        boolean didAddCreditCompany = setCreditCompany(aCreditCompany);
        if (!didAddCreditCompany) {
            throw new RuntimeException("Unable to create account due to creditCompany. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        if (aGuardian == null || aGuardian.getAccount() != null) {
            throw new RuntimeException("Unable to create src.impl.Account due to aGuardian. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        guardian = aGuardian;
    }

    public Account(int aCreditCard, int aBalance, CreditCompany aCreditCompany, int aIDForGuardian, String aNameForGuardian, int aCreditCardForGuardian, WebUser aWebUserForGuardian) {
        creditCard = aCreditCard;
        balance = aBalance;
        boolean didAddCreditCompany = setCreditCompany(aCreditCompany);
        if (!didAddCreditCompany) {
            throw new RuntimeException("Unable to create account due to creditCompany. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        guardian = new Guardian(aIDForGuardian, aNameForGuardian, aCreditCardForGuardian, this, aWebUserForGuardian);
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setCreditCard(int aCreditCard) {
        boolean wasSet = false;
        creditCard = aCreditCard;
        wasSet = true;
        return wasSet;
    }

    public boolean setBalance(int aBalance) {
        boolean wasSet = false;
        balance = aBalance;
        wasSet = true;
        return wasSet;
    }

    public int getCreditCard() {
        return creditCard;
    }

    public int getBalance() {
        return balance;
    }

    /* Code from template association_GetOne */
    public CreditCompany getCreditCompany() {
        return creditCompany;
    }

    /* Code from template association_GetOne */
    public Guardian getGuardian() {
        return guardian;
    }

    /* Code from template association_SetOneToMany */
    public boolean setCreditCompany(CreditCompany aCreditCompany) {
        boolean wasSet = false;
        if (aCreditCompany == null) {
            return wasSet;
        }

        CreditCompany existingCreditCompany = creditCompany;
        creditCompany = aCreditCompany;
        if (existingCreditCompany != null && !existingCreditCompany.equals(aCreditCompany)) {
            existingCreditCompany.removeAccount(this);
        }
        creditCompany.addAccount(this);
        wasSet = true;
        return wasSet;
    }

    public void delete() {
        CreditCompany placeholderCreditCompany = creditCompany;
        this.creditCompany = null;
        if (placeholderCreditCompany != null) {
            placeholderCreditCompany.removeAccount(this);
        }
        Guardian existingGuardian = guardian;
        guardian = null;
        if (existingGuardian != null) {
            existingGuardian.delete();
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "creditCard" + ":" + getCreditCard() + "," +
                "balance" + ":" + getBalance() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "creditCompany = " + (getCreditCompany() != null ? Integer.toHexString(System.identityHashCode(getCreditCompany())) : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "guardian = " + (getGuardian() != null ? Integer.toHexString(System.identityHashCode(getGuardian())) : "null");
    }
}