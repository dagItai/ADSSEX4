package ePark;
import impl.*;
import javafx.util.Pair;

import java.util.Random;

public class eBandController {
    public Pair<Double,Double> getCoordinatesOfBand(eBand band){
        Random rd = new Random(); // creating Random object
        return new Pair<>(rd.nextDouble(),rd.nextDouble());
    }
}
