package ss.grupo3.tp4.files;

import ss.grupo3.tp4.models.Color;
import ss.grupo3.tp4.models.Particle;
import ss.grupo3.tp4.models.Vector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PlanetReader {

    private int N;
    private double L;

    private Particle sun;
    private Particle mercury;
    private Particle venus;
    private Particle earth;
    private Particle mars;
    private Particle jupiter;
    private Particle saturn;
    private Particle uranus;
    private Particle neptune;

    public void initialize(String path) {

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();

            if (line != null) {
                this.N = Integer.valueOf(line);
                this.L = Double.valueOf(br.readLine());
            }

            String[] input;

            input = br.readLine().split(" ");
            sun = new Particle(new Vector(Double.valueOf(input[0]) * 1000.0, Double.valueOf(input[1]) * 1000.0, Double.valueOf(input[2]) * 1000.0));
            sun.setVelocity(new Vector(Double.valueOf(input[3]) * 1000.0, Double.valueOf(input[4]) * 1000.0,  Double.valueOf(input[5]) * 1000.0));
            sun.setRadius(Double.valueOf(input[6]) * 1000.0);
            sun.setMass(Double.valueOf(input[7]));
            sun.setId("Sun");

            input = br.readLine().split(" ");
            earth = new Particle(new Vector(Double.valueOf(input[0]) * 1000.0, Double.valueOf(input[1]) * 1000.0, Double.valueOf(input[2]) * 1000.0));
            earth.setVelocity(new Vector(Double.valueOf(input[3]) * 1000.0, Double.valueOf(input[4]) * 1000.0,  Double.valueOf(input[5]) * 1000.0));
            earth.setRadius(Double.valueOf(input[6]) * 1000.0);
            earth.setMass(Double.valueOf(input[7]));
            earth.setId("Earth");

            input = br.readLine().split(" ");
            mars = new Particle(new Vector(Double.valueOf(input[0]) * 1000.0, Double.valueOf(input[1]) * 1000.0, Double.valueOf(input[2]) * 1000.0));
            mars.setVelocity(new Vector(Double.valueOf(input[3]) * 1000.0, Double.valueOf(input[4]) * 1000.0,  Double.valueOf(input[5]) * 1000.0));
            mars.setRadius(Double.valueOf(input[6]) * 1000.0);
            mars.setMass(Double.valueOf(input[7]));
            mars.setId("Mars");

            input = br.readLine().split(" ");
            mercury = new Particle(new Vector(Double.valueOf(input[0]) * 1000.0, Double.valueOf(input[1]) * 1000.0, Double.valueOf(input[2]) * 1000.0));
            mercury.setVelocity(new Vector(Double.valueOf(input[3]) * 1000.0, Double.valueOf(input[4]) * 1000.0,  Double.valueOf(input[5]) * 1000.0));
            mercury.setRadius(Double.valueOf(input[6]) * 1000.0);
            mercury.setMass(Double.valueOf(input[7]));
            mercury.setId("Mercury");

            input = br.readLine().split(" ");
            venus = new Particle(new Vector(Double.valueOf(input[0]) * 1000.0, Double.valueOf(input[1]) * 1000.0, Double.valueOf(input[2]) * 1000.0));
            venus.setVelocity(new Vector(Double.valueOf(input[3]) * 1000.0, Double.valueOf(input[4]) * 1000.0,  Double.valueOf(input[5]) * 1000.0));
            venus.setRadius(Double.valueOf(input[6]) * 1000.0);
            venus.setMass(Double.valueOf(input[7]));
            venus.setId("Venus");

            input = br.readLine().split(" ");
            jupiter = new Particle(new Vector(Double.valueOf(input[0]) * 1000.0, Double.valueOf(input[1]) * 1000.0, Double.valueOf(input[2]) * 1000.0));
            jupiter.setVelocity(new Vector(Double.valueOf(input[3]) * 1000.0, Double.valueOf(input[4]) * 1000.0,  Double.valueOf(input[5]) * 1000.0));
            jupiter.setRadius(Double.valueOf(input[6]) * 1000.0);
            jupiter.setMass(Double.valueOf(input[7]));
            jupiter.setId("Jupiter");

            input = br.readLine().split(" ");
            saturn = new Particle(new Vector(Double.valueOf(input[0]) * 1000.0, Double.valueOf(input[1]) * 1000.0, Double.valueOf(input[2]) * 1000.0));
            saturn.setVelocity(new Vector(Double.valueOf(input[3]) * 1000.0, Double.valueOf(input[4]) * 1000.0,  Double.valueOf(input[5]) * 1000.0));
            saturn.setRadius(Double.valueOf(input[6]) * 1000.0);
            saturn.setMass(Double.valueOf(input[7]));
            saturn.setId("Saturn");

            input = br.readLine().split(" ");
            uranus = new Particle(new Vector(Double.valueOf(input[0]) * 1000.0, Double.valueOf(input[1]) * 1000.0, Double.valueOf(input[2]) * 1000.0));
            uranus.setVelocity(new Vector(Double.valueOf(input[3]) * 1000.0, Double.valueOf(input[4]) * 1000.0,  Double.valueOf(input[5]) * 1000.0));
            uranus.setRadius(Double.valueOf(input[6]) * 1000.0);
            uranus.setMass(Double.valueOf(input[7]));
            uranus.setId("Uranus");

            input = br.readLine().split(" ");
            neptune = new Particle(new Vector(Double.valueOf(input[0]) * 1000.0, Double.valueOf(input[1]) * 1000.0, Double.valueOf(input[2]) * 1000.0));
            neptune.setVelocity(new Vector(Double.valueOf(input[3]) * 1000.0, Double.valueOf(input[4]) * 1000.0,  Double.valueOf(input[5]) * 1000.0));
            neptune.setRadius(Double.valueOf(input[6]) * 1000.0);
            neptune.setMass(Double.valueOf(input[7]));
            neptune.setId("Neptune");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Particle getVenus() {
        return venus;
    }

    public Particle getMercury() {
        return mercury;
    }

    public Particle getSun() {
        return this.sun.clone();
    }

    public Particle getEarth() {
        return this.earth.clone();
    }

    public Particle getMars() {
        return this.mars.clone();
    }

    public Particle getJupiter() {
        return jupiter;
    }

    public Particle getSaturn() {
        return saturn;
    }

    public Particle getUranus() {
        return uranus;
    }

    public Particle getNeptune() {
        return neptune;
    }
}
