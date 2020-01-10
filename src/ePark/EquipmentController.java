package ePark;

import impl.eBand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        return new eBand();
    }

    public boolean returnUsedBand(eBand eBand) {
        return true;
    }
}
