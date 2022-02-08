import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

// service, most of right answers were given from:
// https://www.omnicalculator.com/math/quaternion

class QuaternionTest {

    @Test
    void inputValuesTests() {
        assertThrows(InvalidParameterException.class,
                () -> new Quaternion(0, Double.NaN, 0, 1));
        assertThrows(InvalidParameterException.class,
                () -> new Quaternion(0, new Vector3(-12, Double.NEGATIVE_INFINITY, 0)));
        assertThrows(InvalidParameterException.class,
                () -> new Quaternion(new Vector3(Double.POSITIVE_INFINITY, 1, 1), Double.NaN));
    }

    @Test
    void plus() {
        assertEquals(new Quaternion(0,0,0,0), new Quaternion().plus(new Quaternion().multiplied(-1)));
        assertEquals(new Quaternion(3, 1, 2, 3), new Quaternion(1, 0, 9.3, 2).plus(new Quaternion(2, new Vector3(1, -7.3, 1))));

        System.out.println(new Quaternion(0, 0, 0, 0).norm());
    }

    @Test
    void minus() {
        Quaternion a = new Quaternion(1, 2, 3, 4);
        Quaternion b = new Quaternion(2, 0, 0, -5);
        assertEquals(new Quaternion(0, 0, 0, 0), a.minus(a));
        assertEquals(new Quaternion(-1, 2, 3, 9), a.minus(b));
        assertEquals(new Quaternion(1, -2, -3, -9), b.minus(a));
    }

    @Test
    void norm() {
        assertEquals(13.05, new Quaternion(3, 1, -3, -12.3).norm(), 0.005);
        assertEquals(2, new Quaternion(1, 1, 1, 1).norm(), 0.005);
    }

    @Test
    void reciprocal() {
        assertEquals(new Quaternion(0, -1.0/3, -1.0/3, -1.0/3), new Quaternion().reciprocal());
        // q₁ = 3 + 2.1i - 2j - 2.5k
        // q₁⁻¹ = 0.126796 - 0.088757i + 0.084531j + 0.105664k.
        // Т.к. сравнивать кватернионы с выборочной точностью мне представляется невозможным,
        // сравню папарно каждый элемент 2-х кватернионов в отдельных тестах.
        Quaternion q1 = new Quaternion(3, 2.1, -2, -2.5).reciprocal();
        Quaternion q2 = new Quaternion(0.126796, -0.088757, 0.084531, 0.105664);
        assertEquals(q2.getW(), q1.getW(), 5e-7);
        assertEquals(q2.getX(), q1.getX(), 5e-7);
        assertEquals(q2.getY(), q1.getY(), 5e-7);
        assertEquals(q2.getZ(), q1.getZ(), 5e-7);
    }

    @Test
    void multipliedScalar() {
        assertEquals(new Quaternion(0, 2, 4, 4), new Quaternion(0, 0.5, 1, 1).multiplied(4));
        assertEquals(new Quaternion(0, 0, 0, 0), new Quaternion(123, 0.2323, -123123, 2).multiplied(0));

        Quaternion q1 = new Quaternion(0, 0.5, 1, 1);
        q1.multiply(4);
        assertEquals(new Quaternion(0, 2, 4, 4), q1);

        q1 = new Quaternion(123, 0.2323, -123123, 2);
        q1.multiply(0);
        assertEquals(new Quaternion(0, 0, 0, 0), q1);
    }

    @Test
    void multipliedQuaternion() {
        assertEquals(new Quaternion(-12, 3, 0, 3), new Quaternion().multiplied(new Quaternion(2, 3, 4, 5)));
        assertEquals(new Quaternion(-12, 1, 4, 1), new Quaternion(2, 3, 4, 5).multiplied(new Quaternion()));

        Quaternion q1 = new Quaternion();
        q1.multiply(new Quaternion(2, 3, 4, 5));
        assertEquals(new Quaternion(-12, 3, 0, 3), q1);

        q1 = new Quaternion(2, 3, 4, 5);
        q1.multiply(new Quaternion());
        assertEquals(new Quaternion(-12, 1, 4, 1), q1);
    }

    @Test
    void multipliedVector3() {
        assertEquals(new Quaternion(36.6, -26.9, 0, 18.3), new Quaternion(2, 0, -12.3, -4).multiplied(new Vector3(1, 2, 3)));
        assertEquals(new Quaternion(0.8, 1, 1.8, 3.2), new Quaternion(2, 0.2, -1, 0).multiplied(new Vector3()));

        Quaternion q1 = new Quaternion(2, 0, -12.3, -4);
        q1.multiply(new Vector3(1, 2, 3));
        assertEquals(new Quaternion(36.6, -26.9, 0, 18.3), q1);

        q1 = new Quaternion(2, 0.2, -1, 0);
        q1.multiply(new Vector3());
        assertEquals(new Quaternion(0.8, 1, 1.8, 3.2), q1);
    }

    @Test
    void conjugated() {
        assertEquals(new Quaternion(0, -1, -1, -1), new Quaternion().conjugated());
        assertEquals(new Quaternion(3, new Vector3(-1, 3, 12.3)), new Quaternion(3, 1, -3, -12.3).conjugated());

        Quaternion q1 = new Quaternion();
        q1.conjugate();
        assertEquals(new Quaternion(0, -1, -1, -1), q1);

        q1 = new Quaternion(3, 1, -3, -12.3);
        q1.conjugate();
        assertEquals(new Quaternion(3, -1, 3, 12.3), q1);
    }

