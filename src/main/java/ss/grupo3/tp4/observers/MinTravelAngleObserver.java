package ss.grupo3.tp4.observers;

import ss.grupo3.tp4.mars.JourneyToMars;
import ss.grupo3.tp4.models.Particle;
import ss.grupo3.tp4.simulation.Simulation;
import ss.grupo3.tp4.simulation.SimulationObserver;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MinTravelAngleObserver implements SimulationObserver {

    private PrintWriter printWriter = null;
    private Map<Double, Double> minTripLengthMap;

    public MinTravelAngleObserver(final String path) {
        this.minTripLengthMap = new HashMap<>();
        try {
            this.printWriter = new PrintWriter(path, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notify(Simulation simulation) {

        JourneyToMars journeyToMars = (JourneyToMars) simulation;

        Double launchAngle = journeyToMars.getLaunchAngle();

        Double currentMinValueForLaunchDate = minTripLengthMap.get(launchAngle);

        Particle mars = journeyToMars.getBodies().get(JourneyToMars.Bodies.MARS);
        Particle spaceship = journeyToMars.getSpaceship();
        Double distance = spaceship.distanceToParticle(mars);

        if(currentMinValueForLaunchDate == null || distance < currentMinValueForLaunchDate) {
            currentMinValueForLaunchDate = distance;
            minTripLengthMap.put(launchAngle, currentMinValueForLaunchDate);
        }

    }

    public void simulationDidFinish(Simulation simulation) {

    }

    public void finish() {
        Set<Double> angles = minTripLengthMap.keySet();
        for(Double angle: angles) {
            printWriter.println("" + angle + "," + minTripLengthMap.get(angle));
        }
        printWriter.close();
    }
}