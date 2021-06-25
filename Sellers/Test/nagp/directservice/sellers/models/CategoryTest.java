package nagp.directservice.sellers.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CategoryTest {

	
	@Test
	public void test() {
		assertEquals( Category.GROOMING.name(), "GROOMING");
	}
	
	@Test
	public void testFailing() {
		assertEquals( Category.GROOMING.name(), "GROMING");
	}
	
}
