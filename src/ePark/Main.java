package ePark;

import impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static List<Object> systemObjects = new ArrayList<>();

    private List<Device> parkDevices = new ArrayList<>();
    private List<Kid> kids = new ArrayList<>();
    private List<WebUser> webUsers = new ArrayList<>();
    private List<eBand> eBands = new ArrayList<>();
    private List<CreditCompany> parkCompanies = new ArrayList<>();

    private eBandController eBandController = new eBandController();
    public static void main(String[] args) {
        System.out.println("Welcome to our ePark system");
        Main ePark = new Main();
        ePark.mainMenu();
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

    private WebUser signUpMenu() {
        //UC-1
        Kid newKid = addKid();
        return null;
    }

    private Kid addKid() {
        return null;
    }

    private WebUser loginMenu() {
        return null;
    }

    private void loggedInUser(WebUser webUser) {
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        WebUser wb;
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
                    int kidID = chooseKidMenu();
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

    private int chooseKidMenu() {
        return 0;
    }

    private void manageKid(int kidID, WebUser webUser) {
        Kid currentKid = null;
        for (Kid kid : kids) {
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
                    removeKid(kidID);
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

    private void removeKid(int kidID) {
    }

    //----------------------------------------------------------------Itai---------------------------------------------------------------------------------------------------
    private void removeEntries(Kid kidID, WebUser webUser, eTicket eTick) {
        List<Integer> devicesToDelete = chooseDevicesMenu(eTick);
        int removedEntries = 0;
        for (Integer integer : devicesToDelete) {
            Entry e = eTick.getEntryByID(integer);
            if (e != null) {
                eTick.removeEntry(e);
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
}
