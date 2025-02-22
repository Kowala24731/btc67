package com.btc67;

import java.math.BigInteger;

import org.bouncycastle.jcajce.provider.digest.RIPEMD160;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Numeric;

public class ProofValidator {
	
	/**
	 * A proof address must start with 48 zero bits.
	 */
	private static final String PROOF_PREFIX = "000000000000"; 
	
	/**
	 * Validates that a WIF key generates an address starting with 48 zero bits (i.e. it's a valid proof)
	 * @param wif the wif to validate
	 * @return the hex version of the private key
	 */
	static String validateProof(String wif) {
		final String privKey = getPrivateKey(wif);
		final Credentials cs = Credentials.create(privKey);
		final String pubKey = compressPubKey(cs.getEcKeyPair().getPublicKey());
		final String pubKeyString = Numeric.toHexStringNoPrefix(new RIPEMD160.Digest().digest(new SHA256.Digest().digest(Numeric.hexStringToByteArray(pubKey))));
		if (!pubKeyString.startsWith(PROOF_PREFIX)) {
			throw new IllegalStateException("Invalid proof ! " + wif + " - " + pubKeyString);
		}
		return privKey;
	}

	/**
	 * Transforms a WIF into an hex private key, strips first byte
	 */
	private static String getPrivateKey(String wif) {
		return Base58.decode(wif).substring(2, 66);
	}
	
	/**
	 * Builds the compressed hex representation of the provided public key
	 */
	private static String compressPubKey(BigInteger pubKey) {
        String pubKeyHex = pubKey.toString(16);
        while (pubKeyHex.length() < 128) {
        	pubKeyHex = "0" + pubKeyHex;
        }
        return (pubKey.testBit(0) ? "03" : "02") + pubKeyHex.substring(0, 64);
    }
}
