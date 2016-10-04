package ss.grupo3.tp4;

import ss.grupo3.tp4.logger.Logger;
import ss.grupo3.tp4.mars.JourneyToMars;
import ss.grupo3.tp4.observers.CollisionDetectorObserver;
import ss.grupo3.tp4.observers.OvitoWriterObserver;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        new File("output/").mkdirs();

        Logger.setDebug(Boolean.FALSE);

        JourneyToMars journeyToMars = new JourneyToMars();
        journeyToMars.setInitialSpaceshipVelocity(4.63005 * 1000.0);
        journeyToMars.setTimeFromMissionStartToLaunch(560 * 86400.0);
        journeyToMars.setMissionMaxTime(10 * 86400.0 * 365);

        journeyToMars.addObserver(new OvitoWriterObserver("output/sim.xyz", 86400.0));
        journeyToMars.addObserver(new CollisionDetectorObserver(5E6, 86400.0/100));

        journeyToMars.initialize();
        journeyToMars.simulate();

    }
}
