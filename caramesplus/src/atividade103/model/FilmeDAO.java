package atividade103.model;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {
    // TO DO: CRUD e filtro de filme

    // Cadastro no banco de dados
    public static boolean cadastrar(Filme p) {
        try {
            // Conex�o com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instru��o SQL que ser� executada
            String sql = "INSERT INTO filme (nome, dataLancamento, categoria, assistido, ativo) VALUES (?,?,?,?,?);";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);
            consulta.setString(1, p.getNome());
            consulta.setString(2, p.getDataLancamento());
            consulta.setString(3, p.getCategoria());
            consulta.setBoolean(4, p.isAssistido());   
            consulta.setBoolean(5, true);            

            // Executa a instru��o SQL
            consulta.execute();

            // Desconectar do banco de dados
            conexao.desconectar();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar no banco de dados");
            return false;
        }
    }

    public static List<Filme> listarTodos() {
        // Declara��o da vari�vel lista que ser� retornada
        List<Filme> lista = new ArrayList<Filme>();

        try {
            // Conex�o com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instru��o SQL que ser� executada
            String sql = "SELECT * FROM filme WHERE ativo = 1";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);

            // Executar a instru��o SQL e pegar os resultados
            ResultSet resposta = consulta.executeQuery();

            while (resposta.next()) {
                Filme p = new Filme();

                p.setId(resposta.getInt("id"));
                p.setNome(resposta.getString("nome"));
                p.setDataLancamento(resposta.getString("dataLancamento"));
                p.setCategoria(resposta.getString("categoria"));
                p.setAssistido(resposta.getBoolean("assistido"));                

                lista.add(p);
            }

            // Desconectar do banco de dados
            conexao.desconectar();
        } catch (SQLException e) {
            System.out.println("Erro ao listar os regitros do banco de dados");
        }

        return lista;
    }

    public static Filme buscarPorId(int id) {
        // Declara��o da vari�vel lista que ser� retornada
        Filme p = new Filme();

        try {
            // Conex�o com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instru��o SQL que ser� executada
            String sql = "SELECT * FROM filme WHERE id=? AND ativo = 1";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);
            consulta.setInt(1, id);

            // Executar a instru��o SQL e pegar os resultados
            ResultSet resposta = consulta.executeQuery();

            if (resposta.next()) {
                p.setId(resposta.getInt("id"));
                p.setNome(resposta.getString("nome"));
                p.setDataLancamento(resposta.getString("dataLancamento"));
                p.setCategoria(resposta.getString("categoria"));
                p.setAssistido(resposta.getBoolean("assistido"));                 
            }

            // Desconectar do banco de dados
            conexao.desconectar();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o regitro " + id + " do banco de dados");
        }

        return p;
    }

    public static boolean atualizar(Filme p) {
        try {
            // Conex�o com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instru��o SQL que ser� executada
            String sql = "UPDATE filme SET nome=?, dataLancamento=?, categoria=?, assistido=? WHERE id=? AND ativo=1;";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);

            // Passar informa��es do objeto para a consulta
            consulta.setString(1, p.getNome());
            consulta.setString(2, p.getDataLancamento());
            consulta.setString(3, p.getCategoria());
            consulta.setBoolean(4, p.isAssistido());            
            consulta.setInt(5, p.getId());
            
            // Executa a instru��o SQL
            consulta.execute();

            // Desconectar do banco de dados
            conexao.desconectar();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar regitro no banco de dados");
            return false;
        }
    }

    public static boolean excluir(int id) {
        try {
            // Conex�o com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // N�O RECOMENDADO
            //String sql = "DELETE FROM filme WHERE id=?;";
            String sql = "UPDATE filme SET ativo = 0 WHERE id=?;";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);

            // Passar informa��es do objeto para a consulta
            consulta.setInt(1, id);

            // Executa a instru��o SQL
            consulta.execute();

            // Desconectar do banco de dados
            conexao.desconectar();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar regitro no banco de dados");
            return false;
        }
    }

    public static boolean recuperar(int id) {
        try {
            // Conex�o com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // N�O RECOMENDADO
            //String sql = "DELETE FROM filme WHERE id=?;";
            String sql = "UPDATE filme SET ativo = 1 WHERE id=?;";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);

            // Passar informa��es do objeto para a consulta
            consulta.setInt(1, id);

            // Executa a instru��o SQL
            consulta.execute();

            // Desconectar do banco de dados
            conexao.desconectar();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar regitro no banco de dados");
            return false;
        }
    }

    public static List<Filme> filtrarPorNome(String textoPesquisa) {

        // Declara��o da vari�vel lista que ser� retornada
        List<Filme> lista = new ArrayList<Filme>();

        try {
            // Conex�o com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instru��o SQL que ser� executada
            String sql = "SELECT * FROM filme WHERE ativo = 1 AND nome LIKE ?";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);
            consulta.setString(1, "%" + textoPesquisa + "%");
            // Executar a instru��o SQL e pegar os resultados
            ResultSet resposta = consulta.executeQuery();

            while (resposta.next()) {
                Filme p = new Filme();

                p.setId(resposta.getInt("id"));
                p.setNome(resposta.getString("nome"));
                p.setDataLancamento(resposta.getString("dataLancamento"));
                p.setCategoria(resposta.getString("categoria"));
                p.setAssistido(resposta.getBoolean("assistido"));                

                lista.add(p);
            }

            // Desconectar do banco de dados
            conexao.desconectar();
        } catch (SQLException e) {
            System.out.println("Erro ao listar os regitros do banco de dados");
        }

        return lista;
    }
}