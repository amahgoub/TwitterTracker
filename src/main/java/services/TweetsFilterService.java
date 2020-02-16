package services;

import java.util.List;
import java.util.Optional;
import models.twitterApi.Tweet;
import services.exceptions.TwitterMessagesInquiryException;

/**
 * @author ahmed
 * An interface for the inquiry service that will collect the tweets from the twitter streaming API
 */
public interface TweetsFilterService {
	/**
	 * This method will call the twitter API to inquire a list of tweets contains the keyword "track"
	 * @param track the keyword that will filter the tweets by it
	 * @return list of all tweets that contains the keyword "track"
	 * @throws TwitterMessagesInquiryException an exception in case of a failure response from the twitter API
	 */
	public Optional<List<Tweet>> getTweetsByTrack(String track) throws TwitterMessagesInquiryException;
}