    @Test
    void normalized() {
        assertEquals(new Quaternion(0.5, 0.5, 0.5, 0.5), new Quaternion(1, 1, 1, 1).normalized());
        // Quaternion(1.2, 1.6, -1.2, -2).norm = 3.0724582991474434
        // 1.2 / 3.0724582991474434 = 0.3905667329424716
        // 1.6 / 3.0724582991474434 = 0.5207556439232954
        // -1.2 / 3.0724582991474434 = -0.3905667329424716
        // -2 / 3.0724582991474434 = -0.6509445549041193
        assertEquals(new Quaternion(0.3905667329424716, 0.5207556439232954, -0.3905667329424716, -0.6509445549041193),
                new Quaternion(1.2, 1.6, -1.2, -2).normalized());

        Quaternion q1 = new Quaternion( 1, 1, 1, 1);
        q1.normalize();
        assertEquals(new Quaternion(0.5, 0.5, 0.5, 0.5), q1);

        q1 = new Quaternion(1.2, 1.6, -1.2, -2);
        q1.normalize();
        assertEquals(new Quaternion(0.3905667329424716, 0.5207556439232954, -0.3905667329424716, -0.6509445549041193), q1);

    }

    @Test
    void quaternionAxisAngle() {
        // https://www.andre-gaschler.com/rotationconverter/

        assertEquals(new Quaternion(1, 0, 0, 0), new Quaternion(new Vector3(1, 1, 1), 0));
        // RotateAxis: x=2.3, y=0, z=5; Angle=PI*0.3
        // Right answer: [ x=0.1897252, y=0, z=0.412446, w=0.8910065 ]
        Quaternion qRes = new Quaternion(new Vector3(2.3, 0, 5), Math.PI * 0.3);
        Quaternion qRight = new Quaternion(0.8910065, 0.1897252, 0, 0.412446);
        assertEquals(qRight.getW(), qRes.getW(), 5e-8);
        assertEquals(qRight.getX(), qRes.getX(), 5e-8);
        assertEquals(qRight.getY(), qRes.getY(), 5e-8);
        assertEquals(qRight.getZ(), qRes.getZ(), 5e-8);

    }

    @Test
    void rotate() {
        // from: https://www.vcalc.com/wiki/vCalc/V3+-+Vector+Rotation

        Quaternion rotateQ = new Quaternion(new Vector3(1, 0, 1), Math.PI/2);
        Vector3 vector = new Vector3(0, 0, 1).rotated(rotateQ);
        assertEquals(0.5, vector.getX(), 0.05);
        assertEquals(-0.70711, vector.getY(), 5e-6);
        assertEquals(0.5, vector.getZ(), 0.05);

        // 1.2506, -2.76661, 2.96342
        rotateQ = new Quaternion(new Vector3(12, 3, -8), 1);
        vector = new Vector3(1, 1, 4).rotated(rotateQ);
        assertEquals(1.2506, vector.getX(), 5e-5);
        assertEquals(-2.76661, vector.getY(), 5e-6);
        assertEquals(2.96342, vector.getZ(), 5e-6);

        // ----------------

        rotateQ = new Quaternion(new Vector3(1, 0, 1), Math.PI/2);
        vector = new Vector3(0, 0, 1);
        vector.rotate(rotateQ);
        assertEquals(0.5, vector.getX(), 0.05);
        assertEquals(-0.70711, vector.getY(), 5e-6);
        assertEquals(0.5, vector.getZ(), 0.05);

        // 1.2506, -2.76661, 2.96342
        rotateQ = new Quaternion(new Vector3(12, 3, -8), 1);
        vector = new Vector3(1, 1, 4);
        vector.rotate(rotateQ);
        assertEquals(1.2506, vector.getX(), 5e-5);
        assertEquals(-2.76661, vector.getY(), 5e-6);
        assertEquals(2.96342, vector.getZ(), 5e-6);
    }

    @Test
    void getAngle() {
        // https://www.andre-gaschler.com/rotationconverter/

        assertEquals(Math.PI / 2.0, new Quaternion(new Vector3(1, 3, 5), Math.PI / 2.0).getAngle());
        assertEquals(2.51515154, new Quaternion(new Vector3(1, 0, 0), 2.51515154).getAngle());
        assertEquals(1 / 0.3, new Quaternion(new Vector3(0, 1, -3.6), 1 / 0.3).getAngle());
    }

    @Test
    void getAxis() {
        // https://www.andre-gaschler.com/rotationconverter/

        assertEquals(new Vector3(1, 3, 5).normalized(),
                new Quaternion(new Vector3(1, 3, 5), Math.PI / 2.0).getAxis());
        assertEquals(new Vector3(1, 0, 0).normalized(),
                new Quaternion(new Vector3(1, 0, 0), 2.51515154).getAxis());
        assertEquals(new Vector3(0, 1, -3.6).normalized(),
                new Quaternion(new Vector3(0, 1, -3.6), 1 / 0.3).getAxis());
        assertEquals(new Vector3(0, 0, 0),
                new Quaternion(new Vector3(3, 1, 8), 0).getAxis());
    }

}