package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.api.client.http.HttpRequestFactory;

import helpers.Constants;
import models.twitterApi.Tweet;
import services.TweetsFilterService;
import services.exceptions.TwitterMessagesInquiryException;
import services.impl.TweetsFilterServiceImpl;

class TweetsFilterServiceTest {
	
	private static TweetsFilterService service ;
	
	@BeforeAll
	static void init() {
		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("testOutput.txt"));
		
			TwitterAuthenticator t = new TwitterAuthenticator(out , Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
			HttpRequestFactory reqFactory = t.getAuthorizedHttpRequestFactory();
			
			service = new TweetsFilterServiceImpl(reqFactory);
		} catch (TwitterAuthenticationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	@Test
	void validResponseTest() {
		try {
			Optional<List<Tweet>> tweets = service.getTweetsByTrack("bieber");
			assert(tweets.isPresent());
			assert(!tweets.get().isEmpty());
			
		} catch (TwitterMessagesInquiryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void invalidParameterTest(){
		assertThrows(TwitterMessagesInquiryException.class, () -> {
			service.getTweetsByTrack("too long input for track to make the API throw tooLong parameter error");
		});
		
	}
	
	@Test
	void invalidServiceParameter() {
		HttpRequestFactory reqFactory = null;;
		TweetsFilterService corruptedService = new TweetsFilterServiceImpl(reqFactory);
		assertThrows(TwitterMessagesInquiryException.class, () -> {
			corruptedService.getTweetsByTrack("bieber");
		});
	}

}
