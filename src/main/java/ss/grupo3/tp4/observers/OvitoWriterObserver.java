package ss.grupo3.tp4.observers;

import ss.grupo3.tp4.logger.LogType;
import ss.grupo3.tp4.mars.JourneyToMars;
import ss.grupo3.tp4.models.Color;
import ss.grupo3.tp4.models.OvitoParticle;
import ss.grupo3.tp4.simulation.Simulation;
import ss.grupo3.tp4.simulation.SimulationObserver;
import ss.grupo3.tp4.logger.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class OvitoWriterObserver implements SimulationObserver {

    private final Double DT;
    private final Double CORNER_VALUE = 3.0E11;
    private PrintWriter printWriter = null;
    private String savePath;

    private Integer iteration;
    private Double lastObservingTime = null;

    public OvitoWriterObserver(final String path, final Double DT) {
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
        Logger.log("Ovito observer started at " + path, LogType.SUCCESS);
    }

    @Override
    public void notify(Simulation simulation) {

        JourneyToMars journeyToMars = (JourneyToMars) simulation;

        if(lastObservingTime == null) lastObservingTime = journeyToMars.getCurrentMissionTime();

        if((journeyToMars.getCurrentMissionTime() - lastObservingTime) >= DT) {
            List<OvitoParticle> printParticles = new ArrayList<>(4);

            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(JourneyToMars.Bodies.SUN), 3E10, new Color(251.0/255.0,236.0/255.0,87.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(JourneyToMars.Bodies.EARTH), 1.5E10, new Color(80.0/255.0,139.0/255.0,181.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getBodies().get(JourneyToMars.Bodies.MARS), 1E10, new Color(183.0/255.0,62.0/255.0,21.0/255.0)));
            printParticles.add(new OvitoParticle(journeyToMars.getSpaceship(), 3E9, new Color(1.0,1.0,1.0)));

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

        printWriter.println(particleList.size() + 4);
        printWriter.println(iteration);
        printWriter.println(-CORNER_VALUE +" " + CORNER_VALUE + " 0 0 1 1 0 0 0");
        printWriter.println(CORNER_VALUE +" " + CORNER_VALUE + " 0 0 1 1 0 0 0");
        printWriter.println(-CORNER_VALUE +" " + -CORNER_VALUE + " 0 0 1 1 0 0 0");
        printWriter.println(CORNER_VALUE +" " + -CORNER_VALUE + " 0 0 1 1 0 0 0");

        for(OvitoParticle p: particleList) {
            printWriter.println(p.getPosition().getX() + " " + p.getPosition().getY() + " " + p.getVelocity().getX() + " " + p.getVelocity().getY() + " " + p.getRadius() + " " + p.getColor().getR() + " " + p.getColor().getG() + " " + p.getColor().getB());
        }

        iteration++;
    }
}
