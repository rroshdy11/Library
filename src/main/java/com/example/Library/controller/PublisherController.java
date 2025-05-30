package com.example.Library.controller;

import com.example.Library.Service.PublisherService;
import com.example.Library.dto.PublisherDTO;
import com.example.Library.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private PublisherService publisherService;
    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }
    // This method will handle the creation of a new publisher
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<?> createPublisher(@RequestBody PublisherDTO publisherdto) {
        return publisherService.createPublisher(publisherdto);
    }
    // This method will handle the update of an existing publisher
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'STAFF')")
    public ResponseEntity<?> updatePublisher(@PathVariable("id") Long id,@RequestBody PublisherDTO publisherdto) {
        return publisherService.updatePublisher(id, publisherdto);
    }
    // This method will handle the deletion of a publisher
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<?>  deletePublisher(@PathVariable("id") Long id) {
       return publisherService.deletePublisher(id);
    }
    // This method will handle the retrieval of a publisher by ID
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<?> getPublisherById(@PathVariable("id") Long id) {
        return publisherService.getPublisherById(id);
    }
    // This method will handle the retrieval of all publishers
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<?> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

}
