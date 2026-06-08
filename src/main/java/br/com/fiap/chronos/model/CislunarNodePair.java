package br.com.fiap.chronos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Objeto de valor embeddable que agrupa os identificadores dos nós de origem e destino
 * de uma transação cislunar dentro da mesma tabela T_CDTN_TRANSACTION_BUFFER.
 *
 * <p>Uso de {@code @Embeddable} conforme especificação JPA para reutilização de
 * atributos comuns sem criação de tabela adicional.</p>
 */
@Embeddable
public class CislunarNodePair implements Serializable {

    /**
     * Identificador do nó gateway de origem da transação (ex: Lunar-Gateway-Alpha).
     */
    @Column(name = "source_node", nullable = false)
    private Long sourceNode;

    /**
     * Identificador do nó gateway de destino da transação (ex: Terrestrial-Gateway-01).
     */
    @Column(name = "target_node", nullable = false)
    private Long targetNode;

    /**
     * Construtor padrão exigido pela especificação JPA para tipos embeddable.
     */
    public CislunarNodePair() {}

    /**
     * Construtor completo para criação direta do par de nós com origem e destino.
     *
     * @param sourceNode identificador do nó de origem
     * @param targetNode identificador do nó de destino
     */
    public CislunarNodePair(Long sourceNode, Long targetNode) {
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
    }

    /**
     * Retorna o identificador do nó de origem.
     */
    public Long getSourceNode() {
        return sourceNode;
    }

    /**
     * Define o identificador do nó de origem.
     */
    public void setSourceNode(Long sourceNode) {
        this.sourceNode = sourceNode;
    }

    /**
     * Retorna o identificador do nó de destino.
     */
    public Long getTargetNode() {
        return targetNode;
    }

    /**
     * Define o identificador do nó de destino.
     */
    public void setTargetNode(Long targetNode) {
        this.targetNode = targetNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CislunarNodePair that)) return false;
        return Objects.equals(sourceNode, that.sourceNode) &&
               Objects.equals(targetNode, that.targetNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceNode, targetNode);
    }

    @Override
    public String toString() {
        return "CislunarNodePair{sourceNode=" + sourceNode + ", targetNode=" + targetNode + "}";
    }
}
