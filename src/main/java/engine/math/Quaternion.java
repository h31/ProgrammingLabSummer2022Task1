package engine.math;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.jetbrains.annotations.*;

import java.security.InvalidParameterException;
import java.text.DecimalFormat;

import engine.Engine;

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

    public Quaternion(@NotNull Quaternion quaternion) {
        this(quaternion.w, quaternion.xyz);
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
     * @exception InvalidParameterException if one of the values of quaternion is NaN or Infinite
     */
    public void tryValidate() {
        if (Double.isNaN(this.w())) {
            throw new InvalidParameterException("NaN value is not allowed");
        }
        if (Double.isInfinite(this.w())) {
            throw new InvalidParameterException("Infinite value is not allowed");
        }

        this.xyz().tryValidate();

    }

    public double getAngle() {
        return Math.acos(this.w()) * 2;
    }

    /**
     * Returns Axis of rotations. If no rotation specified, returns zero-vector
     *
     * @return engine.math.Vector3
     */
    public Vector3 getAxis() {
        var sinus = Math.sin(this.getAngle() * 0.5);
        if (sinus == 0)
            return new Vector3(0, 0, 0);
        return this.xyz().multiplied(1.0 / sinus).normalized();
    }

    /**
     * Returns sum of two quaternions
     *
     * @param other Quaternion
     * @return Quaternion
     */
    public Quaternion plus(@NotNull Quaternion other) {
        return new Quaternion(this.w() + other.w(), this.xyz().plus(other.xyz()));
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
        final double w = this.w();
        final double vectorMagn = this.xyz().magnitude();
        return Math.sqrt(w * w + vectorMagn * vectorMagn);
    }

    /**
     * Returns reciprocal to given quaternion
     *
     * @return Quaternion
     */
    public Quaternion reciprocal() {
        final double module = this.norm();
        if (module == 0) {
            throw new InvalidParameterException("Unable to get reciprocal of zero quaternion");
        }
        return this.conjugated().multiplied(1.0 / (module * module));
    }

    /**
     * Returns Quaternion, represented by multiplication quaternion onto scalar a
     *
     * @param a double
     * @return Quaternion
     */
    public Quaternion multiplied(double a) {
        return new Quaternion(this.w() * a, this.xyz().multiplied(a));
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
        return new Quaternion(this.w(), this.xyz().multiplied(-1));
    }

    /**
     * Returns same Quaternion with norm = 1
     * If zero-quaternion provided - returns it back
     *
     * @return Quaternion
     */
    public Quaternion normalized() {
        if (this.norm() == 0) {
            return this;
        }
        return this.multiplied(1.0 / this.norm());
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

        // Не упрощаю по предложению IDE, потому что кажется, что так понятнее
        if (Math.abs(w() - other.w()) > Engine.EQUIVALENCE_DELTA ||
                !xyz().equals(other.xyz())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        String w = new DecimalFormat("#.######;- #.######").format(this.w());
        String x = new DecimalFormat("+ #.######;- #.######").format(this.x());
        String y = new DecimalFormat("+ #.######;- #.######").format(this.y());
        String z = new DecimalFormat("+ #.######;- #.######").format(this.z());

        return String.format("%s %si %sj %sk", w, x, y, z);
    }
}
