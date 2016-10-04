package ss.grupo3.tp4.algorithms;

import ss.grupo3.tp4.models.Particle;
import ss.grupo3.tp4.models.Vector;

import java.util.Collection;

public class LeapFrogAlgorithm implements TimeStepMolecularAlgorithm {

    @Override
    public void updateVelocities(Particle p, Collection<Particle> bodies, Double dt) {

        Vector forces = sumForces(p, bodies);
        Vector oldVelocity = p.getVelocity();

        p.getVelocity().setX(oldVelocity.getX() + (dt/p.getMass())*forces.getX());
        p.getVelocity().setY(oldVelocity.getY() + (dt/p.getMass())*forces.getY());

    }

    @Override
    public void updatePosition(Particle p, double dt) {

        Vector oldPosition = p.getPosition();

        p.getPosition().setX(oldPosition.getX() + dt*p.getVelocity().getX());
        p.getPosition().setY(oldPosition.getY() + dt*p.getVelocity().getY());

    }

    public static Vector sumForces(Particle p, Collection<Particle> bodies) {

        Double forcesX = 0.0;
        Double forcesY = 0.0;

        for(Particle otherParticle: bodies) {
            if(!otherParticle.equals(p)) {
                Vector force = gravitationalForce(p, otherParticle);
                forcesX += force.getX();
                forcesY += force.getY();
            }
        }

        return new Vector(forcesX, forcesY);
    }

    private static Vector gravitationalForce(Particle p1, Particle p2) {

        Double G = 6.693E-11;

        Double rij = Math.sqrt(Math.pow(p2.getPosition().getX() - p1.getPosition().getX(), 2) + Math.pow(p2.getPosition().getY() - p1.getPosition().getY(), 2));
        Double ei = (p2.getPosition().getX() - p1.getPosition().getX())/rij;
        Double ej = (p2.getPosition().getY() - p1.getPosition().getY())/rij;

        Double Fi = G*((p1.getMass()*p2.getMass())/Math.pow(rij, 2))*ei;
        Double Fj = G*((p1.getMass()*p2.getMass())/Math.pow(rij, 2))*ej;

        return new Vector(Fi, Fj);
    }
}
