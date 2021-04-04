import com.ac101m.seedgrind.util.Area;
import com.ac101m.seedgrind.util.Vec2;

import java.util.Optional;

import org.junit.Test;

public class TestArea {

    /**
     * When initialised, coordinates should be shuffled so that:
     *  - p1 is the lower left corner
     *  - p2 is the upper right corner
     */
    @Test
    public void testInitVec() {
        Vec2 v1 = new Vec2(8, 0);
        Vec2 v2 = new Vec2(0, 8);

        Area area = new Area(v1, v2);

        assert (Vec2.equals(area.min, new Vec2(0, 0)));
        assert (Vec2.equals(area.max, new Vec2(8, 8)));
    }

    /**
     * Test initialization from point and radius
     */
    @Test
    public void testInitRadius() {
        Vec2 center = new Vec2(11, 9);
        int radius = 5;

        Area area = new Area(center, radius);

        assert (Vec2.equals(area.min, new Vec2(6, 4)));
        assert (Vec2.equals(area.max, new Vec2(16, 14)));
    }

    /**
     * Test initialization from integer coordinates
     */
    @Test
    public void testInitInts() {
        Area area = new Area(2, -2, -2, 2);

        assert (area.min.x == -2);
        assert (area.min.z == -2);
        assert (area.max.x == 2);
        assert (area.max.z == 2);
    }

    /**
     * Test width computation
     */
    @Test
    public void testWidth() {
        Area area = new Area(new Vec2(1, -1), new Vec2(8, 6));
        assert (area.width() == 7);
    }

    /**
     * Test height computation
     */
    @Test
    public void testHeight() {
        Area area = new Area(new Vec2(1, -1), new Vec2(8, 5));
        assert (area.height() == 6);
    }

    /**
     * Test size computation
     */
    @Test
    public void testSize() {
        Area area = new Area(new Vec2(1, -1), new Vec2(8, 5));
        assert (area.size() == 42);
    }

    /**
     * Test overlap check with overlapping regions
     */
    @Test
    public void testOverlapOverlapping() {
        Area a1 = new Area(new Vec2(0, 0), new Vec2(7, 7));
        Area a2 = new Area(new Vec2(1, 1), new Vec2(8, 8));

        assert (Area.areOverlapping(a1, a2));

        Optional<Area> overlap = Area.overlap(a1, a2);

        assert (overlap.isPresent());

        Area area = overlap.get();

        assert (Vec2.equals(area.min, new Vec2(1, 1)));
        assert (Vec2.equals(area.max, new Vec2(7, 7)));
    }

    /**
     * Test overlap check with identical regions
     */
    @Test
    public void testOverlapIdentical() {
        Area a1 = new Area(new Vec2(0, 0), new Vec2(8, 8));
        Area a2 = new Area(new Vec2(0, 0), new Vec2(8, 8));

        Optional<Area> overlap = Area.overlap(a1, a2);

        assert (overlap.isPresent());

        Area area = overlap.get();

        assert (Area.equals(area, a1));
        assert (Area.equals(area, a2));
    }

    /**
     * Test overlap check with non overlapping regions
     */
    @Test
    public void testOverlapNonOverlapping() {
        Area a1 = new Area(new Vec2(0, 0), new Vec2(4, 4));
        Area a2 = new Area(new Vec2(4, 4), new Vec2(7, 7));

        assert (!Area.areOverlapping(a1, a2));

        Optional<Area> overlap = Area.overlap(a1, a2);

        assert (!overlap.isPresent());
    }

    /**
     * Test equality check
     */
    @Test
    public void testEqualsNonEqual() {
        Area a1 = new Area(new Vec2(0, 0), new Vec2(4, 4));
        Area a2 = new Area(new Vec2(0, 0), new Vec2(4, 4));

        assert (Area.equals(a1, a2));

        Area a3 = new Area(new Vec2(0, 0), new Vec2(4, 3));
        Area a4 = new Area(new Vec2(0, 0), new Vec2(3, 4));
        Area a5 = new Area(new Vec2(0, 0), new Vec2(3, 3));
        Area a6 = new Area(new Vec2(0, 1), new Vec2(4, 4));
        Area a7 = new Area(new Vec2(1, 0), new Vec2(4, 4));
        Area a8 = new Area(new Vec2(1, 1), new Vec2(4, 4));

        assert (!Area.equals(a1, a3));
        assert (!Area.equals(a1, a4));
        assert (!Area.equals(a1, a5));
        assert (!Area.equals(a1, a6));
        assert (!Area.equals(a1, a7));
        assert (!Area.equals(a1, a8));
    }

    /**
     * Test expansion to nearest containing power of two size
     */
    @Test
    public void testExpandPower2() {
        Area area = new Area(-1, -1, 1, 1);

        area.expandPower2(0);
        assert (Area.equals(area, new Area(-1, -1, 1, 1)));
        area.expandPower2(1);
        assert (Area.equals(area, new Area(-2, -2, 2, 2)));
        area.expandPower2(8);
        assert (Area.equals(area, new Area(-256, -256, 256, 256)));

        area = new Area(-3, -2, -1, -5);

        area.expandPower2(0);
        assert (Area.equals(area, new Area(-3, -5, -1, -2)));
        area.expandPower2(1);
        assert (Area.equals(area, new Area(-4, -6, 0, -2)));
        area.expandPower2(2);
        assert (Area.equals(area, new Area(-4, -8, 0, 0)));
        area.expandPower2(3);
        assert (Area.equals(area, new Area(-8, -8, 0, 0)));
    }

    @Test
    public void testScale() {
        Area area = new Area(-1, -2, 1, 2);

        area.scale(2);
        assert (Area.equals(area, new Area(-2, -4, 2, 4)));

        area.scale(0.5);
        assert (Area.equals(area, new Area(-1, -2, 1, 2)));
    }
}
