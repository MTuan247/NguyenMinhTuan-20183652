package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateRushOrderInfoTest {

	// Nguyen Minh Tuan - 20183652
	
	private PlaceRushOrderController placeRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"day la thong tin giao hang nhanh, true",
		"thong tin $@, false",
		", false"
	})
	
	@DisplayName("validate rush order info")
	void test(String info, boolean expected) {
		boolean isValid = placeRushOrderController.validateRushOrderInfo(info);
		assertEquals(expected, isValid);
	}

}
