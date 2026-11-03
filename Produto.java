import java.util.Date;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

/**
 * Entidade que representa um produto do estoque de um supermercado.
 * Mapeada para a tabela "produto" do banco de dados via ORMLite.
 */
@DatabaseTable(tableName = "produto")
public class Produto
{
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String nome;

    @DatabaseField
    private String codigoBarras;

    @DatabaseField
    private String categoria;

    @DatabaseField
    private int quantidade;

    @DatabaseField
    private double precoUnitario;

    @DatabaseField(dataType = DataType.DATE)
    private Date dataValidade;

    /** Construtor vazio exigido pelo ORMLite. */
    public Produto() {
    }

    public Produto(String nome, String codigoBarras, String categoria,
                   int quantidade, double precoUnitario, Date dataValidade) {
        this.nome = nome;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.dataValidade = dataValidade;
    }

    public String printDataValidade() {
        if (dataValidade == null) return "-";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(dataValidade);
    }

    /** Valor total deste produto em estoque (quantidade x preço). */
    public double valorEmEstoque() {
        return quantidade * precoUnitario;
    }

    @Override
    public String toString() {
        return String.format("Produto[id=%d, nome=%s, codBarras=%s, categoria=%s, qtd=%d, preco=R$ %.2f, validade=%s]",
                id, nome, codigoBarras, categoria, quantidade, precoUnitario, printDataValidade());
    }

    // ===== Getters e Setters =====

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }

    public Date getDataValidade() { return dataValidade; }
    public void setDataValidade(Date dataValidade) { this.dataValidade = dataValidade; }
}
