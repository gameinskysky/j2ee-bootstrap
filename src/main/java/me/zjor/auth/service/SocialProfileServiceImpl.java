package me.zjor.auth.service;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import me.zjor.auth.manager.AuthUserManager;
import me.zjor.auth.manager.SocialProfileManager;
import me.zjor.auth.model.AuthUser;
import me.zjor.auth.model.SocialProfile;
import me.zjor.auth.social.SocialProfileDTO;

/**
 * @author: Sergey Royz
 * Date: 26.03.2014
 */
public class SocialProfileServiceImpl implements SocialProfileService {

	@Inject
	private AuthUserManager authUserManager;

	@Inject
	private SocialProfileManager socialProfileManager;

	@Override
	@Transactional
	public SocialProfile registerUser(SocialProfileDTO dto) {
		AuthUser user = authUserManager.create(getLogin(dto), null);
		return socialProfileManager.create(user, dto);
	}

	@Override
	public boolean isRegistered(SocialProfileDTO dto) {
		return authUserManager.findByLogin(getLogin(dto)) != null;
	}

	@Override
	@Transactional
	public AuthUser authenticate(SocialProfileDTO dto) {
		AuthUser user = authUserManager.findByLogin(getLogin(dto));
		if (user != null) {
			user.touch();
		}

		return user;
	}

	@Override
	public SocialProfile findByAuthUser(AuthUser authUser) {
		return socialProfileManager.findByUserId(authUser.getId());
	}

	@Override
	public SocialProfile findByAuthUser(String userId) {
		return socialProfileManager.findByUserId(userId);
	}

	private String getLogin(SocialProfileDTO dto) {
		return String.format("%s_%s", dto.getSocialNetwork(), dto.getProfileId());
	}
}
