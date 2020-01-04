package ePark;

import impl.*;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Object> systemObjects = new ArrayList<>();

    private List<Device> parkDevices = new ArrayList<>();
    private List<Kid> kids = new ArrayList<>();
    private List<WebUser> webUsers = new ArrayList<>();
    private List<eBand> eBands = new ArrayList<>();
    private List<CreditCompany> parkCompanies = new ArrayList<>();
    private CreditCardController ccController = new CreditCardController();
    private EquipmentController equipmentController = new EquipmentController();
    private static int kID=1;
    private ParkController parkController;

    private eBandController eBandController = new eBandController();
    public static void main(String[] args) {
        System.out.println("Welcome to our ePark system");
        Main ePark = new Main();
        ePark.mainMenu();
    }

    private Kid addKid() {
        return null;
    }

    private int chooseKidMenu(WebUser webUser) {
        System.out.println("Here are all the kids that are associated with you");
        webUser.getGuardian().getKids().stream().forEach(e ->
                System.out.println("Name: " + e.getName() + " ,Id: " + e.getID()));
        System.out.println("Please choose the ID you would like to manage");
        Scanner keyboard = new Scanner(System.in);
        boolean selected = false;
        while (!selected) {
            try {
                int choice = keyboard.nextInt();
                if (webUser.getGuardian().getKids().stream().anyMatch(e -> e.getID()==choice)){
                    return choice;
                }else {
                    continue;
                }
            } catch (Exception e) {
                System.out.println("please enter valid ID from the shown list");
                keyboard.nextLine();
            }
        }
        return 0;
    }

    private void removeKid(int kidID, WebUser webUser) {

    }

    //----------------------------------------------------------------Alisa---------------------------------------------------------------------------------------------------
    private WebUser loginMenu(){
        Scanner keyBoard = new Scanner(System.in);
        String userName;
        String password;
        System.out.println("Please Enter Your User Name");
        userName=keyBoard.nextLine();
        System.out.println("Please Enter Your Password");
        password = keyBoard.nextLine();
        WebUser webUser =checkValidPasswordAndUser(userName,password);
        if(webUser!=null){
            return webUser;
        }
        else{
            System.out.println("User Credentials Are Not Valid, Please Try To Login In Again");
            return null;
        }
    }

    private WebUser signUpMenu() {
        //UC-1
        Scanner keyBoard = new Scanner(System.in);
        String kidName="";
        String kidAge="";
        String creditNumber="";
        String limitCredit="";
        String userName="";
        String password="";
        int gID=0;
        String gName="";

        //Try Get Web User Details
        System.out.println("Please Enter User Name The You Will Login In To The App Next Time");
        userName=keyBoard.next();
        Random rand = new Random();
        password = String.format("%04d", rand.nextInt(10000));
        System.out.println("Your Password For This User Is "+ password+"\n Please Don't Lose It");

        //Try Get Guardian Details
        System.out.println("Now Please Enter Your ID");
        boolean validId=false;
        while(!validId){
            try{
                gID = keyBoard.nextInt();
                validId=true;
            }catch (Exception e){
                System.out.println("It Must Be A Number, Please Try Again");
                keyBoard.nextLine();
            }
        }
        System.out.println("Please Enter Your Name");
        gName = keyBoard.next();

        //Try Get Kid Details
        boolean validKid = false;
        while (!validKid){
            System.out.println("Please Enter Your Kid's Name");
            kidName = keyBoard.next();
            System.out.println("Please Enter Your Kid Age");
            kidAge = keyBoard.next();
            if(!checkValidKidDetails(kidName,kidAge)) {
                System.out.println("Something went wrong .. Below Are The Details Entered\n Kid Name: " + kidName + "\n Kid Age: " + kidAge);
            }
            else{
                validKid=true;
            }
        }

        //Try Get Credit Card Details
        System.out.println(" Great, Now Please Enter A Credit Card Number\n Please note that we accept the following tickets:\n" +
                "Visa, which starts with 1\n" +
                "Mastercard, which starts with 2\n" +
                "American Express, which starts with 3");
        creditNumber = keyBoard.next();
        System.out.println("And Now Please Enter Your Budget Limit For The Park, Please Note That The  Your Budget Limit Should Be At Least $10");
        limitCredit = keyBoard.next();
        if(!checkValidCredit(creditNumber,limitCredit)) {
            System.out.println("Something went wrong .. We Sorry But You Will Have To Go All This Process Again");
            return null;
        }


        //Great! Create An Account, WebUser, Guardian
        System.out.println("Great! We Open For You A User, Please Wait A Minute");
        Guardian newGuardian = new Guardian(gID,gName,Integer.valueOf(creditNumber));
        WebUser newWebUser = new WebUser(userName,password,newGuardian);
        Account newAccount = new Account(Integer.valueOf(creditNumber),Integer.valueOf(limitCredit),creditCompanyFromCreditNumber(creditNumber),newGuardian);
        newGuardian.setWebUser(newWebUser);
        newGuardian.setAccount(newAccount);
        //Create kid, eTicket
        Kid newKid = new Kid(kID,kidName,Integer.valueOf(kidAge),newGuardian);
        eTicket newKideTicket = new eTicket(kID, new Date(),newKid);
        kID++;
        //Create eBand
        eBand newKideband = equipmentController.createNewEBand();
        //setAll
        newKideband.setKid(newKid);
        newKid.seteBand(newKideband);
        newKid.seteTicket(newKideTicket);
        newKideTicket.setKid(newKid);

        //Last Step - Measuring
        System.out.println("One Last Step - Please Put Your Child On The Weight&Height Measuring At The Park Entrance");
        List<Integer> measures = equipmentController.getMeasurementsFromMeasureDevice();
        newKid.setHeight(measures.get(0));
        newKid.setWeight(measures.get(1));

        newGuardian.addKid(newKid);

        //Add All To systemObjects
        systemObjects.add(newWebUser);
        systemObjects.add(newAccount);
        systemObjects.add(newGuardian);
        systemObjects.add(newKid);
        systemObjects.add(newKideband);
        systemObjects.add(newKideTicket);
        //Add To WebUsers
        webUsers.add(newWebUser);

        return newWebUser;

    }

    private WebUser checkValidPasswordAndUser(String userName, String password) {
        for (WebUser webUser : webUsers) {
            if(webUser.getUserName().equals(userName)){
                if(webUser.getPassword().equals(password)){
                    return webUser;
                }
                else{
                    return null;
                }
            }
        }
        return null;

    }

    private boolean checkValidKidDetails(String kidName, String kidAge) {
        try {
            if(Integer.valueOf(kidAge)<1 || kidName.length()<1){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private CreditCompany creditCompanyFromCreditNumber(String cNumber){
        int firstNumber = Integer.valueOf(cNumber.charAt(0));
        if(firstNumber==1){
            return parkCompanies.get(0);
        }
        else if(firstNumber==2){
            return parkCompanies.get(1);
        }
        else {
            return parkCompanies.get(2);
        }
    }

    private boolean checkValidCredit(String creditNumber, String limitCredit) {
        try {
            int creditNum = Integer.valueOf(creditNumber);
            int firstNumber = Integer.parseInt(creditNumber.charAt(0)+"");
            int creditLimit = Integer.valueOf(limitCredit);
            if(firstNumber<1 || firstNumber>3 || creditLimit<10){
                return false;
            }
            return ccController.validateCard(creditNum,creditLimit);
        } catch (Exception e){
            return false;
        }
    }
    //----------------------------------------------------------------Itai---------------------------------------------------------------------------------------------------
    private void removeEntries(Kid kidID, WebUser webUser, eTicket eTick) {
        List<Integer> devicesToDelete = chooseDevicesMenu(eTick);
        int removedEntries = 0;
        for (Integer integer : devicesToDelete) {
            Entry e = eTick.getEntryByID(kidID,integer);
            if (e != null) {
                kidID.getETicket().removeEntry(e);
                systemObjects.remove(e);
                removedEntries++;
            }
        }
        webUser.getGuardian().getAccount().addToBalance(removedEntries * 10);
    }

    private List<Integer> chooseDevicesMenu(eTicket eTick) {
        List<Integer> devicesToRemove = new ArrayList<>();
        System.out.println("Please choose the entries you would like to delete, you can choose -1 at any point to exit this menu");
        Scanner keyboard = new Scanner(System.in);
        boolean stillSelecting = true;
        while (stillSelecting) {
            try {
                int deviceId = keyboard.nextInt();
                if (deviceId != -1) {
                    if (eTick.getEntries().stream().anyMatch(e -> e.getDeviceID() == deviceId)) {
                        devicesToRemove.add(deviceId);
                        System.out.println("Added device " + deviceId + " to the list of devices to remove");
                    } else
                        throw new Exception();
                } else {
                    stillSelecting = false;
                    continue;
                }
            } catch (Exception e) {
                System.out.println("please enter valid number and make sure that the kid have this number");
                keyboard.nextLine();
            }
        }
        return devicesToRemove;
    }

    private void showETicket(Kid currentKid, eTicket eTick) {
        System.out.println("eTicket of " + currentKid.getName() + " ID number: " + currentKid.getID());
        System.out.println("eTicket expiration date: " + eTick.getExpireDate());
        List<Entry> entries = eTick.getEntries();
        for (Entry entry : entries) {
            System.out.println("Ticket for: " + entry.getDeviceID());
        }
    }

    private void addEntries(Kid kidID, WebUser webUser, eTicket eTick) {
        List<Device> devicesToAdd = chooseDevicesToAddMenu(kidID, eTick);
        List<Entry> entriesAdded = new ArrayList<>();
        int addedEntries = 0;
        for (Device device : devicesToAdd) {
            if (device.getIsExtreme()) {
                addedEntries = handleExtremeDevice(eTick, addedEntries, device);
            } else {
                Entry e = new Entry(device.getID(), eTick);
                entriesAdded.add(e);
                systemObjects.add(e);
                addedEntries++;
            }
        }
        if (!webUser.getGuardian().getAccount().removeFromBalance(addedEntries * 10)) {
            entriesAdded.stream().forEach(e -> {
                eTick.removeEntry(e);
                systemObjects.remove(e);
            });
            System.out.println("Operation failed, you don't have enough money for all of this devices!");
        }
    }

    private int handleExtremeDevice(eTicket eTick, int addedEntries, Device device) {
        System.out.println(device.getName() + " id:" + device.getID() + "is EXTREME! please approve that you would like to let your kid get on this device");
        System.out.println("press 'y' for yes or 'n' for no");
        Scanner keyboard = new Scanner(System.in);
        boolean selected = false;
        while (!selected) {
            try {
                String choice = keyboard.next();
                if (choice.equals("y")) {
                    Entry e = new Entry(device.getID(), eTick);
                    systemObjects.add(e);
                    addedEntries++;
                    selected = true;
                } else if (choice.equals("n")) {
                    selected = true;
                } else {
                    continue;
                }
            } catch (Exception e) {
                System.out.println("please enter valid number");
                keyboard.nextLine();
            }
        }
        return addedEntries;
    }

    private List<Device> chooseDevicesToAddMenu(Kid currentKid, eTicket eTick) {
        showRelevantDevices(currentKid);
        List<Device> devicesToAdd = new ArrayList<>();
        System.out.println("Please choose the entries you would like to add, you can choose -1 at any point to exit this menu");
        Scanner keyboard = new Scanner(System.in);
        boolean stillSelecting = true;
        while (stillSelecting) {
            try {
                int deviceId = keyboard.nextInt();
                if (deviceId != -1) {
                    List<Device> collect = parkDevices.stream().filter(e -> e.getID() == deviceId && e.validDeviceForKid(currentKid.getAge(), currentKid.getHeight(), currentKid.getWeight())).collect(Collectors.toList());
                    if (collect.size() > 0) {
                        devicesToAdd.addAll(collect);
                        System.out.println("Added device " + deviceId + " to the list of devices to add");
                    } else
                        throw new Exception();
                } else {
                    stillSelecting = false;
                    continue;
                }
            } catch (Exception e) {
                System.out.println("please enter valid number");
                keyboard.nextLine();
            }
        }
        return devicesToAdd;
    }

    private void showRelevantDevices(Kid currentKid) {
        boolean existRelevant = false;
        for (Device parkDevice : parkDevices) {
            if (parkDevice.validDeviceForKid(currentKid.getAge(), currentKid.getHeight(), currentKid.getWeight())) {
                if (!existRelevant) {
                    existRelevant = true;
                    System.out.println("We have fixed price for all the devices - 10 new shekel for each device");
                }
                System.out.println(parkDevice.getName() + " - deviceID: " + parkDevice.getID());
            }
        }
        if (!existRelevant) {
            System.out.println("Sorry there are no relevant devices for your kid :(");
        }
    }

    private void showMyKids(WebUser webUser) {
        System.out.println("Here are all the kids that are associated with you");
        webUser.getGuardian().getKids().stream().forEach(e ->
                System.out.println("Name: " + e.getName() + " ,Id: " + e.getID() + " ,Location: " + eBandController.getCoordinatesOfBand(e.getEBand())));
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Main() {
        addStartDevices();
        addCreditCardCompanies();

    }

    private void addCreditCardCompanies() {
        CreditCompany visa = new CreditCompany("Visa");
        CreditCompany masterCard = new CreditCompany("Master Card");
        CreditCompany amex = new CreditCompany("American Express");
        parkCompanies.add(visa);
        Main.systemObjects.add(visa);
        parkCompanies.add(masterCard);
        Main.systemObjects.add(masterCard);
        parkCompanies.add(amex);
        Main.systemObjects.add(amex);
    }

    private int printFirstStepMenu() {
        int option = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Main Menu:");
        System.out.println("--------------");
        System.out.println("1.Login");
        System.out.println("2.Sign Up");
        System.out.println("3.Exit");
        System.out.println("--------------");
        System.out.println("Please enter your choice:");
        try {
            option = keyboard.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid choice:");
            return -1;
        }
        return option;
    }

    private int printSecondStepMenu() {
        int option = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Main Menu:");
        System.out.println("--------------");
        System.out.println("1.Add kid");
        System.out.println("2.Show my kids");
        System.out.println("3.Manage specific kid");
        System.out.println("--------------");
        System.out.println("Please enter your choice:");
        try {
            option = keyboard.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid choice!");
            return -1;
        }
        return option;
    }

    private int printThirdStepMenu() {
        int option = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Main Menu:");
        System.out.println("--------------");
        System.out.println("1.Add Entries");
        System.out.println("2.Remove Entries");
        System.out.println("3.Remove kid from the park");
        System.out.println("4.Show eTicket again");
        System.out.println("5.Take me back to previous menu");
        System.out.println("--------------");
        System.out.println("Please enter your choice:");
        try {
            option = keyboard.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid choice");
            return -1;
        }
        return option;
    }

    private void addStartDevices() {
        Device mamba = new Device(1, "Mamba Ride", false, false, false, true, 140, 0, 12);
        Device wheel = new Device(2, "Giant Wheel", false, false, false, false, 0, 0, 0);
        Device carrousel = new Device(3, "Carrousel", false, false, false, false, 0, 0, 8);
        parkDevices.add(mamba);
        Main.systemObjects.add(mamba);
        parkDevices.add(wheel);
        Main.systemObjects.add(wheel);
        parkDevices.add(carrousel);
        Main.systemObjects.add(carrousel);
    }

    private void manageKid(int kidID, WebUser webUser) {
        Kid currentKid = null;
        for (Kid kid : webUser.getGuardian().getKids()) {
            if (kid.getID() == kidID) {
                currentKid = kid;
            }
        }
        eTicket eTick = currentKid.getETicket();
        showETicket(currentKid, eTick);
        boolean exit = false;
        while (!exit) {
            int choice = printThirdStepMenu();
            switch (choice) {
                case 1:
                    addEntries(currentKid, webUser, eTick);
                    continue;
                case 2:
                    removeEntries(currentKid, webUser, eTick);
                    continue;
                case 3:
                    removeKid(kidID, webUser);
                    continue;
                case 4:
                    showETicket(currentKid,eTick);
                    continue;
                case 5:
                    System.out.println("Back to previous menu :) ");
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    private void loggedInUser(WebUser webUser) {
        boolean exit = false;
        while (!exit) {
            int choice = printSecondStepMenu();
            switch (choice) {
                case 1:
                    addKid();
                    continue;
                case 2:
                    showMyKids(webUser);
                    continue;
                case 3:
                    int kidID = chooseKidMenu(webUser);
                    manageKid(kidID, webUser);
                    continue;
                case 4:
                    System.out.println("Goodbye, see you again soon :) ");
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    public void mainMenu(){
        boolean exit = false;
        WebUser wb;
        while (!exit) {
            int choice = printFirstStepMenu();
            switch (choice) {
                case 1:
                    wb = loginMenu();
                    if (wb != null)
                        loggedInUser(wb);
                    continue;
                case 2:
                    wb = signUpMenu();
                    if (wb != null)
                        loggedInUser(wb);
                    continue;
                case 3:
                    System.out.println("Goodbye, see you again soon :) ");
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }
}
