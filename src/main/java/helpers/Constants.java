package helpers;

public class Constants {
	
    public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
    public static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
    public static final String REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token";
	
	public static final String CONSUMER_KEY = "RLSrphihyR4G2UxvA0XBkLAdl";
	public static final String CONSUMER_SECRET = "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4";
	
	public static final String TWITTER_TRACK_BASE_URL = "https://stream.twitter.com/1.1/statuses/filter.json?";
	public static final String TRACK_PARAM_KEY = "track=";
	
	public static final int MAX_ACCEPTED_TWEETS = 100;
	public static final long MAX_WAITING_TIME_IN_SECONDS = 30L;
	
	public static final String DATE_FORMATE = "E MMM d HH:mm:ss Z yyyy";
}
