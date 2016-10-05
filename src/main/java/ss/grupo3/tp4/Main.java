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

        MinTravelAngleObserver cW = new MinTravelAngleObserver("output/min7.csv");
        journeyToMars.addObserver(cW);

        journeyToMars.setTimeFromMissionStartToLaunch(0.0);
        journeyToMars.setMissionMaxTime(2 * 365 * 86400.0);

        Double maxStartAngle = 0.3*Math.PI;
        Double startAngle = -0.3*Math.PI;
        Double angleStep = Math.PI/20;

        while (startAngle <= maxStartAngle) {

            Logger.log("Current Angle: " + startAngle, LogType.INFO);
            Double maxVelocity = 8.0 * 1000;
            Double velocity = 4.0 * 1000;
            Double velocityStep = 0.10 * 1000;
            journeyToMars.setLaunchAngle(startAngle);
            while (velocity <= maxVelocity) {
                journeyToMars.reset();
                journeyToMars.setInitialSpaceshipVelocity(velocity);
                journeyToMars.simulate();
                velocity += velocityStep;
            }

            startAngle += angleStep;
        }

        cW.finish();
    }

    public static void generateAnimations() {
        JourneyToMars journeyToMars = new JourneyToMars();
        journeyToMars.setInitialSpaceshipVelocity(7.0327 * 1000.0);
        journeyToMars.setMissionMaxTime(2 * 86400.0 * 365);
        journeyToMars.setLaunchAngle(-0.40005*Math.PI);

        journeyToMars.addObserver(new OvitoWriterObserver("output/sim.xyz", 86400.0));

        journeyToMars.initialize();
        journeyToMars.simulate();
    }

    public static void findCollsions() {
        JourneyToMars journeyToMars = new JourneyToMars();
        journeyToMars.initialize();
        journeyToMars.setMissionMaxTime(2 * 365 * 86400.0);

        CollisionDetectorObserver cW = new CollisionDetectorObserver(2E7, 200.0);
        journeyToMars.addObserver(cW);

        Double maxVelocity = 7.033 * 1000;
        Double velocity = 7.032 * 1000;
        Double velocityStep = 0.0001 * 1000;

        journeyToMars.setLaunchAngle(-0.40005* Math.PI);

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
