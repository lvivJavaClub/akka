package com.lohika.jclub;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

// client side
public class SlotMachineActor extends AbstractLoggingActor {

    ActorRef supervisor;

    public SlotMachineActor(ActorRef supervisor) {
        this.supervisor = supervisor;
        this.supervisor.tell(new MessagingProtocol.NewGameRequest("Solitaire", "a@example.com"), self());
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(MessagingProtocol.NewGameResponse.class, this::onResponse)
                .match(MessagingProtocol.Balance.class, this::onBalance)
                .build();
    }

    private void onResponse(MessagingProtocol.NewGameResponse newGameResponse) {
        if (newGameResponse.status.equals("OK")) {
            log().info("!!!!!! :):):):):)");
        } else if (newGameResponse.status.equals("error")) {
            log().error("ERROR: " + newGameResponse.message);
        }
    }

    private void onBalance(MessagingProtocol.Balance balance) {
        log().info("Balance: " + balance.balance);
    }


    public static Props props(ActorRef supervisor) {
        return Props.create(SlotMachineActor.class, supervisor);
    }
}
