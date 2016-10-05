package ss.grupo3.tp4.observers;

import ss.grupo3.tp4.logger.LogType;
import ss.grupo3.tp4.logger.Logger;
import ss.grupo3.tp4.mars.SolarSystem;
import ss.grupo3.tp4.models.Color;
import ss.grupo3.tp4.models.OvitoParticle;
import ss.grupo3.tp4.simulation.Simulation;
import ss.grupo3.tp4.simulation.SimulationObserver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Ovito3DWriterObserver implements SimulationObserver {

    private final Double DT;
    private final Double CORNER_VALUE = 5.0E12;
    private PrintWriter printWriter = null;
    private String savePath;

    private Integer iteration;
    private Double lastObservingTime = null;

    public Ovito3DWriterObserver(final String path, final Double DT) {
        this.DT = DT;
        this.iteration = 0;
        this.savePath = path;
        try {
            this.printWriter = new PrintWriter(path, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Logger.log("Ovito 3D observer started at " + path, LogType.SUCCESS);
    }

    @Override
    public void notify(Simulation simulation) {

        SolarSystem journeyToMars = (SolarSystem) simulation;

        if(lastObservingTime == null) lastObservingTime = journeyToMars.getCurrentMissionTime();

        if((journeyToMars.getCurrentMissionTime() - lastObservingTime) >= DT) {
            List<OvitoParticle> printParticles = new ArrayList<>(4);

            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(SolarSystem.Bodies.SUN), 3E10, new Color(251.0/255.0,236.0/255.0,87.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(SolarSystem.Bodies.EARTH), 1.5E10, new Color(80.0/255.0,139.0/255.0,181.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(SolarSystem.Bodies.MARS), 1E10, new Color(183.0/255.0,62.0/255.0,21.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(SolarSystem.Bodies.MERCURY), 1E10, new Color(195.0/255.0,195.0/255.0,195.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(SolarSystem.Bodies.VENUS), 1.5E10, new Color(237.0/255.0,179.0/255.0,96.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(SolarSystem.Bodies.JUPITER), 2E10, new Color(228.0/255.0,236.0/255.0,226.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(SolarSystem.Bodies.SATURN), 1.9E10, new Color(166.0/255.0,152.0/255.0,119.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(SolarSystem.Bodies.URANUS), 1.7E10, new Color(195.0/255.0,232.0/255.0,235.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(SolarSystem.Bodies.NEPTUNE), 1.7E10, new Color(67.0/255.0,103.0/255.0,246.0/255.0)));

            write(printParticles);

            lastObservingTime = lastObservingTime + DT;
        }
    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        printWriter.close();
        Logger.log("Ovito file saved at " + savePath, LogType.SUCCESS);
    }

    private void write(List<OvitoParticle> particleList) {

        printWriter.println(particleList.size() + 8);
        printWriter.println(iteration);
        printWriter.println(-CORNER_VALUE +" " + CORNER_VALUE + " 0 1E10 0 0 0");
        printWriter.println(CORNER_VALUE +" " + CORNER_VALUE + " 0 1E10 0 0 0");
        printWriter.println(-CORNER_VALUE +" " + -CORNER_VALUE + " 0 1E10 0 0 0");
        printWriter.println(CORNER_VALUE +" " + -CORNER_VALUE + " 0 1E10 0 0 0");
        printWriter.println(-CORNER_VALUE +" " + CORNER_VALUE + " " + CORNER_VALUE + " 1E10 0 0 0");
        printWriter.println(CORNER_VALUE +" " + CORNER_VALUE + " " + CORNER_VALUE + " 1E10 0 0 0");
        printWriter.println(-CORNER_VALUE +" " + -CORNER_VALUE + " " + CORNER_VALUE + " 1E10 0 0 0");
        printWriter.println(CORNER_VALUE +" " + -CORNER_VALUE + " " + CORNER_VALUE + " 1E10 0 0 0");

        for(OvitoParticle p: particleList) {
            printWriter.println(p.getPosition().getX() + " " + p.getPosition().getY() + " " + p.getPosition().getZ() + " " + p.getRadius() + " " + p.getColor().getR() + " " + p.getColor().getG() + " " + p.getColor().getB());
        }

        iteration++;
    }
}