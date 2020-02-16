package services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

import helpers.Constants;
import helpers.ProcessingHelper;
import models.twitterApi.Tweet;
import services.TweetsFilterService;
import services.exceptions.TwitterMessagesInquiryException;
import services.lov.ResponseStatus;

/**
 * @author ahmed
 *	A concrete class to implement the TweetsFilterService interface. 
 */
public class TweetsFilterServiceImpl implements TweetsFilterService{

	private static final Logger logger = Logger.getLogger(TweetsFilterServiceImpl.class.getName()); 
	private final HttpRequestFactory reqFactory;
	
	public TweetsFilterServiceImpl(HttpRequestFactory reqFactory) {
		this.reqFactory = reqFactory;
	}
	
	@Override
	public Optional<List<Tweet>> getTweetsByTrack(String track) throws TwitterMessagesInquiryException{
		
		try {
			GenericUrl trackEndPointURL = new GenericUrl(Constants.TWITTER_TRACK_BASE_URL + Constants.TRACK_PARAM_KEY + track);
			HttpContent content =  ByteArrayContent.fromString("application/json", "");
			
			HttpRequest request = reqFactory.buildPostRequest(trackEndPointURL, content);
			HttpResponse response = request.execute();
			
			if(response.getStatusCode() != ResponseStatus.SUCCESS.getCode()) {
				String message = ResponseStatus.getByCode(response.getStatusCode()).getMessage();
				throw new TwitterMessagesInquiryException(message);
			}else {
				System.out.println("The service has connected successfully with the Twitter. It may take up to 30 seconds to provide the result. Please wait!");
			}
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getContent()));

			final long end = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(Constants.MAX_WAITING_TIME_IN_SECONDS);
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			
			List<Tweet> tweets = bufferedReader.lines()
					.limit(Constants.MAX_ACCEPTED_TWEETS)
					.peek((e) -> ProcessingHelper.logTweetTime(Instant.now().getEpochSecond()))
					.takeWhile(e -> System.currentTimeMillis() <= end)
					.map(jsonTweet -> {
						try {
							return objectMapper.readValue(jsonTweet, Tweet.class);
						} catch (JsonMappingException e1) {
							logger.log(Level.SEVERE, e1.getMessage());
							return null;
						} catch (JsonProcessingException e1) {
							logger.log(Level.SEVERE, e1.getMessage());
							return null;
						}
					})
					.collect(Collectors.toList());
			
			return Optional.of(tweets);
		} catch (Exception e) {
			throw new TwitterMessagesInquiryException(e);
		}
	}
	
}
