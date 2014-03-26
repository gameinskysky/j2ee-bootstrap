package me.zjor.auth.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Contains information from user's profile from social networks.
 *
 * @author: Sergey Royz
 * Date: 26.03.2014
 */
@Data
@Entity
@Table(name = "social_profile")
public class SocialProfile {

	public enum SocialNetwork {
		FACEBOOK;
	}

	@Id
	@Column(name = "profile_id", unique = true)
	private String profileId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private AuthUser user;

	@Enumerated(EnumType.STRING)
	@Column(name = "social_network", nullable = false)
	private SocialNetwork socialNetwork = SocialNetwork.FACEBOOK;

	@Column(name = "first_name", nullable = true)
	private String firstName;

	@Column(name = "last_name", nullable = true)
	private String lastName;

	@Column(name = "username", nullable = true)
	private String username;

	@Column(name = "avatar_url", nullable = true)
	private String avatarURL;

}
