package ePark;

import impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<Object> systemObjects = new ArrayList<>();

    private static List<Device> parkDevices = new ArrayList<>();
    private static List<Kid> kids = new ArrayList<>();
    private static List<Guardian> guardians = new ArrayList<>();
    private static List<eBand> eBands = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to our ePark system");
        addStartDervices();
        Main ePark = new Main();
        ePark.printMainMenu();
    }

    private static void addStartDervices() {
        Device mamba = new Device(1,"Mamba Ride",false,false,false,true,140,0,12);
        Device wheel = new Device(2,"Giant Wheel",false,false,false,false,0,0,0);
        Device carrousel = new Device(3,"Carrousel",false,false,false,false,0,0,8);
        parkDevices.add(mamba);
        systemObjects.add(mamba);
        parkDevices.add(wheel);
        systemObjects.add(wheel);
        parkDevices.add(carrousel);
        systemObjects.add(carrousel);
    }

    private int printMainMenu() {
        int optionn = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Main Menu:");
        System.out.println("--------------");
        System.out.println("1.Login");
        System.out.println("2.Add products");
        System.out.println("3.Edit products ");
        System.out.println("4.Remove products ");
        System.out.println("5.Add users");
        System.out.println("6.Edit users ");
        System.out.println("7.Remove users ");
        System.out.println("8.Exit ");
        System.out.println("--------------");
        System.out.println("Enter your choice:");
        optionn = keyboard.nextInt();
        return optionn;
    }
}
