package dao;

import model.Venda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaDAO {

    private Conexao conexao;
    private String query;
    private PreparedStatement ps;

    public VendaDAO(){
        conexao = Conexao.getConexao();
    }

    public void inserirVenda(Venda venda){
        this.query = "INSERT INTO venda (clienteId, vendedorId, data, tipoPagamento, parcelas, total) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            this.ps = conexao.getConnection().prepareStatement(this.query);
            this.ps.setInt(1, venda.getClienteId());
            this.ps.setInt(2, venda.getVendedorId());
            this.ps.setDate(3, venda.getData());
            this.ps.setString(4, venda.getTipoPagamento());
            this.ps.setInt(5, venda.getParcelas());
            this.ps.setBigDecimal(6, venda.getTotal());
            this.ps.executeUpdate();
            this.ps.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
