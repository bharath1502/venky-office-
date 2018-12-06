package com.chatak.pay.spring.security;

import java.security.MessageDigest;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

@RunWith(MockitoJUnitRunner.class)
public class JdbcTokenStoreTest {

	@InjectMocks
	JdbcTokenStore jdbcTokenStore;

	@Mock
	DataSource dataSource;

	@Mock
	AuthenticationKeyGenerator authenticationKeyGenerator;

	@Mock
	OAuth2AccessToken token;

	@Mock
	OAuth2RefreshToken refreshToken;

	@Mock
	OAuth2Authentication authentication;

	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Mock
	MessageDigest digest;

	@Test
	public void testSetAuthenticationKeyGenerator() {

		jdbcTokenStore.setAuthenticationKeyGenerator(authenticationKeyGenerator);
	}

	@Test(expected=NullPointerException.class)
	public void testGetAccessToken() {
		jdbcTokenStore.getAccessToken(authentication);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testStoreAccessToken() {
		jdbcTokenStore.storeAccessToken(token, authentication);
	}

	@Test(expected=NullPointerException.class)
	public void testReadAccessToken() {
		jdbcTokenStore.readAccessToken("243");
	}

	@Test(expected=NullPointerException.class)
	public void testRemoveAccessToken() {
		jdbcTokenStore.removeAccessToken(token);
	}

	@Test(expected=NullPointerException.class)
	public void testReadAuthentication() {
		jdbcTokenStore.readAuthentication(token);
	}

	@Test(expected=NullPointerException.class)
	public void testReadAuthenticationString() {
		jdbcTokenStore.readAuthentication("123");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testStoreAccessTokenRefreshToken() {
		jdbcTokenStore.storeRefreshToken(refreshToken, authentication);
	}

	@Test(expected=NullPointerException.class)
	public void testReadRefreshToken() {
		jdbcTokenStore.readRefreshToken("abcd");
	}

	@Test(expected=NullPointerException.class)
	public void testRemoveRefreshToken() {
		jdbcTokenStore.removeRefreshToken(refreshToken);
	}

	@Test(expected=NullPointerException.class)
	public void testRemoveRefreshTokenString() {
		jdbcTokenStore.removeRefreshToken("12345");
	}

	@Test(expected=NullPointerException.class)
	public void testReadAuthenticationForRefreshToken() {
		jdbcTokenStore.readAuthenticationForRefreshToken(refreshToken);
	}

	@Test(expected=NullPointerException.class)
	public void testReadAuthenticationForRefreshTokenString() {
		jdbcTokenStore.readAuthenticationForRefreshToken("1234");
	}

	@Test(expected=NullPointerException.class)
	public void testRemoveAccessTokenUsingRefreshToken() {
		jdbcTokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
	}

	@Test(expected=NullPointerException.class)
	public void testRemoveAccessTokenUsingRefreshTokenString() {
		jdbcTokenStore.removeAccessTokenUsingRefreshToken("abcde");
	}

	@Test(expected=NullPointerException.class)
	public void testFindTokensByClientId() {
		jdbcTokenStore.findTokensByClientId("123");
	}

	@Test(expected=NullPointerException.class)
	public void testFindTokensByUserName() {
		jdbcTokenStore.findTokensByUserName("abcde");
	}

	@Test
	public void testSetInsertAccessTokenSql() {
		jdbcTokenStore.setInsertAccessTokenSql("abcde");
	}

	@Test
	public void testSetSelectAccessTokenSql() {
		jdbcTokenStore.setSelectAccessTokenSql("abcde");
	}

	@Test
	public void testSetDeleteAccessTokenSql() {
		jdbcTokenStore.setDeleteAccessTokenSql("abcde");
	}

	@Test
	public void testSetInsertRefreshTokenSql() {
		jdbcTokenStore.setInsertRefreshTokenSql("abcde");
	}

	@Test
	public void testSetSelectRefreshTokenSql() {
		jdbcTokenStore.setSelectRefreshTokenSql("abcde");
	}

	@Test
	public void testSetDeleteRefreshTokenSql() {
		jdbcTokenStore.setDeleteRefreshTokenSql("abcde");
	}

	@Test
	public void testSetSelectAccessTokenAuthenticationSql() {
		jdbcTokenStore.setSelectAccessTokenAuthenticationSql("abcde");
	}

	@Test
	public void testSetSelectRefreshTokenAuthenticationSql() {
		jdbcTokenStore.setSelectRefreshTokenAuthenticationSql("abcde");
	}

	@Test
	public void testSetSelectAccessTokenFromAuthenticationSql() {
		jdbcTokenStore.setSelectAccessTokenFromAuthenticationSql("abcde");
	}

	@Test
	public void testSetDeleteAccessTokenFromRefreshTokenSql() {
		jdbcTokenStore.setDeleteAccessTokenFromRefreshTokenSql("abcde");
	}

}
