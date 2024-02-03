package com.chess.backend.dto;

public class Move {

    private String from;
    private String to;
    private String prevPos;
    private String newPos;

    public Move() {
    }

    public Move(String from, String to, String prevFen, String newFen) {
        this.from = from;
        this.to = to;
        this.prevPos = prevFen;
        this.newPos = newFen;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getPrevPos() {
        return prevPos;
    }

    public String getNewPos() {
        return newPos;
    }
}
