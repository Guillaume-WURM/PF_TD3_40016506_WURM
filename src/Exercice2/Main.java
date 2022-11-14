package Exercice2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Matiere m1 = new Matiere("MAT1");
        Matiere m2 = new Matiere("MAT2");
        UE ue1 = new UE("UE1", Map.of(m1, 2, m2, 2));
        Matiere m3 = new Matiere("MAT3");
        UE ue2 = new UE("UE2", Map.of(m3, 1));
        Annee a1 = new Annee(Set.of(ue1, ue2));
        Etudiant e1 = new Etudiant("39001", "Alice", "Merveille", a1);
        e1.noter(m1, 12.0);
        e1.noter(m2, 14.0);
        e1.noter(m3, 10.0);
        Etudiant e2 = new Etudiant("39002", "Bob", "Eponge", a1);
        e2.noter(m1, 14.0);
        e2.noter(m3, 14.0);
        Etudiant e3 = new Etudiant("39003", "Charles", "Chaplin", a1);
        e3.noter(m1, 18.0);
        e3.noter(m2, 5.0);
        e3.noter(m3, 14.0);

        System.out.println("************ Début : Exercice 3 : Question 1 : V1 ************");
        afficheSi("**TOUS LES ETUDIANTS", etudiant -> etudiant.notes().containsKey(m3),a1);
        System.out.println("************ Fin : Exercice 3 : Question 1 : V1 ************ \n");


        System.out.println("************ Début : Exercice 3 : Question 1 : V2 ************");
        afficheSi2("**TOUS LES ETUDIANTS", etudiant -> etudiant.notes().containsKey(m3),a1);
        System.out.println("************ Fin : Exercice 3 : Question 1 : V2 ************ \n");

        System.out.println("************ Début : Exercice 3 : Question 2 : aDEF ************");
        afficheSi2("**Etudiant défaillant", aDEF,a1);
        System.out.println("************ Fin : Exercice 3 : Question 2 : aDEF ************\n");

        System.out.println("************ Début : Exercice 3 : Question 3 : aNoteEliminatoire ************");
        afficheSi2("**ETUDIANTS AVEC NOTE ELIMINATOIRE", aNoteEliminatoire,a1);
        System.out.println("************ Fin : Exercice 3 : Question 3 : aNoteEliminatoire ************\n");

        System.out.println("************ Début : Exercice 3 : Question 4 : moyenne ************");
        afficheMoyenne(aDEF,a1);
        System.out.println("************ Fin : Exercice 3 : Question 4 : moyenne ************\n");

        /*System.out.println("************ Début : Exercice 3 : Question 5 : naPasLaMoyennev1 ************\n");
        afficheSi2("**ETUDIANTS SOUS LA MOYENNE v1",naPasLaMoyennev1,a1);
        System.out.println("************ Fin : Exercice 3 : Question 5 : naPasLaMoyennev1 ************\n");*/

        System.out.println("************ Début : Exercice 3 : Question 6 : naPasLaMoyennev2 ************");
        afficheSi2("**ETUDIANTS SOUS LA MOYENNE v1",naPasLaMoyennev2,a1);
        System.out.println("************ Fin : Exercice 3 : Question 6 : naPasLaMoyennev2 ************\n");

        System.out.println("************ Début : Exercice 3 : Question 7 : session2v1 ************");
        afficheSi2("**ETUDIANTS SOUS LA MOYENNE v1",session2v1,a1);
        System.out.println("************ Fin : Exercice 3 : Question 7 : session2v1 ************\n");

        System.out.println("************ Début : Exercice 3 : Question 8 ************");
        afficheSiV2("**TOUS LES ETUDIANTS", etudiant -> etudiant.notes().containsKey(m3), a1, new Function<Etudiant, String>() {
            @Override
            public String apply(Etudiant etudiant) {
                if (moyenne.apply(etudiant) == null) {
                    return etudiant.prenom() + " " + etudiant.nom() + " : " + "défaillant";
                } else {
                    return etudiant.prenom() + " " + etudiant.nom() + " : " + moyenne.apply(etudiant);
                }
            }
        });
        System.out.println("************ Fin : Exercice 3 : Question 8  ************\n");

        System.out.println("************ Début : Exercice 3 : Question 9 : moyenneIndicative ************");
        afficheSiV2("**TOUS LES ETUDIANTS", etudiant -> etudiant.notes().containsKey(m3), a1, new Function<Etudiant, String>() {
            @Override
            public String apply(Etudiant etudiant) {
                return etudiant.prenom() + " " + etudiant.nom() + " : " + moyenneIndicative.apply(etudiant);
            }
        });
        System.out.println("************ Fin : Exercice 3 : Question 9 : moyenneIndicative ************\n");

        System.out.println("************ Début : Exercice 3 : Question 10 : naPasLaMoyenneGeneralise ************");
        afficheSiV2("**TOUS LES ETUDIANTS SOUS LA MOYENNE INDICATIVE", naPasLaMoyenneGeneralisee(moyenneIndicative), a1, new Function<Etudiant, String>() {
            @Override
            public String apply(Etudiant etudiant) {
                return etudiant.prenom() + " " + etudiant.nom() + " : " + moyenneIndicative.apply(etudiant);
            }
        });
        System.out.println("************ Fin : Exercice 3 : Question 10 : naPasLaMoyenneGeneralise ************\n");


        System.out.println("************ Début : Exercice 3 : Question 10 : naPasLaMoyenneGeneralise en remplaçant les deux 14/20 de Bob par des 20/20 ************");
        e2.noter(m1,20.0);
        e2.noter(m3,20.0);
        afficheSiV2("**TOUS LES ETUDIANTS SOUS LA MOYENNE INDICATIVE", naPasLaMoyenneGeneralisee(moyenneIndicative), a1, new Function<Etudiant, String>() {
            @Override
            public String apply(Etudiant etudiant) {
                return etudiant.prenom() + " " + etudiant.nom() + " : " + moyenneIndicative.apply(etudiant);
            }
        });
        System.out.println("************ Fin : Exercice 3 : Question 10 : naPasLaMoyenneGeneralise en remplaçant les deux 14/20 de Bob par des 20/20 ************\n");

    }

    /************ TD3 : Exercice 2 : matieresA  ************/
    public static final Function<Annee, Stream<Matiere>> matieresA = annee -> annee.ues().stream().flatMap(ue -> ue.ects().keySet().stream());

    /************ TD3 : Exercice 2 : matieresE  ************/
    public static final Function<Etudiant, Stream<Matiere>> matieresE = etudiant -> matieresA.apply(etudiant.annee());

    /************ TD3 : Exercice 2 :  matieresCoefE_  ************/
    public static final Function<Etudiant, Stream<Map.Entry<Matiere, Integer>>> matieresCoefE_ = etudiant -> etudiant.annee().ues().stream().flatMap(ue -> ue.ects().entrySet().stream());

    /************ TD3 : Exercice 2 : entry2paire  ************/
    public static final Function<Map.Entry<Matiere,Integer>, Paire<Matiere, Integer>> entry2paire = entry -> new Paire<>(entry.getKey(), entry.getValue());

    /************ TD3 : Exercice 2 :  matieresCoefE ************/
    public static final Function<Etudiant, Stream<Paire<Matiere, Integer>>> matieresCoefE = etudiant -> matieresCoefE_.apply(etudiant).map(entry2paire);

    /************ TD3 : Exercice 2 :  accumulateurMoyenne ************/
    public static final BinaryOperator<Paire<Double, Integer>> accumulateurMoyenne = (sommeCoeff, notesCoeff) -> new Paire<>(sommeCoeff.fst() + notesCoeff.fst() * notesCoeff.snd(), sommeCoeff.snd() + notesCoeff.snd());

    /************ TD3 : Exercice 2 :  zero ************/
    public static final Paire<Double, Integer> zero = new Paire<>(0.0, 0);

    /************ TD3 : Exercice 2 :  notesPonderees ************/
    public static final Function<Etudiant, List<Paire<Double, Integer>>> notesPoderees = etudiant -> matieresCoefE.apply(etudiant).map(paire -> new Paire<>(etudiant.notes().get(paire.fst()),paire.snd())).collect(Collectors.toList());

    /************ TD3 : Exercice 2 :  notesPondereesIndicatives ************/
    public static final Function<Etudiant, List<Paire<Double, Integer>>> notesPondereesIndicatives = etudiant -> matieresCoefE.apply(etudiant).map(p -> new Paire<>(etudiant.notes().containsKey(p.fst()) ? etudiant.notes().get(p.fst()) : 0.0 , p.snd())).collect(Collectors.toList());

    /************ TD3 : Exercice 2 :  reduit ************/
    public static final Function<List<Paire<Double, Integer>>, Paire<Double, Integer>> reduit = list -> list.stream().reduce(zero,accumulateurMoyenne);

    /************ TD3 : Exercice 2 :  divise ************/
    public static final Function<Paire<Double, Integer>, Double> divise = paire -> paire.fst()/paire.snd();

    /************ TD3 : Exercice 2 :  computeMoyenne ************/
    public static final Function<Etudiant, Double> computeMoyenne = notesPoderees.andThen(reduit).andThen(divise);

    /************ TD3 : Exercice 2 :  computeMoyenneIndicative ************/
    public static final Function<Etudiant, Double> computeMoyenneIndicative = notesPondereesIndicatives.andThen(reduit).andThen(divise);

    /************ TD3 : Exercice 2 :  aDeF ************/
    public static final Predicate<Etudiant> aDEF = e -> matieresE.apply(e).anyMatch(m -> !e.notes().containsKey(m));

    /************ TD3 : Exercice 2 :  moyenne ************/
    public static final Function<Etudiant, Double> moyenne = etudiant -> (etudiant == null || aDEF.test(etudiant)) ? null : computeMoyenne.apply(etudiant);

    /************ TD3 : Exercice 2 :  moyenneIndicative ************/
    public static final Function<Etudiant, Double> moyenneIndicative = computeMoyenneIndicative;



    /************ TD3 : Exercice 2 :  otherFunction ************/
    private static void afficheSi(String enTete, Predicate<Etudiant> e1, Annee anne){
        System.out.println(enTete);
        for(Etudiant e : anne.etudiants()){
            if(e1.test(e)){
                System.out.println(e);
            }
        }
    }

    private static void afficheSi2(String enTete, Predicate<Etudiant> e1, Annee anne){
        System.out.println(enTete);
        anne.etudiants().forEach(e -> {
            if(e1.test(e)){
                System.out.println(e);
            }
        });
    }

    private  static void afficheMoyenne(Predicate<Etudiant> aDEF, Annee annee){
        System.out.println("**MOYENNE DES ETUDIANTS");
        for(Etudiant e : annee.etudiants()){
            if(!aDEF.test(e)){
                System.out.println(e.nom() + " a " + moyenne.apply(e) + " de moyenne");
            }
            else{
                System.out.println(e.nom() + " a une moyenne null");
            }
        }
    }

    private static void afficheSiV2(String enTete, Predicate<Etudiant> e, Annee annee, Function<Etudiant, String> r) {
        System.out.println(enTete);
        annee.etudiants().forEach(e1 -> {
            if (e.test(e1)) {
                System.out.println(r.apply(e1));
            }
        });
    }

    private static Predicate<Etudiant> naPasLaMoyenneGeneralisee(Function<Etudiant, Double> moyenne) {
        return etudiant -> moyenne.apply(etudiant) < 10;
    }

    private static final Predicate<Etudiant> aNoteEliminatoire = e -> {
        Collection<Double> list = e.notes().values();
        for (Double l1 : list)
            if (l1 < 6) {
                return true;
            }
        return false;
    };

    private static final Predicate<Etudiant> naPasLaMoyennev1 = etudiant -> moyenne.apply(etudiant) < 10;
    private static final Predicate<Etudiant> naPasLaMoyennev2 = aDEF.or(naPasLaMoyennev1);
    private static final Predicate<Etudiant> session2v1 = aDEF.or(naPasLaMoyennev1).or(aNoteEliminatoire);




}
