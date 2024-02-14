package com.chess.backend.dto;


// You can use to send the kafka JSON objects.
public class PositionKafkaMessage {
    private final String command;
    private final String opening;
    private final String fen;

    public PositionKafkaMessage(String command, String opening, String fen) {
        this.command = command;
        this.opening = opening;
        this.fen = fen;
    }

    public String getCommand() {
        return command;
    }

    public String getOpening() {
        return opening;
    }

    public String getFen() {
        return fen;
    }

    @Override
    public String toString() {
        return "PositionKafkaMessage{" +
                "command=" + command +
                ", opening='" + opening + '\'' +
                ", fen='" + fen + '\'' +
                '}';
    }
}
