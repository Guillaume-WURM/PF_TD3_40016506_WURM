package Exercice1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static Exercice1.Categorie.*;

public class DAO {
    private List<Commande> commandes;

    private DAO(Commande c1, Commande ...cs) {
        commandes = new ArrayList<>();
        commandes.add(c1);
        commandes.addAll(List.of(cs));
    }

    public static DAO instance = null;

    public static final DAO instance() {
        if (instance == null) {
            Produit camembert = new Produit("Camembert", 4.0, NORMAL);
            Produit yaourts = new Produit("Yaourts", 2.5, INTERMEDIAIRE);
            Produit masques = new Produit("Masques", 25.0, REDUIT);
            Produit gel = new Produit("Gel", 5.0, REDUIT);
            Produit tournevis = new Produit("Tournevis", 4.5, NORMAL);
            //
            Commande c1 = new Commande()
                    .ajouter(camembert, 1)
                    .ajouter(yaourts, 6);
            Commande c2 = new Commande()
                    .ajouter(masques, 2)
                    .ajouter(gel, 10)
                    .ajouter(camembert, 2)
                    .ajouter(masques, 3);
            //
            instance = new DAO(c1,c2);
        }
        return instance;
    }

    /**
     * liste de toutes les commandes
     */
    public List<Commande> commandes() { return commandes; }

    /**
     * ensemble des différents produits commandés
     */
    public Set<Produit> produits() {
        Set<Produit> produits = new HashSet<>();
        for (Commande c : commandes) {
            for (Paire<Produit, Integer> p : c.lignes()) {
                produits.add(p.fst());
            }
        }
        return produits;
    }

    /**
     * liste des commandes vérifiant un prédicat
     */
    public List<Commande> selectionCommande(Predicate<Commande> p) {
        List<Commande> selectCommande = new ArrayList<>();
        for(Commande c : this.commandes) {
            if(p.test(c)) {
                selectCommande.add(c);
            }
        }
        return selectCommande;
    }

    /**
     * liste des commandes dont au moins une ligne vérifie un prédicat
     */
    public List<Commande> selectionCommandeSurExistanceLigne(Predicate<Paire<Produit,Integer>> p) {
        List<Commande> selectionCommandes = new ArrayList<>();
        for(Commande c : this.commandes) {
            Boolean verif = false;
            for(Paire<Produit,Integer> ligne : c.lignes()) {
                if(p.test(ligne)) {
                    verif = true;
                }
            }
            if(verif == true) {
                selectionCommandes.add(c);
            }
        }
        return selectionCommandes;
    }

    /**
     * ensemble des différents produits commandés vérifiant un prédicat
     */
    public Set<Produit> selectionProduits(Predicate<Produit> p) {
        Set<Produit> produits = new HashSet<>();
        for (Produit produit : produits()) {
            if (p.test(produit)) {
                produits.add(produit);
            }
        }
        return produits;
    }
}
