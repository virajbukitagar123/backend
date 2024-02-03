package com.chess.backend.service;

import com.chess.backend.dto.Move;
import com.chess.backend.dto.Position;
import com.chess.backend.dto.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RecordService {

    private String currentOpening = null;

    private final PositionRepository positionRepository;

    public RecordService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public String getCurrentOpening() {
        return currentOpening;
    }

    public void createStartNode() {
        positionRepository.createStartNode();
    }

    public void startRecording(String currentOpening) {
        if(this.currentOpening != null) {
            log.error("Please stop previous recording - current opening is set");
            return;
        }
        this.currentOpening = currentOpening;
        positionRepository.saveOpeningInStart(this.currentOpening);
    }

    public void stopRecording() {
        if(this.currentOpening == null) {
            log.warn("No recording started");
            return;
        }
        this.currentOpening = null;
    }

    public void savePosition(Move move) {
        Position prevPosition, nextPosition;

        prevPosition= positionRepository.findByPosition(move.getPrevPos());
        if(prevPosition != null) {
            // TODO: Safety net to not add openings twice
            prevPosition.getOpenings().add(currentOpening);
        } else {
            prevPosition = null;
            log.error("Previous Position not saved");
        }

        nextPosition = positionRepository.findByPosition(move.getNewPos());
        if(nextPosition != null) {
            nextPosition.getOpenings().add(currentOpening);
        } else {
            List<String> openings = new ArrayList<>();
            openings.add(currentOpening);
            nextPosition = Position.builder()
                    .position(move.getNewPos())
                    .openings(openings)
                    .type(Type.pos)
                    .build();
        }

        positionRepository.savePositions(prevPosition, nextPosition, currentOpening);
    }
}
