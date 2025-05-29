package com.example.Library.Service;

import com.example.Library.dto.PublisherDTO;
import com.example.Library.model.Publisher;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.PublisherRepository;
import com.example.Library.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final DTOMapper dtoMapper;
    private final BookRepository bookRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, DTOMapper dtoMapper, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.dtoMapper = dtoMapper;
        this.bookRepository = bookRepository;
    }
    // Create Publisher
    public ResponseEntity<?> createPublisher(PublisherDTO publisherDTO) {
        if (publisherDTO == null || publisherDTO.getName() == null || publisherDTO.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Publisher name cannot be empty");
        }
        if (publisherRepository.existsById(publisherDTO.getId())) {
            return ResponseEntity.badRequest().body("Publisher with this ID already exists");
        }
        if (!publisherRepository.findByName(publisherDTO.getName()).isEmpty()) {
            return ResponseEntity.badRequest().body("Publisher with this name already exists");
        }
        // Convert PublisherDTO to Publisher entity
        Publisher publisher = dtoMapper.mapToPublisher(publisherDTO);
        return ResponseEntity.ok(publisherRepository.save(publisher));
    }
    // Update Publisher
    public ResponseEntity<?> updatePublisher(Long id, PublisherDTO publisherDTO) {
        if (!publisherRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Publisher publisher = dtoMapper.mapToPublisher(publisherDTO);
        Publisher existingPublisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        // Update the publisher fields based on the provided DTO and ignore unsent fields
        if (publisher.getName() != null && !publisher.getName().isEmpty()) {
            existingPublisher.setName(publisher.getName());
        }
        if (publisher.getAddress() != null && !publisher.getAddress().isEmpty()) {
            existingPublisher.setAddress(publisher.getAddress());
        }
        return ResponseEntity.ok(publisherRepository.save(existingPublisher));
    }
    // Delete Publisher
    public ResponseEntity<?> deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // Check if the publisher has any books associated with it
        if (bookRepository.existsByPublisherId(id)) {
            return ResponseEntity.badRequest().body("Cannot delete publisher with associated books");
        }
        publisherRepository.deleteById(id);
        return ResponseEntity.ok("Publisher deleted successfully");
    }

    // Get Publisher by ID
    public ResponseEntity<?> getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .map(publisher -> ResponseEntity.ok(dtoMapper.mapPublisherToDTO(publisher)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get All Publishers
    public ResponseEntity<?> getAllPublishers() {
        return ResponseEntity.ok(publisherRepository.findAll().stream()
                .map(dtoMapper::mapPublisherToDTO)
                .toList());
    }



}
