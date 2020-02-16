package unitTesting;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import helpers.ProcessingHelper;
import models.twitterApi.Tweet;
import models.twitterApi.User;
import models.view.Author;
import models.view.TwitterMessage;

class ProcessingHelperTest {
	
	private static List<Tweet> tweets;
	private static Map<User, List<Tweet>> userMap;
	
	@BeforeAll
	static void init() {
		User user1 = new User("123", LocalDateTime.of(2020, 02, 14, 11, 30, 20), "ahmed", "ahmedsaleh");
		User user2 = new User("567", LocalDateTime.of(2020, 02, 13, 11, 30, 20), "mark", "markooo");
		User user3 = new User("111", LocalDateTime.of(2020, 02, 15, 11, 30, 20), "zaid", "zizo");
		User user4 = new User("333", LocalDateTime.of(2020, 02, 11, 11, 30, 20), "aline", "aline");
		
		Tweet tweet1 = new Tweet("1111", LocalDateTime.of(2020, 02, 14, 11, 30, 20), "tweet1 text messge", user1);
		Tweet tweet2 = new Tweet("2222", LocalDateTime.of(2020, 02, 14, 12, 30, 20), "tweet2 text messge", user2);
		Tweet tweet3 = new Tweet("3333", LocalDateTime.of(2020, 02, 14, 11, 30, 21), "tweet3 text messge", user1);
		Tweet tweet4 = new Tweet("4444", LocalDateTime.of(2020, 02, 14, 14, 30, 20), "tweet4 text messge", user3);
		Tweet tweet5 = new Tweet("5555", LocalDateTime.of(2020, 02, 14, 11, 30, 20), "tweet5 text messge", user4);
		Tweet tweet6 = new Tweet("6666", LocalDateTime.of(2020, 02, 14, 15, 30, 20), "tweet6 text messge", user2);
		Tweet tweet7 = new Tweet("7777", LocalDateTime.of(2020, 02, 14, 12, 30, 20), "tweet7 text messge", user1);
		Tweet tweet8 = new Tweet("8888", LocalDateTime.of(2020, 02, 14, 11, 31, 20), "tweet8 text messge", user1);
		
		tweets = Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8);
		
		userMap = new HashMap<User, List<Tweet>>();
		userMap.put(user1, Arrays.asList(tweet1, tweet3, tweet7, tweet8));
		userMap.put(user2, Arrays.asList(tweet2, tweet6));
		userMap.put(user3, Arrays.asList(tweet4));
		userMap.put(user4, Arrays.asList(tweet5));
		
		
		
	}


	@Test
	void processTweetAuthorListCountTest() {
		List<Author> authors = ProcessingHelper.processTweets(tweets);
		assertEquals(4, authors.size());
	}
	
	@Test
	void processTweetAuthorMessagesGroupingAndOrderingTest() {
		List<Author> authors = ProcessingHelper.processTweets(tweets);
		for(Author author : authors) {
			if(author.getId().equals("123")) { // user 1 id
				assertThat(author.getOwnedMessages(), contains(
						TwitterMessage.newBuilder()
									.setId("1111")
									.setCreationEpoch( LocalDateTime.of(2020, 02, 14, 11, 30, 20).toEpochSecond(ZoneOffset.UTC))
									.setText("tweet1 text messge")
									.build(),
						TwitterMessage.newBuilder()
									.setId("3333")
									.setCreationEpoch( LocalDateTime.of(2020, 02, 14, 11, 30, 21).toEpochSecond(ZoneOffset.UTC))
									.setText("tweet3 text messge")
									.build(),
						TwitterMessage.newBuilder()
									.setId("8888")
									.setCreationEpoch( LocalDateTime.of(2020, 02, 14, 11, 31, 20).toEpochSecond(ZoneOffset.UTC))
									.setText("tweet8 text messge")
									.build(),
						TwitterMessage.newBuilder()
									.setId("7777")
									.setCreationEpoch( LocalDateTime.of(2020, 02, 14, 12, 30, 20).toEpochSecond(ZoneOffset.UTC))
									.setText("tweet7 text messge")
									.build()));
			}
		}		
	}
	
	
	@Test
	void processTweetAuthorListOrderChornologicallyTest() {
		List<Author> authors = ProcessingHelper.processTweets(tweets);
		assertThat(authors, contains(
				Author.newBuilder()
						.setId("333")
						.setCreationEpoch(LocalDateTime.of(2020, 02, 11, 11, 30, 20).toEpochSecond(ZoneOffset.UTC))
						.setName("aline")
						.setScreenName("aline")
						.build(),
				Author.newBuilder()
						.setId("567")
						.setCreationEpoch(LocalDateTime.of(2020, 02, 13, 11, 30, 20).toEpochSecond(ZoneOffset.UTC))
						.setName("mark")
						.setScreenName("markooo")
						.build(),
				Author.newBuilder()
						.setId("123")
						.setCreationEpoch(LocalDateTime.of(2020, 02, 14, 11, 30, 20).toEpochSecond(ZoneOffset.UTC))
						.setName("ahmed")
						.setScreenName("ahmedsaleh")
						.build(),
				Author.newBuilder()
						.setId("111")
						.setCreationEpoch(LocalDateTime.of(2020, 02, 15, 11, 30, 20).toEpochSecond(ZoneOffset.UTC))
						.setName("zaid")
						.setScreenName("zizo")
						.build()));
		
	}
}
