package com.btc67;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckAllProofs {

	/**
	 * Start of the range.
	 */
	final static BigInteger RANGE_START = BigInteger.TWO.pow(66);
	
	/**
	 * End of the range.
	 */
	final static BigInteger RANGE_END = BigInteger.TWO.pow(67).subtract(BigInteger.ONE);
	
	/**
	 * Size of a sub range. Problem is divided into 256 sub ranges.
	 */
	final static BigInteger SUBRANGE_SIZE = BigInteger.TWO.pow(58);
	
	/**
	 * Main program, we check proofs in the selected range
	 */
	public static void main (String args []) throws Exception {
		checkProofs(RANGE_START, RANGE_END, SUBRANGE_SIZE);
	}

	/**
	 * Checks proofs within the given range
	 */
	static int checkProofs(BigInteger rangeStart, BigInteger rangeEnd, BigInteger rangeSize) throws IOException {
		
		// Welcome message
		System.out.println("Checking proofs from " + rangeStart.toString(16) + " to " + rangeEnd.toString(16));
		
		// Create buckets to dispatch keys
		final TreeMap<BigInteger, AtomicInteger> buckets = new TreeMap<>();
		for (int i = 0 ; i < order().size() ; ++i) {
			buckets.put(rangeStart.add(BigInteger.valueOf(i).multiply(rangeSize)), new AtomicInteger(0));
		}
		
		// Read proof file and display proven percentage
		final List<String> proofs = Arrays.asList(new String(CheckAllProofs.class.getResourceAsStream("proofs.txt").readAllBytes()).split("\n"));
		final double provenPercent = (proofs.size() * 100) / (1024.0 * order().size());
		System.out.println(String.format("Number of proofs : %d (%.2f%% proven)", proofs.size(), provenPercent));

		// Validate each proof and dispatch it in it's bucket
		for (String p : proofs) {
			final String pkey = ProofValidator.validateProof(p);
			buckets.floorEntry(new BigInteger(pkey, 16)).getValue().incrementAndGet();
		}
		
		// Print how many proofs we have in each bucket
		for (Entry<BigInteger, AtomicInteger> e : buckets.entrySet()) {
			
			// Don't print if we have no proof in this range
			if (e.getValue().get() == 0) {
				continue;
			}
			
			// Print range data
			System.out.println(e.getKey().toString(16).substring(0,4) + " -> " + 
							   e.getKey().add(rangeSize).toString(16).substring(0,4) + " : " + 
							   e.getValue().get());
		}
	
		return proofs.size();
	}

	/**
	 * The random order in which BTC67 brute force was performed.
	 */
	private final static List<Integer> order() {
		return Arrays.asList(161, 102, 171, 141, 233, 227, 142, 148, 246, 143, 79, 63, 8, 115, 88, 14, 46, 72, 15, 237,
				78, 38, 50, 172, 56, 195, 153, 194, 253, 29, 6, 75, 21, 37, 174, 24, 126, 44, 136, 30, 154, 144, 74,
				100, 226, 240, 191, 224, 156, 107, 0, 95, 104, 86, 165, 163, 53, 22, 61, 94, 49, 66, 111, 2, 247, 183,
				10, 198, 96, 67, 212, 221, 200, 244, 27, 114, 215, 220, 81, 170, 134, 109, 168, 62, 146, 219, 70, 77,
				181, 245, 18, 201, 196, 160, 13, 249, 166, 242, 205, 155, 42, 41, 255, 231, 55, 130, 91, 173, 101, 3,
				11, 51, 193, 80, 254, 113, 19, 179, 239, 64, 82, 192, 40, 68, 87, 1, 232, 52, 184, 236, 98, 106, 58, 83,
				138, 241, 175, 149, 16, 28, 213, 26, 207, 204, 39, 35, 234, 185, 238, 73, 108, 116, 103, 105, 9, 127,
				117, 137, 203, 152, 230, 121, 85, 23, 229, 206, 25, 69, 20, 119, 5, 248, 125, 7, 122, 31, 36, 110, 199,
				235, 32, 252, 228, 139, 251, 48, 158, 57, 129, 180, 176, 157, 43, 45, 189, 135, 223, 150, 47, 167, 159,
				243, 76, 164, 169, 131, 140, 71, 33, 93, 97, 187, 208, 147, 202, 17, 145, 162, 218, 132, 118, 250, 54,
				217, 210, 89, 34, 128, 177, 211, 60, 4, 123, 151, 225, 124, 209, 90, 84, 188, 186, 216, 112, 190, 120,
				92, 65, 59, 99, 178, 12, 133, 214, 182, 222, 197);
	}
}
