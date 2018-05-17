package exobanque.exceptions;

public class MontantInferieurA0Exception extends MontantIncorrectException {
    public MontantInferieurA0Exception() {
        super("Le montant est inférieur à 0");
    }
}
