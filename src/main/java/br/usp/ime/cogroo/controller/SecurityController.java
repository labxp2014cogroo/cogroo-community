package br.usp.ime.cogroo.controller;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.usp.ime.cogroo.dao.UserDAO;
import br.usp.ime.cogroo.logic.SecurityUtil;
import br.usp.ime.cogroo.logic.TextSanitizer;
import br.usp.ime.cogroo.util.RestUtil;

/**
 * Provides security interface for OpenOffice plugin.
 * 
 * @author William Colen
 * 
 */
@Resource
public class SecurityController {

	private static final Logger LOG = Logger
			.getLogger(SecurityController.class);

	private SecurityUtil securityUtil;
	private Result result;
	private Validator validator;

	private UserDAO userDAO;

	private TextSanitizer sanitizer;

	public SecurityController(Result result, Validator validator,
			SecurityUtil securityUtil, UserDAO userDAO, TextSanitizer sanitizer) {
		this.result = result;
		this.validator = validator;
		this.securityUtil = securityUtil;
		this.userDAO = userDAO;
		this.sanitizer = sanitizer;
	}

	@Post
	@Path("/saveClientSecurityKey")
	public void saveClientSecurityKey(String user, String pubKey) {
		user = sanitizer.sanitize(user, false);
		LOG.debug("Saving pubkey for user: " + user
				+ ". Will prepare a secret key for user send the password.");
		try {
			if (this.userDAO.existLogin("cogroo", user)) {
				String key = this.securityUtil.genSecretKeyForUser(
						this.userDAO.retrieveByLogin("cogroo", user),
						this.securityUtil.decodeURLSafe(pubKey));
				result.include("encryptedSecretKey",
						RestUtil.prepareResponse("encryptedSecretKey", key));
			} else {
				LOG.error("Unknown user trying to save security key");
				result.include("error",
						RestUtil.prepareResponse("error", "INVALID_USER"));
			}

		} catch (Throwable e) {
			LOG.error("Got an invalid key from user: " + user, e);
			result.include("error",
					RestUtil.prepareResponse("error", "INVALID_USER"));
		}

	}

	/**
	 * Method used by OOo plug-in
	 * 
	 * @param username
	 * @param encryptedPassword
	 */
	@Post
	@Path("/generateAuthenticationForUser")
	public void generateAuthenticationForUser(String username,
			String encryptedPassword) {
		username = sanitizer.sanitize(username, false);
		try {
			if (this.userDAO.existLogin("cogroo", username)) {
				LOG.debug("Will generate token for " + username);
				String token = this.securityUtil
						.generateAuthenticationTokenForUser(this.userDAO
								.retrieveByLogin("cogroo", username),
								securityUtil.decodeURLSafe(encryptedPassword));
				if (token != null) {
					result.include("token",
							RestUtil.prepareResponse("token", token));
				} else {
					result.include("error",
							RestUtil.prepareResponse("error", "INVALID_USER"));
				}
			} else {
				LOG.error("Unknown user trying to authenticate.");
				result.include("error",
						RestUtil.prepareResponse("error", "INVALID_USER"));
			}

		} catch (Exception e) {
			LOG.error("Got an invalid key from user: " + username, e);
			result.include("error",
					RestUtil.prepareResponse("error", "INVALID_USER"));
		}

	}

}
