package ePark;

import impl.Kid;
import impl.WebUser;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WebController {
    public static List<Object> systemObjects = new ArrayList<>();
    private List<WebUser> webUsers = new ArrayList<>();
    private ParkController parkController = new ParkController();

    private WebUser signUpMenu() {
        //UC-1
        Scanner keyBoard = new Scanner(System.in);
        String kidName = "";
        String kidAge = "";
        String creditNumber = "";
        String limitCredit = "";
        String userName = "";
        String password = "";
        int gID = 0;
        String gName = "";

        //Try Get Web User Details
        System.out.println("Please Enter User Name The You Will Login In To The App Next Time");
        userName = keyBoard.next();
        Random rand = new Random();
        password = String.format("%04d", rand.nextInt(10000));
        System.out.println("Your Password For This User Is " + password + "\n Please Don't Lose It");

        //Try Get Guardian Details
        System.out.println("Now Please Enter Your ID");
        boolean validId = false;
        while (!validId) {
            try {
                gID = keyBoard.nextInt();
                validId = true;
            } catch (Exception e) {
                System.out.println("It Must Be A Number, Please Try Again");
                keyBoard.nextLine();
            }
        }
        System.out.println("Please Enter Your Name");
        gName = keyBoard.next();

        //Try Get Kid Details
        boolean validKid = false;
        while (!validKid) {
            System.out.println("Please Enter Your Kid's Name");
            kidName = keyBoard.next();
            System.out.println("Please Enter Your Kid Age");
            kidAge = keyBoard.next();
            if (!checkValidKidDetails(kidName, kidAge)) {
                System.out.println("Something went wrong .. Below Are The Details Entered\n Kid Name: " + kidName + "\n Kid Age: " + kidAge);
            } else {
                validKid = true;
            }
        }

        //Try Get Credit Card Details
        System.out.println(" Great, Now Please Enter A Credit Card Number\nPlease note that we accept the following tickets:\n" +
                "Visa, which starts with 1\n" +
                "Mastercard, which starts with 2\n" +
                "American Express, which starts with 3");
        creditNumber = keyBoard.next();
        System.out.println("And Now Please Enter Your Budget Limit For The Park, Please Note That The  Your Budget Limit Should Be At Least 10 shekel");
        limitCredit = keyBoard.next();
        if (!parkController.checkValidCredit(creditNumber, limitCredit)) {
            System.out.println("Something went wrong .. We Sorry But You Will Have To Go All This Process Again");
            return null;
        }

        //Great! Create An Account, WebUser, Guardian
        System.out.println("Great! We Open For You A User, Please Wait A Minute");

        System.out.println("One Last Step - Please Put Your Child On The Weight&Height Measuring At The Park Entrance");

        List<Integer> measurements = parkController.getMeasurements();
        int kidHeight = measurements.get(0);
        int kidWeight = measurements.get(1);

        System.out.println("Your kid Height is: " + kidHeight);
        System.out.println("Your kid Weight is: " + kidWeight);

        WebUser newWebUser = parkController.getWebUser(kidName, kidAge, kidWeight, kidHeight, creditNumber, limitCredit, userName, password, gID, gName);

        //Add To WebUsers
        webUsers.add(newWebUser);

        return newWebUser;
    }


    //-------------------------------------****************************************************************************************--------------------------------------------------
    public static void main(String[] args) {
        System.out.println("Welcome to our ePark system");
        WebController ePark = new WebController();
        ePark.mainMenu();
    }

    private void loggedInUser(WebUser webUser) {
        boolean exit = false;
        while (!exit) {
            int choice = printSecondStepMenu();
            switch (choice) {
                case 1:
                    addKid(webUser);
                    continue;
                case 2:
                    showMyKids(webUser);
                    continue;
                case 3:
                    int kidID = chooseKidMenu(webUser);
                    if (!manageKid(kidID, webUser)) {
                        return;
                    }
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

    public void mainMenu() {
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

    private int printFirstStepMenu() {
        int option = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("WebController Menu:");
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
        System.out.println("WebController Menu:");
        System.out.println("--------------");
        System.out.println("1.Add kid");
        System.out.println("2.Show my kids");
        System.out.println("3.Manage specific kid");
        System.out.println("4.Sign out");
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
        System.out.println("WebController Menu:");
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

    private void showMyKids(WebUser webUser) {
        System.out.println("Here are all the kids that are associated with you");
        List<Pair<Integer, String>> kids = parkController.getKidsOfGuardian(webUser.getGuardian());
        kids.stream().forEach(e ->
                System.out.println("Name: " + e.getValue() + " ,Id: " + e.getKey() + " ,Location: " + parkController.getCoordinatesOfKid(e.getKey())));
    }

    private void showRelevantDevices(int currentKid) {
        List<Pair<Integer, String>> parkDevicesForKid = parkController.getRelevantDevicesForKid(currentKid);
        if (!parkDevicesForKid.isEmpty()) {
            System.out.println("We have fixed price for all the devices - 10 new shekel for each device");
            parkDevicesForKid.stream().forEach(e -> System.out.println(e.getValue() + " - deviceID: " + e.getKey()));
        } else {
            System.out.println("Sorry there are no relevant devices for your kid :(");
        }
    }

    private List<Integer> chooseDevicesToAddMenu(int currentKid) {
        showRelevantDevices(currentKid);
        List<Integer> devicesToAdd = new ArrayList<>();
        List<Pair<Integer, String>> devicesForKid = parkController.getRelevantDevicesForKid(currentKid);
        System.out.println("Please choose the entries you would like to add, you can choose -1 at any point to exit this menu");
        Scanner keyboard = new Scanner(System.in);
        boolean stillSelecting = true;
        while (stillSelecting) {
            try {
                int deviceId = keyboard.nextInt();
                if (deviceId != -1) {
                    boolean matchAny = devicesForKid.stream().anyMatch(e -> e.getKey() == deviceId);
                    if (matchAny) {
                        devicesToAdd.add(deviceId);
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

    private WebUser loginMenu() {
        Scanner keyBoard = new Scanner(System.in);
        String userName;
        String password;
        System.out.println("Please Enter Your User Name");
        userName = keyBoard.nextLine();
        System.out.println("Please Enter Your Password");
        password = keyBoard.nextLine();
        WebUser webUser = checkValidPasswordAndUser(userName, password);
        if (webUser != null) {
            System.out.println("Welcome Back " + parkController.getGurdianName(webUser.getGuardian()) + "!!!");
            return webUser;
        } else {
            System.out.println("User Credentials Are Not Valid, Please Try To Login In Again");
            return null;
        }
    }

    private int chooseKidMenu(WebUser webUser) {
        System.out.println("Here are all the kids that are associated with you");
        List<Pair<Integer, String>> kids = parkController.getKidsOfGuardian(webUser.getGuardian());
        kids.stream().forEach(e ->
                System.out.println("Name: " + e.getValue() + " ,Id: " + e.getKey()));
        System.out.println("Please choose the ID you would like to manage");
        Scanner keyboard = new Scanner(System.in);
        boolean selected = false;
        while (!selected) {
            try {
                int choice = keyboard.nextInt();
                if (kids.stream().anyMatch(e -> e.getKey() == choice)) {
                    return choice;
                } else {
                    continue;
                }
            } catch (Exception e) {
                System.out.println("please enter valid ID from the shown list");
                keyboard.nextLine();
            }
        }
        return 0;
    }

    private boolean addKid(WebUser webUser) {
        Scanner keyBoard = new Scanner(System.in);
        String kidName = "",kidAge = "";
        int kidWeight = 0 ,kidHeight = 0;
        boolean validKid = false;
        while (!validKid) {
            System.out.println("Please Enter Your Kid's Name");
            kidName = keyBoard.next();
            System.out.println("Please Enter Your Kid Age");
            kidAge = keyBoard.next();
            if (!checkValidKidDetails(kidName, kidAge)) {
                System.out.println("Something went wrong .. Below Are The Details Entered\n Kid Name: " + kidName + "\n Kid Age: " + kidAge);
            } else {
                System.out.println("One Last Step - Please Put Your Child On The Weight&Height Measuring At The Park Entrance");

                List<Integer> measurements = parkController.getMeasurements();
                kidHeight  = measurements.get(0);
                kidWeight = measurements.get(1);

                System.out.println("Your kid Height is: " + kidHeight);
                System.out.println("Your kid Weight is: " + kidWeight);
                validKid = true;
            }
        }
        return parkController.addNewKidToPark(webUser.getGuardian(), kidName, kidAge,kidHeight,kidWeight);
    }

    private boolean checkValidKidDetails(String kidName, String kidAge) {
        try {
            if (Integer.valueOf(kidAge) < 1 || kidName.length() < 1) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private List<Integer> chooseDevicesMenu(int kidID) {
        List<Integer> devicesToRemove = new ArrayList<>();
        System.out.println("Please choose the entries you would like to delete, you can choose -1 at any point to exit this menu");
        Scanner keyboard = new Scanner(System.in);
        boolean stillSelecting = true;
        while (stillSelecting) {
            try {
                int deviceId = keyboard.nextInt();
                if (deviceId != -1) {
                    if (parkController.kidHasThisEntry(kidID, deviceId)) {
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

    private void showETicket(int currentKid) {
        String kidName = parkController.getKidName(currentKid);
        System.out.println("eTicket of " + kidName + " ID number: " + currentKid);
        System.out.println("eTicket expiration date: " + parkController.getETickExpireDateForKid(currentKid));
        List<Integer> entries = parkController.getEntriesForKid(currentKid);
        for (int entry : entries) {
            System.out.println("Ticket for: " + entry);
        }
    }

    private boolean handleExtremeDevice(int kidID, int deviceID) {
        System.out.println(parkController.getDeviceName(deviceID) + " id:" + deviceID + "is EXTREME! please approve that you would like to let your kid get on this device");
        System.out.println("press 'y' for yes or 'n' for no");
        Scanner keyboard = new Scanner(System.in);
        boolean selected = false;
        while (!selected) {
            try {
                String choice = keyboard.next();
                if (choice.equals("y")) {
                    parkController.addDeviceToKid(kidID, deviceID);
                    return true;
                } else if (choice.equals("n")) {
                    return false;
                } else {
                    continue;
                }
            } catch (Exception e) {
                System.out.println("please enter valid number");
                keyboard.nextLine();
            }
        }
        return false;
    }

    private WebUser checkValidPasswordAndUser(String userName, String password) {
        for (WebUser webUser : webUsers) {
            if (webUser.getUserName().equals(userName)) {
                if (webUser.getPassword().equals(password)) {
                    return webUser;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public boolean addEntries(int kidID, WebUser webUser) {
        showETicket(kidID);
        List<Integer> devicesToAdd = chooseDevicesToAddMenu(kidID);
        List<Integer> entriesAdded = new ArrayList<>();
        int addedEntries = 0;
        for (Integer deviceID : devicesToAdd) {
            if (parkController.isDeviceExtreme(deviceID)) {
                if (handleExtremeDevice(kidID, deviceID)) {
                    addedEntries++;
                    entriesAdded.add(deviceID);
                }
            } else {
                parkController.addDeviceToKid(kidID, deviceID);
                entriesAdded.add(deviceID);
                addedEntries++;
            }
        }
        if (!parkController.removeFromGuardianBalance(webUser.getGuardian(), addedEntries)) {
            entriesAdded.stream().forEach(e -> parkController.removeEntryFromKid(kidID, e));
            return false;
        }
        return true;
    }

    private void removeEntries(int kidID, WebUser webUser) {
        showETicket(kidID);
        List<Integer> devicesToDelete = chooseDevicesMenu(kidID);
        int removedEntries = 0;
        for (Integer deviceID : devicesToDelete) {
            if (parkController.removeEntryFromKid(kidID, deviceID)) {
                removedEntries++;
            }
        }
        parkController.addToGuardianBalance(webUser.getGuardian(), removedEntries);
    }

    private boolean removeKid(int kidID, WebUser webUser) {
        System.out.println("First ,please return " + parkController.getKidName(kidID) + "'s eBand");
        parkController.returnKidBand(kidID);
        System.out.println("Now we will remove " + parkController.getKidName(kidID) + " from the system, please wait");
        if (!parkController.removeAndChrageKid(kidID, webUser.getGuardian())) {
            System.out.println("No more kids for you in the system, therefore we are deleting your account");
            System.out.println("Hopefully we will see you again soon :) ");
            webUsers.remove(webUser);
            return false;
        }
        return true;
    }

    private boolean manageKid(int kidID, WebUser webUser) {
        boolean exit = false;
        while (!exit) {
            int choice = printThirdStepMenu();
            switch (choice) {
                case 1:
                    if (!addEntries(kidID, webUser)) {
                        System.out.println("Operation failed, you don't have enough money for all of this devices!");
                    }
                    continue;
                case 2:
                    removeEntries(kidID, webUser);
                    continue;
                case 3:
                    if (removeKid(kidID, webUser)) {
                        return true;
                    } else {
                        return false;
                    }
                case 4:
                    showETicket(kidID);
                    continue;
                case 5:
                    System.out.println("Back to previous menu :) ");
                    return true;
                default:
                    System.out.println("Wrong choice");
            }
        }
        return true;
    }
}
