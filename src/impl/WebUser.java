package impl;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4753.5a97eca04 modeling language!*/


/**
 * Positioning
 *
 * @@@testlanguage=java,cpp,php
 */
// line 1 "system.ump"
// line 70 "system.ump"
public class WebUser {

    //------------------------
    // MEMBER VARIABLES
    //------------------------
    //src.impl.WebUser Attributes
    private String userName;
    private String password;

    //src.impl.WebUser Associations
    private Guardian guardian;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public WebUser(String aUserName, String aPassword, Guardian aGuardian) {
        userName = aUserName;
        password = aPassword;
        if (aGuardian == null || aGuardian.getWebUser() != null) {
            throw new RuntimeException("Unable to create src.impl.WebUser due to aGuardian. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        guardian = aGuardian;
    }

    public WebUser(String aUserName, String aPassword, int aIDForGuardian, String aNameForGuardian, int aCreditCardForGuardian, Account aAccountForGuardian) {
        userName = aUserName;
        password = aPassword;
        guardian = new Guardian(aIDForGuardian, aNameForGuardian, aCreditCardForGuardian, aAccountForGuardian, this);
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setUserName(String aUserName) {
        boolean wasSet = false;
        userName = aUserName;
        wasSet = true;
        return wasSet;
    }

    public boolean setPassword(String aPassword) {
        boolean wasSet = false;
        password = aPassword;
        wasSet = true;
        return wasSet;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    /* Code from template association_GetOne */
    public Guardian getGuardian() {
        return guardian;
    }

    public void delete() {
        Guardian existingGuardian = guardian;
        guardian = null;
        if (existingGuardian != null) {
            existingGuardian.delete();
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "userName" + ":" + getUserName() + "," +
                "password" + ":" + getPassword() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "guardian = " + (getGuardian() != null ? Integer.toHexString(System.identityHashCode(getGuardian())) : "null");
    }
}