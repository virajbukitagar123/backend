package com.chess.backend.service;

import com.chess.backend.dto.Position;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends Neo4jRepository<Position, Long> {

    @Query("MATCH (p:Position) WHERE $opening in p.openings RETURN(p)")
    List<Position> findPositionsByOpening(String opening);

    @Query("MATCH (p:Position) WHERE $position = p.position RETURN(p)")
    Position findByPosition(String position);

    @Query("MATCH(a:Position) WHERE a.type = \"start\" SET a.openings = a.openings + $opening")
    void saveOpeningInStart(String opening);

    // TODO: Make Sure start node if exists do not create one.
    @Query("MERGE (a:Position{type: \"start\", position:\"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1\", openings: []})")
    void createStartNode();

    @Query("MATCH (p:Position {position: :#{#position1.position}}) " +
            "MERGE (p)-[:MOVED_TO]->(x:Position{type: \"pos\", position: :#{#position2.position}}) " +
            "ON CREATE SET x.openings = [$currentOpening] ON MATCH SET x.openings = x.openings + $currentOpening")
    void savePositions(Position position1, Position position2, String currentOpening);

}
