package ePark;

import impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Object> systemObjects = new ArrayList<>();

    private List<Device> parkDevices = new ArrayList<>();
    private List<Kid> kids = new ArrayList<>();
    private List<WebUser> webUsers = new ArrayList<>();
    private List<eBand> eBands = new ArrayList<>();
    private List<CreditCompany> parkCompanies = new ArrayList<>();
    //yaara
    private CreditCardController CreditCardController;
    private eBandController eBandController;
    private EquipmentController equipmentController;
    private ParkController parkController;

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to our ePark system");
        Main ePark = new Main();
        ePark.mainMenu();
    }

    public void mainMenu() throws Exception {
        Scanner input = new Scanner(System.in);
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
                    showMyKids();
                    continue;
                case 3:
                    int kidID = chooseKidMenu();
                    manageKid(kidID,webUser);
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

    private void manageKid(int kidID, WebUser wb) {
        Scanner input = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            int choice = printThirdStepMenu();
            switch (choice) {
                case 1:
                    addEntries(kidID);
                    continue;
                case 2:
                    removeEntries(kidID);
                    continue;
                case 3:
                    removeKid(kidID, wb);
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

    private void removeKid(int kidID, WebUser wb) {


    }

    private void removeEntries(int kidID) {
    }

    private void addEntries(int kidID) {
    }

    private void showMyKids() {

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
