package com.lohika.jclub;

import akka.actor.*;

public class GameSupervisorActor extends AbstractLoggingActor {

    public Receive createReceive() {
        return receiveBuilder()
                .match(MessagingProtocol.NewGameRequest.class, this::onNewGame)
                .build();
    }

    private void onNewGame(MessagingProtocol.NewGameRequest newGameRequest) {
        log().info("New game started: " + newGameRequest.name);

        if (context().child(newGameRequest.userId).isEmpty()) {
            ActorRef actorRef = context().actorOf(PlayerActor.props(), newGameRequest.userId);
            actorRef.forward(newGameRequest, context());
        } else {
            sender().tell(new MessagingProtocol.NewGameResponse("error", "Already connected"), self());
        }
    }

    public static Props props() {
        return Props.create(GameSupervisorActor.class);
    }
}
