package Xadrez.pecas;

import BoardGame.Tabuleiro;
import Xadrez.Cor;
import Xadrez.PartidaDeXadrez;
import Xadrez.PecaDeXadrez;
import BoardGame.Posicao;

public class Rei extends PecaDeXadrez{

    private PartidaDeXadrez partidaDeXadrez;

    public Rei(Tabuleiro tabuleiro, Cor cor , PartidaDeXadrez partidaDeXadrez) {
        super(tabuleiro, cor);
        this.partidaDeXadrez = partidaDeXadrez;
    }

    @Override
    public String toString(){
        return "R";
    }
    private boolean canMove(Posicao posicao){
         PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
         return p == null || p.getCor() != getCor();

    }

    private boolean testRookCastling(Posicao posicao){
        PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao); 
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getMoveCount() == 0;
    }


    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        //above
        p.setValues(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
         //below
        p.setValues(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
         //left
        p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //right
        p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //NW
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //NE
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //SW
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //SE
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //#Special Move Roque
        if (getMoveCount() == 0 && !partidaDeXadrez.getCheck()) {
            //special move roque pequeno
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testRookCastling(posT1)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
            //special move roque grande
            Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testRookCastling(posT2)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);

                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }
        return mat;
    }
}
