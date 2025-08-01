package Xadrez.pecas;

import BoardGame.Tabuleiro;
import Xadrez.Cor;
import Xadrez.PecaDeXadrez;
import BoardGame.Posicao;

public class Cavalo extends PecaDeXadrez {

     public Cavalo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "C";
    }
    private boolean canMove(Posicao posicao){
         PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
         return p == null || p.getCor() != getCor();

    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        p.setValues(posicao.getLinha() - 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        p.setValues(posicao.getLinha() - 2, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        p.setValues(posicao.getLinha() + 2, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        p.setValues(posicao.getLinha() + 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExiste(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        return mat;
    }
}
