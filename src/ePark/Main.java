package ePark;

import impl.*;

import java.util.*;

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
        userName=keyBoard.nextLine();
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
            }
        }
        System.out.println("Please Enter Your Name");
        gName = keyBoard.nextLine();

        //Try Get Kid Details
        boolean validKid = false;
        while (!validKid){
            System.out.println("Please Enter Your Kid's Name");
            kidName = keyBoard.nextLine();
            System.out.println("Please Enter Your Kid Age");
            kidAge = keyBoard.nextLine();
            if(!checkValidKidDeatiles(kidName,kidAge)) {
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
        creditNumber = keyBoard.nextLine();
        System.out.println("And Now Please Enter Your Budget Limit For The Park, Please Note That The  Your Budget Limit Should Be At Least $10");
        limitCredit = keyBoard.nextLine();
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



    //Alisa
    private CreditCompany creditCompanyFromCreditNumber(String cNumber){
        int firstNumber = Integer.valueOf(cNumber.charAt(0));
        if(firstNumber==1){
            return parkCompanies.get(1);
        }
        else if(firstNumber==2){
            return parkCompanies.get(2);
        }
        else {
            return parkCompanies.get(3);
        }
    }
    //Alisa
    private boolean checkValidCredit(String creditNumber, String limitCredit) {
        try {
            int creditNum = Integer.valueOf(creditNumber);
            int firstNumber = Integer.valueOf(creditNumber.charAt(0));
            int creditLimit = Integer.valueOf(limitCredit);
            if(firstNumber<1 || firstNumber>4 || creditLimit<10){
                return false;
            }
            return ccController.validateCard(creditNum,creditLimit);
        } catch (Exception e){
            return false;
        }
    }

    //Alisa
    private boolean checkValidKidDeatiles(String kidName, String kidAge) {
        try {
            if(Integer.valueOf(kidAge)<1 || kidName.length()<1){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private Kid addKid() {
        return null;
    }

    //Alisa
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

    //Alisa
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
                    manageKid(kidID);
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

    private void manageKid(int kidID) {
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        WebUser wb;
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
