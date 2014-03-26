package me.zjor.auth.social;

import lombok.Data;
import me.zjor.auth.model.SocialProfile;

/**
 * @author: Sergey Royz
 * Date: 26.03.2014
 */
@Data
public class SocialProfileDTO {

	private String profileId;

	private SocialProfile.SocialNetwork socialNetwork;

	private String firstName;

	private String lastName;

	private String username;

	private String avatarURL;

}
