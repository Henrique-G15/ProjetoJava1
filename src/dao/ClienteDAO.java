package dao;

import model.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    private Conexao conexao;
    private String query;
    private PreparedStatement ps;

    public ClienteDAO(){
        conexao = Conexao.getConexao();
    }

    public int inserirCliente(Cliente cliente){
        int generatedId = -1;
        this.query = "INSERT INTO cliente (nome, sobrenome, dataNascimento, telefone, cpf, cidade, estado, pais, endereco, numero, email, senha, dataCadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            this.ps = conexao.getConnection().prepareStatement(this.query, PreparedStatement.RETURN_GENERATED_KEYS);
            this.ps.setString(1, cliente.getNome());
            this.ps.setString(2, cliente.getSobrenome());
            this.ps.setDate(3, cliente.getDataNascimento());
            this.ps.setString(4, cliente.getTelefone());
            this.ps.setString(5, cliente.getCpf());
            this.ps.setString(6, cliente.getCidade());
            this.ps.setString(7, cliente.getEstado());
            this.ps.setString(8, cliente.getPais());
            this.ps.setString(9, cliente.getEndereco());
            this.ps.setString(10, cliente.getNumero());
            this.ps.setString(11, cliente.getEmail());
            this.ps.setString(12, cliente.getSenha());
            this.ps.setDate(13, cliente.getDataCadastro());
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
