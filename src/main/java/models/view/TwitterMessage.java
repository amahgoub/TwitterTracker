package models.view;

public class TwitterMessage {
	private final String id;
	private final long creationEpoch;
	private final String text;
	
	private TwitterMessage(String id, long creationEpoch, String text) {
		this.id = id;
		this.creationEpoch = creationEpoch;
		this.text = text;
	}
	
	public String getId() {
		return id;
	}

	public long getCreationEpoch() {
		return creationEpoch;
	}

	public String getText() {
		return text;
	}
	
	public static TwitterMessageBuilder newBuilder() {
		return new TwitterMessageBuilder(); 
	}
	
	@Override
	public String toString() {
		return " [ epoch: "+creationEpoch+" - "+ text +" ] ";
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TwitterMessage)) {
			return false;
		}
		TwitterMessage other = (TwitterMessage)obj;
		return getId().equals(other.getId());
	}



	public static class TwitterMessageBuilder{
		private String id;
		private long creationEpoch;
		private String text;
		
		private TwitterMessageBuilder() {
			
		}
		
		public TwitterMessageBuilder setId(String id) {
			this.id = id;
			return this;
		}
		
		public TwitterMessageBuilder setCreationEpoch(long creationEpoch) {
			this.creationEpoch = creationEpoch;
			return this;
		}
		
		public TwitterMessageBuilder setText(String text) {
			this.text = text;
			return this;
		}
		
		public TwitterMessage build() {
			return new TwitterMessage(this.id, this.creationEpoch, this.text);
		}
		
	}
}
