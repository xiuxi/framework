package com.framework.module.common.security.jwt;

import java.util.Date;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JWT工具类(基于jose4j实现)
 * 
 * @author qq
 * 
 */
public class JWTUtils {
	private final static Logger logger = LoggerFactory.getLogger(JWTUtils.class);

	/**
	 * 秘钥
	 */
	private static RsaJsonWebKey rsaJsonWebKey;

	/**
	 * keyId
	 */
	private static String keyId = new Date().toString();

	/**
	 * 使用算法
	 */
	private static String alg = AlgorithmIdentifiers.RSA_USING_SHA256;

	/**
	 * 发行者
	 */
	private static String issuer = "Issuer";

	/**
	 * 订阅者
	 */
	private static String audience = "Audience";

	/**
	 * JWT有效时间,单位分钟（默认15天）
	 */
	// private static float expirationTimeMinutes = 15 * 24 * 60;

	/**
	 * JWT有效时间,单位分钟（默认1分钟）
	 */
	private static float expirationTimeMinutes = 1;

	static {
		try {
			rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
			rsaJsonWebKey.setKeyId(keyId);
		} catch (JoseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将数据加密为JWT字符串
	 * 
	 * @param subjectJson
	 *            要保存在token中的主题Json对象
	 * 
	 * @return JWT字符串
	 * 
	 * @throws JoseException
	 */
	public static String encryptJWT(String subjectJson) throws JoseException {
		JwtClaims claims = new JwtClaims();
		claims.setIssuer(issuer);
		claims.setAudience(audience);
		claims.setExpirationTimeMinutesInTheFuture(expirationTimeMinutes); // 从现在起expirationTimeMinutes分钟后该token将过期
		claims.setGeneratedJwtId();
		claims.setIssuedAtToNow();
		claims.setNotBeforeMinutesInThePast(2);
		claims.setSubject(subjectJson);

		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(rsaJsonWebKey.getPrivateKey());
		jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
		jws.setAlgorithmHeaderValue(alg);

		String jwt = jws.getCompactSerialization();
		System.out.println(jwt);
		logger.info("create JWT succeeded!", jwt);
		return jwt;
	}

	/**
	 * 解密JWT数据
	 * 
	 * @param jwt
	 *            加密的JWT
	 * 
	 * @return JwtClaims
	 * 
	 * @throws InvalidJwtException
	 */
	public static JwtClaims decryptJWT(String jwt) throws InvalidJwtException {
		JwtConsumer jwtConsumer = new JwtConsumerBuilder() //
		.setRequireExpirationTime() // 加密的JWT必须设置一个过期时间
		// .setMaxFutureValidityInMinutes(300)
		.setAllowedClockSkewInSeconds(30) //
		.setRequireSubject() // 加密的JWT必须设置一个Subject
		.setExpectedIssuer(issuer) // 与加密时的一致
		.setExpectedAudience(audience) // 与加密时的一致
		.setVerificationKey(rsaJsonWebKey.getKey()) // 与加密时的一致
		.build();

		// Validate the JWT and process it to the Claims
		JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
		System.out.println(jwtClaims);
		logger.info("JWT validation succeeded!", jwtClaims);
		return jwtClaims;
	}
}
