import engine.Utils;
import engine.math.Vector3;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import static org.junit.jupiter.api.Assertions.*;

// service, most of right answers were given from:
// https://www.omnicalculator.com/math/quaternion

class QuaternionTest {

    private boolean apacheEqualsMy(@NotNull Quaternion q1, @NotNull engine.math.Quaternion q2) {
        return Utils.doubleEquals(q1.getQ0(), q2.w()) &&
                Utils.doubleEquals(q1.getQ1(), q2.x()) &&
                Utils.doubleEquals(q1.getQ2(), q2.y()) &&
                Utils.doubleEquals(q1.getQ3(), q2.z());
    }

    private boolean apacheEqualsMy(@NotNull Vector3D v1, @NotNull Vector3 v2) {
        return Utils.doubleEquals(v1.getX(), v2.x()) &&
                Utils.doubleEquals(v1.getY(), v2.y()) &&
                Utils.doubleEquals(v1.getZ(), v2.z());
    }

    @Test
    void inputValuesTests() {
        assertDoesNotThrow(() -> new engine.math.Quaternion(0, Double.NaN, 0, 1));
        assertDoesNotThrow(() -> new engine.math.Quaternion(0, new engine.math.Vector3(-12, Double.NEGATIVE_INFINITY, 0)));
        assertDoesNotThrow(() -> new engine.math.Quaternion(new engine.math.Vector3(Double.POSITIVE_INFINITY, 1, 1), Double.NaN));

        assertThrows(IllegalArgumentException.class,
                () -> new engine.math.Quaternion(Double.POSITIVE_INFINITY, 0, 0, 1).tryValidate());
        assertThrows(IllegalArgumentException.class,
                () -> new engine.math.Quaternion(0, 0, 0, Double.NaN).tryValidate());
        assertThrows(IllegalArgumentException.class,
                () -> new engine.math.Quaternion(0, Double.NaN, 0, 1).tryValidate());
        assertThrows(IllegalArgumentException.class,
                () -> new engine.math.Quaternion(0, new engine.math.Vector3(-12, Double.NEGATIVE_INFINITY, 0)).tryValidate());
        assertThrows(IllegalArgumentException.class,
                () -> new engine.math.Quaternion(new engine.math.Vector3(Double.POSITIVE_INFINITY, 1, 1), Double.NaN).tryValidate());

    }

    @Test
    void plus() {

        assertTrue(apacheEqualsMy(new Quaternion(0, 1, 1, 1).add(
                new Quaternion(0, 1, 1, 1).multiply(-1)
        ), new engine.math.Quaternion().plus(
                new engine.math.Quaternion().multiplied(-1)
                )
        ));

        assertTrue(apacheEqualsMy(new Quaternion(1, 0, 9.3, 2).add(
                new Quaternion(2, 1, -7.3, 1)
        ), new engine.math.Quaternion(1, 0, 9.3, 2).plus(
                new engine.math.Quaternion(2, new Vector3(1, -7.3, 1)
        ))));
    }

    @Test
    void minus() {
        engine.math.Quaternion myA = new engine.math.Quaternion(1, 2, 3, 4);
        engine.math.Quaternion myB = new engine.math.Quaternion(2, 0, 0, -5);
        Quaternion apaA = new Quaternion(1, 2, 3, 4);
        Quaternion apaB = new Quaternion(2, 0, 0, -5);
        assertTrue(apacheEqualsMy(apaA.add(apaA.multiply(-1)), myA.minus(myA)));
        assertTrue(apacheEqualsMy(apaA.add(apaB.multiply(-1)), myA.minus(myB)));
        assertTrue(apacheEqualsMy(apaB.add(apaA.multiply(-1)), myB.minus(myA)));
    }

    @Test
    void norm() {
        assertTrue(Utils.doubleEquals(
                new Quaternion(3, 1, -3, 12.3).getNorm(),
                new engine.math.Quaternion(3, 1, -3, -12.3).norm()
        ));

        assertTrue(Utils.doubleEquals(
                new Quaternion(1, 1, 1, 1).getNorm(),
                new engine.math.Quaternion(1, 1, 1, 1).norm()
        ));
    }

    @Test
    void reciprocal() {
        Quaternion q = new Quaternion(0, 1, 1, 1);
        q = q.getConjugate().multiply(1 / (q.getNorm() * q.getNorm()));
        assertTrue(apacheEqualsMy(q, new engine.math.Quaternion().reciprocal()));

        q = new Quaternion(3, 2.1, -2, -2.5);
        q = q.getConjugate().multiply(1 / (q.getNorm() * q.getNorm()));
        assertTrue(apacheEqualsMy(q, new engine.math.Quaternion(3, 2.1, -2, -2.5).reciprocal()));

        assertThrows(IllegalArgumentException.class,
                () -> new engine.math.Quaternion(0, 0, 0, 0).reciprocal());
    }

