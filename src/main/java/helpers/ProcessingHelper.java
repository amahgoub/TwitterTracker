package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.twitterApi.Tweet;
import models.twitterApi.User;
import models.view.Author;
import models.view.TwitterMessage;

/**
 * @author ahmed
 * This Class for separating the processing logic of the converting the API models to the application view model.
 *
 */
public class ProcessingHelper {
	
	private static final Logger logger = Logger.getLogger(ProcessingHelper.class.getName());
	private static Map<Long, Integer> tweetsCountsPerSecond = new HashMap<Long, Integer>();
	
	/**
	 * This method for converting the list of twitter's API tweets object to list of our view object Author
	 * after merging the all the related author's twitter messages inside the author object.
	 * @param tweets list of the tweets returned from the twitter streaming API/
	 * @return list of authors that will be of the accepted view format which author holds all his tweets.
	 */
	public static List<Author> processTweets(List<Tweet> tweets){
		List<Author> authors = new ArrayList<Author>();
		Map<User, List<Tweet>> tweetsMap = tweets.stream().collect(Collectors.groupingBy(Tweet::getUser, Collectors.toList()));
		
		authors = tweetsMap.entrySet().stream()
					.map(ProcessingHelper::convertToAuthor)
					.sorted((author1, author2) -> (int)(author1.getCreationEpoch() - author2.getCreationEpoch()))
					.collect(Collectors.toList());
					
		
		return authors;
	}
	
	/**
	 * @param entry grouping of all the tweets by user
	 * @return list of Authors containing list of TwitterMessages inside it 
	 */
	private static Author convertToAuthor(Map.Entry<User, List<Tweet>> entry) {
		List<TwitterMessage> messges = entry.getValue().stream()
				.map(tweet -> {
					return TwitterMessage.newBuilder()
							.setId(tweet.getId())
							.setCreationEpoch(tweet.getCreationDate().toEpochSecond(ZoneOffset.UTC))
							.setText(tweet.getText())
							.build();
				})
				.sorted((message1, message2) -> (int)(message1.getCreationEpoch() - message2.getCreationEpoch()))
				.collect(Collectors.toList());
		
		return Author.newBuilder()
							.setId(entry.getKey().getId())
							.setCreationEpoch(entry.getKey().getCreationDate().toEpochSecond(ZoneOffset.UTC))
							.setName(entry.getKey().getName())
							.setScreenName(entry.getKey().getScreenName())
							.setOwnedMessages(messges)
							.build();
	}

	/**
	 * This method for writing the authors as json in the log and in a file if provided
	 * @param authors List of the authors to be written as json
	 * @param storageFile an optional file to store the json representation of the authors in it.
	 */
	public static void writeAuthors(List<Author> authors, Optional<File> storageFile) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonAuthors = objectMapper.writeValueAsString(authors);
			
			
			
			if(storageFile.isPresent()) {
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(storageFile.get()))){
					bw.write(jsonAuthors);
					System.out.println("The result data has been written as json in the following file: "+storageFile.get().getAbsolutePath());
				}
			}else {
				logger.log(Level.INFO, jsonAuthors);
			}
			
		} catch (JsonProcessingException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
	
	public static void logTweetTime(long tweetSecondEpoch) {
		tweetsCountsPerSecond.put(tweetSecondEpoch, tweetsCountsPerSecond.getOrDefault(tweetSecondEpoch, 0) + 1);
	}
	
	/**
	 * This method appends the current run statistics in the file statistics.
	 * Right now, I store the number of request per seconds. I should store an avg number or median 
	 * This way of storing all the statistics in the file is not the best way because during time the file 
	 * will become too large. instead we can store only final avg or median or we can store the statistics in database.
	 */
	public static void saveStatistics() {
		try {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter("statistics.txt", true))) {
				PrintWriter pw = new PrintWriter(bw);
				tweetsCountsPerSecond.entrySet().stream().forEach(e -> {
						pw.append(e.getKey() + " : " + e.getValue() + "\n");
				});
			}
			logger.log(Level.INFO, "Number of receieved messages per second has been written in a statistics.txt file ");
			// reset the map 
			tweetsCountsPerSecond = new HashMap<Long, Integer>();
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to open the statistics file because of " + e.getMessage());
		}
		
	}
}
