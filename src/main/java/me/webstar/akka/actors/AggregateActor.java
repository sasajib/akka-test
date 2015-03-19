package me.webstar.akka.actors;

import akka.actor.UntypedActor;
import java.util.HashMap;
import java.util.Map;
import me.webstar.akka.messages.ReduceData;
import me.webstar.akka.messages.Result;

/**
 * @author sasajib
 */
public class AggregateActor extends UntypedActor
{
  private Map<String, Integer> finalData = new HashMap<>();

  @Override
  public void onReceive(Object message) throws Exception
  {
    System.out.println("Aggregator Actor received :" + message);
    if (message instanceof ReduceData) {
      ReduceData reduceData = (ReduceData) message;
      aggregate(reduceData.getReducedDataList());
    } else if (message instanceof Result) {
      getSender().tell(finalData.toString(), getSelf());
    } else {
      unhandled(message);
    }
  }

  private void aggregate(Map<String, Integer> reduceData)
  {
    for (String key : reduceData.keySet()) {
      if (finalData.containsKey(key)) {
        finalData.put(key, reduceData.get(key) + finalData.get(key));
      } else {
        finalData.put(key, reduceData.get(key));
      }
    }
  }
}
