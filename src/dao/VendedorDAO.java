package dao;

import model.Vendedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendedorDAO {

    private Conexao conexao;
    private String query;
    private PreparedStatement ps;

    public VendedorDAO(){
        conexao = Conexao.getConexao();
    }

    public int inserirVendedor(Vendedor vendedor){
        int generatedId = -1;
        this.query = "INSERT INTO vendedor (nome, sobrenome, dataNascimento, telefone, cpf, cidade, estado, pais, endereco, dataCadastro, email, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            this.ps = conexao.getConnection().prepareStatement(this.query, PreparedStatement.RETURN_GENERATED_KEYS);
            this.ps.setString(1, vendedor.getNome());
            this.ps.setString(2, vendedor.getSobrenome());
            this.ps.setDate(3, vendedor.getDataNascimento());
            this.ps.setString(4, vendedor.getTelefone());
            this.ps.setString(5, vendedor.getCpf());
            this.ps.setString(6, vendedor.getCidade());
            this.ps.setString(7, vendedor.getEstado());
            this.ps.setString(8, vendedor.getPais());
            this.ps.setString(9, vendedor.getEndereco());
            this.ps.setDate(10, vendedor.getDataCadastro());
            this.ps.setString(11, vendedor.getEmail());
            this.ps.setString(12, vendedor.getSenha());
            this.ps.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            this.ps.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return generatedId;
    }
}
