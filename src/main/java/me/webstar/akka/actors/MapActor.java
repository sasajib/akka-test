package me.webstar.akka.actors;

import akka.actor.UntypedActor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import me.webstar.akka.messages.MapData;
import me.webstar.akka.messages.WordCount;

/**
 * @author sasajib
 */
public class MapActor extends UntypedActor
{
  private final String[] STOP_WORDS = {"a", "am", "an", "and", "are", "as", "at",
      "be", "do", "go", "if", "in", "is", "it", "of", "on", "the", "to"};
  private final List<String> STOP_WORDS_LIST = Arrays.asList(STOP_WORDS);

  @Override
  public void onReceive(Object message) throws Exception
  {
    System.out.println("Map Actor received :" + message);
    if (message instanceof String) {
      String sentence = (String) message;
      MapData mapData = evaluateSentence(sentence);
      getSender().tell(mapData, getSelf());
    } else {
      unhandled(message);
    }
  }

  private MapData evaluateSentence(String sentence)
  {
    List<WordCount> mapList = new ArrayList<>();
    StringTokenizer words = new StringTokenizer(sentence);
    while (words.hasMoreTokens()) {
      String word = words.nextToken().toLowerCase();
      if (!STOP_WORDS_LIST.contains(word)) {
        mapList.add(new WordCount(word, Integer.valueOf(1)));
      }
    }
    return new MapData(mapList);
  }
}
