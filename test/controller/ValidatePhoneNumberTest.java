package controller;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidatePhoneNumberTest {

	// Nguyen Minh Tuan - 20183652
	private PlaceOrderController placeOrderController;
	
	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"0123456789, true",
		"01234, false",
		"1234567890, false",
		"qwe123weq, false",
	})

	@DisplayName("validate phone number")
	public void test(String phoneNumber, boolean expected) {
		boolean isValid = placeOrderController.validatePhoneNumber(phoneNumber);
		assertEquals(expected, isValid);
	}

}
