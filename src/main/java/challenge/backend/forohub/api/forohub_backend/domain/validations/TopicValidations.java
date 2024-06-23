package challenge.backend.forohub.api.forohub_backend.domain.validations;

/*
 * Interface que validara cualquier regla de negocio que se quiera implementar,
 * esta reciebe cualquier tipo de objeto ya que la clase que la implemente
 * podra castear a cualquier tipo de objeto y validar los datos de ese objeto.
 */
public interface TopicValidations {
    public void validateTopic(Object data);
}
