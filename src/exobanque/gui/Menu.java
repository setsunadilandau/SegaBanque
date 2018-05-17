package exobanque.gui;

import exobanque.compte.model.Compte;
import exobanque.compte.model.CompteEpargne;
import exobanque.compte.model.ComptePayant;
import exobanque.compte.model.CompteSimple;

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

        sb.setLength(0);
        sb.append("0 - Retour à l'accueil");
        sb.append(System.lineSeparator());
        sb.append("Sélectionnez un compte :");
        sb.append(System.lineSeparator());

        if (listeComptes.size() == 0)
        {
            sb.append("Aucune compte n'a été créé");
        }
        else
        {
            for (int i = 0; i < listeComptes.size(); i++)
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
            else if (choix > 0 && choix <= listeComptes.size())
            {
                afficherOptionsCompte(listeComptes.get(choix - 1));
            }
            else
            {
                System.out.println("Valeur incorrecte");
                System.out.println(choix);
                System.out.println(listeComptes.size());
                afficherSelectionCompte();
            }
        } catch (Exception e) {
            System.out.println("Valeur incorrecte");
            System.out.println(e);
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
        sb.append("1 - Versement");
        sb.append(System.lineSeparator());
        sb.append("2 - Retrait");
        sb.append(System.lineSeparator());
        if (compte.getClass().getSimpleName().equals("CompteEpargne"))
        {
            sb.append("3 - Calculer et ajouter les intérêts");
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
                    afficherVersement(compte);
                    break;
                case 2:
                    afficherRetrait(compte);
                    break;
                case 3:
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

    private void afficherRetrait(Compte compte) {

    }

    private void afficherVersement(Compte compte) {

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
