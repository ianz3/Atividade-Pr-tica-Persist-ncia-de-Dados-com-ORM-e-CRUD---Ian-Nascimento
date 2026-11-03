import java.util.*;
public class Etapa {
  static Date d(int dia,int mes,int ano){Calendar c=Calendar.getInstance();c.clear();c.set(ano,mes-1,dia);return c.getTime();}
  public static void main(String[] a){
    Database db=new Database("estoque.db");
    ProdutoRepository repo=new ProdutoRepository(db);
    switch(a[0]){
      case "create":
        repo.create(new Produto("Arroz Tipo 1 5kg","7891000100101","Mercearia",40,27.90,d(10,6,2027)));
        repo.create(new Produto("Leite Integral 1L","7891000200202","Laticínios",120,5.49,d(15,12,2026)));
        repo.create(new Produto("Queijo Mussarela 500g","7891000300303","Laticínios",25,24.90,d(30,11,2026)));
        break;
      case "read":
        repo.loadAll();
        repo.loadFromId(2);
        System.out.println("[READ] Categoria Laticínios:");
        for(Produto p:repo.loadByCategoria("Laticínios")) System.out.println("   "+p);
        break;
      case "update":
        Produto arroz=repo.loadFromId(1);
        arroz.setPrecoUnitario(25.50);
        repo.update(arroz);
        repo.movimentarEstoque(2,-30);
        break;
      case "delete":
        Produto q=repo.loadFromId(3);
        repo.delete(q);
        System.out.println("Busca do produto excluído (deve ser null):");
        repo.loadFromId(3);
        break;
      case "persist":
        repo.loadAll();
        break;
    }
    db.close();
  }
}
