package com.btc67;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

/**
 * Base58 Utility class
 */
public class Base58 {

	public static final String B58_VALUES = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
	
	/**
	 * Base58 is encoding hex string as a big number in Base58 using a provided dictionary
	 */
	public static String decode(String base58) {
		BigInteger res = BigInteger.ZERO;
		for (int i = 0 ; i < base58.length() ; i++) {
			res = res.add(BigInteger.valueOf(B58_VALUES.indexOf(base58.charAt(base58.length() - 1 - i))).multiply(BigInteger.valueOf(58L).pow(i)));
		}
		return Numeric.toHexStringNoPrefixZeroPadded(res, 76);
	}
}
