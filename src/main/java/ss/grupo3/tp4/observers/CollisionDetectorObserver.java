package ss.grupo3.tp4.observers;

import ss.grupo3.tp4.logger.LogType;
import ss.grupo3.tp4.logger.Logger;
import ss.grupo3.tp4.mars.JourneyToMars;
import ss.grupo3.tp4.models.Particle;
import ss.grupo3.tp4.simulation.Simulation;
import ss.grupo3.tp4.simulation.SimulationObserver;

public class CollisionDetectorObserver implements SimulationObserver{

    private Double DT;
    private Double epsilon;
    private Double lastObservingTime = null;

    public CollisionDetectorObserver(final Double epsilon, final Double DT) {
        this.DT = DT;
        this.epsilon = epsilon;
    }

    @Override
    public void notify(Simulation simulation) {
        JourneyToMars journeyToMars = (JourneyToMars) simulation;

        if(lastObservingTime == null) lastObservingTime = journeyToMars.getCurrentMissionTime();

        if((journeyToMars.getCurrentMissionTime() - lastObservingTime) >= DT) {
            Particle mars = journeyToMars.getBodies().get(JourneyToMars.Bodies.MARS);
            Particle spaceship = journeyToMars.getSpaceship();
            Double distance = spaceship.distanceToParticle(mars);

            if(distance <= epsilon) {
                Logger.log("COLLSION! Day: " + (lastObservingTime/86400) + ", Distance: " + distance, LogType.SUCCESS);
            }

            Logger.log("Day: " + (int)(lastObservingTime/86400) + ", Distance: " + distance, LogType.DEBUG);

            lastObservingTime = lastObservingTime + DT;
        }

    }

    @Override
    public void simulationDidFinish(Simulation simulation) {

    }
}
