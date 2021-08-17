package io.zipcoder.tc_spring_poll_application.controller;

import io.zipcoder.tc_spring_poll_application.domain.Poll;
import io.zipcoder.tc_spring_poll_application.domain.Vote;
import io.zipcoder.tc_spring_poll_application.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class VoteController {

    public VoteRepository voteRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping("/votes")
    public ResponseEntity<Iterable<Vote>> getAllVotes() {
        Iterable<Vote> allVotes = voteRepository.findAll();
        return new ResponseEntity<>(allVotes, HttpStatus.OK);
    }

    @PostMapping("/votes")
    public ResponseEntity<?> createVote(@RequestBody Vote vote) {
        vote = voteRepository.save(vote);
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vote.getId())
                .toUri();
        return new ResponseEntity<>(newPollUri, HttpStatus.CREATED);
    }

    @GetMapping("/votes/{voteId}")
    public ResponseEntity<?> getVote(@PathVariable Long voteId) {
        Vote v = voteRepository.findOne(voteId);
        return new ResponseEntity<> (v, HttpStatus.OK);
    }

    @PutMapping("/votes/{voteId}")
    public ResponseEntity<?> updateVote(@RequestBody Vote vote, @PathVariable Long voteId) {
        // Save the entity
        Vote v = voteRepository.save(vote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/votes/{voteId}")
    public ResponseEntity<?> deleteVote(@PathVariable Long voteId) {
        voteRepository.delete(voteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
