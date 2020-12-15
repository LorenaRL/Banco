package br.com.java.jsf.meuBancoVirtual.dao;

import br.com.java.jsf.meuBancoVirtual.bean.ContaBean;
import br.com.java.jsf.meuBancoVirtual.conexao.Conexao;
import br.com.java.jsf.meuBancoVirtual.util.Conta;
import br.com.java.jsf.meuBancoVirtual.util.MovimentacaoConta;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author lorena
 */
public class ContaDAO   {
    
    String sql;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public void insereConta(Conta conta) {
        sql = "insert into tb_conta (numero, cliente, saldo, limite) "
                + " values (?, ?, ?, ?)";
        con = Conexao.conectar();
        try {
            ps = con.prepareStatement(sql);
          
            ps.setInt(1, conta.getNumero());
            ps.setString(2, conta.getCliente());
            ps.setDouble(3, conta.getSaldo());
            ps.setDouble(4, conta.getLimite());
           
            ps.execute();
            System.out.println("Dados gravados com sucesso");
            
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        public void insereMovimentacao(MovimentacaoConta movConta) {
        sql = "insert into tb_movimentacao (tipo_movimentacao, valor, data_movimentacao, num_conta) "
                + " values (?, ?,?,?)";
        con = Conexao.conectar();
        try {
            ps = con.prepareStatement(sql);
          
            ps.setString(1, movConta.getTipoMovimentacao());
            ps.setDouble(2, movConta.getValor());
            ps.setDate(3, (Date) movConta.getDt_movimentacao());
            ps.setInt(4, movConta.getNumeroDaConta());
           
            ps.execute();
            System.out.println("Dados gravados com sucesso");
            
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public List<Conta> buscar() {
            try {
                Connection conexao = Conexao.conectar();
                PreparedStatement ps = conexao.prepareStatement("select * from tb_conta");
                ResultSet resultSet = ps.executeQuery();
                List<Conta> conta = new ArrayList<>();
                while(resultSet.next()){
                    Conta contaUsuario = new Conta();
                    contaUsuario.setNumero(resultSet.getInt("numero"));
                    contaUsuario.setSaldo(resultSet.getDouble("saldo"));
                    contaUsuario.setLimite(resultSet.getDouble("limite"));
                    contaUsuario.setCliente(resultSet.getString("cliente"));

                    conta.add(contaUsuario);
                }
                Conexao.fecharConexao();
                return conta;

            } catch (SQLException ex) {
                this.adicionarMensagem("Erro ao tentar buscar conta!", FacesMessage.SEVERITY_ERROR);
            }
        return null;
        }
        
        public void atualizarConta(Conta conta) {
	        sql = "update tb_conta set  saldo=? where numero=? ";
	        con = Conexao.conectar();
	        try {
	            ps = con.prepareStatement(sql);
                    ps.setDouble(1, conta.getSaldo());
	            ps.setInt(2, conta.getNumero());
                    ps.execute();
	            System.out.println("Dados atualizados com sucesso");
                    
                     if(conta.getNumero() == null || conta.getSaldo() == null){
                          this.adicionarMensagem("Não é possível atualizar o saldo da conta, campos em BRANCO!",FacesMessage.SEVERITY_ERROR);
               
                       }
                
                    Conexao.fecharConexao();
	        } catch (SQLException ex) {
                      Logger.getLogger(ContaBean.class.getName()).log(Level.SEVERE, null, ex);
                      this.adicionarMensagem("Erro ao tentar atualizar saldo!", FacesMessage.SEVERITY_ERROR);
                }
        }
                public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro){
	        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
	        FacesContext.getCurrentInstance().addMessage(null, fm);
	    }
  
}

