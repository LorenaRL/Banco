
package br.com.java.jsf.meuBancoVirtual.util;

import java.util.Date;

/**
 *
 * @author lorena
 */
public class MovimentacaoConta {

    public Date getDt_movimentacao() {
        return dt_movimentacao;
    }

    public void setDt_movimentacao(Date dt_movimentacao) {
        this.dt_movimentacao = dt_movimentacao;
    }

    public Integer getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(Integer numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    private Date dt_movimentacao;
    private Integer numeroDaConta;
    private String tipoMovimentacao;
    private Double valor;
    
}
