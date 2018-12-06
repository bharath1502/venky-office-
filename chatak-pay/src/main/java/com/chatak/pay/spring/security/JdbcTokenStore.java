package com.chatak.pay.spring.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;

import com.chatak.pg.util.Constants;

public class JdbcTokenStore implements TokenStore{

	private static final Log LOG = LogFactory.getLog(JdbcTokenStore.class);

	private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT = "insert into PG_OAUTH_ACCESS_TOKEN (TOKEN_ID, TOKEN, AUTHENTICATION_ID, USER_NAME, CLIENT_ID, AUTHENTICATION, REFRESH_TOKEN) values (?, ?, ?, ?, ?, ?, ?)";

	private static final String DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT = "select TOKEN_ID, TOKEN from PG_OAUTH_ACCESS_TOKEN where TOKEN_ID = ?";

	private static final String DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select TOKEN_ID, AUTHENTICATION from PG_OAUTH_ACCESS_TOKEN where TOKEN_ID = ?";

	private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT = "select TOKEN_ID, TOKEN from PG_OAUTH_ACCESS_TOKEN where AUTHENTICATION_ID = ?";

	private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT = "select TOKEN_ID, TOKEN from PG_OAUTH_ACCESS_TOKEN where USER_NAME = ?";

	private static final String DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT = "select TOKEN_ID, TOKEN from PG_OAUTH_ACCESS_TOKEN where CLIENT_ID = ?";

	private static final String DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT = "delete from PG_OAUTH_ACCESS_TOKEN where TOKEN_ID = ?";

	private static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT = "delete from PG_OAUTH_ACCESS_TOKEN where REFRESH_TOKEN = ?";

	private static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT = "insert into PG_OAUTH_REFRESH_TOKEN (TOKEN_ID, TOKEN, AUTHENTICATION) values (?, ?, ?)";

	private static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT = "select TOKEN_ID, TOKEN from PG_OAUTH_REFRESH_TOKEN where TOKEN_ID = ?";

	private static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select TOKEN_ID, AUTHENTICATION from PG_OAUTH_REFRESH_TOKEN where TOKEN_ID = ?";

	private static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT = "delete from PG_OAUTH_REFRESH_TOKEN where TOKEN_ID = ?";

	private String insertAccessTokenSql = DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT;

	private String selectAccessTokenSql = DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT;

	private String selectAccessTokenAuthenticationSql = DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT;

	private String selectAccessTokenFromAuthenticationSql = DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT;

	private String selectAccessTokensFromUserNameSql = DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT;

	private String selectAccessTokensFromClientIdSql = DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT;

	private String deleteAccessTokenSql = DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT;

	private String insertRefreshTokenSql = DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT;

	private String selectRefreshTokenSql = DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT;

	private String selectRefreshTokenAuthenticationSql = DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT;

	private String deleteRefreshTokenSql = DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT;

	private String deleteAccessTokenFromRefreshTokenSql = DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT;

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

	private final JdbcTemplate jdbcTemplate;

	public JdbcTokenStore(DataSource dataSource) {
		Assert.notNull(dataSource, "DataSource required");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}

	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		OAuth2AccessToken accessToken = null;

