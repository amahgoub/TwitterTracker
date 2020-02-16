package models.twitterApi;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import utils.LocalDateTimeDeserializer;
/*
 * The message ID
	The creation date of the message as epoch value
	The text of the message
	The author of the message
 * */
public class Tweet {
	public static final String DATE_FORMATE = "E MMM d HH:mm:ss Z yyyy";
	
	
	private String id;
	
	private LocalDateTime creationDate;
	
	private String text;
	private User user;
	
	@JsonCreator
	public Tweet(@JsonProperty("id_str") String id,
					@JsonProperty("created_at") @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime creationDate,
					@JsonProperty("text") String text,
					@JsonProperty("user") User user) {
		this.id = id;
		this.creationDate = creationDate;
		this.text = text;
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public String getText() {
		return text;
	}

	public User getUser() {
		return user;
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Tweet other = (Tweet)obj;
		return this.getId().equals(other.getId());
	}

	@Override
	public String toString() {
		return " [ "+ id +" - "+ creationDate +" - "+ text +" - "+ user +" ] ";
	}
	
	
}
