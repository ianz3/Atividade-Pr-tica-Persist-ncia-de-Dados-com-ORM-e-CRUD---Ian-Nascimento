import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import java.util.List;
import java.util.ArrayList;

/**
 * Repositório (padrão Repository) da entidade Produto.
 * Implementa as operações CRUD usando o Dao do ORMLite.
 */
public class ProdutoRepository
{
    private static Database database;
    private static Dao<Produto, Integer> dao;
    private List<Produto> loadedProdutos;
    private Produto loadedProduto;

    public ProdutoRepository(Database database) {
        ProdutoRepository.setDatabase(database);
        loadedProdutos = new ArrayList<Produto>();
    }

    public static void setDatabase(Database database) {
        ProdutoRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Produto.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Produto.class);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    // ==================== CREATE ====================
    public Produto create(Produto produto) {
        int nrows = 0;
        try {
            nrows = dao.create(produto);
            if ( nrows == 0 )
                throw new SQLException("Erro: produto não foi salvo");
            this.loadedProduto = produto;
            loadedProdutos.add(produto);
            System.out.println("[CREATE] Produto cadastrado: " + produto);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return produto;
    }

    // ==================== READ ====================
    public Produto loadFromId(int id) {
        try {
            this.loadedProduto = dao.queryForId(id);
            if (this.loadedProduto != null)
                this.loadedProdutos.add(this.loadedProduto);
            System.out.println("[READ] Busca por id " + id + ": " + this.loadedProduto);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedProduto;
    }

    public List<Produto> loadAll() {
        try {
            this.loadedProdutos = dao.queryForAll();
            if (this.loadedProdutos.size() != 0)
                this.loadedProduto = this.loadedProdutos.get(0);
            System.out.println("[READ] " + loadedProdutos.size() + " produto(s) no estoque:");
            for (Produto p : loadedProdutos)
                System.out.println("   " + p);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedProdutos;
    }

    /** Consulta extra: lista produtos de uma categoria (ex.: "Laticínios"). */
    public List<Produto> loadByCategoria(String categoria) {
        List<Produto> resultado = new ArrayList<Produto>();
        try {
            resultado = dao.queryForEq("categoria", categoria);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultado;
    }

    // ==================== UPDATE ====================
    public void update(Produto produto) {
        try {
            int nrows = dao.update(produto);
            if ( nrows == 0 )
                throw new SQLException("Erro: produto não foi atualizado (id inexistente?)");
            this.loadedProduto = produto;
            System.out.println("[UPDATE] Produto atualizado: " + produto);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /** Regra de negócio de estoque: soma (entrada) ou subtrai (saída) unidades. */
    public void movimentarEstoque(int id, int quantidade) {
        Produto p = loadFromId(id);
        if (p == null) {
            System.out.println("Produto id " + id + " não encontrado.");
            return;
        }
        int nova = p.getQuantidade() + quantidade;
        if (nova < 0) {
            System.out.println("Estoque insuficiente para " + p.getNome());
            return;
        }
        p.setQuantidade(nova);
        update(p);
    }

    // ==================== DELETE ====================
    public void delete(Produto produto) {
        try {
            int nrows = dao.delete(produto);
            if ( nrows == 0 )
                throw new SQLException("Erro: produto não foi excluído (id inexistente?)");
            loadedProdutos.remove(produto);
            if (this.loadedProduto != null && this.loadedProduto.getId() == produto.getId())
                this.loadedProduto = null;
            System.out.println("[DELETE] Produto excluído: id " + produto.getId() + " - " + produto.getNome());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteById(int id) {
        Produto p = loadFromId(id);
        if (p != null)
            delete(p);
        else
            System.out.println("Produto id " + id + " não encontrado.");
    }

    // ===== Getters e Setters =====
    public List<Produto> getLoadedProdutos() { return loadedProdutos; }
    public Produto getLoadedProduto() { return loadedProduto; }
}
