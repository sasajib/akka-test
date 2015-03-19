package me.webstar.akka.actors;

import akka.actor.UntypedActor;

/**
 * @author sasajib
 */
public class Printer extends UntypedActor
{
  @Override
  public void onReceive(Object message) throws Exception
  {
    if(message instanceof String){
      System.out.println(message);
    }else {
      unhandled(message);
    }
  }
}
