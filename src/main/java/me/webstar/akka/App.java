package me.webstar.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.util.concurrent.TimeUnit;
import me.webstar.akka.actors.MasterActor;
import me.webstar.akka.actors.Printer;
import me.webstar.akka.messages.Result;
import scala.concurrent.Future;

/**
 * @author sasajib
 */
public class App
{
  public static void main(String[] args) throws Exception
  {
    Timeout timeout = new Timeout(5, TimeUnit.DAYS);
    ActorSystem app = ActorSystem.create("MapReduceApp");
    ActorRef master = app.actorOf(Props.create(MasterActor.class), "MasterActor");
    ActorRef printer = app.actorOf(Props.create(Printer.class), "Printer");
    System.out.println("Sending messages...");
    master.tell("The quick brown fox tried to jump over the lazy dog and fell on the dog", ActorRef.noSender());
    master.tell("Dog is man's best friend", ActorRef.noSender());
    master.tell("Dog and Fox belong to the same family", ActorRef.noSender());
    printMessage(timeout, app, master, printer);
    System.out.println("Taking break==");
    Thread.sleep(200);
    master.tell("A quick brown fox jumps over the lazy dog", ActorRef.noSender());
    printMessage(timeout, app, master, printer);
    app.shutdown();
    System.out.println(app.uptime());
  }

  private static void printMessage(Timeout timeout, ActorSystem app, ActorRef master, ActorRef printer) throws InterruptedException
  {
    Thread.sleep(1000);
    Future<Object> future = Patterns.ask(master, new Result(), timeout);
    Patterns.pipe(future, app.dispatcher()).to(printer);
  }

}
