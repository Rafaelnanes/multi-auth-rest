package rbn.com.multi.auth;

import java.text.ParseException;
import java.util.Date;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class SignAndEncryptExample {

	public static void main(String[] args) throws JOSEException, ParseException {
		RSAKey senderJWK = new RSAKeyGenerator(2048).keyID("123").keyUse(KeyUse.SIGNATURE).generate();
		RSAKey senderPublicJWK = senderJWK.toPublicJWK();

		RSAKey recipientJWK = new RSAKeyGenerator(2048).keyID("456").keyUse(KeyUse.ENCRYPTION).generate();
		RSAKey recipientPublicJWK = recipientJWK.toPublicJWK();
		recipientPublicJWK.toKeyPair();
		recipientJWK.toKeyPair();
		// Create JWT
		SignedJWT signedJWT = new SignedJWT(
				new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(senderJWK.getKeyID()).build(),
				new JWTClaimsSet.Builder().subject("alice").issueTime(new Date()).build());

		// Sign the JWT
		signedJWT.sign(new RSASSASigner(senderJWK));

		// Create JWE object with signed JWT as payload
		JWEObject jweObject = new JWEObject(
				new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).contentType("JWT") // required
						.build(),
				new Payload(signedJWT));

		// Encrypt with the recipient's public key
		jweObject.encrypt(new RSAEncrypter(recipientPublicJWK));

		// Serialise to JWE compact form
		String jweString = jweObject.serialize();
		System.out.println(jweString);

		// Parse the JWE string
		jweObject = JWEObject.parse(jweString);

		// Decrypt with private key
		jweObject.decrypt(new RSADecrypter(recipientJWK));

		// Extract payload
		signedJWT = jweObject.getPayload().toSignedJWT();
		System.out.println(signedJWT.getParsedString());

		System.out.println("Payload a signed JWT " + signedJWT);

		// Check the signature
		System.out.println(signedJWT.verify(new RSASSAVerifier(senderPublicJWK)));

		// Retrieve the JWT claims...
		System.out.println("alice " + signedJWT.getJWTClaimsSet().getSubject());
	}

}
