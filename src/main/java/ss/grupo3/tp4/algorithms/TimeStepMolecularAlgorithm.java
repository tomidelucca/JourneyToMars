package ss.grupo3.tp4.algorithms;

import ss.grupo3.tp4.models.Particle;

import java.util.Collection;

public interface TimeStepMolecularAlgorithm {
    void updateVelocities(Particle p, Collection<Particle> bodies, Double dt);
    void updatePosition(Particle p, double dt);
}
