package dao;

import model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO {

    private Conexao conexao;
    private String query;
    private PreparedStatement ps;

    public ProdutoDAO(){
        conexao = Conexao.getConexao();
    }

    public void inserirProduto(Produto produto){
        this.query = "INSERT INTO produto (descricao, quantidade, preco, fornecedorId) VALUES (?, ?, ?, ?)";
        try {
            this.ps = conexao.getConnection().prepareStatement(this.query);
            this.ps.setString(1, produto.getDescricao());
            this.ps.setInt(2, produto.getQuantidade());
            this.ps.setBigDecimal(3, produto.getPreco());
            this.ps.setInt(4, produto.getFornecedorId());
            this.ps.executeUpdate();
            this.ps.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
