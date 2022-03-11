package engine.math;

import engine.Utils;
import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;

public record Vector3(double x, double y, double z) {

    public Vector3() {
        this(1, 1, 1);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    private static void validateNumber(double value) {
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("Infinite value is not allowed");
        }

        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("NaN value is not allowed");
        }
    }

    /**
     * Checks if the vector is valid.
     * @exception InvalidParameterException if one of the values of vector is NaN or Infinite
     */
    public void tryValidate() {
        validateNumber(x);
        validateNumber(y);
        validateNumber(z);
    }

    /**
     * Returns sum of the vector with given
     *
     * @param other engine.math.Vector3
     * @return engine.math.Vector3
     */
    public Vector3 plus(@NotNull Vector3 other) {
        return new Vector3(this.x() + other.x(), this.y() + other.y(), this.z() + other.z());
    }

    /**
     * Returns magnitude of the vector
     *
     * @return double
     */
    public double magnitude() {
        return Math.sqrt(x() * x() + y() * y() + z() * z());
    }

    /**
     * Returns vector multiplied by scalar a
     *
     * @param a double
     * @return double
     */
    public Vector3 multiplied(double a) {
        return new Vector3(x() * a, y() * a, z() * a);
    }

    /**
     * Returns dot product of two given vectors
     *
     * @param other engine.math.Vector3
     * @return double
     */
    public double dotProduct(@NotNull Vector3 other) {
        return this.x() * other.x() + this.y() * other.y() + this.z() * other.z();
    }

    /**
     * Returns cross product of two given vectors
     *
     * @param other engine.math.Vector3
     * @return double
     */
    public Vector3 crossProduct(@NotNull Vector3 other) {
        return new Vector3(
                this.y() * other.z() - this.z() * other.y(),
                this.z() * other.x() - this.x() * other.z(),
                this.x() * other.y() - this.y() * other.x());
    }

    /**
     * Returns normalized vector
     *
     * @return engine.math.Vector3
     */
    public Vector3 normalized() {
        final double mag = magnitude();
        if (mag == 0) {
            return this;
        }
        return this.multiplied(1.0 / mag);
    }

    /**
     * Returns rotated by given quaternion vector
     *
     * @param q Quaternion
     * @return engine.math.Vector3
     */
    public Vector3 rotated(@NotNull Quaternion q) {
        double a = q.w();
        double b = q.x();
        double c = q.y();
        double d = q.z();
        double dotProd = b * x + c * y + d * z;
        return new Vector3(
                2.0 * (a * (x() * a - (c * z() - d * y())) + dotProd * b) - x(),
                2.0 * (a * (y() * a - (d * x() - b * z())) + dotProd * c) - y(),
                2.0 * (a * (z() * a - (b * y() - c * x())) + dotProd * d) - z()
        );
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

        return Utils.doubleEquals(this.x(), other.x()) &&
                Utils.doubleEquals(this.y(), other.y()) &&
                Utils.doubleEquals(this.z(), other.z());
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(x());
        result = 31 * result + Double.hashCode(y());
        result = 31 * result + Double.hashCode(z());
        return result;
    }

    @Override
    public String toString() {
        return "engine.math.Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
