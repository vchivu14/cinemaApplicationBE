package cinema.shows.exceptions;


public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L; //this is used for versioning of classes

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
