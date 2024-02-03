package com.chess.backend.dto;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Node
@Builder
public class Position {

    @Id
    @GeneratedValue
    private Long id;
    private Type type;
    private String position;
    private List<String> openings;

    public Position() {
    }

    public Position(Long id, Type type, String position, List<String> openings) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.openings = openings;
    }

    public Position(Type type, String position, List<String> openings) {
        this.type = type;
        this.position = position;
        this.openings = openings;
    }

    public Type getType() {
        return type;
    }

    public String getPosition() {
        return position;
    }

    public List<String> getOpenings() {
        return openings;
    }
}
