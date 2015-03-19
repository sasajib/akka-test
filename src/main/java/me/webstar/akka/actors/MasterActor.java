package me.webstar.akka.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import me.webstar.akka.messages.MapData;
import me.webstar.akka.messages.ReduceData;
import me.webstar.akka.messages.Result;

/**
 * @author sasajib
 */
public class MasterActor extends UntypedActor
{

  ActorRef mapActor = getContext().actorOf(Props.create(MapActor.class), "MapActor");
  ActorRef reduceActor = getContext().actorOf(Props.create(ReduceActor.class), "ReduceActor");
  ActorRef aggregateActor = getContext().actorOf(Props.create(AggregateActor.class), "AgregateActor");

  @Override
  public void onReceive(Object message) throws Exception
  {
    System.out.println("Master Actor received :" + message);

    if (message instanceof String) {
      mapActor.tell(message, getSelf());
    } else if (message instanceof MapData) {
      reduceActor.tell(message, getSelf());
    } else if (message instanceof ReduceData) {
      aggregateActor.tell(message, getSelf());
    } else if (message instanceof Result) {
      aggregateActor.forward(message, getContext());
    } else {
      unhandled(message);
    }
  }
}
