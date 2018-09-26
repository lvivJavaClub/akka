package com.lohika.jclub;

import akka.actor.*;

public class GameSupervisorActor extends AbstractLoggingActor {

    // protocol
    public static class NewGame {
        String name;

        public NewGame(String name) {
            this.name = name;
        }
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(NewGame.class, this::onNewGame)
                .build();
    }

    private void onNewGame(NewGame newGame) {
        log().info("New game started: " + newGame.name);
        ActorRef actorRef = context().actorOf(PlayerActor.props());
        actorRef.forward(newGame, context());
    }

    public static Props props() {
        return Props.create(GameSupervisorActor.class);
    }
}
