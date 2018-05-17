package exobanque.exceptions;

public class MontantIncorrectException extends Exception {

    private String detailErreur;

    public MontantIncorrectException() {
    }

    public MontantIncorrectException(String detailErreur) {
        this.detailErreur = detailErreur;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Le montant renseigné est incorrect");
        if (this.detailErreur != null)
        {
            sb.append(System.lineSeparator());
            sb.append(detailErreur);
        }
        return sb.toString();
    }
}
