package me.zjor.auth.service;

import me.zjor.auth.model.AuthUser;
import me.zjor.auth.model.SocialProfile;
import me.zjor.auth.social.SocialProfileDTO;

/**
 * @author: Sergey Royz
 * Date: 26.03.2014
 */
public interface SocialProfileService {

	SocialProfile registerUser(SocialProfileDTO dto);

	boolean isRegistered(SocialProfileDTO dto);

	AuthUser authenticate(SocialProfileDTO dto);

	SocialProfile findByAuthUser(AuthUser authUser);

	SocialProfile findByAuthUser(String userId);

}
