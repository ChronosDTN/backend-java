package br.com.fiap.chronos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade JPA que representa o buffer de transações financeiras cislunares
 * aguardando sincronização com o nó Terra via protocolo DTN store-and-forward.
 *
 * <p>Utiliza {@code @Embedded} com {@link CislunarNodePair} para agrupar
 * os identificadores de nó como objeto de valor, conforme modelagem avançada JPA.</p>
 */
@Entity
@Table(name = "T_CDTN_TRANSACTION_BUFFER")
public class TransactionBuffer {

    /**
     * Identificador único gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tx")
    private Long idTx;

    /**
     * Par de nós cislunar (origem e destino) encapsulado como objeto de valor embeddable.
     * As colunas source_node e target_node ficam na mesma tabela T_CDTN_TRANSACTION_BUFFER.
     */
    @Embedded
    private CislunarNodePair nodePair;

    /**
     * Carga de dados JSON com detalhes financeiros da transação (moeda, valor, ledger hash).
     */
    @Column(name = "payload", length = 4000, nullable = false)
    private String payload;

    /**
     * Timestamp local registrado no momento da criação da transação no nó lunar.
     * Será corrigido relativisticamente pela procedure SP_CORRIGIR_TEMPO_LUNAR.
     */
    @Column(name = "local_timestamp", nullable = false)
    private LocalDateTime localTimestamp;

    /**
     * Status atual de sincronização no ciclo DTN: PENDING → SYNCED | CANCELLED.
     */
    @Column(name = "sync_status", length = 20, nullable = false)
    private String syncStatus = "PENDING";

    /**
     * Construtor padrão necessário para a especificação JPA.
     */
    public TransactionBuffer() {}

    /**
     * Construtor completo para criação direta de instâncias com todos os dados.
     *
     * @param idTx           identificador único
     * @param nodePair       par de nós origem/destino
     * @param payload        dados JSON da transação
     * @param localTimestamp timestamp lunar de criação
     * @param syncStatus     status de sincronização inicial
     */
    public TransactionBuffer(Long idTx, CislunarNodePair nodePair, String payload,
                             LocalDateTime localTimestamp, String syncStatus) {
        this.idTx = idTx;
        this.nodePair = nodePair;
        this.payload = payload;
        this.localTimestamp = localTimestamp;
        this.syncStatus = syncStatus;
    }

    /** Retorna o identificador único da transação. */
    public Long getIdTx() { return idTx; }

    /** Define o identificador único da transação. */
    public void setIdTx(Long idTx) { this.idTx = idTx; }

    /** Retorna o par de nós cislunar (origem e destino). */
    public CislunarNodePair getNodePair() { return nodePair; }

    /** Define o par de nós cislunar (origem e destino). */
    public void setNodePair(CislunarNodePair nodePair) { this.nodePair = nodePair; }

    /** Atalho de conveniência para obter o nó de origem a partir do nodePair embeddable. */
    public Long getSourceNode() { return nodePair != null ? nodePair.getSourceNode() : null; }

    /** Atalho de conveniência para obter o nó de destino a partir do nodePair embeddable. */
    public Long getTargetNode() { return nodePair != null ? nodePair.getTargetNode() : null; }

    /** Retorna a carga de dados JSON da transação. */
    public String getPayload() { return payload; }

    /** Define a carga de dados JSON da transação. */
    public void setPayload(String payload) { this.payload = payload; }

    /** Retorna o timestamp local de criação no nó lunar. */
    public LocalDateTime getLocalTimestamp() { return localTimestamp; }

    /** Define o timestamp local de criação no nó lunar. */
    public void setLocalTimestamp(LocalDateTime localTimestamp) { this.localTimestamp = localTimestamp; }

    /** Retorna o status atual de sincronização DTN. */
    public String getSyncStatus() { return syncStatus; }

    /** Define o status de sincronização DTN. */
    public void setSyncStatus(String syncStatus) { this.syncStatus = syncStatus; }
}
