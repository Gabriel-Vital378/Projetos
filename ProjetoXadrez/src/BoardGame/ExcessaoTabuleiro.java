package BoardGame;

public class ExcessaoTabuleiro extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ExcessaoTabuleiro(String msg){
        super(msg);
    }
}
