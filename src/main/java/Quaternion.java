import org.jetbrains.annotations.*;

import java.lang.invoke.VarHandle;
import java.security.InvalidParameterException;

/* Артём Олегович, здравствуйте!

* Работа выполнена, даже чрезмерно и у меня есть к Вам вопрос,
* касаемый принятых норм в написании классов:
* сейчас у меня в одном классе мешанина из статических методов,
* методов, возвращающие отредактированные копии, и
* методов, преобразующие данные объекты. Скажите, насколько правильно
* сохранять такую структуру класса и как правильно распределить всё,
* если это мовитон?
* */

public class Quaternion {

    private static final double equivalenceDelta = 1e-13;
    private double w;
    private Vector3 xyz;

    public Quaternion() {
        setW(0);
        setVector(new Vector3());
    }

    public Quaternion(double w, double x, double y, double z) {
        setW(w);
        setVector(new Vector3(x, y, z));
    }

    public Quaternion(double w, Vector3 xyz) {
        setW(w);
        setVector(xyz);
    }

    public Quaternion(@NotNull Quaternion quaternion) {
        setW(quaternion.getW());
        setVector(quaternion.getVector());
    }

    public Quaternion(@NotNull Vector3 axis, double angle) {
        setVector(axis.normalized().multiplied(Math.sin(angle / 2.0)));
        setW(Math.cos(angle / 2.0));
    }

    public double getW() {
        return w;
    }

    public double getX() { return getVector().getX(); }

    public double getY() { return getVector().getY(); }

    public double getZ() { return getVector().getZ(); }

    public Vector3 getVector() {
        return xyz;
    }

    public void setW(double w) {
        validateInput(w);
        this.w = w;
    }

    public void setX(double x) { this.xyz.setX(x); }

    public void setY(double y) { this.xyz.setY(y); }

    public void setZ(double z) { this.xyz.setZ(z); }

    public void setVector(Vector3 xyz) {
        this.xyz = new Vector3(xyz);
    }

    private static void validateInput(double value) {
        if (Double.isNaN(value)) {
            throw new InvalidParameterException("NaN value is not allowed");
        }
        if (Double.isInfinite(value)) {
            throw new InvalidParameterException("Infinite value is not allowed");
        }
    }

    public static double getAngleFromW(double w) {
        return Math.acos(w) * 2;
    }

    public double getAngle() {
        return getAngleFromW(this.getW());
    }

    /**
     * Returns Axis of rotations. If no rotation specified, returns zero-vector
     * @param quaternion Quaternion
     * @return Vector3
     */
    public static Vector3 getAxis(@NotNull Quaternion quaternion) {
        var sinus = Math.sin(quaternion.getAngle() / 2.0);
        if (sinus == 0)
            return new Vector3(0, 0, 0);
        return quaternion.getVector().multiplied(1.0 / sinus).normalized();
    }

    /**
     * Returns Axis of rotations. If no rotation specified, returns zero-vector
     * @return Vector3
     */
    public Vector3 getAxis() {
        return getAxis(this);
    }

    /**
     * Returns sum of two quaternions
     * @param first Quaternion
     * @param second Quaternion
     * @return Quaternion
     */
    public static Quaternion plus(@NotNull Quaternion first, @NotNull Quaternion second) {
        return new Quaternion(first.getW() + second.getW(), first.getVector().plus(second.getVector()));
    }

    /**
     * Returns sum of two quaternions
     * @param other Quaternion
     * @return Quaternion
     */
    public Quaternion plus(@NotNull Quaternion other) {
        return plus(this, other);
    }

    /**
     * Returns difference between two Quaternions
     * @param first Quaternion
     * @param second Quaternion
     * @return Quaternion
     */
    public static Quaternion minus(@NotNull Quaternion first, @NotNull Quaternion second) {
        return plus(first, second.multiplied(-1));
    }

    /**
     * Returns difference between two Quaternions
     * @param other Quaternion
     * @return Quaternion
     */
    public Quaternion minus(@NotNull Quaternion other) {
        return minus(this, other);
    }

    /**
     * Returns norm of given Quaternion
     * @param quaternion Quaternion
     * @return Quaternion
     */
    public static double norm(@NotNull Quaternion quaternion) {
        final double w = quaternion.getW();
        final double vectorMagn = quaternion.getVector().magnitude();
        return Math.sqrt(w * w + vectorMagn * vectorMagn);
    }

    /**
     * Returns norm of given Quaternion
     * @return Quaternion
     */
    public double norm() {
        return norm(this);
    }

    /**
     *
     * @param quaternion Quaternion
     * @exception InvalidParameterException if zero-quaternion was provided
     * @return Quaternion
     */
    public static Quaternion reciprocal(@NotNull Quaternion quaternion) {
        if (quaternion.norm() == 0) {
            throw new InvalidParameterException("Unable to get reciprocal of zero quaternion");
        }
        return quaternion.conjugated().multiplied(1.0 / (quaternion.norm() * quaternion.norm()));
    }

