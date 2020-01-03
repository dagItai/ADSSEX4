package impl;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4753.5a97eca04 modeling language!*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// line 21 "system.ump"
// line 90 "system.ump"
public class CreditCompany {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //src.impl.CreditCompany Attributes
    private String name;

    //src.impl.CreditCompany Associations
    private List<Account> accounts;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public CreditCompany(String aName) {
        name = aName;
        accounts = new ArrayList<Account>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setName(String aName) {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public String getName() {
        return name;
    }

    /* Code from template association_GetMany */
    public Account getAccount(int index) {
        Account aAccount = accounts.get(index);
        return aAccount;
    }

    public List<Account> getAccounts() {
        List<Account> newAccounts = Collections.unmodifiableList(accounts);
        return newAccounts;
    }

    public int numberOfAccounts() {
        int number = accounts.size();
        return number;
    }

    public boolean hasAccounts() {
        boolean has = accounts.size() > 0;
        return has;
    }

    public int indexOfAccount(Account aAccount) {
        int index = accounts.indexOf(aAccount);
        return index;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfAccounts() {
        return 0;
    }

    /* Code from template association_AddManyToOne */
    public Account addAccount(int aCreditCard, int aBalance, Guardian aGuardian) {
        return new Account(aCreditCard, aBalance, this, aGuardian);
    }

    public boolean addAccount(Account aAccount) {
        boolean wasAdded = false;
        if (accounts.contains(aAccount)) {
            return false;
        }
        CreditCompany existingCreditCompany = aAccount.getCreditCompany();
        boolean isNewCreditCompany = existingCreditCompany != null && !this.equals(existingCreditCompany);
        if (isNewCreditCompany) {
            aAccount.setCreditCompany(this);
        } else {
            accounts.add(aAccount);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeAccount(Account aAccount) {
        boolean wasRemoved = false;
        //Unable to remove aAccount, as it must always have a creditCompany
        if (!this.equals(aAccount.getCreditCompany())) {
            accounts.remove(aAccount);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addAccountAt(Account aAccount, int index) {
        boolean wasAdded = false;
        if (addAccount(aAccount)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfAccounts()) {
                index = numberOfAccounts() - 1;
            }
            accounts.remove(aAccount);
            accounts.add(index, aAccount);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveAccountAt(Account aAccount, int index) {
        boolean wasAdded = false;
        if (accounts.contains(aAccount)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfAccounts()) {
                index = numberOfAccounts() - 1;
            }
            accounts.remove(aAccount);
            accounts.add(index, aAccount);
            wasAdded = true;
        } else {
            wasAdded = addAccountAt(aAccount, index);
        }
        return wasAdded;
    }

    public void delete() {
        for (int i = accounts.size(); i > 0; i--) {
            Account aAccount = accounts.get(i - 1);
            aAccount.delete();
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "name" + ":" + getName() + "]";
    }



}