		try {
			accessToken = jdbcTemplate.queryForObject(selectAccessTokenFromAuthenticationSql,
					new RowMapper<OAuth2AccessToken>() {
						public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
							return deserializeAccessToken(rs.getBytes(Constants.TWO));
						}
					}, authenticationKeyGenerator.extractKey(authentication));
		}
		catch (EmptyResultDataAccessException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: getAccessToken method", e);
			if (LOG.isInfoEnabled()) {
				LOG.debug("Failed to find access token for authentication " + authentication);
			}
		} catch (IllegalArgumentException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: getAccessToken method", e);
		}

		if (accessToken != null && !authentication.equals(readAuthentication(accessToken.getValue()))) {
			removeAccessToken(accessToken.getValue());
			// Keep the store consistent (maybe the same user is represented by this authentication but the details have
			// changed)
			storeAccessToken(accessToken, authentication);
		}
		return accessToken;
	}

	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		String refreshToken = null;
		if (token.getRefreshToken() != null) {
			refreshToken = token.getRefreshToken().getValue();
		}

		jdbcTemplate.update(insertAccessTokenSql, new Object[] { extractTokenKey(token.getValue()),
				new SqlLobValue(serializeAccessToken(token)), authenticationKeyGenerator.extractKey(authentication),
				authentication.isClientOnly() ? null : authentication.getName(),
				authentication.getAuthorizationRequest().getClientId(),
				new SqlLobValue(serializeAuthentication(authentication)), extractTokenKey(refreshToken) }, new int[] { Types.VARCHAR,
				Types.BLOB, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.VARCHAR });
	}

	public OAuth2AccessToken readAccessToken(String tokenValue) {
		OAuth2AccessToken accessToken = null;

		try {
			accessToken = jdbcTemplate.queryForObject(selectAccessTokenSql, new RowMapper<OAuth2AccessToken>() {
				public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
					return deserializeAccessToken(rs.getBytes(Constants.TWO));
				}
			}, extractTokenKey(tokenValue));
		}
		catch (EmptyResultDataAccessException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: readAccessToken method", e);
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + tokenValue);
			}
		} catch (IllegalArgumentException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: readAccessToken method", e);
			LOG.warn("Failed to deserialize access token for " + tokenValue);
			removeAccessToken(tokenValue);
		}

		return accessToken;
	}

	public void removeAccessToken(OAuth2AccessToken token) {
		removeAccessToken(token.getValue());
	}

	public void removeAccessToken(String tokenValue) {
		jdbcTemplate.update(deleteAccessTokenSql, extractTokenKey(tokenValue));
	}

	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	public OAuth2Authentication readAuthentication(String token) {
		OAuth2Authentication authentication = null;

		try {
			authentication = jdbcTemplate.queryForObject(selectAccessTokenAuthenticationSql,
					new RowMapper<OAuth2Authentication>() {
						public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
							return deserializeAuthentication(rs.getBytes(Constants.TWO));
						}
					}, extractTokenKey(token));
		}
		catch (EmptyResultDataAccessException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: readAuthentication method", e);
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + token);
			}
		} catch (IllegalArgumentException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: readAuthentication method", e);
			LOG.warn("Failed to deserialize authentication for " + token);
			removeAccessToken(token);
		}

		return authentication;
	}

	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		jdbcTemplate.update(insertRefreshTokenSql, new Object[] { extractTokenKey(refreshToken.getValue()),
				new SqlLobValue(serializeRefreshToken(refreshToken)),
				new SqlLobValue(serializeAuthentication(authentication)) }, new int[] { Types.VARCHAR, Types.BLOB,
				Types.BLOB });
	}

	public OAuth2RefreshToken readRefreshToken(String token) {
		OAuth2RefreshToken refreshToken = null;

		try {
			refreshToken = jdbcTemplate.queryForObject(selectRefreshTokenSql, new RowMapper<OAuth2RefreshToken>() {
				public OAuth2RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
					return deserializeRefreshToken(rs.getBytes(Constants.TWO));
				}
			}, extractTokenKey(token));
		}
		catch (EmptyResultDataAccessException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: readRefreshToken method", e);
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find refresh token for token " + token);
			}
		} catch (IllegalArgumentException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: readRefreshToken method", e);
			LOG.warn("Failed to deserialize refresh token for token " + token);
			removeRefreshToken(token);
		}

		return refreshToken;
	}

	public void removeRefreshToken(OAuth2RefreshToken token) {
		removeRefreshToken(token.getValue());
	}

	public void removeRefreshToken(String token) {
		jdbcTemplate.update(deleteRefreshTokenSql, extractTokenKey(token));
	}

	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return readAuthenticationForRefreshToken(token.getValue());
	}

	public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
		OAuth2Authentication authentication = null;

		try {
			authentication = jdbcTemplate.queryForObject(selectRefreshTokenAuthenticationSql,
					new RowMapper<OAuth2Authentication>() {
						public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
							return deserializeAuthentication(rs.getBytes(Constants.TWO));
						}
					}, extractTokenKey(value));
		}
		catch (EmptyResultDataAccessException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: readAuthenticationForRefreshToken method", e);
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + value);
			}
		} catch (IllegalArgumentException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: readAuthenticationForRefreshToken method", e);
			LOG.warn("Failed to deserialize access token for " + value);
			removeRefreshToken(value);
		}

		return authentication;
	}

	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		removeAccessTokenUsingRefreshToken(refreshToken.getValue());
	}

	public void removeAccessTokenUsingRefreshToken(String refreshToken) {
		jdbcTemplate.update(deleteAccessTokenFromRefreshTokenSql, new Object[] { extractTokenKey(refreshToken) },
				new int[] { Types.VARCHAR });
	}

	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

		try {
			accessTokens = jdbcTemplate.query(selectAccessTokensFromClientIdSql, new SafeAccessTokenRowMapper(), clientId);
		}
		catch (EmptyResultDataAccessException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: findTokensByClientId method", e);
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for clientId " + clientId);
			}
		}
		accessTokens = removeNulls(accessTokens);

		return accessTokens;
	}

	public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

		try {
			accessTokens = jdbcTemplate.query(selectAccessTokensFromUserNameSql, new SafeAccessTokenRowMapper(), userName);
		}
		catch (EmptyResultDataAccessException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: findTokensByUserName method", e);
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for userName " + userName);
			}
		}
		accessTokens = removeNulls(accessTokens);

		return accessTokens;
	}

	private List<OAuth2AccessToken> removeNulls(List<OAuth2AccessToken> accessTokens) {
		List<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
		for (OAuth2AccessToken token : accessTokens) {
			if (token!=null) {
				tokens.add(token);
			}
		}
		return tokens;
	}

	protected String extractTokenKey(String value) {
		if (value==null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: extractTokenKey method", e);
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
		  LOG.error("ERROR:: JdbcTokenStore:: extractTokenKey method", e);
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}

	private final class SafeAccessTokenRowMapper implements RowMapper<OAuth2AccessToken> {
		public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				return deserializeAccessToken(rs.getBytes(Constants.TWO));
			} catch (IllegalArgumentException e) {
			  LOG.error("ERROR:: JdbcTokenStore:: SafeAccessTokenRowMapper method", e);
				String token = rs.getString(1);
				jdbcTemplate.update(deleteAccessTokenSql, token);
				return null;
			}
		}
	}

	protected byte[] serializeAccessToken(OAuth2AccessToken token) {
		return SerializationUtils.serialize(token);
	}

	protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
		return SerializationUtils.serialize(token);
	}

	protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
		return SerializationUtils.serialize(authentication);
	}

	protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
		return SerializationUtils.deserialize(token);
	}

	protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
		return SerializationUtils.deserialize(token);
	}

	protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
		return SerializationUtils.deserialize(authentication);
	}

	public void setInsertAccessTokenSql(String insertAccessTokenSql) {
		this.insertAccessTokenSql = insertAccessTokenSql;
	}

	public void setSelectAccessTokenSql(String selectAccessTokenSql) {
		this.selectAccessTokenSql = selectAccessTokenSql;
	}

	public void setDeleteAccessTokenSql(String deleteAccessTokenSql) {
		this.deleteAccessTokenSql = deleteAccessTokenSql;
	}

	public void setInsertRefreshTokenSql(String insertRefreshTokenSql) {
		this.insertRefreshTokenSql = insertRefreshTokenSql;
	}

	public void setSelectRefreshTokenSql(String selectRefreshTokenSql) {
		this.selectRefreshTokenSql = selectRefreshTokenSql;
	}

	public void setDeleteRefreshTokenSql(String deleteRefreshTokenSql) {
		this.deleteRefreshTokenSql = deleteRefreshTokenSql;
	}

	public void setSelectAccessTokenAuthenticationSql(String selectAccessTokenAuthenticationSql) {
		this.selectAccessTokenAuthenticationSql = selectAccessTokenAuthenticationSql;
	}

	public void setSelectRefreshTokenAuthenticationSql(String selectRefreshTokenAuthenticationSql) {
		this.selectRefreshTokenAuthenticationSql = selectRefreshTokenAuthenticationSql;
	}

	public void setSelectAccessTokenFromAuthenticationSql(String selectAccessTokenFromAuthenticationSql) {
		this.selectAccessTokenFromAuthenticationSql = selectAccessTokenFromAuthenticationSql;
	}

	public void setDeleteAccessTokenFromRefreshTokenSql(String deleteAccessTokenFromRefreshTokenSql) {
		this.deleteAccessTokenFromRefreshTokenSql = deleteAccessTokenFromRefreshTokenSql;
	}

}