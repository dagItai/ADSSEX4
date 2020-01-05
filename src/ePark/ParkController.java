package ePark;

import impl.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ParkController {
    private List<Device> parkDevices = new ArrayList<>();
    private List<Kid> kids = new ArrayList<>();
    private List<CreditCompany> parkCompanies = new ArrayList<>();

    private CreditCardController ccController = new CreditCardController();
    private EquipmentController equipmentController = new EquipmentController();
    private eBandController eBandController = new eBandController();
    private static int kID=1;

    public ParkController() {
        addStartDevices();
        addCreditCardCompanies();
    }

    public Kid addNewKidToPark(Guardian guardian, String kidName, String kidAge) {
        Kid newKid = new Kid(kID,kidName,Integer.valueOf(kidAge),guardian);
        eTicket newKideTicket = new eTicket(kID, new Date(),newKid);
        kID++;
        //Create eBand
        eBand newKideband = equipmentController.createNewEBand();
        //setAll
        newKideband.setKid(newKid);
        newKid.seteBand(newKideband);
        newKid.seteTicket(newKideTicket);
        newKideTicket.setKid(newKid);
        System.out.println( newKid.getName()+ " added to your kids");
        //Last Step - Measuring
        System.out.println("One Last Step - Please Put Your Child On The Weight&Height Measuring At The Park Entrance");
        List<Integer> measures = equipmentController.getMeasurementsFromMeasureDevice();
        newKid.setHeight(measures.get(0));
        newKid.setWeight(measures.get(1));

        guardian.addKid(newKid);

        //Add All To systemObjects
        WebController.systemObjects.add(newKid);
        WebController.systemObjects.add(newKideband);
        WebController.systemObjects.add(newKideTicket);
        //Add To local lists
        kids.add(newKid);
        return null;
    }

    public void returnKidBand(int kidID) {
        for (Kid kid : kids) {
            if(kid.getID() == kidID)
                equipmentController.returnUsedBand(kid.getEBand());
        }
    }

    public boolean removeAndChrageKid(int kidID, Guardian guardian) {
        Kid currKid = null;
        for (Kid kid : kids) {
            if(kid.getID() == kidID)
                currKid = kid;
        }
        int numOfEntries = currKid.getETicket().getEntries().size();
        int finalCharge = numOfEntries*10;
        if ( finalCharge>0 ){// we need to pay balnace < maxPrice
            if (ccController.chargeCard(guardian.getCreditCard(), finalCharge)){
                System.out.println("We will charge your credit card for: "+ finalCharge+ " shekel");
                //System.out.println("balance:" +guardian.getAccount().getBalance());
            }
        }
        if (guardian.removeKid(currKid)){
            WebController.systemObjects.remove(currKid.getETicket());
            WebController.systemObjects.remove(currKid);
            kids.remove(currKid);
            currKid.delete();
            // System.out.println("");
            return true;

        }
        else { // means he is only child:)
            WebController.systemObjects.remove(guardian);
            WebController.systemObjects.remove(guardian.getWebUser());
            WebController.systemObjects.remove(guardian.getAccount());
            WebController.systemObjects.remove(currKid.getETicket());
            WebController.systemObjects.remove(currKid);
            kids.remove(currKid);
            guardian.delete();
            return false;
        }
    }

    private void addStartDevices() {
        Device mamba = new Device(1, "Mamba Ride", false, false, false, true, 140, 0, 12);
        Device wheel = new Device(2, "Giant Wheel", false, false, false, false, 0, 0, 0);
        Device carrousel = new Device(3, "Carrousel", false, false, false, false, 0, 0, 8);
        parkDevices.add(mamba);
        WebController.systemObjects.add(mamba);
        parkDevices.add(wheel);
        WebController.systemObjects.add(wheel);
        parkDevices.add(carrousel);
        WebController.systemObjects.add(carrousel);
    }

    private void addCreditCardCompanies() {
        CreditCompany visa = new CreditCompany("Visa");
        CreditCompany masterCard = new CreditCompany("Master Card");
        CreditCompany amex = new CreditCompany("American Express");
        parkCompanies.add(visa);
        WebController.systemObjects.add(visa);
        parkCompanies.add(masterCard);
        WebController.systemObjects.add(masterCard);
        parkCompanies.add(amex);
        WebController.systemObjects.add(amex);
    }

    public void getRelevantDevicesForKid(){

    }



    public List<Pair<Integer, String>> getKidsOfGuardian(Guardian guardian) {
        List<Pair<Integer, String>> kids = new ArrayList<>();
        for (Kid kid : guardian.getKids()) {
            kids.add(new Pair(kid.getID(),kid.getName()));
        }
        return kids;
    }

    public Pair<Double,Double> getCoordinatesOfKid(Integer key) {
        for (Kid kid : kids) {
            if(kid.getID() == key)
                return eBandController.getCoordinatesOfBand(kid.getEBand());
        }
        return null;
    }

    public List<Pair<Integer, String>> getRelevantDevicesForKid(Integer key){
        List<Pair<Integer, String>> relevantDevices = new ArrayList<>();
        for (Kid kid : kids) {
            if(kid.getID() == key){
                for (Device parkDevice : parkDevices) {
                    if (parkDevice.validDeviceForKid(kid.getAge(), kid.getHeight(), kid.getWeight())) {
                        relevantDevices.add(new Pair(parkDevice.getID(),parkDevice.getName()));
                    }
                }
            }
        }
        return relevantDevices;
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

    public boolean isDeviceExtreme(Integer deviceID) {
        return parkDevices.stream().anyMatch(d -> d.getID() == deviceID && d.getIsExtreme());
    }

    public boolean kidHasThisEntry(int kidID, int deviceId) {
        for (Kid kid : kids) {
            if(kid.getID() == kidID)
                return kid.getETicket().getEntries().stream().anyMatch(e -> e.getDeviceID()==deviceId);
        }
        return false;
    }

    public Date getETickExpireDateForKid(int kidID) {
        for (Kid kid : kids) {
            if(kid.getID() == kidID)
                return kid.getETicket().getExpireDate();
        }
        return null;
    }

    public String getKidName(int kidID) {
        for (Kid kid : kids) {
            if(kid.getID() == kidID)
                return kid.getName();
        }
        return null;
    }


    public List<Integer> getEntriesForKid(int kidID) {
        List<Integer> entries = new ArrayList<>();
        for (Kid kid : kids) {
            if(kid.getID() == kidID){
                entries.addAll(kid.getETicket().getEntries().stream().mapToInt(e -> e.getDeviceID()).boxed().collect(Collectors.toList()));
            }
        }
        return entries;
    }

    public String getDeviceName(int deviceID){
        for (Device parkDevice : parkDevices) {
            if(parkDevice.getID()==deviceID)
                return parkDevice.getName();
        }
        return null;
    }

    public void addDeviceToKid(int kidID, int deviceID){
        for (Kid kid : kids) {
            if(kid.getID() == kidID){
                Entry e = new Entry(deviceID, kid.getETicket());
                WebController.systemObjects.add(e);
            }
        }
    }

    public boolean removeFromGuardianBalance(Guardian guardian, int numOfEntries){
        return guardian.getAccount().removeFromBalance(numOfEntries * 10);
    }

    public boolean removeEntryFromKid(int kidID, int deviceID){
        for (Kid kid : kids) {
            if(kid.getID() == kidID){
                eTicket eTick = kid.getETicket();
                for (Entry entry : eTick.getEntries()) {
                    if(entry.getDeviceID() == deviceID) {
                        eTick.removeEntry(entry);
                        WebController.systemObjects.remove(entry);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean addToGuardianBalance(Guardian guardian, int removedEntries){
        return guardian.getAccount().addToBalance(removedEntries * 10);
    }

    public boolean checkValidCredit(String creditNumber, String limitCredit) {
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

    public WebUser getWebUser(String kidName, String kidAge, String creditNumber, String limitCredit, String userName, String password, int gID, String gName) {
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
        List<Integer> measures = equipmentController.getMeasurementsFromMeasureDevice();
        newKid.setHeight(measures.get(0));
        newKid.setWeight(measures.get(1));

        newGuardian.addKid(newKid);

        //Add All To systemObjects
        WebController.systemObjects.add(newWebUser);
        WebController.systemObjects.add(newAccount);
        WebController.systemObjects.add(newGuardian);
        WebController.systemObjects.add(newKid);
        WebController.systemObjects.add(newKideband);
        WebController.systemObjects.add(newKideTicket);
        //Add To local lists
        kids.add(newKid);
        return newWebUser;
    }

    public String getGurdianName(Guardian guardian) {
        return guardian.getName();
    }

}
