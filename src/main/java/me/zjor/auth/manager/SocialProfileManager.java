package me.zjor.auth.manager;

import com.google.inject.persist.Transactional;
import me.zjor.auth.model.AuthUser;
import me.zjor.auth.model.SocialProfile;
import me.zjor.auth.social.SocialProfileDTO;
import me.zjor.manager.AbstractManager;
import me.zjor.util.JpaQueryUtils;

import javax.persistence.TypedQuery;

/**
 * @author: Sergey Royz
 * Date: 26.03.2014
 */
//TODO: move @Transactional methods to service
//TODO: extract interfaces
public class SocialProfileManager extends AbstractManager {

	@Transactional
	public SocialProfile create(AuthUser authUser, SocialProfileDTO dto) {

		SocialProfile profile = new SocialProfile();
		profile.setProfileId(dto.getProfileId());
		profile.setUser(authUser);
		profile.setUsername(dto.getUsername());
		profile.setFirstName(dto.getFirstName());
		profile.setLastName(dto.getLastName());
		profile.setAvatarURL(dto.getAvatarURL());
		jpa().persist(profile);

		return profile;
	}

	public SocialProfile findByUserId(String userId) {
		TypedQuery<SocialProfile> query = jpa()
				.createQuery("SELECT p FROM SocialProfile p WHERE p.user.id = :uid", SocialProfile.class)
				.setParameter("uid", userId);
		return JpaQueryUtils.getFirstOrNull(query);
	}

}
