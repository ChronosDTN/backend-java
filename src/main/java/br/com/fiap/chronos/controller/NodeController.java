package br.com.fiap.chronos.controller;

import br.com.fiap.chronos.model.Node;
import br.com.fiap.chronos.repository.NodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    private final NodeRepository nodeRepository;

    public NodeController(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    // GET: Lista todos os nós
    @GetMapping
    public ResponseEntity<List<Node>> getAllNodes() {
        return ResponseEntity.ok(nodeRepository.findAll());
    }

    // GET: Busca nó por ID
    @GetMapping("/{id}")
    public ResponseEntity<Node> getNodeById(@PathVariable Long id) {
        Optional<Node> node = nodeRepository.findById(id);
        return node.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST: Cria um novo nó
    @PostMapping
    public ResponseEntity<Node> createNode(@RequestBody Node node) {
        // Se vier sem status, garante que fique ACTIVE
        if (node.getStatus() == null || node.getStatus().isEmpty()) {
            node.setStatus("ACTIVE");
        }
        if (node.getBufferedPackets() == null) {
            node.setBufferedPackets(0);
        }
        Node savedNode = nodeRepository.save(node);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNode);
    }

    // PATCH: Atualização parcial de nó
    @PatchMapping("/{id}")
    public ResponseEntity<Node> updateNodePartially(@PathVariable Long id, @RequestBody Node partialNode) {
        Optional<Node> existingNodeOpt = nodeRepository.findById(id);
        
        if (existingNodeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Node existingNode = existingNodeOpt.get();

        if (partialNode.getName() != null) {
            existingNode.setName(partialNode.getName());
        }
        if (partialNode.getLocation() != null) {
            existingNode.setLocation(partialNode.getLocation());
        }
        if (partialNode.getBufferedPackets() != null) {
            existingNode.setBufferedPackets(partialNode.getBufferedPackets());
        }
        if (partialNode.getStatus() != null) {
            existingNode.setStatus(partialNode.getStatus());
        }

        nodeRepository.save(existingNode);
        return ResponseEntity.ok(existingNode);
    }

    // DELETE: Remove um nó
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        if (!nodeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        nodeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
