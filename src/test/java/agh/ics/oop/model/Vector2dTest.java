package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    public void testEquals(){
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(1,2);

        assertTrue(v1.equals(v2));
        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v3));
    }

    @Test
    public void testAdd(){
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(2,4);
        assertEquals(v1.add(v2),v3);
    }

    @Test
    public void testToString(){
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(4,-2);
        assertEquals(v1.toString(),"(1, 2)");
        assertEquals(v2.toString(),"(4, -2)");
    }
}