    /**
     * Returns reciprocal to given quaternion
     * @return Quaternion
     */
    public Quaternion reciprocal() {
        return reciprocal(this);
    }

    /**
     * Returns Quaternion, represented by multiplication quaternion onto scalar a
     * @param quaternion Quaternion
     * @param a double
     * @return Quaternion
     */
    public static Quaternion multiplied(@NotNull Quaternion quaternion, double a) {
        Quaternion res = new Quaternion(quaternion);
        res.setW(res.getW() * a);
        res.setVector(res.getVector().multiplied(a));
        return res;
    }

    /**
     * Returns Quaternion, represented by multiplication quaternion onto scalar a
     * @param a double
     * @return Quaternion
     */
    public Quaternion multiplied(double a) {
        return multiplied(this, a);
    }

    /**
     * Multiplies quaternion by scalar a
     * @param a double
     */
    public void multiply(double a) {
        setW(getW() * a);
        setVector(getVector().multiplied(a));
    }

    /**
     * Returns Quaternion, represented by multiplication of two quaternions
     * @param first Quaternion
     * @param second Quaternion
     * @return Quaternion
     */
    public static Quaternion multiplied(@NotNull Quaternion first, @NotNull Quaternion second) {

        final double w1 = first.getW(), w2 = second.getW();
        final Vector3 v1 = first.getVector(), v2 = second.getVector();

        return new Quaternion(
                w1 * w2 - v1.dotProduct(v2),
                v2.multiplied(w1).plus(v1.multiplied(w2)).plus(v1.crossProduct(v2))
        );
    }

    /**
     * Returns Quaternion, represented by multiplication of two quaternions
     * @param other Quaternion
     * @return Quaternion
     */
    public Quaternion multiplied(@NotNull Quaternion other) {
        return multiplied(this, other);
    }

    /**
     * Multiplies quaternion by other one
     * @param other Quaternion
     */
    public void multiply(@NotNull Quaternion other) {
        final double w1 = getW(), w2 = other.getW();
        final Vector3 v1 = getVector(), v2 = other.getVector();

        setW(w1 * w2 - v1.dotProduct(v2));
        setVector(v2.multiplied(w1).plus(v1.multiplied(w2)).plus(v1.crossProduct(v2)));
    }


    /**
     * Returns Quaternion multiplied by given vector
     * @param quaternion Quaternion
     * @param vector Vector3
     * @return Quaternion
     */
    public static Quaternion multiplied(@NotNull Quaternion quaternion, @NotNull Vector3 vector) {
        return quaternion.multiplied(new Quaternion(0, vector));
    }

    /**
     * Returns Quaternion multiplied by given vector
     * @param vector Vector3
     * @return Quaternion
     */
    public Quaternion multiplied(@NotNull Vector3 vector) {
        return multiplied(this, vector);
    }

    /**
     * Multiplies quaternion by given vector
     * @param vector Vector3
     */
    public void multiply(@NotNull Vector3 vector) {
        multiply(new Quaternion(0, vector));
    }

    /**
     * Returns quaternion conjugated to given
     * @param quaternion Quaternion
     * @return Quaternion
     */
    public static Quaternion conjugated(@NotNull Quaternion quaternion) {
        return new Quaternion(quaternion.getW(), quaternion.getVector().multiplied(-1));
    }

    /**
     * Returns conjugated quaternion
     * @return Quaternion
     */
    public Quaternion conjugated() {
        return conjugated(this);
    }

    public void conjugate() {
        setVector(getVector().multiplied(-1));
    }

    /**
     * Returns same Quaternion with norm = 1
     * @param quaternion Quaternion
     * @return Quaternion
     */
    public static Quaternion normalized (@NotNull Quaternion quaternion) {
        if (quaternion.norm() == 0) {
            return quaternion;
        }

        return quaternion.multiplied(1.0 / quaternion.norm());
    }

    /**
     * Returns same Quaternion with norm = 1
     * @return Quaternion
     */
    public Quaternion normalized () {
        return normalized(this);
    }

    /**
     * Converts Quaternion to same with norm = 1
     */
    public void normalize() {
        Quaternion res = normalized(this);
        setW(res.getW());
        setVector(res.getVector());
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
        if (Math.abs(getW() - other.getW()) > equivalenceDelta || !getVector().equals(other.getVector())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        double w = getW();
        double x = getX();
        double y = getY();
        double z = getZ();

        sb.append("Quaternion: ");
        sb.append(w);
        if (x < 0) {
            sb.append(" - ");
        } else sb.append(" + ");
        sb.append(Math.abs(getX()));

        if (y < 0) {
            sb.append("i - ");
        } else sb.append("i + ");
        sb.append(Math.abs(getY()));

        if (z < 0) {
            sb.append("j - ");
        } else sb.append("j + ");
        sb.append(Math.abs(getZ()));
        sb.append("k");

        return sb.toString();
    }
}
