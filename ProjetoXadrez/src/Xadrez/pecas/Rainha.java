package Xadrez.pecas;

import BoardGame.Posicao;
import BoardGame.Tabuleiro;
import Xadrez.Cor;
import Xadrez.PecaDeXadrez;

public class Rainha extends PecaDeXadrez{

    public Rainha(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "Q";
    }
     @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        //above
        p.setValues(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsAPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
          p.setLinha(p.getLinha() - 1);  
        }
        if (getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
        }

        //left
        p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsAPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
          p.setColuna(p.getColuna() - 1);  
        }
        if (getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
        }
        
        //right
         p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsAPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
          p.setColuna(p.getColuna() + 1);  
        }
        if (getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
        }

        //below
        p.setValues(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsAPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
          p.setLinha(p.getLinha() + 1);  
        }
        if (getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;

        }
        //nw
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsAPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
          p.setValues(p.getLinha() - 1, p.getColuna() - 1);  
        }
        if (getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
        }

        //ne
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsAPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
          p.setValues(p.getLinha() - 1, p.getColuna() + 1);  
        }
        if (getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
        }
        
        //se
         p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsAPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
          p.setValues(p.getLinha() + 1, p.getColuna() + 1);  
        }
        if (getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
        }

        //sw
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsAPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;
          p.setValues(p.getLinha() + 1, p.getColuna() - 1); 
        }
        if (getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
          mat[p.getLinha()][p.getColuna()] = true;

        }
        return mat;
    }
}


