package ss.grupo3.tp4.mars;

import ss.grupo3.tp4.algorithms.LeapFrogAlgorithm;
import ss.grupo3.tp4.algorithms.TimeStepMolecularAlgorithm;
import ss.grupo3.tp4.files.PlanetReader;
import ss.grupo3.tp4.models.Particle;
import ss.grupo3.tp4.models.Vector;
import ss.grupo3.tp4.simulation.Simulation;

import java.util.HashMap;
import java.util.Map;

public class JourneyToMars extends Simulation {

    public enum Bodies {
        SUN, EARTH, MARS
    }

    private static final Double SPACESHIP_INITIAL_DISTANCE = 1.5E6;
    private static final Double SPACESHIP_ORBITAL_SPEED = 7.12 * 1000;
    private static final Double DT = 100.0;

    public static final Double HOUR = 3600.0;
    public static final Double DAY = 24 * HOUR;
    public static final Double YEAR = 365 * DAY;

    private Double initialSpaceshipVelocity = 3.0 * 1000;
    private Double currentMissionTime = 0.0;
    private Double launchAngle = 0.0;
    private Double missionMaxTime = 5.0 * YEAR;
    private Double timeFromMissionStartToLaunch = 0.0;

    private Map<Bodies, Particle> bodies;
    private Particle spaceship;

    private TimeStepMolecularAlgorithm algorithm = new LeapFrogAlgorithm();

    public JourneyToMars() {
        bodies = new HashMap<>(3);
    }

    public void initialize() {
        PlanetReader planetReader = new PlanetReader();
        planetReader.initialize("input/input.xyz");

        bodies.put(Bodies.EARTH, planetReader.getEarth());
        bodies.put(Bodies.MARS, planetReader.getMars());
        bodies.put(Bodies.SUN, planetReader.getSun());
    }

    public Boolean simulate() {

        while (currentMissionTime <= timeFromMissionStartToLaunch) {
            algorithm.updateVelocities(bodies.get(Bodies.EARTH), bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.MARS), bodies.values(), DT);

            algorithm.updatePosition(bodies.get(Bodies.EARTH), DT);
            algorithm.updatePosition(bodies.get(Bodies.MARS), DT);

            currentMissionTime += DT;
        }

        Double angleToSun = angleToSun(bodies.get(Bodies.EARTH));
        launchAngle += angleToSun;

        Double sPosX = bodies.get(Bodies.EARTH).getPosition().getX() + bodies.get(Bodies.EARTH).getRadius()*Math.cos(angleToSun) + SPACESHIP_INITIAL_DISTANCE*Math.cos(angleToSun);
        Double sPosY = bodies.get(Bodies.EARTH).getPosition().getY() + bodies.get(Bodies.EARTH).getRadius()*Math.sin(angleToSun) + SPACESHIP_INITIAL_DISTANCE*Math.sin(angleToSun);

        Double sVelX = initialSpaceshipVelocity*Math.cos(Math.PI/2+launchAngle) + SPACESHIP_ORBITAL_SPEED*Math.cos(Math.PI/2+angleToSun) + bodies.get(Bodies.EARTH).getVelocity().getX();
        Double sVelY = initialSpaceshipVelocity*Math.sin(Math.PI/2+launchAngle) + SPACESHIP_ORBITAL_SPEED*Math.sin(Math.PI/2+angleToSun) + bodies.get(Bodies.EARTH).getVelocity().getY();

        Vector sPos = new Vector(sPosX, sPosY);
        Vector sVel = new Vector(sVelX, sVelY);

        spaceship = new Particle(sPos);
        spaceship.setVelocity(sVel);
        spaceship.setRadius(100.0);
        spaceship.setMass(2E5);

        while (currentMissionTime <= missionMaxTime) {
            algorithm.updateVelocities(spaceship, bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.EARTH), bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.MARS), bodies.values(), DT);

            algorithm.updatePosition(spaceship, DT);
            algorithm.updatePosition(bodies.get(Bodies.EARTH), DT);
            algorithm.updatePosition(bodies.get(Bodies.MARS), DT);

            updateObservers();
            currentMissionTime += DT;
        }

        finishSimulation();
        return Boolean.TRUE;
    }

    public void reset() {
        initialize();
        currentMissionTime = 0.0;
    }

    public Double daysFromMissionStart() {
        return currentMissionTime / DAY;
    }

    private double angleToSun(Particle body) {
        return Math.atan2(body.getPosition().getY(), body.getPosition().getX());
    }

    public Double getInitialSpaceshipVelocity() {
        return initialSpaceshipVelocity;
    }

    public void setInitialSpaceshipVelocity(Double initialSpaceshipVelocity) {
        this.initialSpaceshipVelocity = initialSpaceshipVelocity;
    }

    public Double getCurrentMissionTime() {
        return currentMissionTime;
    }

    public Double getLaunchAngle() {
        return launchAngle;
    }

    public void setLaunchAngle(Double launchAngle) {
        this.launchAngle = launchAngle;
    }

    public Double getMissionMaxTime() {
        return missionMaxTime;
    }

    public void setMissionMaxTime(Double missionMaxTime) {
        this.missionMaxTime = missionMaxTime;
    }

    public Double getTimeFromMissionStartToLaunch() {
        return timeFromMissionStartToLaunch;
    }

    public void setTimeFromMissionStartToLaunch(Double timeFromMissionStartToLaunch) {
        this.timeFromMissionStartToLaunch = timeFromMissionStartToLaunch;
    }

    public Map<Bodies, Particle> getBodies() {
        return bodies;
    }

    public Particle getSpaceship() {
        return spaceship;
    }

    public static Double getDT() {
        return DT;
    }
}
