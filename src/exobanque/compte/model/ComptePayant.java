package exobanque.compte.model;

import exobanque.exceptions.MontantInferieurA0Exception;
import exobanque.exceptions.SoldeInsuffisantException;

public class ComptePayant extends Compte {
    private int tauxDePaiement;

    public ComptePayant() {
        this(5);
    }

    public ComptePayant(int tauxDePaiement) {
        this.tauxDePaiement = tauxDePaiement;
    }

    public int getTauxDePaiement() {
        return tauxDePaiement;
    }

    public void changerTauxDePaiement(int nouveauTaux) throws MontantInferieurA0Exception {
        if (nouveauTaux > 0)
        {this.tauxDePaiement = nouveauTaux;}
        else
        {
            throw new MontantInferieurA0Exception();
        }
    }

    private double getComission(double montant) {
        return montant * this.tauxDePaiement/100;
    }

    @Override public void versement(double montant) throws MontantInferieurA0Exception {
        if (montant > 0)
        { this.solde += montant - this.getComission(montant); }
        else
        {
            throw new MontantInferieurA0Exception();
        }
    }

    @Override public void retrait(double montant) throws MontantInferieurA0Exception, SoldeInsuffisantException {
        double montantAvecComission = montant + this.getComission(montant);

        if (montantAvecComission < 0)
        {
            throw new MontantInferieurA0Exception();
        }
        else if (this.solde < montantAvecComission)
        {
            throw new SoldeInsuffisantException();
        }
        else
        {this.solde -= montantAvecComission;}
    }


}
