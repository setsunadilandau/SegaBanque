package exobanque.exceptions;

public class SoldeInsuffisantException extends Exception {
    @Override
    public String getMessage() {
        return "Impossible d'effectuer l'action demandée, le solde est insuffisant";
    }
}
