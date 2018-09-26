package com.lohika.jclub;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;


public class Main  {
    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("GameActorSystem");

        ActorRef gameSupervisor = system.actorOf(GameSupervisorActor.props(), "game-supervisor-actor");
        ActorRef webPlayer = system.actorOf(WebPlayerActor.props(gameSupervisor), "web-player-actor-" + System.currentTimeMillis());

        System.out.println("First: " + gameSupervisor);

        System.out.println(">>> Press ENTER to exit <<<");
        try {
            System.in.read();
        } finally {
            system.terminate();
        }
    }
}
