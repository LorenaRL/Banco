package br.com.java.jsf.meuBancoVirtual.bean;

import br.com.java.jsf.meuBancoVirtual.dao.ContaDAO;
import br.com.java.jsf.meuBancoVirtual.util.Conta;
import br.com.java.jsf.meuBancoVirtual.util.MovimentacaoConta;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean
public class ContaBean {
    
    // declarando objetos
    Conta conta;
    ContaDAO dao;
    MovimentacaoConta movConta;

    // cria construtor
    public ContaBean(){
        conta = new Conta();
        dao = new ContaDAO();
        movConta = new MovimentacaoConta();
    }
   
    // acionamento de botões
    
   public void btBuscarDados(){
        try{ 
        if(conta.getNumero() != null){
                    this.buscarDadosContaEspecifica(conta.getNumero());
              }}
        catch(NullPointerException ex){
          Logger.getLogger(ContaBean.class.getName()).log(Level.SEVERE, null, ex);
          this.adicionarMensagem("Dados precisam estar todos preenchidos", FacesMessage.SEVERITY_ERROR);
      }
    }
   
    public void btDeposito() {
       try{ 
            List<Conta> dadosConta = this.buscaContas();
            if(movConta.getValor() != null && movConta.getNumeroDaConta() != null  && movConta.getTipoMovimentacao() != null){
                for(Conta contas : dadosConta){
                    if(contas.getNumero().equals(movConta.getNumeroDaConta())){
                         if(movConta.getValor() >  contas.getLimite()){
                               this.adicionarMensagem("O valor do DEPÓSITO é superior ao LIMITE da conta.",FacesMessage.SEVERITY_ERROR);
                          }
                         else if(movConta.getValor() >= 0){
                                this.depositar(movConta.getValor(), movConta.getNumeroDaConta(), contas.getLimite(), contas.getSaldo());
                                this.adicionarMensagem("Depósito realizado com sucesso!", FacesMessage.SEVERITY_INFO);
                           }
                         else if(movConta.getValor() < 0){
                              this.adicionarMensagem("O valor digitado deve ser um valor positivo.",FacesMessage.SEVERITY_ERROR);
                          }}}}}
        catch(NullPointerException ex){
          Logger.getLogger(ContaBean.class.getName()).log(Level.SEVERE, null, ex);
          this.adicionarMensagem("Dados precisam estar todos preenchidos", FacesMessage.SEVERITY_ERROR);
      }
      }
    
    public void btSacar(){
        try{
            List<Conta> dadosConta = this.buscaContas();
            
                if(movConta.getValor() != null && movConta.getNumeroDaConta() != null ){
                    for(Conta contas : dadosConta){
                         if(contas.getNumero().equals(movConta.getNumeroDaConta())){
                                if(movConta.getValor() >  conta.getSaldo()){
                                     this.adicionarMensagem("O valor do SAQUE é superior ao SALDO da conta.",FacesMessage.SEVERITY_ERROR);
                                }
                                else if(movConta.getValor() >= 0){
                                      this.sacar(movConta.getValor(), movConta.getNumeroDaConta(), contas.getSaldo());
                                      this.adicionarMensagem("Saque realizado com sucesso!", FacesMessage.SEVERITY_INFO);
                                  }
                                else if(movConta.getValor() < 0){
                                    this.adicionarMensagem("O valor deve ser positivo", FacesMessage.SEVERITY_ERROR);
                    }}}}}
        catch(NullPointerException ex){
          Logger.getLogger(ContaBean.class.getName()).log(Level.SEVERE, null, ex);
          this.adicionarMensagem("Dados precisam estar todos preenchidos", FacesMessage.SEVERITY_ERROR);
      }
  }   
    public void limpaDados(){
           conta.setCliente(null);
           conta.setNumero(null);
           conta.setLimite(null);
           conta.setSaldo(null);
    }
      
    public void salvarConta(){
        try{
        dao.insereConta(conta);
        conta = new Conta();
        this.adicionarMensagem("Conta criada com sucesso!", FacesMessage.SEVERITY_INFO);
        }   
        catch (NullPointerException ex) {
            Logger.getLogger(ContaBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    ///getters e setters do objeto
    
    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
     public MovimentacaoConta getMovConta() {
        return movConta;
    }

    public void setMovConta(MovimentacaoConta movConta) {
        this.movConta = movConta;
    }
    
    //métodos para serem chamados
    
     public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro){
	        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
	        FacesContext.getCurrentInstance().addMessage(null, fm);
	    }

   //usados tanto para depositar quanto para sacar
    public void atualizarConta(Integer numConta, Double saldo) {
        conta.setSaldo(saldo);
        conta.setNumero(numConta);
        dao.atualizarConta(conta);
        conta = new Conta();
    }
    
      public void depositarOuSacarNaConta(){
        movConta.setDt_movimentacao(Date.valueOf(LocalDate.now()));
        dao.insereMovimentacao(movConta);
        movConta = new MovimentacaoConta();
    }
      
     //////////////////////////////////////////////////////////////////////////// 
    
    public void depositar(Double valorDepositado, Integer numConta, Double limite, Double saldo ) {
     
                Double saldoTotal = Double.sum(saldo, valorDepositado);
                if(this.verificaLimiteDaContaDeposito(limite, saldoTotal) == true){
                    this.depositarOuSacarNaConta();
                    this.atualizarConta(numConta,saldoTotal);
                }
    }
         
      public void sacar(Double valorSacado, Integer numConta, Double saldo ) {
   
                if(this.verificaLimiteDaContaSaque( saldo, valorSacado) == true){
                    Double saldoTotal = saldo - valorSacado;
                    this.depositarOuSacarNaConta();
                    this.atualizarConta(numConta,saldoTotal);
                }
             }
   
        
    public List<Conta> buscaContas(){
         return dao.buscar();       
    }
    
    public boolean verificaLimiteDaContaDeposito(Double limite, Double saldoTotal){
        
                if(limite < saldoTotal ){
                    this.adicionarMensagem("O valor excedeu ao limite da conta.",FacesMessage.SEVERITY_ERROR);
                }
                return true;
             }
    
 public boolean verificaLimiteDaContaSaque(Double saldo, Double saque){
        
                if(saldo < saque ){
                    this.adicionarMensagem("O valor excedeu ao limite da conta.",FacesMessage.SEVERITY_ERROR);
                }
                return true;
             }
        
    
    public void buscarDadosContaEspecifica(Integer numConta){
        List<Conta> dados = this.buscaContas();
         for(Conta contas : dados){
             if(contas.getNumero().equals(numConta)){
                 conta.setCliente(contas.getCliente());
                 conta.setLimite(contas.getLimite());
                 conta.setSaldo(contas.getSaldo());
             }
    }
  }
}       