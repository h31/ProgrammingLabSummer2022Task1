import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;

public class Vector3 {

    private static final double equivalenceDelta = 1e-13;

    private double x, y, z;

    public Vector3(){
        setX(1);
        setY(1);
        setZ(1);
    }

    public Vector3(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public Vector3(@NotNull Vector3 vector) {
        setX(vector.x);
        setY(vector.y);
        setZ(vector.z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        validateInput(x);
        this.x = x;
    }

    public void setY(double y) {
        validateInput(y);
        this.y = y;
    }

    public void setZ(double z) {
        validateInput(z);
        this.z = z;
    }

    private static void validateInput(double value) {
        if (Double.isNaN(value)) {
            throw new InvalidParameterException("NaN value is not allowed");
        }
        if (Double.isInfinite(value)) {
            throw new InvalidParameterException("Infinite value is not allowed");
        }
    }
    private static void validateVector(@NotNull Vector3 vector) {
        if (Double.isNaN(vector.x) || Double.isNaN(vector.y) || Double.isNaN(vector.z)) {
            throw new InvalidParameterException("NaN value is not allowed");
        }
        if (Double.isInfinite(vector.x) || Double.isInfinite(vector.y) || Double.isInfinite(vector.z)) {
            throw new InvalidParameterException("Infinite value is not allowed");
        }
    }
    /**
     * Returns vector of sum of two given
     * @param v1 Vector3
     * @param v2 Vector3
     * @return Vector3
     */
    public static Vector3 plus (@NotNull Vector3 v1, @NotNull Vector3 v2) {
        return new Vector3(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
    }

    /**
     * Returns sum of the vector with given
     * @param other Vector3
     * @return Vector3
     */
    public Vector3 plus (@NotNull Vector3 other) {
        return plus(this, other);
    }

    /**
     * Returns magnitude of given vector
     * @param vector Vector3
     * @return double
     */
    public static double magnitude(@NotNull Vector3 vector) {
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();

        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Returns magnitude of the vector
     * @return double
     */
    public double magnitude() {
        return magnitude(this);
    }

    /**
     * Returns vector multiplied by scalar a
     * @param vector Vector3
     * @param a double
     * @return Vector3
     */
    public static Vector3 multiplied(@NotNull Vector3 vector, double a) {
        return new Vector3(vector.getX() * a, vector.getY() * a, vector.getZ() * a);
    }

    /**
     * Returns vector multiplied by scalar a
     * @param a double
     * @return double
     */
    public Vector3 multiplied(double a) {
        return multiplied(this, a);
    }

    /**
     * Multiplies vector with scalar a
     * @param a double
     */
    public void multiply(double a) {
        setX(getX() * a);
        setY(getY() * a);
        setZ(getZ() * a);
    }

    /**
     * Returns dot product of two given vectors
     * @param v1 Vector3
     * @param v2 Vector3
     * @return double
     */
    public static double dotProduct(@NotNull Vector3 v1, @NotNull Vector3 v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    /**
     * Returns dot product of two given vectors
     * @param other Vector3
     * @return double
     */
    public double dotProduct(@NotNull Vector3 other) {
        return dotProduct(this, other);
    }

    /**
     * Returns cross product of two given vectors
     * @param v1 Vector3
     * @param v2 Vector3
     * @return double
     */
    public static Vector3 crossProduct(@NotNull Vector3 v1, @NotNull Vector3 v2) {
        return new Vector3(
                v1.getY() * v2.getZ() - v1.getZ() * v2.getY(),
                v1.getZ() * v2.getX() - v1.getX() * v2.getZ(),
                v1.getX() * v2.getY() - v1.getY() * v2.getX());
    }

    /**
     * Returns cross product of two given vectors
     * @param other Vector3
     * @return double
     */
    public Vector3 crossProduct(@NotNull Vector3 other) {
        return crossProduct(this, other);
    }

    /**
     * Returns normalized vector
     * @param vector Vector3
     * @return Vector3
     */
    public static Vector3 normalized(@NotNull Vector3 vector) {
        return vector.multiplied(1.0 / vector.magnitude());
    }

    /**
     * Returns normalized vector
     * @return Vector3
     */
    public Vector3 normalized() {
        return normalized(this);
    }

    /**
     * Normalizes the vector
     */
    public void normalize() {
        Vector3 res = this.normalized();
        setX(res.getX());
        setY(res.getY());
        setZ(res.getZ());
    }

    /**
     * Returns result of multiplication of given vector with quaternion
     * @param vector Vector3
     * @param quaternion Quaternion
     * @return Quaternion
     */
    public static Quaternion scaled(@NotNull Vector3 vector, @NotNull Quaternion quaternion) {
        return new Quaternion(0, vector).multiplied(quaternion);
    }

    /**
     * Returns result of multiplication of given vector with quaternion
     * @param quaternion Quaternion
     * @return Quaternion
     */
    public Quaternion scaled(@NotNull Quaternion quaternion) {
        return scaled(this, quaternion);
    }

    /**
     * Returns rotated by given quaternion vector
     * @param vector Vector3
     * @param quaternion Quaternion
     * @return Vector3
     */
    public static Vector3 rotated(@NotNull Vector3 vector, @NotNull Quaternion quaternion) {
        Quaternion q = new Quaternion(quaternion.getW(), quaternion.getVector());
        return q.multiplied(vector).multiplied(q.reciprocal()).getVector();
    }

    /**
     * Returns rotated by given quaternion vector
     * @param quaternion Quaternion
     * @return Vector3
     */
    public Vector3 rotated(@NotNull Quaternion quaternion) {
        return rotated(this, quaternion);
    }

    /**
     * Rotates vector by given quaternion
     * @param quaternion Quaternion
     */
    public void rotate(@NotNull Quaternion quaternion) {
        Vector3 res = this.rotated(quaternion);
        setX(res.getX());
        setY(res.getY());
        setZ(res.getZ());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != Vector3.class) {
            return false;
        }

        final Vector3 other = (Vector3) obj;

        validateVector(other);

        // Не упрощаю по предложению IDE, потому что кажется, что так понятнее
        if (Math.abs(getX() - other.getX()) > equivalenceDelta ||
                Math.abs(getY() - other.getY()) > equivalenceDelta ||
                Math.abs(getZ() - other.getZ()) > equivalenceDelta) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
