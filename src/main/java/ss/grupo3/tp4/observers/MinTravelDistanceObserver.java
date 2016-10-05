package ss.grupo3.tp4.observers;

import ss.grupo3.tp4.mars.JourneyToMars;
import ss.grupo3.tp4.models.Particle;
import ss.grupo3.tp4.simulation.Simulation;
import ss.grupo3.tp4.simulation.SimulationObserver;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MinTravelDistanceObserver implements SimulationObserver {

    private PrintWriter printWriter = null;
    private Map<Integer, Double> minTripLengthMap;

    public MinTravelDistanceObserver(final String path) {
        this.minTripLengthMap = new HashMap<>();
        try {
            this.printWriter = new PrintWriter(path, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notify(Simulation simulation) {

        JourneyToMars journeyToMars = (JourneyToMars) simulation;

        Integer launchDate = (int)(journeyToMars.getTimeFromMissionStartToLaunch() / 86400);

        Double currentMinValueForLaunchDate = minTripLengthMap.get(launchDate);

        Particle mars = journeyToMars.getBodies().get(JourneyToMars.Bodies.MARS);
        Particle spaceship = journeyToMars.getSpaceship();
        Double distance = spaceship.distanceToParticle(mars);

        if(currentMinValueForLaunchDate == null || distance < currentMinValueForLaunchDate) {
            currentMinValueForLaunchDate = distance;
            minTripLengthMap.put(launchDate, currentMinValueForLaunchDate);
        }

    }

    public void simulationDidFinish(Simulation simulation) {

    }

    public void finish() {
        Set<Integer> days = minTripLengthMap.keySet();
        for(Integer day: days) {
            printWriter.println("" + day + "," + minTripLengthMap.get(day));
        }
        printWriter.close();
    }
}
