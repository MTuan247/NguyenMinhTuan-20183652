package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateReceiveTimeTest {
	// Nguyen Minh Tuan - 20183652

	private PlaceRushOrderController placeRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"07/12/2021 13:00, true",
		"8/12/2021 13:00, true",
		"07-12-2021 13:00, false",
		"32/12/2021 13:00, false",
		"20/13/2021 13:00, false",
		"20/11/2021 26:00, false",
		"abc123, false",
		", false",
	})
	
	@DisplayName("validate receive time")
	void test(String timeStr, boolean expected) {
		boolean isValid = placeRushOrderController.validateReceiveTime(timeStr);
		assertEquals(expected, isValid);
	}
}
