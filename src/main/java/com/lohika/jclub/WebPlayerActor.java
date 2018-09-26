package com.lohika.jclub;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;

public class WebPlayerActor extends AbstractLoggingActor {

    ActorRef supervisor;

    public WebPlayerActor(ActorRef supervisor) {
        this.supervisor = supervisor;
        this.supervisor.tell(new GameSupervisorActor.NewGame("Solitaire"), self());
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(PlayerActor.Response.class, this::onResponse)
                .match(PlayerActor.Balance.class, this::onBalance)
                .build();
    }

    private void onResponse(PlayerActor.Response response) {
        if (response.status.equals("OK")) {
            log().info("!!!!!! :):):):):)");
        } else if (response.status.equals("error")) {
            log().error("ERROR: " + response.message);
        }
    }

    private void onBalance(PlayerActor.Balance balance) {
        log().info("Balance: " + balance.balance);
    }


    public static Props props(ActorRef supervisor) {
        return Props.create(WebPlayerActor.class, supervisor);
    }
}
