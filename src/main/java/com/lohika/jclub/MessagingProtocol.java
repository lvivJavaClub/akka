package com.lohika.jclub;

public class MessagingProtocol {
    public static class RollRequest {
        String userId;

        public RollRequest(String userId) {
            this.userId = userId;
        }
    }

    public static class RollResponse {
        int[][] screen;
        long win;
    }

    public static class NewGameRequest {
        String name;
        String userId;

        public NewGameRequest(String name, String userId) {
            this.name = name;
            this.userId = userId;
        }
    }

    public static class Balance {
        int balance;

        public Balance(int balance) {
            this.balance = balance;
        }
    }

    // response protocol
    public static class NewGameResponse {
        String status;
        String message;

        public NewGameResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
