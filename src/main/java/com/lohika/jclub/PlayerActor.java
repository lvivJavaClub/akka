package com.lohika.jclub;

import akka.actor.AbstractLoggingActor;
import akka.actor.PoisonPill;
import akka.actor.Props;

public class PlayerActor extends AbstractLoggingActor {

    int balance = 100;

    public Receive createReceive() {
        return receiveBuilder()
                .match(GameSupervisorActor.NewGame.class, this::onNewGame)
                .build();
    }

    private void onNewGame(GameSupervisorActor.NewGame newGame) {
        if (balance <= 0) {
            sender().tell(new Response("error","Insufficient balance"), self());
            self().tell(PoisonPill.getInstance(), self());
        } else {
            log().info("New game started: " + newGame.name);
            System.out.println("New game started: " + newGame.name);
            sender().tell(new Response("OK", "New game started"), self());
            sender().tell(new Balance(balance), self());
        }
    }

    public static class Balance {
        int balance;

        public Balance(int balance) {
            this.balance = balance;
        }
    }

    // response protocol
    public static class Response {
        String status;
        String message;

        public Response(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }

    public static Props props() {
        return Props.create(PlayerActor.class);
    }
}
