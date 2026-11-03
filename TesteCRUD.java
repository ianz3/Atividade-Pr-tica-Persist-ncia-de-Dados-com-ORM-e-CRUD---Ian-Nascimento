import java.util.Calendar;
import java.util.Date;

/**
 * Demonstração das quatro operações CRUD da entidade Produto.
 * Pode ser executada pelo BlueJ (botão direito > void main(String[] args))
 * ou os métodos podem ser chamados interativamente na bancada de objetos.
 */
public class TesteCRUD
{
    private static Date data(int dia, int mes, int ano) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(ano, mes - 1, dia);
        return c.getTime();
    }

    public static void main(String[] args) {
        Database db = new Database("estoque.db");
        ProdutoRepository repo = new ProdutoRepository(db);

        System.out.println("\n===== 1. CREATE =====");
        Produto arroz  = repo.create(new Produto("Arroz Tipo 1 5kg", "7891000100101", "Mercearia", 40, 27.90, data(10, 6, 2027)));
        Produto leite  = repo.create(new Produto("Leite Integral 1L", "7891000200202", "Laticínios", 120, 5.49, data(15, 12, 2026)));
        Produto queijo = repo.create(new Produto("Queijo Mussarela 500g", "7891000300303", "Laticínios", 25, 24.90, data(30, 11, 2026)));

        System.out.println("\n===== 2. READ =====");
        repo.loadAll();
        repo.loadFromId(leite.getId());
        System.out.println("Laticínios: " + repo.loadByCategoria("Laticínios"));

        System.out.println("\n===== 3. UPDATE =====");
        arroz.setPrecoUnitario(25.50);          // promoção
        repo.update(arroz);
        repo.movimentarEstoque(leite.getId(), -30); // venda de 30 unidades
        repo.loadFromId(arroz.getId());

        System.out.println("\n===== 4. DELETE =====");
        repo.delete(queijo);
        System.out.println("Busca do produto excluído (deve ser null):");
        repo.loadFromId(queijo.getId());

        System.out.println("\n===== Estado final do estoque =====");
        repo.loadAll();

        db.close();
    }
}
