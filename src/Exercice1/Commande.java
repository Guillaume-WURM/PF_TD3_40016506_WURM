package Exercice1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Commande {
    private List<Paire<Produit, Integer>> lignes;

    private Function<Paire<Produit,Integer>, String> formatteurLigne;
    private static Function<Paire<Produit,Integer>, String> formatteurLigneDefaut = paire -> String.format("%s x %d = %.2f", paire.fst(), paire.snd(), paire.fst().prix() * paire.snd());

    public Commande() {
        this.lignes = new ArrayList<>();
        this.formatteurLigne = formatteurLigneDefaut;
    }

    public Commande ajouter(Produit p, int q) {
        lignes.add(new Paire<>(p, q));
        return this;
    }

    public List<Paire<Produit, Integer>> lignes() {
        return lignes;
    }

    @Override
    public String toString() {
        return lignes.stream().map(formatteurLigne).collect(Collectors.joining("==>"));
    }

    /**
     * cumule les lignes en fonction des produits
     */
    public Commande normaliser() {
        Commande commandeNormalisee = new Commande();
        regrouper(lignes).forEach((produit, integer) -> commandeNormalisee.ajouter(produit, integer.stream().reduce(0,Integer::sum)));
        return commandeNormalisee;
    }

    public Double cout(Function<Paire<Produit,Integer>,Double> calculLigne) {
        return normaliser().lignes.stream()
                .map(l -> calculLigne.apply(l))
                .reduce(0.0, (x,y) -> x+y);
    }

    public String affiche(Function<Paire<Produit, Integer>, Double> calculLigne) {
        Commande c = this.normaliser();
        final String HLINE = "+------------+------------+-----+------------+--------+------------+\n";
        StringBuilder str = new StringBuilder();
        str.append("\n\nCommande\n");
        str.append(HLINE);
        str.append("+ nom        + prix       + qté + prix ht    + tva    + prix ttc   +\n");
        str.append(HLINE);
        for (Paire<Produit, Integer> ligne : c.lignes) {
            str.append(String.format("+ %10s + %10.2f + %3d + %10.2f + %5.2f%% + %10.2f +\n", ligne.fst(), // nom
                    ligne.fst().prix(), // prix unitaire
                    ligne.snd(), // qté
                    ligne.fst().prix() * ligne.snd(), // prix ht
                    ligne.fst().cat().tva() * 100, // tva
                    calculLigne.apply(ligne)));
        }
        str.append(HLINE);
        str.append(String.format("Total : %10.2f", c.cout(calculLigne)));
        return str.toString();
    }

    public static <A,B> Map<A,List<B>> regrouper (List<Paire<A,B>> ligne){
        Map<A, List<B>> l1 = new HashMap<>();
        ligne.forEach(paire -> l1.computeIfAbsent(paire.fst(), snd -> new ArrayList<>()).add(paire.snd()));
        return l1;
    }
}
