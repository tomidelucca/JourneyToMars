package ss.grupo3.tp4.mars;

import ss.grupo3.tp4.algorithms.LeapFrogAlgorithm;
import ss.grupo3.tp4.algorithms.TimeStepMolecularAlgorithm;
import ss.grupo3.tp4.files.PlanetReader;
import ss.grupo3.tp4.models.Particle;
import ss.grupo3.tp4.simulation.Simulation;

import java.util.HashMap;
import java.util.Map;

public class SolarSystem extends Simulation {

    public enum Bodies {
        SUN, EARTH, MARS, VENUS, MERCURY, JUPITER, SATURN, URANUS, NEPTUNE
    }

    private static final Double DT = 100.0;

    public static final Double HOUR = 3600.0;
    public static final Double DAY = 24 * HOUR;
    public static final Double YEAR = 365 * DAY;

    private Boolean stop = Boolean.FALSE;

    private PlanetReader planetReader = null;

    private Double currentMissionTime = 0.0;
    private Double missionMaxTime = 5.0 * YEAR;

    private Map<Bodies, Particle> bodies;

    private TimeStepMolecularAlgorithm algorithm = new LeapFrogAlgorithm();

    public SolarSystem() {
        bodies = new HashMap<>(5);
    }

    public void initialize() {
        planetReader = new PlanetReader();
        planetReader.initialize("input/input2.xyz");

        bodies.put(Bodies.EARTH, planetReader.getEarth());
        bodies.put(Bodies.MARS, planetReader.getMars());
        bodies.put(Bodies.SUN, planetReader.getSun());
        bodies.put(Bodies.VENUS, planetReader.getVenus());
        bodies.put(Bodies.MERCURY, planetReader.getMercury());
        bodies.put(Bodies.JUPITER, planetReader.getJupiter());
        bodies.put(Bodies.SATURN, planetReader.getSaturn());
        bodies.put(Bodies.NEPTUNE, planetReader.getNeptune());
        bodies.put(Bodies.URANUS, planetReader.getUranus());

    }

    public Boolean simulate() {

        while (currentMissionTime <= missionMaxTime) {
            algorithm.updateVelocities(bodies.get(Bodies.EARTH), bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.MARS), bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.VENUS), bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.MERCURY), bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.JUPITER), bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.SATURN), bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.URANUS), bodies.values(), DT);
            algorithm.updateVelocities(bodies.get(Bodies.NEPTUNE), bodies.values(), DT);

            algorithm.updatePosition(bodies.get(Bodies.EARTH), DT);
            algorithm.updatePosition(bodies.get(Bodies.MARS), DT);
            algorithm.updatePosition(bodies.get(Bodies.VENUS), DT);
            algorithm.updatePosition(bodies.get(Bodies.MERCURY), DT);
            algorithm.updatePosition(bodies.get(Bodies.JUPITER), DT);
            algorithm.updatePosition(bodies.get(Bodies.SATURN), DT);
            algorithm.updatePosition(bodies.get(Bodies.URANUS), DT);
            algorithm.updatePosition(bodies.get(Bodies.NEPTUNE), DT);


            updateObservers();
            currentMissionTime += DT;
        }

        finishSimulation();
        return Boolean.TRUE;
    }

    public void stop() {

    }

    public void reset() {
    }

    public Map<Bodies, Particle> getBodies() {
        return bodies;
    }

    public Double getCurrentMissionTime() {
        return currentMissionTime;
    }

    public void setCurrentMissionTime(Double currentMissionTime) {
        this.currentMissionTime = currentMissionTime;
    }
}
