package ss.grupo3.tp4.observers;

import ss.grupo3.tp4.mars.JourneyToMars;
import ss.grupo3.tp4.models.Particle;
import ss.grupo3.tp4.simulation.Simulation;
import ss.grupo3.tp4.simulation.SimulationObserver;

public abstract class AbstractCollisionDetectorObserver implements SimulationObserver {

    private Double DT;
    private Double epsilon;
    protected Double lastObservingTime = null;

    public AbstractCollisionDetectorObserver(final Double epsilon, final Double DT) {
        this.epsilon = epsilon;
        this.DT = DT;
    }

    protected Boolean detectCollision(JourneyToMars journeyToMars) {

        if(lastObservingTime == null) lastObservingTime = journeyToMars.getCurrentMissionTime();

        if((journeyToMars.getCurrentMissionTime() - lastObservingTime) >= DT) {
            Particle mars = journeyToMars.getBodies().get(JourneyToMars.Bodies.MARS);
            Particle spaceship = journeyToMars.getSpaceship();
            Double distance = spaceship.distanceToParticle(mars);

            if(distance <= epsilon) {
                return Boolean.TRUE;
            }

            lastObservingTime = lastObservingTime + DT;
        }

        return Boolean.FALSE;
    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        lastObservingTime = null;
    }

}
