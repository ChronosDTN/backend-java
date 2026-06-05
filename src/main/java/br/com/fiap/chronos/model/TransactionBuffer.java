package br.com.fiap.chronos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa o buffer de transações a serem sincronizadas com a Terra.
 */
@Entity
@Table(name = "T_CDTN_TRANSACTION_BUFFER")
public class TransactionBuffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tx")
    private Long idTx;

    @Column(name = "source_node", nullable = false)
    private Long sourceNode;

    @Column(name = "target_node", nullable = false)
    private Long targetNode;

    @Column(name = "payload", length = 4000, nullable = false)
    private String payload;

    @Column(name = "local_timestamp", nullable = false)
    private LocalDateTime localTimestamp;

    @Column(name = "sync_status", length = 20, nullable = false)
    private String syncStatus = "PENDING";

    /**
     * Construtor padrão necessário para a especificação JPA.
     */
    public TransactionBuffer() {
    }

    /**
     * Construtor completo para criação direta de instâncias com todos os dados.
     */
    public TransactionBuffer(Long idTx, Long sourceNode, Long targetNode, String payload, LocalDateTime localTimestamp, String syncStatus) {
        this.idTx = idTx;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.payload = payload;
        this.localTimestamp = localTimestamp;
        this.syncStatus = syncStatus;
    }

    /**
     * Recupera o identificador único da transação.
     */
    public Long getIdTx() {
        return idTx;
    }

    /**
     * Seta o identificador único da transação.
     */
    public void setIdTx(Long idTx) {
        this.idTx = idTx;
    }

    /**
     * Recupera o identificador do nó de origem da transação.
     */
    public Long getSourceNode() {
        return sourceNode;
    }

    /**
     * Seta o identificador do nó de origem da transação.
     */
    public void setSourceNode(Long sourceNode) {
        this.sourceNode = sourceNode;
    }

    /**
     * Recupera o identificador do nó de destino.
     */
    public Long getTargetNode() {
        return targetNode;
    }

    /**
     * Seta o identificador do nó de destino.
     */
    public void setTargetNode(Long targetNode) {
        this.targetNode = targetNode;
    }

    /**
     * Recupera a carga de dados JSON contendo as informações da transação.
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Seta a carga de dados JSON contendo as informações da transação.
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * Recupera a data e hora em que a transação foi registrada.
     */
    public LocalDateTime getLocalTimestamp() {
        return localTimestamp;
    }

    /**
     * Seta a data e hora em que a transação foi registrada.
     */
    public void setLocalTimestamp(LocalDateTime localTimestamp) {
        this.localTimestamp = localTimestamp;
    }

    /**
     * Recupera o status de sincronização no buffer.
     */
    public String getSyncStatus() {
        return syncStatus;
    }

    /**
     * Seta o status de sincronização no buffer.
     */
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
}
