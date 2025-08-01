package Xadrez;

import BoardGame.Tabuleiro;
import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;
import Xadrez.pecas.Bispo;
import Xadrez.pecas.Cavalo;
import Xadrez.pecas.Peao;
import Xadrez.pecas.Rainha;
import BoardGame.Posicao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import BoardGame.Peca;
public class PartidaDeXadrez {
    private int turno;
    private Cor currentPlayer;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;
    private PecaDeXadrez enPassantVulnerable;
    private PecaDeXadrez promoted;

    private List<Peca> piecesOnTheBoard = new ArrayList<>();
    private List<Peca> capturedPieces = new ArrayList<>();

    public PartidaDeXadrez(){
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        currentPlayer = Cor.WHITE;
        SetupInicial();
    }

    public int getTurno(){
        return turno;
    }
    public Cor getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
    }
    
    public PecaDeXadrez getEnPassantVulnerable(){
        return enPassantVulnerable;
    }

    public PecaDeXadrez getPromoted(){
        return promoted;
    }


    public PecaDeXadrez[][] getPecas(){
          PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for(int i = 0 ; i < tabuleiro.getLinhas(); i++){
            for(int j = 0 ; j < tabuleiro.getColunas(); j++){
                mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i , j);
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(PosicaoXadrez sourcePosition){
        Posicao posicao = sourcePosition.toPosicao();
        validateSourcePosicao(posicao);
        return tabuleiro.peca(posicao).possibleMoves();
    }

    public PecaDeXadrez performPecaDeXadrez(PosicaoXadrez sourcePosicao,  PosicaoXadrez targetPosicao){
        Posicao source = sourcePosicao.toPosicao();
        Posicao target = targetPosicao.toPosicao();
        validateSourcePosicao(source);
        validateTargetPosicao(source, target);
        Peca capturaPeca = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturaPeca);
            throw new ExcessaoXadrez("Você não pode se colocar em check");
        }
        PecaDeXadrez movedPiece = (PecaDeXadrez)tabuleiro.peca(target);  
        //#specialMove promoved

        promoted = null;
        if (movedPiece instanceof Peao) {
            if ((movedPiece.getCor() == Cor.WHITE && target.getLinha() == 0) ||(movedPiece.getCor() == Cor.BLACK && target.getLinha() == 7) ) {
                promoted = (PecaDeXadrez)tabuleiro.peca(target);
                promoted = replacePromotedPiece("Q");
            }
        }
        check = (testCheck(opponent(currentPlayer))) ? true : false;

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        }else{
            nextTurn();
        } 

        // #specialMove en passant
        if (movedPiece instanceof Peao && (target.getLinha() == source.getLinha() - 2 ||target.getLinha() == source.getLinha() + 2 )) {
            enPassantVulnerable = movedPiece;
        }else{
            enPassantVulnerable = null;
        }
        return (PecaDeXadrez) capturaPeca;
    }

    public PecaDeXadrez replacePromotedPiece(String type){
        if (promoted == null) {
            throw new IllegalStateException("Não há peça para ser promovida");
        }
        if (!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
           return promoted;
        }
        Posicao pos = promoted.getPosicaoXadrez().toPosicao();
        Peca p = tabuleiro.removePeca(pos);
        piecesOnTheBoard.remove(p);

        PecaDeXadrez newPiece = newPiece(type, promoted.getCor());
        tabuleiro.LugarPeca(newPiece, pos);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    private PecaDeXadrez newPiece(String type , Cor cor){
          if (type.equals("B")) return new Bispo(tabuleiro, cor);  
          if (type.equals("C")) return new Cavalo(tabuleiro, cor); 
          if (type.equals("Q")) return new Rainha(tabuleiro, cor);  
          return new Torre(tabuleiro, cor);  
    }

    private Peca makeMove(Posicao source , Posicao target){
        PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removePeca(source);
        p.increaseMoveCount();
        Peca capturaPeca = tabuleiro.removePeca(target);
        tabuleiro.LugarPeca(p, target);

        if (capturaPeca != null) {
            piecesOnTheBoard.remove(capturaPeca);
            capturedPieces.add(capturaPeca);
        }

        // #specialmove Roque pequeno
        if (p instanceof Rei && target.getColuna() == source.getColuna() + 2) {
            Posicao sourceT = new Posicao(source.getLinha(), source.getColuna() + 3);
            Posicao targetT = new Posicao(source.getLinha(), source.getColuna() + 1);
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(sourceT);
            tabuleiro.LugarPeca(torre, targetT);
            torre.increaseMoveCount();
        }
        // #specialmove Roque grande
        if (p instanceof Rei && target.getColuna() == source.getColuna() -2) {
            Posicao sourceT = new Posicao(source.getLinha(), source.getColuna() - 4);
            Posicao targetT = new Posicao(source.getLinha(), source.getColuna() - 1);
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(sourceT);
            tabuleiro.LugarPeca(torre, targetT);
            torre.increaseMoveCount();
        }

        // #specialMove en passant
        if (p instanceof Peao) {
            if (source.getColuna() != target.getColuna() && capturaPeca == null) {
                Posicao posicaoPeao;
                if (p.getCor() == Cor.WHITE) {
                    posicaoPeao = new Posicao(target.getLinha() + 1, target.getColuna());
                }else{
                    posicaoPeao = new Posicao(target.getLinha() - 1, target.getColuna());
                }
                capturaPeca = tabuleiro.removePeca(posicaoPeao);
                capturedPieces.add(capturaPeca);
                piecesOnTheBoard.remove(capturaPeca);
            }
        }

        return capturaPeca;
    }

    private void undoMove(Posicao source , Posicao target, Peca capturaPeca){
        PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removePeca(target);
        p.decreaseMoveCount();
        tabuleiro.LugarPeca(p, source);

        if (capturaPeca != null) {
            tabuleiro.LugarPeca(capturaPeca, target);
            capturedPieces.remove(capturaPeca);
            piecesOnTheBoard.add(capturaPeca);
        }
         // #specialmove Roque pequeno
        if (p instanceof Rei && target.getColuna() == source.getColuna() + 2) {
            Posicao sourceT = new Posicao(source.getLinha(), source.getColuna() + 3);
            Posicao targetT = new Posicao(source.getLinha(), source.getColuna() + 1);
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(targetT);
            tabuleiro.LugarPeca(torre, sourceT);
            torre.decreaseMoveCount();
        }
        // #specialmove Roque grande
        if (p instanceof Rei && target.getColuna() == source.getColuna() -2) {
            Posicao sourceT = new Posicao(source.getLinha(), source.getColuna() - 4);
            Posicao targetT = new Posicao(source.getLinha(), source.getColuna() - 1);
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(targetT);
            tabuleiro.LugarPeca(torre, sourceT);
            torre.decreaseMoveCount();
        }

        // #specialMove en passant
        if (p instanceof Peao) {
            if (source.getColuna() != target.getColuna() && capturaPeca == enPassantVulnerable) {
                PecaDeXadrez peao =(PecaDeXadrez)tabuleiro.removePeca(target);
                Posicao posicaoPeao;
                if (p.getCor() == Cor.WHITE) {
                    posicaoPeao = new Posicao(3, target.getColuna());
                }else{
                    posicaoPeao = new Posicao(4, target.getColuna());
                }
                tabuleiro.LugarPeca(peao, posicaoPeao);
            }
        }
 
    }
    
    private void validateSourcePosicao(Posicao posicao){
        if (!tabuleiro.thereIsAPiece(posicao)) {
            throw new ExcessaoXadrez("Não há peça na posição de origem");
        }
        if (currentPlayer != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
            throw new ExcessaoXadrez("A peça escolhida não e sua");
        }
        if (!tabuleiro.peca(posicao).isThereAnyPossibleMovies()) {
            throw new ExcessaoXadrez("Não existe movimento possível para a peça");
        }
    }

    private void validateTargetPosicao(Posicao source , Posicao target){
        if (!tabuleiro.peca(source).possibleMoves(target)) {
            throw new ExcessaoXadrez("A peça escolhida não pode se mover para a posição de destino");
        }
    }

    private void nextTurn(){
       turno++;
       currentPlayer = (currentPlayer == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
    }

    private Cor opponent(Cor cor){
        return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
    }
    
    private PecaDeXadrez rei(Cor cor){
        List<Peca> list = piecesOnTheBoard.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());

        for(Peca p : list){
            if (p instanceof Rei) {
                return (PecaDeXadrez)p;
            }
        }
        throw new IllegalStateException("Não exite um rei da cor " + cor + " no tabuleiro");
    }

    private boolean testCheck(Cor cor){
        Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
        List<Peca> opponentPecas = piecesOnTheBoard.stream().filter(x -> ((PecaDeXadrez)x).getCor() == opponent(cor)).collect(Collectors.toList());
        for(Peca p : opponentPecas){
            boolean[][] mat = p.possibleMoves();
            if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }
    private boolean testCheckMate(Cor cor){
          if (!testCheck(cor)) {
            return false;
          }
          List<Peca> list = piecesOnTheBoard.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
          for(Peca p : list){
             boolean[][] mat = p.possibleMoves();
             for(int i = 0 ; i < tabuleiro.getLinhas();i++){
                for(int j = 0 ; j < tabuleiro.getColunas(); j++){
                    if (mat[i][j]) {
                        Posicao source = ((PecaDeXadrez)p).getPosicaoXadrez().toPosicao(); 
                        Posicao target = new Posicao(i, j);
                        Peca capturaPeca = makeMove(source, target);
                        boolean testCheck = testCheck(cor);
                        undoMove(source, target, capturaPeca);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
             }
          }
          return true;
    }
    private void LugarNovaPeca(char coluna , int linha, PecaDeXadrez peca){
        tabuleiro.LugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
        piecesOnTheBoard.add(peca);
    }

    private void SetupInicial(){
        LugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.WHITE));
        LugarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.WHITE));
        LugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.WHITE));
        LugarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.WHITE));
        LugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.WHITE, this));
        LugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.WHITE));
        LugarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.WHITE));
        LugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.WHITE));
        LugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.WHITE , this));
        LugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.WHITE, this));
        LugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.WHITE, this));
        LugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.WHITE, this));
        LugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.WHITE, this));
        LugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.WHITE, this));
        LugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.WHITE, this));
        LugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.WHITE, this));

        LugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.BLACK));
        LugarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.BLACK));
        LugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.BLACK));
        LugarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.BLACK));
        LugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.BLACK, this));
        LugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.BLACK));
        LugarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.BLACK));
        LugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.BLACK));
        LugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.BLACK, this));
        LugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.BLACK, this));
        LugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.BLACK, this));
        LugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.BLACK, this));
        LugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.BLACK, this));
        LugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.BLACK, this));
        LugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.BLACK, this));
        LugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.BLACK, this));
    }
}
