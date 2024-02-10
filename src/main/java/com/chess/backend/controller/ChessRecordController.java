package com.chess.backend.controller;

import com.chess.backend.dto.Move;
import com.chess.backend.dto.Position;
import com.chess.backend.dto.Message;
import com.chess.backend.service.PositionRepository;
import com.chess.backend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ChessRecordController {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    RecordService recordService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody Message message) {
        template.convertAndSend("/topic/message", message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload Message message) {
        // receive message from client
        System.out.println(message.getMessage());
        String cmd = message.getMessage();
        if (cmd.contains("Start")) {
            String opening = cmd.split("-")[1];
            template.convertAndSend("/topic/message", new Message("Started Recording Opening: " + opening));
            recordService.startRecording(opening);
        } else if (cmd.contains("Stop")) {
            recordService.stopRecording();
            template.convertAndSend("/topic/message", new Message("Stopped Recording"));
        }
    }

    @MessageMapping("/sendChessMove")
    public void receiveMove(@Payload Move move) {
        // receive message from client
        System.out.println("Move from client --  From: " + move.getFrom() + " To: " + move.getTo());
        recordService.savePosition(move);
        template.convertAndSend("/topic/message", new Message("Move from client --  From: " + move.getFrom() + " To: " + move.getTo()));
    }

    @SendTo("/topic/message")
    public Message broadcastMessage(@Payload Message message) {
        return message;
    }

    @GetMapping("getPositions/{opening}")
    public List<Position> getPositionsForOpenings(@PathVariable String opening) {
        return positionRepository.findPositionsByOpening(opening);
    }
}