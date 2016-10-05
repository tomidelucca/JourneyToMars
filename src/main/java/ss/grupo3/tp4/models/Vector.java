package ss.grupo3.tp4.models;

public class Vector implements Cloneable {

    private Double x;
    private Double y;
    private Double z;

    public Vector(Double x, Double y) {
        this(x,y, 0.0);
    }

    public Vector(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double distanceToVector(Vector otherVector) {
        return Math.sqrt(Math.pow(getX()-otherVector.getX(), 2) +
                Math.pow(getY()-otherVector.getY(), 2) +
                Math.pow(getZ()-otherVector.getZ(), 2));
    }

    public Vector clone() {
        return new Vector(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        if (!x.equals(vector.x)) return false;
        if (!y.equals(vector.y)) return false;
        return z != null ? z.equals(vector.z) : vector.z == null;

    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        result = 31 * result + (z != null ? z.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + getX() + "; " + getY() + "; " + getZ() + ")";
    }
}
