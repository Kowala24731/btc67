# Btc67
This repository contains the proofs of work for BTC67.

## Proof methodology
- The problem was split in 256 sub ranges of size 2^58. 
- In each sub range, we save every private key which generates an address starting with 48 zeros.
- There are statistically 1024 proofs per sub range.
- If we can provide an average of 1024 proofs per sub range, it statistically guarantees that we scanned the whole sub range.

## How to run
- Proofs are in com/btc67/proofs.txt
- You are free to write your own code to validate them, a java program is provided for convenience.
- This is a java maven project. To run the proof check, run this command in the project directory : **mvn clean install**
	
## Miscellaneous
- Why does it display only 54.71% proven when you stated 57% on twitter ?
	* First four sub ranges did not have proofs enabled so sub ranges 161, 102, 171, 141 have been scanned but don't have proofs. That's 1.56% missing.	
	* A subset of logs were lost during a crash, we lost a part of the proofs for ranges 64 and 97, that explains the other missing .7%