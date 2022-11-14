package Exercice1;

public class Main {
    public static void main(String[] args) {
        DAO dao = DAO.instance();
        System.out.println("\n************ Affichage des commandes (non normalisées) ************");
        for (Commande c1 : dao.commandes()) {
            System.out.println(c1);
        }

        System.out.println(" \n************ Affichage des commandes normalisées ************");
        for (Commande c1 : dao.commandes()) {
            System.out.println(c1.normaliser());
        }

        System.out.println("************ Affichage des produits du dao ************");
        System.out.println(dao.produits());

        System.out.println("\n************ Produits à TVA basse ************");
        System.out.println(dao.selectionProduits(p -> p.cat() == Categorie.REDUIT));

        System.out.println("\n************ Produits à TVA basse coûtant plus de 5 EUR ************");
        System.out.println(dao.selectionProduits(p -> p.cat() == Categorie.REDUIT && p.prix() > 5.0));

        System.out.println("\n************ Commandes de plus de 2 items ************");
        System.out.println(dao.selectionCommande(c -> c.lignes().size() > 2));

        System.out.println("\n************ Commandes (non normalisées) contenant au moins un produit à TVA basse commandé en plus de 2 exemplaires ************");
        System.out.println(dao.selectionCommandeSurExistanceLigne(p -> p.fst().cat() == Categorie.REDUIT && p.snd() > 2 ));

    }
}