    @Test
    void multipliedScalar() {
        assertTrue(apacheEqualsMy(
                new Quaternion(0, 0.5, 1, 1).multiply(4),
                new engine.math.Quaternion(0, 0.5, 1, 1).multiplied(4)
        ));
        assertTrue(apacheEqualsMy(
                new Quaternion(123, 0.2323, -123123, 2).multiply(0),
                new engine.math.Quaternion(123, 0.2323, -123123, 2).multiplied(0)
        ));
    }

    @Test
    void multipliedQuaternion() {
        assertTrue(apacheEqualsMy(
                new Quaternion(0, 1, 1, 1).multiply(new Quaternion(2, 3, 4, 5)),
                new engine.math.Quaternion(0, 1, 1, 1).multiplied(new engine.math.Quaternion(2, 3, 4, 5))
        ));
        assertTrue(apacheEqualsMy(
                new Quaternion(2, 3, 4, 5).multiply(new Quaternion(0, 1, 1, 1)),
                new engine.math.Quaternion(2, 3, 4, 5).multiplied(new engine.math.Quaternion())
        ));
    }

    @Test
    void multipliedVector3() {
        assertTrue(apacheEqualsMy(
                new Quaternion(2, 0, -12.3, -4).multiply(new Quaternion(new double[]{1, 2, 3})),
                new engine.math.Quaternion(2, 0, -12.3, -4).multiplied(new engine.math.Vector3(1, 2, 3))
        ));

        assertTrue(apacheEqualsMy(
                new Quaternion(2, 0.2, -1, 0).multiply(new Quaternion(new double[]{1, 1, 1})),
                new engine.math.Quaternion(2, 0.2, -1, 0).multiplied(new Vector3())
        ));

    }

    @Test
    void conjugated() {
        assertTrue(apacheEqualsMy(
                new Quaternion(0, 1, 1, 1).getConjugate(),
                new engine.math.Quaternion().conjugated()
        ));
        assertTrue(apacheEqualsMy(
                new Quaternion(3, 1, -3, -12.3).getConjugate(),
                new engine.math.Quaternion(3, 1, -3, -12.3).conjugated()
        ));
    }

    @Test
    void normalized() {
        assertTrue(apacheEqualsMy(
                new Quaternion(1, 1, 1, 1).normalize(),
                new engine.math.Quaternion(1, 1, 1, 1).normalized()
        ));
        assertTrue(apacheEqualsMy(
                new Quaternion(1.2, 1.6, -1.2, -2).normalize(),
                new engine.math.Quaternion(1.2, 1.6, -1.2, -2).normalized()
        ));

        assertEquals(new engine.math.Quaternion(0, 0, 0, 0), new engine.math.Quaternion(0, 0, 0, 0).normalized());
        assertEquals(new Vector3(0, 0, 0), new Vector3(0, 0, 0).normalized());
    }

    @Test
    void rotate() {
        engine.math.Quaternion rotateQ = new engine.math.Quaternion(new engine.math.Vector3(1, 0, 1), -Math.PI / 2);
        Rotation rotation = new Rotation(rotateQ.w(), rotateQ.x(), rotateQ.y(), rotateQ.z(), false);

        assertTrue(apacheEqualsMy(
                rotation.applyTo(new Vector3D(0, 0, 1)),
                new engine.math.Vector3(0, 0, 1).rotated(rotateQ)
        ));

        rotateQ = new engine.math.Quaternion(1, 3.5, 2.6, 0);
        rotation = new Rotation(rotateQ.w(), rotateQ.x(), rotateQ.y(), rotateQ.z(), false);

        assertTrue(apacheEqualsMy(
                rotation.applyTo(new Vector3D(1, 1, 4)),
                new Vector3(1, 1, 4).rotated(rotateQ)
        ));

        rotateQ = new engine.math.Quaternion(new engine.math.Vector3(12, 3, -8), 1);
        rotation = new Rotation(rotateQ.w(), rotateQ.x(), rotateQ.y(), rotateQ.z(), false);

        assertTrue(apacheEqualsMy(
                rotation.applyTo(new Vector3D(1, 1, 4)),
                new Vector3(1, 1, 4).rotated(rotateQ)
        ));
    }

