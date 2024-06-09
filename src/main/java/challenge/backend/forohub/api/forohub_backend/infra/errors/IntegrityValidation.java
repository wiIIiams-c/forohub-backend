package challenge.backend.forohub.api.forohub_backend.infra.errors;

public class IntegrityValidation extends RuntimeException{
    public IntegrityValidation(String msg){
        super(msg);
    }
}
