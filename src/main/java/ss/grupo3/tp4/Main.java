package ss.grupo3.tp4;

import ss.grupo3.tp4.logger.LogType;
import ss.grupo3.tp4.logger.Logger;
import ss.grupo3.tp4.mars.JourneyToMars;
import ss.grupo3.tp4.observers.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        new File("output/").mkdirs();

        Logger.setDebug(Boolean.FALSE);

        generateAnimations();
        //findCollsions();
        //minTravelLength();
    }

    public static void minTravelLength() {
        JourneyToMars journeyToMars = new JourneyToMars();
        journeyToMars.initialize();

        MinTravelDistanceObserver cW = new MinTravelDistanceObserver("output/min5.csv");
        journeyToMars.addObserver(cW);

        Double maxStartTime = 2500.0 * 86400.0;
        Double startTime = 1800.0 * 86400.0;
        Double timeStep = 30 * 86400.0;

        while (startTime <= maxStartTime) {
            journeyToMars.setTimeFromMissionStartToLaunch(startTime);
            journeyToMars.setMissionMaxTime(0.5 * 365 * 86400.0 + startTime);
            Logger.log("Current Date: " + startTime/86400, LogType.INFO);
            Double maxVelocity = 9.0 * 1000;
            Double velocity = 3.0 * 1000;
            Double velocityStep = 0.15 * 1000;
            while (velocity <= maxVelocity) {
                journeyToMars.reset();
                journeyToMars.setInitialSpaceshipVelocity(velocity);
                journeyToMars.simulate();
                velocity += velocityStep;
            }

            startTime += timeStep;
        }

        cW.finish();
    }

    public static void generateAnimations() {
        JourneyToMars journeyToMars = new JourneyToMars();
        journeyToMars.setInitialSpaceshipVelocity(5.0 * 1000.0);
        journeyToMars.setTimeFromMissionStartToLaunch(1.0 * 86400.0);
        journeyToMars.setMissionMaxTime(5 * 86400.0 * 365);

        journeyToMars.addObserver(new OvitoWriterObserver("output/sim.xyz", 86400.0));

        journeyToMars.initialize();
        journeyToMars.simulate();
    }

    public static void findCollsions() {
        JourneyToMars journeyToMars = new JourneyToMars();
        journeyToMars.initialize();
        Double missionStartDate = 0.0 * 86400.0;
        journeyToMars.setTimeFromMissionStartToLaunch(missionStartDate);
        journeyToMars.setMissionMaxTime(5 * 365 * 86400.0 + missionStartDate);

        CollisionDetectorObserver cW = new CollisionDetectorObserver(1E8, 150.0);
        journeyToMars.addObserver(cW);

        Double maxVelocity = 3.8 * 1000;
        Double velocity = 3.7 * 1000;
        Double velocityStep = 0.01 * 1000;

        while (velocity <= maxVelocity) {
            if(velocity%1000 == 0) {
                Logger.log("Current Velocity: " + velocity, LogType.INFO);
            }
            journeyToMars.reset();
            journeyToMars.setInitialSpaceshipVelocity(velocity);
            journeyToMars.simulate();
            velocity += velocityStep;
        }

    }

}
