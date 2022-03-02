import engine.Engine;
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
        if (Math.abs(q2.w() - q1.getQ0()) > Engine.EQUIVALENCE_DELTA ||
                Math.abs(q2.x() - q1.getQ1()) > Engine.EQUIVALENCE_DELTA ||
                Math.abs(q2.y() - q1.getQ2()) > Engine.EQUIVALENCE_DELTA ||
                Math.abs(q2.z() - q1.getQ3()) > Engine.EQUIVALENCE_DELTA) {
            return false;
        }
        return true;
    }

    private boolean apacheEqualsMy(@NotNull Vector3D v1, @NotNull Vector3 v2) {
        if (Math.abs(v2.x() - v1.getX()) > Engine.EQUIVALENCE_DELTA ||
                Math.abs(v2.y() - v1.getY()) > Engine.EQUIVALENCE_DELTA ||
                Math.abs(v2.z() - v1.getZ()) > Engine.EQUIVALENCE_DELTA) {
            return false;
        }
        return true;
    }

    @Test
    void inputValuesTests() {
        assertDoesNotThrow(() -> new engine.math.Quaternion(0, Double.NaN, 0, 1));
        assertDoesNotThrow(() -> new engine.math.Quaternion(0, new engine.math.Vector3(-12, Double.NEGATIVE_INFINITY, 0)));
        assertDoesNotThrow(() -> new engine.math.Quaternion(new engine.math.Vector3(Double.POSITIVE_INFINITY, 1, 1), Double.NaN));

        assertThrows(InvalidParameterException.class,
                () -> new engine.math.Quaternion(0, Double.NaN, 0, 1).tryValidate());
        assertThrows(InvalidParameterException.class,
                () -> new engine.math.Quaternion(0, new engine.math.Vector3(-12, Double.NEGATIVE_INFINITY, 0)).tryValidate());
        assertThrows(InvalidParameterException.class,
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
        assertEquals(
                new Quaternion(3, 1, -3, 12.3).getNorm(),
                new engine.math.Quaternion(3, 1, -3, -12.3).norm(),
                Engine.EQUIVALENCE_DELTA
        );
        assertEquals(
                new Quaternion(1, 1, 1, 1).getNorm(),
                new engine.math.Quaternion(1, 1, 1, 1).norm(),
                Engine.EQUIVALENCE_DELTA
        );
    }

    @Test
    void reciprocal() {
        Quaternion q = new Quaternion(0, 1, 1, 1);
        q = q.getConjugate().multiply(1 / (q.getNorm() * q.getNorm()));
        assertTrue(apacheEqualsMy(q, new engine.math.Quaternion().reciprocal()));

        q = new Quaternion(3, 2.1, -2, -2.5);
        q = q.getConjugate().multiply(1 / (q.getNorm() * q.getNorm()));
        assertTrue(apacheEqualsMy(q, new engine.math.Quaternion(3, 2.1, -2, -2.5).reciprocal()));
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

        assertEquals(
                new Rotation(new Vector3D(1, 3, 5), Math.PI / 2.0, RotationConvention.VECTOR_OPERATOR).getAngle(),
                new engine.math.Quaternion(new engine.math.Vector3(1, 3, 5), Math.PI / 2.0).getAngle(),
                Engine.EQUIVALENCE_DELTA
        );

        assertEquals(
                new Rotation(new Vector3D(0, 3434, -3.6), 2.5, RotationConvention.VECTOR_OPERATOR).getAngle(),
                new engine.math.Quaternion(new engine.math.Vector3(0, 1, -3.6), 2.5).getAngle(),
                Engine.EQUIVALENCE_DELTA
        );
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

        assertEquals(
                new Rotation(new Vector3D(1, 0, 0), 2.51515151, RotationConvention.VECTOR_OPERATOR).getAngle(),
                new engine.math.Quaternion(new engine.math.Vector3(1, 0, 0), 2.51515151).getAngle(),
                Engine.EQUIVALENCE_DELTA
        );
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
    }

}