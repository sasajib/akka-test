package me.webstar.akka.actors;

import akka.actor.UntypedActor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.webstar.akka.messages.MapData;
import me.webstar.akka.messages.ReduceData;
import me.webstar.akka.messages.WordCount;

/**
 * @author sasajib
 */
public class ReduceActor extends UntypedActor
{
  @Override
  public void onReceive(Object message) throws Exception
  {
    System.out.println("Reduce Actor received :" + message);
    if (message instanceof MapData) {
      MapData mapData = (MapData) message;
      ReduceData reduceDataList = reduce(mapData.getDataList());
      getSender().tell(reduceDataList, getSelf());
    } else {
      unhandled(message);
    }
  }

  private ReduceData reduce(List<WordCount> mapData)
  {
    Map<String, Integer> reduceDataList = new HashMap<>();
    for (WordCount wordCount : mapData) {
      if (reduceDataList.containsKey(wordCount.getWord())) {
        Integer count = wordCount.getCount();
        reduceDataList.put(wordCount.getWord(), count++);
      } else {
        reduceDataList.put(wordCount.getWord(), 1);
      }
    }
    return new ReduceData(reduceDataList);
  }
}
