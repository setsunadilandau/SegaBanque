package exobanque.compte.model;

public class CompteEpargne extends Compte {
    private double tauxInteret;

    public CompteEpargne() {
        this(0.5);
    }

    public CompteEpargne(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() {
        return this.tauxInteret;
    }

    public void changerTauxInteret(double nouveauTaux) {
        if (nouveauTaux > 0)
        { this.tauxInteret = nouveauTaux; }
        else
        {
            //TODO Exception
        }
    }

    public void calculInteret() {
        if (this.solde > 0)
        { this.solde += this.solde * (this.tauxInteret/100); }
        else
        {
            //TODO Exception
        }
    }
}
