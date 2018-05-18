package exobanque.compte.model;

import exobanque.exceptions.MontantIncorrectException;
import exobanque.exceptions.MontantInferieurA0Exception;
import exobanque.exceptions.SoldeInsuffisantException;

public class CompteSimple extends Compte {
    protected double decouvertAutorise;

    public CompteSimple() {
        this(0);
    }

    public CompteSimple(double decouvertAutorise) {
        if (decouvertAutorise > 0)
        { this.decouvertAutorise = decouvertAutorise; }
    }

    public void setDecouvertAutorise(double nouveauDecouvert) throws MontantInferieurA0Exception {
        if (nouveauDecouvert > 0)
        {this.decouvertAutorise = nouveauDecouvert;}
        else
        {
            throw new MontantInferieurA0Exception();
        }
    }

    public double getDecouvertAutorise() {
        return this.decouvertAutorise;
    }

    @Override public void retrait(double montant) throws MontantInferieurA0Exception, SoldeInsuffisantException {
        if (montant < 0)
        {
           throw new MontantInferieurA0Exception();
        }
        else if (this.solde - montant < -this.decouvertAutorise)
        {
            throw new SoldeInsuffisantException();
        }
        else
        { this.solde -= montant; }
    }
}
