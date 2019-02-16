package be.leerstad.tictactoe.service.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import be.leerstad.tictactoe.service.dto.GameMode;

public class GameModeTest {

private GameMode classUnderTest;

@Before
public void setup() {
	classUnderTest = new GameMode();
}

@Test
public void testSINGLE() {
	assertNull(classUnderTest.getSINGLE());
}

@Test
public void testDUAL() {
	assertNull(classUnderTest.getDUAL());
}

@Test
public void testValue() {
	classUnderTest.setValue("test value");
	assertEquals("test value", classUnderTest.getValue());
	classUnderTest.setValue("test2 value");
	assertEquals("test2 value", classUnderTest.getValue());
}

@Test
public void testENUM$VALUES() {
	assertNull(classUnderTest.getENUM$VALUES());
}

@Test
public void testToString() {
	fail("Not yet implemented");
}

@Test
public void testValues() {
	fail("Not yet implemented");
}

@Test
public void testValueOf() {
	fail("Not yet implemented");
}

}