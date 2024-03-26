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
            // Conexão com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instrução SQL que será executada
            String sql = "INSERT INTO filme (nome, dataLancamento, categoria, assistido, ativo) VALUES (?,?,?,?,?);";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);
            consulta.setString(1, p.getNome());
            consulta.setString(2, p.getDataLancamento());
            consulta.setString(3, p.getCategoria());
            consulta.setBoolean(4, p.isAssistido());   
            consulta.setBoolean(5, true);            

            // Executa a instrução SQL
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
        // Declaração da variável lista que será retornada
        List<Filme> lista = new ArrayList<Filme>();

        try {
            // Conexão com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instrução SQL que será executada
            String sql = "SELECT * FROM filme WHERE ativo = 1";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);

            // Executar a instrução SQL e pegar os resultados
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
        // Declaração da variável lista que será retornada
        Filme p = new Filme();

        try {
            // Conexão com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instrução SQL que será executada
            String sql = "SELECT * FROM filme WHERE id=? AND ativo = 1";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);
            consulta.setInt(1, id);

            // Executar a instrução SQL e pegar os resultados
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
            // Conexão com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instrução SQL que será executada
            String sql = "UPDATE filme SET nome=?, dataLancamento=?, categoria=?, assistido=? WHERE id=? AND ativo=1;";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);

            // Passar informações do objeto para a consulta
            consulta.setString(1, p.getNome());
            consulta.setString(2, p.getDataLancamento());
            consulta.setString(3, p.getCategoria());
            consulta.setBoolean(4, p.isAssistido());            
            consulta.setInt(5, p.getId());
            
            // Executa a instrução SQL
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
            // Conexão com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // NÃO RECOMENDADO
            //String sql = "DELETE FROM filme WHERE id=?;";
            String sql = "UPDATE filme SET ativo = 0 WHERE id=?;";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);

            // Passar informações do objeto para a consulta
            consulta.setInt(1, id);

            // Executa a instrução SQL
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
            // Conexão com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // NÃO RECOMENDADO
            //String sql = "DELETE FROM filme WHERE id=?;";
            String sql = "UPDATE filme SET ativo = 1 WHERE id=?;";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);

            // Passar informações do objeto para a consulta
            consulta.setInt(1, id);

            // Executa a instrução SQL
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

        // Declaração da variável lista que será retornada
        List<Filme> lista = new ArrayList<Filme>();

        try {
            // Conexão com o banco de dados
            ConexaoJDBC conexao = new ConexaoJDBC();
            conexao.conectar();

            // Instrução SQL que será executada
            String sql = "SELECT * FROM filme WHERE ativo = 1 AND nome LIKE ?";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);
            consulta.setString(1, "%" + textoPesquisa + "%");
            // Executar a instrução SQL e pegar os resultados
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