package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateRushOrderInstructionTest {
	
	private PlaceRushOrderController placeRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"day la chi dan giao hang nhanh, true",
		"chi dan 123 $@, false",
		", false"
	})
	
	@DisplayName("validate rush order instruction")
	void test(String instruction, boolean expected) {
		boolean isValid = placeRushOrderController.validateRushOrderInstruction(instruction);
		assertEquals(expected, isValid);
	}

}
