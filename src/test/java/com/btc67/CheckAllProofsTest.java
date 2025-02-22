package com.btc67;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CheckAllProofsTest {

	@Test
	public void checkAllProofs() throws IOException {
		assertEquals(143413, CheckAllProofs.checkProofs(CheckAllProofs.RANGE_START, CheckAllProofs.RANGE_END, CheckAllProofs.SUBRANGE_SIZE));
	}
}
