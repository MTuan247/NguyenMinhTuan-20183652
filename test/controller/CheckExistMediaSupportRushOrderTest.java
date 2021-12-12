package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class CheckExistMediaSupportRushOrderTest {

	// Nguyen Minh Tuan - 20183652

	private PlaceRushOrderController placeRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	}

	@ParameterizedTest
	@MethodSource("generateData")
	
	@DisplayName("check exist media support rush order")
	void test(List<Integer> mediaIds, boolean expected) {
		boolean isValid = placeRushOrderController.checkExistMediaSupportRushOrder(mediaIds);
		assertEquals(expected, isValid);
	}

	
	static Stream<Arguments> generateData() {
	    return Stream.of(
	        Arguments.of(List.of(1), true),
	        Arguments.of(List.of(1, 3, 5), true),
	        Arguments.of(List.of(11, 1, 10), true),
	        Arguments.of(List.of(20, 30), false),
	        Arguments.of(null, false)
	    );
	}
}
