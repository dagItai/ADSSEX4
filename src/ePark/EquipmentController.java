package ePark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import impl.*;

public class EquipmentController {
    List<eBand> existingBands = new ArrayList<>();
    public List<Integer> getMeasurementsFromMeasureDevice() {
        List<Integer> returnMeasure = new ArrayList<>();
        Random r = new Random();
        int low = 100;
        int high = 201;
        returnMeasure.add(r.nextInt(high - low) + low);
        low = 20;
        high = 90;
        returnMeasure.add(r.nextInt(high - low) + low);

        return returnMeasure;
    }

    public eBand createNewEBand() {
        if(!existingBands.isEmpty())
            return existingBands.remove(0);
        else{
            return new eBand();
        }
    }

    public boolean returnUsedBand(eBand eBand) {
        return false;
    }
}