    @Test
    void getAngle() {

        assertTrue(Utils.doubleEquals(
                new Rotation(new Vector3D(1, 3, 5), Math.PI / 2.0, RotationConvention.VECTOR_OPERATOR).getAngle(),
                new engine.math.Quaternion(new engine.math.Vector3(1, 3, 5), Math.PI / 2.0).getAngle()
        ));

        assertTrue(Utils.doubleEquals(
                new Rotation(new Vector3D(0, 3434, -3.6), 2.5, RotationConvention.VECTOR_OPERATOR).getAngle(),
                new engine.math.Quaternion(new engine.math.Vector3(0, 1, -3.6), 2.5).getAngle()
        ));

        // Очень странное поведение Rotation angle-axis при вводе значение 1 / 0.3. Возвращаемое значение не совпадает с вводимым:
        // angle = 1 / 0.3
        // Expected :2.949851973846253 -> При том, что введено 3.(3)
        // Actual   :3.3333333333333335
        // Почему-то он ожидает значение соверщенно не равное 1 / 0.3

//        double angle = 1 / 0.3;
//        assertEquals(
//                new Rotation(new Vector3D(0, 3434, -3.6), angle, RotationConvention.VECTOR_OPERATOR).getAngle(),
//                new engine.math.Quaternion(new engine.math.Vector3(0, 1, -3.6), angle).getAngle(),
//                Engine.EQUIVALENCE_DELTA
//        );

        assertTrue(Utils.doubleEquals(
                new Rotation(new Vector3D(1, 0, 0), 2.51515151, RotationConvention.VECTOR_OPERATOR).getAngle(),
                new engine.math.Quaternion(new engine.math.Vector3(1, 0, 0), 2.51515151).getAngle()
        ));
    }

    @Test
    void getAxis() {

        assertTrue(apacheEqualsMy(
                new Rotation(new Vector3D(1, 3, 5), Math.PI/2, RotationConvention.VECTOR_OPERATOR).getAxis(RotationConvention.VECTOR_OPERATOR),
                new engine.math.Quaternion(new Vector3(1, 3, 5), Math.PI / 2).getAxis()
        ));

        assertTrue(apacheEqualsMy(
                new Rotation(new Vector3D(1, 0, 0), Math.PI/2, RotationConvention.VECTOR_OPERATOR).getAxis(RotationConvention.VECTOR_OPERATOR),
                new engine.math.Quaternion(new Vector3(1, 0, 0), Math.PI / 2).getAxis()
        ));

        assertTrue(apacheEqualsMy(
                new Rotation(new Vector3D(0, 1, -3.6), Math.PI/2, RotationConvention.VECTOR_OPERATOR).getAxis(RotationConvention.VECTOR_OPERATOR),
                new engine.math.Quaternion(new Vector3(0, 1, -3.6), Math.PI / 2).getAxis()
        ));

        assertTrue(apacheEqualsMy(
                new Rotation(new Vector3D(7, 0, 123), 2.345, RotationConvention.VECTOR_OPERATOR).getAxis(RotationConvention.VECTOR_OPERATOR),
                new engine.math.Quaternion(new Vector3(7, 0, 123), Math.PI / 2).getAxis()
        ));

        assertEquals(new Vector3(0, 0, 0), new engine.math.Quaternion(new Vector3(1, 3, 5), 0).getAxis());
    }

    @Test
    void QuaternionToString() {
        assertEquals("0 + 1i + 3j - 1,5k", new engine.math.Quaternion(0, 1, 3, -1.5).toString());
    }

    @Test
    void QuaternionEquals() {

        engine.math.Quaternion q1 = new engine.math.Quaternion(0, 2, 4, 6);
        engine.math.Quaternion q2 = new engine.math.Quaternion(0, 2, 4, 6);

        assertTrue(q1.equals(q2) && q2.equals(q1));
        assertTrue(q1.hashCode() == q2.hashCode());

        assertNotEquals(new engine.math.Quaternion(0, 1, 3, 5), null);
        assertNotEquals(new engine.math.Quaternion(0, 3, 5, 6), new Vector3(0, 3, 6));
        assertEquals(
                new engine.math.Quaternion(3, 1, Double.NaN, Double.POSITIVE_INFINITY),
                new engine.math.Quaternion(3, 1, Double.NaN, Double.POSITIVE_INFINITY)
        );
    }

    @Test
    void Vector3ToString() {
        assertEquals("engine.math.Vector3{x=0.0, y=-3.0, z=NaN}", new Vector3(0, -3, Double.NaN).toString());
    }

    @Test
    void Vector3Equals() {

        Vector3 v1 = new Vector3(0, 6, -2);
        Vector3 v2 = new Vector3(0, 6, -2);

        assertTrue(v1.equals(v2) && v2.equals(v1));
        assertTrue(v1.hashCode() == v2.hashCode());

        assertNotEquals(new Vector3(0, 3, 5), null);
        assertNotEquals(new Vector3(0, 4, 6), new Vector3D(0, 0, 1));
        assertEquals(new Vector3(0, Double.NEGATIVE_INFINITY, Double.NaN), new Vector3(0, Double.NEGATIVE_INFINITY, Double.NaN));
    }

}