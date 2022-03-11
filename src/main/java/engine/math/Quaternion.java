package engine.math;

import org.jetbrains.annotations.*;

import java.text.DecimalFormat;

import engine.Utils;

// Mutable record, that provides some extra functionality
public record Quaternion(double w, @NotNull Vector3 xyz) {

    /**
     * Creates Quaternion, that specifies no rotation around {1, 1, 1} Vector
     */
    public Quaternion() {
        this(0, new Vector3());
    }

    public Quaternion(double w, double x, double y, double z) {
        this(w, new Vector3(x, y, z));
    }

    public Quaternion(@NotNull Vector3 axis, double angle){
        this(
                Math.cos(angle * 0.5),
                axis.normalized().multiplied(Math.sin(angle * 0.5))
        );
    }

    public double w() {
        return w;
    }

    public double x() {
        return xyz().x();
    }

    public double y() {
        return xyz().y();
    }

    public double z() {
        return xyz().z();
    }

    public Vector3 xyz() {
        return xyz;
    }

    /**
     * Checks if the quaternion is valid.
     * @exception IllegalArgumentException if one of the values of quaternion is NaN or Infinite
     */
    public void tryValidate() {
        if (Double.isInfinite(w())) {
            throw new IllegalArgumentException("Infinite value is not allowed");
        }

        if (Double.isNaN(w())) {
            throw new IllegalArgumentException("NaN value is not allowed");
        }

        xyz().tryValidate();

    }

    public double getAngle() {
        return Math.acos(w()) * 2;
    }

    /**
     * Returns Axis of rotations. If no rotation specified, returns zero-vector
     *
     * @return engine.math.Vector3
     */
    public Vector3 getAxis() {
        var sinus = Math.sin(getAngle() * 0.5);
        if (sinus == 0)
            return new Vector3(0, 0, 0);
        return xyz().multiplied(1.0 / sinus).normalized();
    }

    /**
     * Returns sum of two quaternions
     *
     * @param other Quaternion
     * @return Quaternion
     */
    public Quaternion plus(@NotNull Quaternion other) {
        return new Quaternion(w() + other.w(), xyz().plus(other.xyz()));
    }

    /**
     * Returns difference between two Quaternions
     *
     * @param other Quaternion
     * @return Quaternion
     */
    public Quaternion minus(@NotNull Quaternion other) {
        return this.plus(other.multiplied(-1));
    }

    /**
     * Returns norm of given Quaternion
     *
     * @return Quaternion
     */
    public double norm() {
        final double w = w();
        final double vectorMagn = xyz().magnitude();
        return Math.sqrt(w * w + vectorMagn * vectorMagn);
    }

    /**
     * Returns reciprocal to given quaternion
     *
     * @return Quaternion
     */
    public Quaternion reciprocal() {
        final double module = norm();
        if (module == 0) {
            throw new IllegalArgumentException("Unable to get reciprocal of zero quaternion");
        }
        return conjugated().multiplied(1.0 / (module * module));
    }

    /**
     * Returns Quaternion, represented by multiplication quaternion onto scalar a
     *
     * @param a double
     * @return Quaternion
     */
    public Quaternion multiplied(double a) {
        return new Quaternion(w() * a, xyz().multiplied(a));
    }


    /**
     * Returns Quaternion, represented by multiplication of two quaternions
     *
     * @param other Quaternion
     * @return Quaternion
     */
    public Quaternion multiplied(@NotNull Quaternion other) {
        final double w1 = this.w(), w2 = other.w();
        final Vector3 v1 = this.xyz(), v2 = other.xyz();

        return new Quaternion(
                w1 * w2 - v1.dotProduct(v2),
                v2.multiplied(w1).plus(v1.multiplied(w2)).plus(v1.crossProduct(v2))
        );
    }


    /**
     * Returns Quaternion multiplied by given vector
     *
     * @param vector engine.math.Vector3
     * @return Quaternion
     */
    public Quaternion multiplied(@NotNull Vector3 vector) {
        return this.multiplied(new Quaternion(0, vector));
    }

    /**
     * Returns conjugated quaternion
     *
     * @return Quaternion
     */
    public Quaternion conjugated() {
        return new Quaternion(w(), xyz().multiplied(-1));
    }

    /**
     * Returns same Quaternion with norm = 1
     * If zero-quaternion provided - returns it back
     *
     * @return Quaternion
     */
    public Quaternion normalized() {
        if (norm() == 0) {
            return this;
        }
        return this.multiplied(1.0 / norm());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != Quaternion.class) {
            return false;
        }

        final Quaternion other = (Quaternion) obj;

        return Utils.doubleEquals(this.w(), other.w()) &&
                this.xyz().equals(other.xyz());
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(w());
        result = 31 * result + Double.hashCode(xyz.x());
        result = 31 * result + Double.hashCode(xyz.y());
        result = 31 * result + Double.hashCode(xyz.z());
        return result;
    }

    @Override
    public String toString() {
        String w = new DecimalFormat("#.######;- #.######").format(w());
        String x = new DecimalFormat("+ #.######;- #.######").format(x());
        String y = new DecimalFormat("+ #.######;- #.######").format(y());
        String z = new DecimalFormat("+ #.######;- #.######").format(z());

        return String.format("%s %si %sj %sk", w, x, y, z);
    }
}
