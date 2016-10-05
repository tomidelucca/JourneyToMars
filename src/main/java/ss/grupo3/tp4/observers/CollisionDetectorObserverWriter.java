package ss.grupo3.tp4.observers;

import ss.grupo3.tp4.mars.JourneyToMars;
import ss.grupo3.tp4.simulation.Simulation;

import java.io.PrintWriter;

public class CollisionDetectorObserverWriter extends AbstractCollisionDetectorObserver {

    private PrintWriter printWriter = null;

    public CollisionDetectorObserverWriter(final Double epsilon, final Double DT, final String path) {
        super(epsilon, DT);
        try {
            this.printWriter = new PrintWriter(path, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notify(Simulation simulation) {
        JourneyToMars journeyToMars = (JourneyToMars) simulation;

        if (detectCollision(journeyToMars)) {
            printWriter.println("" + (lastObservingTime / 86400) + ", " + journeyToMars.getSpaceship().distanceToParticle(journeyToMars.getBodies().get(JourneyToMars.Bodies.MARS)) + ", " + journeyToMars.getInitialSpaceshipVelocity() + ", " + journeyToMars.getSpaceship().getVelocity());
            simulation.stop();
        }

    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        super.simulationDidFinish(simulation);
    }

    public void finish() {
        printWriter.close();
    }
}
