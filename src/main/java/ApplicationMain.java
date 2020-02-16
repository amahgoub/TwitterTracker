import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterAuthenticator;

import com.google.api.client.http.HttpRequestFactory;

import helpers.Constants;
import helpers.ProcessingHelper;
import models.twitterApi.Tweet;
import models.view.Author;
import services.TweetsFilterService;
import services.exceptions.TwitterMessagesInquiryException;
import services.impl.TweetsFilterServiceImpl;

public class ApplicationMain {
	private static final Logger logger = Logger.getLogger(ApplicationMain.class.getName());
	
	public static void main(String[] args) {
		try {
			File file = new File("console.txt");
			PrintStream out = new PrintStream(new FileOutputStream(file));
			TwitterAuthenticator t = new TwitterAuthenticator(out , Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
			
			HttpRequestFactory reqFactory = t.getAuthorizedHttpRequestFactory();
			
			TweetsFilterService filterService = new TweetsFilterServiceImpl(reqFactory);
			
			Optional<List<Tweet>> tweets = filterService.getTweetsByTrack("bieber");
			List<Author> authors = new ArrayList<Author>();
			
			if(tweets.isPresent()) {
				authors = ProcessingHelper.processTweets(tweets.get());
				
				File outputFile = new File("data.json");
				ProcessingHelper.writeAuthors(authors, Optional.of(outputFile));
				
				ProcessingHelper.saveStatistics();
			}else {
				logger.log(Level.WARNING, "No tweets returned!");
			}
			
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
		
		} catch (TwitterAuthenticationException e) {
			logger.log(Level.SEVERE, e.getMessage());
		
		} catch (TwitterMessagesInquiryException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
}
