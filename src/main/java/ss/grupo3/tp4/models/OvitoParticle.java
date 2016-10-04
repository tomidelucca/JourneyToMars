package ss.grupo3.tp4.models;

import ss.grupo3.tp4.models.Color;
import ss.grupo3.tp4.models.Particle;

public class OvitoParticle {
    private Particle particle;
    private Double radius;
    private Color color;

    public OvitoParticle(Particle particle) {
        this.particle = particle;
    }

    public OvitoParticle(Particle particle, Double radius, Color color) {
        this.particle = particle;
        this.radius = radius;
        this.color = color;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector getPosition() {
        return particle.getPosition();
    }

    public Vector getVelocity() {
        return particle.getVelocity();
    }

    public Double getMass() {
        return particle.getMass();
    }
}
