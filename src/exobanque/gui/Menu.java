package exobanque.gui;

import exobanque.compte.model.Compte;
import exobanque.compte.model.CompteEpargne;
import exobanque.compte.model.ComptePayant;
import exobanque.compte.model.CompteSimple;
import exobanque.exceptions.MontantInferieurA0Exception;
import exobanque.exceptions.SoldeInsuffisantException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private StringBuilder sb = new StringBuilder();
    private Scanner scan = new Scanner(System.in);
    private List<Compte> listeComptes = new ArrayList<>();

    public Menu() {
        afficherAccueil();
    }

    private void afficherAccueil() {

        sb.setLength(0);
        sb.append("Bienvenue chez SegaBanque");
        sb.append(System.lineSeparator());
        sb.append("Choisissez une action : ");
        sb.append(System.lineSeparator());
        sb.append("1 - Créer un compte");
        sb.append(System.lineSeparator());
        sb.append("2 - Sélectionner un compte");
        sb.append(System.lineSeparator());
        System.out.println(sb.toString());

        try {
            switch(scan.nextInt())
            {
                case 1:
                    afficherCreationCompte();
                    break;
                case 2:
                    afficherSelectionCompte();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Valeur incorrecte");
            afficherAccueil();
        }
    }

    private void afficherSelectionCompte() {
        int choix;
        int nbrComptes = listeComptes.size();;

        sb.setLength(0);
        sb.append("0 - Retour à l'accueil");
        sb.append(System.lineSeparator());
        sb.append("Sélectionnez un compte :");
        sb.append(System.lineSeparator());

        if (nbrComptes == 0)
        {
            sb.append("Aucune compte n'a été créé");
        }
        else
        {
            for (int i = 0; i < nbrComptes; i++)
            {
                Compte compte = listeComptes.get(i);
                String typeCompte = compte.getClass().getSimpleName().substring(6);
                sb.append(i + 1);
                sb.append(" - ");
                sb.append("Compte n° ");
                sb.append(compte.codeCompte);
                sb.append(" - type ");
                sb.append(typeCompte);
                sb.append(System.lineSeparator());
            }
        }

        System.out.println(sb.toString());

        try {
            choix = scan.nextInt();
            if (choix == 0)
            {afficherAccueil();}
            else if (choix > 0 && choix <= nbrComptes)
            {
                afficherOptionsCompte(listeComptes.get(choix - 1));
            }
            else
            {
                System.out.println("Valeur incorrecte");
                afficherSelectionCompte();
            }
        } catch (Exception e) {
            System.out.println("Valeur incorrecte");
            afficherSelectionCompte();
        }

    }

    private void afficherOptionsCompte(Compte compte) {
        sb.setLength(0);
        sb.append("0 - Retour à l'accueil");
        sb.append(System.lineSeparator());
        sb.append("Compte n°");
        sb.append(compte.codeCompte);
        sb.append(System.lineSeparator());
        sb.append("1 - Afficher le solde");
        sb.append(System.lineSeparator());
        sb.append("2 - Versement");
        sb.append(System.lineSeparator());
        sb.append("3 - Retrait");
        sb.append(System.lineSeparator());
        if (compte.getClass().getSimpleName().equals("CompteEpargne"))
        {
            sb.append("4 - Calculer et ajouter les intérêts");
            sb.append(System.lineSeparator());
        }

        System.out.println(sb.toString());

        try {
            switch(scan.nextInt())
            {
                case 0:
                    afficherSelectionCompte();
                    break;
                case 1:
                    afficherSolde(compte);
                    break;
                case 2:
                    afficherVersement(compte);
                    break;
                case 3:
                    afficherRetrait(compte);
                    break;
                case 4:
                    if (compte.getClass().getSimpleName().equals("CompteEpargne"))
                    {
                        try {
                            ((CompteEpargne) compte).calculInteret();
                            sb.setLength(0);
                            sb.append("Intérêts calculés");
                            sb.append(System.lineSeparator());
                            sb.append("Nouveau solde : ");
                            sb.append(compte.getSolde());
                            System.out.println(sb.toString());
                            afficherOptionsCompte(compte);
                        } catch (Exception e) {
                            //TODO Catch Exception
                        }
                    }
                    else
                    {
                        System.out.println("Valeur incorrecte");
                        afficherOptionsCompte(compte);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Valeur incorrecte");
            afficherOptionsCompte(compte);
        }
    }

    private void afficherSolde(Compte compte) {
        String nomClasse = compte.getClass().getSimpleName();

        sb.setLength(0);
        sb.append("Solde du compte : ");
        sb.append(compte.getSolde());
        sb.append(System.lineSeparator());


        if (nomClasse.equals("CompteSimple"))
        {
            sb.append("Découvert autorisé : ");
            sb.append(((CompteSimple)compte).getDecouvertAutorise());
        }
        else if (nomClasse.equals("CompteEpargne"))
        {
            sb.append("Taux d'intérêts : ");
            sb.append(((CompteEpargne)compte).getTauxInteret());
            sb.append("%");
        }
        else if (nomClasse.equals("ComptePayant"))
        {
            sb.append("Pourcentage de comission : ");
            sb.append(((ComptePayant)compte).getTauxDePaiement());
            sb.append("%");
        }
        sb.append(System.lineSeparator());

        System.out.println(sb.toString());
        afficherOptionsCompte(compte);
    }

    private void afficherRetrait(Compte compte) {
        double entree;

        sb.setLength(0);
        sb.append("0 - Retour à l'accueil");
        sb.append(System.lineSeparator());
        sb.append("Montant du retrait : ");
        sb.append(System.lineSeparator());
        System.out.println(sb.toString());

        try {
            entree = scan.nextDouble();
            if (entree == 0)
            {afficherOptionsCompte(compte);}
            else {
                effectuerRetrait(compte, entree);
            }
        }
        catch (Exception e) {
            System.out.println("Valeur incorrecte");
            afficherVersement(compte);
        }
    }

    private void effectuerRetrait(Compte compte, double montant) {
        try {
            compte.retrait(montant);
            sb.setLength(0);
            sb.append("Le retrait a été effectué");
            sb.append(System.lineSeparator());
            sb.append("Nouveau solde : ");
            sb.append(compte.getSolde());
            sb.append(System.lineSeparator());
            System.out.println(sb.toString());
            afficherOptionsCompte(compte);
        } catch (MontantInferieurA0Exception e) {
            System.out.println("Le montant ne peut être inférieur à 0");
            afficherRetrait(compte);
        } catch (SoldeInsuffisantException e) {
            System.out.println("Le solde est insuffisant pour effectuer ce retrait");
            afficherRetrait(compte);
        }
    }

    private void afficherVersement(Compte compte) {
        double entree;

        sb.setLength(0);
        sb.append("0 - Retour à l'accueil");
        sb.append(System.lineSeparator());
        sb.append("Montant du versement : ");
        sb.append(System.lineSeparator());
        System.out.println(sb.toString());

        try {
            entree = scan.nextDouble();
            if (entree == 0)
            {afficherOptionsCompte(compte);}
            else
            { effectuerVersement(compte, entree); }
        }
        catch (Exception e) {
            System.out.println("Valeur incorrecte");
            afficherVersement(compte);
        }
    }

    private void effectuerVersement(Compte compte, double montant) {
        try {
            compte.versement(montant);
            sb.setLength(0);
            sb.append("Le versement a été effectué");
            sb.append(System.lineSeparator());
            sb.append("Nouveau solde : ");
            sb.append(compte.getSolde());
            sb.append(System.lineSeparator());
            System.out.println(sb.toString());
            afficherOptionsCompte(compte);
        } catch (MontantInferieurA0Exception e) {
            System.out.println("Le montant ne peut être inférieur à 0");
            afficherVersement(compte);
        }
    }

    private void afficherCreationCompte() {
        sb.setLength(0);
        sb.append("0 - Retour à l'accueil");
        sb.append(System.lineSeparator());
        sb.append("Créer un compte :");
        sb.append(System.lineSeparator());
        sb.append("1 - Simple");
        sb.append(System.lineSeparator());
        sb.append("2 - Épargne");
        sb.append(System.lineSeparator());
        sb.append("3 - Payant");
        sb.append(System.lineSeparator());
        System.out.println(sb.toString());

        try {
            switch(scan.nextInt())
            {
                case 0:
                    afficherAccueil();
                    break;
                case 1:
                    afficherCreationSimple();
                    break;
                case 2:
                    afficherCreationEpargne();
                    break;
                case 3:
                    afficherCreationPayant();
                    break;
            }
        } catch(Exception e) {
            System.out.println("Valeur incorrecte");
            afficherCreationCompte();
        }
    }

    private void afficherCreationSimple() {
        double decouvert = 0;

        sb.setLength(0);
        sb.append("0 - Retour à l'accueil");
        sb.append(System.lineSeparator());
        sb.append("Création d'un compte Simple");
        sb.append(System.lineSeparator());
        sb.append("Veuillez renseigner le découvert autorisé : ");
        System.out.println(sb.toString());

        try {
            decouvert = scan.nextDouble();
            listeComptes.add(new CompteSimple(decouvert));
            System.out.println("Un nouveau compte simple a été créé");
            afficherAccueil();
        } catch(Exception e) {
            System.out.println("Valeur incorrecte");
            afficherCreationSimple();
        }
    }

    private void afficherCreationEpargne() {
        double tauxInteret = 0;

        sb.setLength(0);
        sb.append("0 - Retour à l'accueil");
        sb.append(System.lineSeparator());
        sb.append("Création d'un compte Épargne");
        sb.append(System.lineSeparator());
        sb.append("Veuillez renseigner le taux d'intérêt (chiffre uniquement) : ");
        System.out.println(sb.toString());

        try {
            tauxInteret = scan.nextDouble();
            listeComptes.add(new CompteEpargne(tauxInteret));
            System.out.println("Un nouveau compte épargne a été créé");
            afficherAccueil();
        } catch(Exception e) {
            System.out.println("Valeur incorrecte");
            afficherCreationEpargne();
        }
    }

    private void afficherCreationPayant() {
        int tauxDePaiement = 0;

        sb.setLength(0);
        sb.append("0 - Retour à l'accueil");
        sb.append(System.lineSeparator());
        sb.append("Création d'un compte Payant");
        sb.append(System.lineSeparator());
        sb.append("Veuillez renseigner le taux de comission : ");
        System.out.println(sb.toString());

        try {
            tauxDePaiement = scan.nextInt();
            listeComptes.add(new ComptePayant(tauxDePaiement));
            System.out.println("Un nouveau compte payant a été créé");
            afficherAccueil();
        } catch(Exception e) {
            System.out.println("Valeur incorrecte");
            afficherCreationPayant();
        }
    }

}
