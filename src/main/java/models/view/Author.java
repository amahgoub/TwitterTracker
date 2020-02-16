package models.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Author {
	private final String id;
	private final long creationEpoch;
	private final String name;
	private final String screenName;
	private final List<TwitterMessage> ownedMessages;
	
	private Author(String id, long creationEpoch, 
			String name, String screenName, List<TwitterMessage> ownedMessages) {
		this.id = id;
		this.creationEpoch = creationEpoch;
		this.name = name;
		this.screenName = screenName;
		this.ownedMessages = ownedMessages;
	}
	
	public String getId() {
		return id;
	}

	public long getCreationEpoch() {
		return creationEpoch;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}
	
	public List<TwitterMessage> getOwnedMessages() {
		return ownedMessages;
	}

	public static AuthorBuilder newBuilder() {
		return new AuthorBuilder();
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Author other = (Author)obj;
		return getId().equals(other.getId());
	}

	@Override
	public String toString() {
		
		return " [ id: " + id + "epoch:" + creationEpoch + " name:"+ name +" - screenName: "+ screenName +" ] -> " +
					ownedMessages.size() + ownedMessages.stream().map(String::valueOf).collect(Collectors.joining(" - "));
	}



	public static class AuthorBuilder {
		private String id;
		private long creationEpoch;
		private String name;
		private String screenName;
		private List<TwitterMessage> ownedMessages;
		
		
		private AuthorBuilder() {
			ownedMessages = new ArrayList<TwitterMessage>();
		}

		public AuthorBuilder setId(String id) {
			this.id = id;
			return this;
		}

		public AuthorBuilder setCreationEpoch(long creationEpoch) {
			this.creationEpoch = creationEpoch;
			return this;
		}

		public AuthorBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public AuthorBuilder setScreenName(String screenName) {
			this.screenName = screenName;
			return this;
		}
		
		public AuthorBuilder setOwnedMessages(List<TwitterMessage> ownedMessages) {
			this.ownedMessages = ownedMessages;
			return this;
		}

		public Author build() {
			return new Author(this.id, this.creationEpoch, this.name, this.screenName, this.ownedMessages);
		}
		
		
	}
}
