package ss.grupo3.tp4.simulation;

public interface SimulationObserver {
    void notify(Simulation simulation);
    void simulationDidFinish(Simulation simulation);
}
