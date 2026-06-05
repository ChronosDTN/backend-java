package br.com.fiap.chronos.controller;

import br.com.fiap.chronos.dto.TransactionRequest;
import br.com.fiap.chronos.model.TransactionBuffer;
import br.com.fiap.chronos.repository.TransactionBufferRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST que expõe os endpoints para gerenciar e sincronizar transações cislunares.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionBufferRepository repository;
    private final EntityManager entityManager;

    /**
     * Construtor para injeção manual das dependências do repositório e gerenciador de entidade.
     */
    public TransactionController(TransactionBufferRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    /**
     * Recebe uma transação, salva no buffer local e executa a procedure de correção de tempo relativístico.
     */
    @PostMapping("/sync")
    @Transactional
    public ResponseEntity<EntityModel<TransactionBuffer>> syncTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionBuffer buffer = new TransactionBuffer();
        buffer.setSourceNode(request.sourceNode());
        buffer.setTargetNode(request.targetNode());
        buffer.setPayload(request.payload());
        buffer.setLocalTimestamp(request.localTimestamp());
        buffer.setSyncStatus("PENDING");

        TransactionBuffer savedBuffer = repository.save(buffer);

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SP_CORRIGIR_TEMPO_LUNAR");
        query.registerStoredProcedureParameter("p_id_tx", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_status", String.class, ParameterMode.OUT);
        query.setParameter("p_id_tx", savedBuffer.getIdTx());
        query.execute();

        String status = (String) query.getOutputParameterValue("p_status");
        if (status != null && status.startsWith("ERROR")) {
            throw new RuntimeException("Falha na correcao relativistica: " + status);
        }

        TransactionBuffer syncedBuffer = repository.findById(savedBuffer.getIdTx())
                .orElseThrow(() -> new RuntimeException("Transacao nao encontrada apos execucao."));

        EntityModel<TransactionBuffer> model = EntityModel.of(syncedBuffer);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionController.class)
                .getTransactionById(syncedBuffer.getIdTx())).withSelfRel());
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionController.class)
                .getBufferTransactions(null)).withRel("buffer"));

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    /**
     * Recupera uma transação específica no buffer pelo seu identificador.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TransactionBuffer>> getTransactionById(@PathVariable Long id) {
        TransactionBuffer buffer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacao nao encontrada."));

        EntityModel<TransactionBuffer> model = EntityModel.of(buffer);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionController.class)
                .getTransactionById(id)).withSelfRel());

        return ResponseEntity.ok(model);
    }

    /**
     * Lista todas as transações cadastradas no buffer, opcionalmente filtradas pelo status de sincronização.
     */
    @GetMapping
    public ResponseEntity<List<EntityModel<TransactionBuffer>>> getBufferTransactions(@RequestParam(required = false) String status) {
        List<TransactionBuffer> list;

        if (status != null) {
            list = repository.findBySyncStatus(status);
        } else {
            list = repository.findAll();
        }

        List<EntityModel<TransactionBuffer>> models = list.stream().map(tx -> {
            EntityModel<TransactionBuffer> model = EntityModel.of(tx);
            model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionController.class)
                    .getTransactionById(tx.getIdTx())).withSelfRel());
            return model;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(models);
    }
}
