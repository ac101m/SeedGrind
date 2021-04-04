import com.ac101m.seedgrind.util.Area;
import com.ac101m.seedgrind.util.Vec2;

import org.junit.Test;

public class TestVec2 {

    @Test
    public void testWithinBoxInclusive() {
        Vec2 p1 = new Vec2(8, 8);
        Vec2 p2 = new Vec2(16, 16);

        Area box = new Area(p1, p2);

        assert (new Vec2(12, 12).withinBoxInclusive(box));
        assert (!new Vec2(24, 24).withinBoxInclusive(box));
        assert (p1.withinBoxInclusive(box));
        assert (p2.withinBoxInclusive(box));
    }

    @Test
    public void testWithinBoxExclusive() {
        Vec2 p1 = new Vec2(8, 8);
        Vec2 p2 = new Vec2(16, 16);

        Area box = new Area(p1, p2);

        assert (new Vec2(12, 12).withinBoxExclusive(box));
        assert (!new Vec2(24, 24).withinBoxExclusive(box));
        assert (!p1.withinBoxExclusive(box));
        assert (!p2.withinBoxExclusive(box));
    }

    @Test
    public void testEquals() {
        Vec2 p1 = new Vec2(5, 5);
        Vec2 p2 = new Vec2(5, 5);
        Vec2 p3 = new Vec2(5, 6);
        Vec2 p4 = new Vec2(6, 5);

        assert (Vec2.equals(p1, p1));
        assert (Vec2.equals(p1, p2));
        assert (!Vec2.equals(p1, p3));
        assert (!Vec2.equals(p1, p4));
    }

    @Test
    public void testScale() {
        Vec2 vec = new Vec2(10, 10);

        vec.scale(0.5);
        assert (Vec2.equals(vec, new Vec2(5, 5)));

        vec.scale(3);
        assert (Vec2.equals(vec, new Vec2(15, 15)));
    }
}
