import java.sql.*; //Email admin: adminemail@admin.com senha: admin
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import dao.Conexao;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection;

    public static void main(String[] args) {
        // Estabelecer conexão usando a classe Conexao do pacote dao
        connection = Conexao.getConexao().getConnection();
        if (connection == null) {
            System.out.println("Não foi possível estabelecer a conexão com o banco de dados.");
            return;
        }

        String userType = login();
        if (userType == null) {
            return; // Saída caso o login falhe
        }

        int option;
        do {
            if ("admin".equals(userType)) {
                System.out.println("=== Menu Admin ===");
                System.out.println("1. Cadastrar Admin");
                System.out.println("2. Cadastrar Vendedor");
                System.out.println("3. Cadastrar Cliente");
                System.out.println("4. Cadastrar Fornecedor");
                System.out.println("5. Cadastrar Produto");
                System.out.println("6. Listar Vendedores");
                System.out.println("7. Listar Clientes");
                System.out.println("8. Listar Fornecedores");
                System.out.println("9. Listar Produtos");
                System.out.println("10. Registrar Venda");
                System.out.println("11. Listar Vendas");
                System.out.println("12. Fechamento do Dia");
                System.out.println("13. Deletar Vendedor");
                System.out.println("14. Deletar Cliente");
                System.out.println("15. Deletar Fornecedor");
                System.out.println("16. Deletar Produto");
                System.out.println("17. Editar Vendedor");
                System.out.println("18. Editar Cliente");
                System.out.println("19. Editar Fornecedor");
                System.out.println("20. Editar Produto");
                System.out.println("0. Sair");
            } else {
                System.out.println("=== Menu Vendedor ===");
                System.out.println("3. Cadastrar Cliente");
                System.out.println("5. Cadastrar Produto");
                System.out.println("7. Listar Clientes");
                System.out.println("9. Listar Produtos");
                System.out.println("10. Registrar Venda");
                System.out.println("11. Listar Vendas");
                System.out.println("17. Editar Vendedor");
                System.out.println("18. Editar Cliente");
                System.out.println("19. Editar Fornecedor");
                System.out.println("20. Editar Produto");
                System.out.println("0. Sair");
            }
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    if ("admin".equals(userType)) cadastrarAdmin();
                    break;
                case 2:
                    if ("admin".equals(userType)) cadastrarVendedor();
                    break;
                case 3:
                    cadastrarCliente();
                    break;
                case 4:
                    if ("admin".equals(userType)) cadastrarFornecedor();
                    break;
                case 5:
                    cadastrarProduto();
                    break;
                case 6:
                    if ("admin".equals(userType)) listarVendedor();
                    break;
                case 7:
                    listarCliente();
                    break;
                case 8:
                    if ("admin".equals(userType)) listarFornecedor();
                    break;
                case 9:
                    listarProduto();
                    break;
                case 10:
                    registrarVenda();
                    break;
                case 11:
                    listarVendas();
                    break;
                case 12:
                    if ("admin".equals(userType)) fechamentoDia();
                    break;
                case 13:
                    if ("admin".equals(userType)) deletarVendedor();
                    break;
                case 14:
                    if ("admin".equals(userType)) deletarCliente();
                    break;
                case 15:
                    if ("admin".equals(userType)) deletarFornecedor();
                    break;
                case 16:
                    if ("admin".equals(userType)) deletarProduto();
                    break;
                case 17:
                    editarVendedor();
                    break;
                case 18:
                    editarCliente();
                    break;
                case 19:
                    editarFornecedor();
                    break;
                case 20:
                    editarProduto();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    private static String login() {
        System.out.println("=== Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        String sql = "SELECT 'admin' AS tipo FROM admin WHERE email = ? AND senha = ? UNION SELECT 'vendedor' AS tipo FROM vendedor WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            stmt.setString(3, email);
            stmt.setString(4, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("tipo");
            } else {
                System.out.println("Credenciais inválidas.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void cadastrarAdmin() {
        System.out.println("=== Cadastrar Admin ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        String sql = "INSERT INTO admin (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.executeUpdate();
            System.out.println("Admin cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cadastrarVendedor() {
        System.out.println("=== Cadastrar Vendedor ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine();
        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        String dataNascimento = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("País: ");
        String pais = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Data de Cadastro (AAAA-MM-DD): ");
        String dataCadastro = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        String sql = "INSERT INTO vendedor (nome, sobrenome, dataNascimento, telefone, cpf, cidade, estado, pais, endereco, dataCadastro, email, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, sobrenome);
            stmt.setDate(3, Date.valueOf(dataNascimento));
            stmt.setString(4, telefone);
            stmt.setString(5, cpf);
            stmt.setString(6, cidade);
            stmt.setString(7, estado);
            stmt.setString(8, pais);
            stmt.setString(9, endereco);
            stmt.setDate(10, Date.valueOf(dataCadastro));
            stmt.setString(11, email);
            stmt.setString(12, senha);
            stmt.executeUpdate();
            System.out.println("Vendedor cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cadastrarCliente() {
        System.out.println("=== Cadastrar Cliente ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine();
        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        String dataNascimento = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("País: ");
        String pais = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Data de Cadastro (AAAA-MM-DD): ");
        String dataCadastro = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        String sql = "INSERT INTO cliente (nome, sobrenome, dataNascimento, telefone, cpf, cidade, estado, pais, endereco, numero, dataCadastro, email, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, sobrenome);
            stmt.setDate(3, Date.valueOf(dataNascimento));
            stmt.setString(4, telefone);
            stmt.setString(5, cpf);
            stmt.setString(6, cidade);
            stmt.setString(7, estado);
            stmt.setString(8, pais);
            stmt.setString(9, endereco);
            stmt.setString(10, numero);
            stmt.setDate(11, Date.valueOf(dataCadastro));
            stmt.setString(12, email);
            stmt.setString(13, senha);
            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cadastrarFornecedor() {
        System.out.println("=== Cadastrar Fornecedor ===");
        System.out.print("Nome Fantasia: ");
        String nomeFantasia = scanner.nextLine();
        System.out.print("Razão Social: ");
        String razaoSocial = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("País: ");
        String pais = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Data de Cadastro (AAAA-MM-DD): ");
        String dataCadastro = scanner.nextLine();

        String sql = "INSERT INTO fornecedor (nomeFantasia, razaoSocial, cnpj, cidade, estado, pais, endereco, numero, telefone, email, dataCadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeFantasia);
            stmt.setString(2, razaoSocial);
            stmt.setString(3, cnpj);
            stmt.setString(4, cidade);
            stmt.setString(5, estado);
            stmt.setString(6, pais);
            stmt.setString(7, endereco);
            stmt.setString(8, numero);
            stmt.setString(9, telefone);
            stmt.setString(10, email);
            stmt.setDate(11, Date.valueOf(dataCadastro));
            stmt.executeUpdate();
            System.out.println("Fornecedor cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cadastrarProduto() {
        System.out.println("=== Cadastrar Produto ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Preço: ");
        double preco = Double.parseDouble(scanner.nextLine());
        System.out.print("ID do Fornecedor: ");
        int fornecedorid = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "INSERT INTO produto (nome, descricao, quantidade, preco, fornecedorId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setInt(3, quantidade);
            stmt.setDouble(4, preco);
            stmt.setInt(5, fornecedorid);
            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void listarVendedor() {
        System.out.println("=== Listar Vendedores ===");
        String sql = "SELECT * FROM vendedor";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Sobrenome: " + rs.getString("sobrenome"));
                System.out.println("Data de Nascimento: " + rs.getDate("dataNascimento"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("CPF: " + rs.getString("cpf"));
                System.out.println("Cidade: " + rs.getString("cidade"));
                System.out.println("Estado: " + rs.getString("estado"));
                System.out.println("País: " + rs.getString("pais"));
                System.out.println("Endereço: " + rs.getString("endereco"));
                System.out.println("Data de Cadastro: " + rs.getDate("dataCadastro"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarCliente() {
        System.out.println("=== Listar Clientes ===");
        String sql = "SELECT * FROM cliente";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Sobrenome: " + rs.getString("sobrenome"));
                System.out.println("Data de Nascimento: " + rs.getDate("dataNascimento"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("CPF: " + rs.getString("cpf"));
                System.out.println("Cidade: " + rs.getString("cidade"));
                System.out.println("Estado: " + rs.getString("estado"));
                System.out.println("País: " + rs.getString("pais"));
                System.out.println("Endereço: " + rs.getString("endereco"));
                System.out.println("Número: " + rs.getString("numero"));
                System.out.println("Data de Cadastro: " + rs.getDate("dataCadastro"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarFornecedor() {
        System.out.println("=== Listar Fornecedores ===");
        String sql = "SELECT * FROM fornecedor";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome Fantasia: " + rs.getString("nomeFantasia"));
                System.out.println("Razão Social: " + rs.getString("razaoSocial"));
                System.out.println("CNPJ: " + rs.getString("cnpj"));
                System.out.println("Cidade: " + rs.getString("cidade"));
                System.out.println("Estado: " + rs.getString("estado"));
                System.out.println("País: " + rs.getString("pais"));
                System.out.println("Endereço: " + rs.getString("endereco"));
                System.out.println("Número: " + rs.getString("numero"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Data de Cadastro: " + rs.getDate("dataCadastro"));
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarProduto() {
        System.out.println("=== Listar Produtos ===");
        String sql = "SELECT * FROM produto";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Descrição: " + rs.getString("descricao"));
                System.out.println("Quantidade: " + rs.getInt("quantidade"));
                System.out.println("Preço: " + rs.getDouble("preco"));
                System.out.println("Data de Cadastro: " + rs.getDate("dataCadastro"));
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registrarVenda() {
        System.out.println("=== Registrar Venda ===");

        // Verificar se é vendedor
        System.out.print("ID do Vendedor: ");
        int vendedorId = scanner.nextInt();
        scanner.nextLine();  // Consumir nova linha

        String sqlVerificaVendedor = "SELECT COUNT(*) FROM vendedor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlVerificaVendedor)) {
            stmt.setInt(1, vendedorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("ID do Vendedor inválido.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        System.out.print("ID do Cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha

        ArrayList<int[]> itens = new ArrayList<>();
        String adicionarMaisItens;
        do {
            System.out.print("ID do Produto: ");
            int produtoId = scanner.nextInt();
            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha
            itens.add(new int[]{produtoId, quantidade});

            System.out.print("Deseja adicionar mais itens? (sim/não): ");
            adicionarMaisItens = scanner.nextLine();
        } while (adicionarMaisItens.equalsIgnoreCase("sim"));

        System.out.print("Tipo de Pagamento (crédito, débito, dinheiro): ");
        String tipoPagamento = scanner.nextLine();

        double totalVenda = 0;
        for (int[] item : itens) {
            totalVenda += calcularTotalItem(item[0], item[1]);
        }

        int numParcelas = 1;
        if ("crédito".equalsIgnoreCase(tipoPagamento) && totalVenda > 1000) {
            System.out.print("Deseja parcelar? (sim/não): ");
            String desejaParcelar = scanner.nextLine();
            if ("sim".equalsIgnoreCase(desejaParcelar)) {
                System.out.print("Número de parcelas (até 10): ");
                numParcelas = scanner.nextInt();
                scanner.nextLine(); // Consumir nova linha
                if (numParcelas > 5) {
                    totalVenda *= 1.05;
                }
            }
        }

        String sqlVenda = "INSERT INTO venda (clienteId, vendedorId, tipoPagamento, total, parcelas, dataVenda) VALUES (?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, clienteId);
            stmt.setInt(2, vendedorId);
            stmt.setString(3, tipoPagamento);
            stmt.setDouble(4, totalVenda);
            stmt.setInt(5, numParcelas);
            stmt.setDate(6, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int vendaId = rs.getInt(1);
                for (int[] item : itens) {
                    registrarItensVenda(vendaId, item[0], item[1]);
                }
            }
            System.out.println("Venda registrada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static double calcularTotalItem(int produtoId, int quantidade) {
        String sql = "SELECT preco FROM produto WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("preco") * quantidade;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static void registrarItensVenda(int vendaId, int produtoId, int quantidade) {
        String sql = "INSERT INTO item_venda (vendaId, produtoId, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vendaId);
            stmt.setInt(2, produtoId);
            stmt.setInt(3, quantidade);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarVendas() {
        System.out.println("=== Listar Vendas ===");
        String sql = "SELECT venda.clienteId, item_venda.produtoId, item_venda.quantidade,item_venda.vendaId FROM item_venda CROSS JOIN venda;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID da Venda: " + rs.getInt("vendaId"));
                System.out.println("ID do Cliente: " + rs.getInt("clienteId"));
                System.out.println("ID do Produto: " + rs.getInt("produtoId"));
                System.out.println("Quantidade: " + rs.getInt("quantidade"));
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void fechamentoDia() {
        System.out.println("=== Fechamento do Dia ===");
        Date dataVenda = Date.valueOf(String.valueOf(Date.valueOf(LocalDate.now())));

        String sql = "SELECT total FROM venda WHERE DATE(dataVenda) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dataVenda.toLocalDate()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Total de vendas do dia " + dataVenda + ": R$ " + rs.getDouble("total"));
            } else {
                System.out.println("Nenhuma venda registrada para o dia " + dataVenda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deletarVendedor() {
        System.out.println("=== Deletar Vendedor ===");
        System.out.print("ID do Vendedor: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        // Primeiro, deletar as vendas associadas ao vendedor
        String deleteVendasSql = "DELETE FROM venda WHERE vendedorId = ?";
        try (PreparedStatement deleteVendasStmt = connection.prepareStatement(deleteVendasSql)) {
            deleteVendasStmt.setInt(1, id);
            deleteVendasStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return; // Se falhar em deletar vendas, não continuar
        }

        // Agora, deletar o vendedor
        String deleteVendedorSql = "DELETE FROM vendedor WHERE id = ?";
        try (PreparedStatement deleteVendedorStmt = connection.prepareStatement(deleteVendedorSql)) {
            deleteVendedorStmt.setInt(1, id);
            int rowsAffected = deleteVendedorStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vendedor deletado com sucesso!");
            } else {
                System.out.println("Nenhum vendedor encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private static void deletarCliente() {
        System.out.println("=== Deletar Cliente ===");
        System.out.print("ID do Cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        String sql = "DELETE FROM cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente deletado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deletarFornecedor() {
        System.out.println("=== Deletar Fornecedor ===");
        System.out.print("ID do Fornecedor: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        String sql = "DELETE FROM fornecedor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Fornecedor deletado com sucesso!");
            } else {
                System.out.println("Nenhum fornecedor encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deletarProduto() {
        System.out.println("=== Deletar Produto ===");
        System.out.print("ID do Produto: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto deletado com sucesso!");
            } else {
                System.out.println("Nenhum produto encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Métodos de edição
    private static void editarVendedor() {
        System.out.println("=== Editar Vendedor ===");
        System.out.print("ID do Vendedor: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        System.out.print("Novo Nome: ");
        String novoNome = scanner.nextLine();
        System.out.print("Novo Email: ");
        String novoEmail = scanner.nextLine();
        System.out.print("Nova Senha: ");
        String novaSenha = scanner.nextLine();

        String sql = "UPDATE vendedor SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoEmail);
            stmt.setString(3, novaSenha);
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vendedor atualizado com sucesso!");
            } else {
                System.out.println("Nenhum vendedor encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editarCliente() {
        System.out.println("=== Editar Cliente ===");
        System.out.print("ID do Cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        System.out.print("Novo Nome: ");
        String novoNome = scanner.nextLine();
        System.out.print("Novo Email: ");
        String novoEmail = scanner.nextLine();
        System.out.print("Nova Senha: ");
        String novaSenha = scanner.nextLine();

        String sql = "UPDATE cliente SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoEmail);
            stmt.setString(3, novaSenha);
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editarFornecedor() {
        System.out.println("=== Editar Fornecedor ===");
        System.out.print("ID do Fornecedor: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        System.out.print("Novo Nome Fantasia: ");
        String novoNome = scanner.nextLine();
        System.out.print("Novo Email: ");
        String novoEmail = scanner.nextLine();
        System.out.print("Novo Telefone: ");
        String novoTelefone = scanner.nextLine();

        String sql = "UPDATE fornecedor SET nomeFantasia = ?, email = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoEmail);
            stmt.setString(3, novoTelefone);
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Fornecedor atualizado com sucesso!");
            } else {
                System.out.println("Nenhum fornecedor encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editarProduto() {
        System.out.println("=== Editar Produto ===");
        System.out.print("ID do Produto: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        System.out.print("Novo Nome: ");
        String novoNome = scanner.nextLine();
        System.out.print("Nova Descrição: ");
        String novaDescricao = scanner.nextLine();
        System.out.print("Novo Preço: ");
        double novoPreco = scanner.nextDouble();
        scanner.nextLine(); // Consumir a nova linha

        String sql = "UPDATE produto SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novaDescricao);
            stmt.setDouble(3, novoPreco);
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Nenhum produto encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
