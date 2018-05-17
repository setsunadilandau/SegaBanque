package exobanque.compte.model;

import exobanque.exceptions.MontantInferieurA0Exception;
import exobanque.exceptions.SoldeInsuffisantException;

public abstract class Compte {
    protected static int nombreComptes = 0;

    public int codeCompte;
    protected double solde;

    public Compte() {
        Compte.nombreComptes++;
        this.codeCompte = Compte.nombreComptes;
        this.solde = 0;
    }

    public double getSolde() {
        return this.solde;
    }

    public void versement(double montant) throws MontantInferieurA0Exception {
        if (montant > 0)
        { this.solde += montant; }
        else
        {
            throw new MontantInferieurA0Exception();
        }
    }

    public void retrait(double montant) throws MontantInferieurA0Exception, SoldeInsuffisantException {
        if (montant < 0)
        {
            throw new MontantInferieurA0Exception();
        }
        else if (this.solde < montant)
        {
            throw new SoldeInsuffisantException();
        }
        else
        { this.solde -= montant; }
    }
}
