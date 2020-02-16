package models.twitterApi;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import utils.LocalDateTimeDeserializer;
/*
 * The user ID
 * The creation date of the user as epoch value
 * The name of the user
 * The screen name of the user
 * */
public class User {
	private String id;
	
	private LocalDateTime creationDate;
	
	private String name;
	private String screenName;
	
	@JsonCreator
	public User(@JsonProperty("id_str") String id,
				@JsonProperty("created_at") @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime creationDate,
				@JsonProperty("name") String name,
				@JsonProperty("screen_name") String screenName) {
		this.id = id;
		this.creationDate = creationDate;
		this.name = name;
		this.screenName = screenName;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		User other = (User)obj;
		return this.getId().equals(other.getId());
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public String toString() {
		return " [ "+ id +" - "+ creationDate +" - "+ name +" - "+ screenName +" ] ";
	}
	
	
}
