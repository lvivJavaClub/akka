package com.lohika.jclub;

import akka.actor.AbstractLoggingActor;
import akka.actor.PoisonPill;
import akka.actor.Props;

public class PlayerActor extends AbstractLoggingActor {

    int balance = 100;

    public Receive initState = receiveBuilder()
            .match(MessagingProtocol.NewGameRequest.class, this::onNewGame)
            .build();

    public Receive roll = receiveBuilder()
                .match(MessagingProtocol.RollRequest.class, this::rollRequest)
                .build();

    public Receive createReceive() {
        return initState;
    }

    private void onNewGame(MessagingProtocol.NewGameRequest newGameRequest) {
        if (balance <= 0) {
            sender().tell(new MessagingProtocol.NewGameResponse("error","Insufficient balance"), self());
            self().tell(PoisonPill.getInstance(), self());
        } else {
            log().info("New game started: " + newGameRequest.name);
            System.out.println("New game started: " + newGameRequest.name);
            sender().tell(new MessagingProtocol.NewGameResponse("OK", "New game started"), self());
            sender().tell(new MessagingProtocol.Balance(balance), self());
            context().become(roll.onMessage());
        }
    }

    private void rollRequest(MessagingProtocol.RollRequest rollRequest) {
        throw new UnsupportedOperationException("Rolling not implemented yet");
    }

    public static Props props() {
        return Props.create(PlayerActor.class);
    }
}
