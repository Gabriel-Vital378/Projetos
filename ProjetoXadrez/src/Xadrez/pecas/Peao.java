package Xadrez.pecas;

import BoardGame.Posicao;
import BoardGame.Tabuleiro;
import Xadrez.Cor;
import Xadrez.PartidaDeXadrez;
import Xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez{

    private PartidaDeXadrez partidaDeXadrez;

    public Peao(Tabuleiro tabuleiro, Cor cor ,PartidaDeXadrez partidaDeXadrez) {
        super(tabuleiro, cor);
        this.partidaDeXadrez = partidaDeXadrez;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);
        if (getCor() == Cor.WHITE) {
            p.setValues(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(p) &&  !getTabuleiro().thereIsAPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(p) &&  !getTabuleiro().thereIsAPiece(p) && getTabuleiro().posicaoExiste(p2) &&  !getTabuleiro().thereIsAPiece(p2) &&getMoveCount() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
             p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExiste(p) &&  isThereOpponentPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExiste(p) &&  isThereOpponentPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            // #specialmove en passant white
            if (posicao.getLinha() == 3) {
                Posicao left = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(left) && isThereOpponentPiece(left) && getTabuleiro().peca(left) == partidaDeXadrez.getEnPassantVulnerable()) {
                    mat[left.getLinha() - 1][left.getColuna()] = true;
                }
                Posicao right = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(right) && isThereOpponentPiece(right) && getTabuleiro().peca(right) == partidaDeXadrez.getEnPassantVulnerable()) {
                    mat[right.getLinha() - 1][right.getColuna()] = true;
                }
            }
        }else{
             p.setValues(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(p) &&  !getTabuleiro().thereIsAPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(p) &&  !getTabuleiro().thereIsAPiece(p) && getTabuleiro().posicaoExiste(p2) &&  !getTabuleiro().thereIsAPiece(p2) &&getMoveCount() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
             p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExiste(p) &&  isThereOpponentPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExiste(p) &&  isThereOpponentPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

             // #specialmove en passant black
            if (posicao.getLinha() == 4) {
                Posicao left = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(left) && isThereOpponentPiece(left) && getTabuleiro().peca(left) == partidaDeXadrez.getEnPassantVulnerable()) {
                    mat[left.getLinha() + 1][left.getColuna()] = true;
                }
                Posicao right = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(right) && isThereOpponentPiece(right) && getTabuleiro().peca(right) == partidaDeXadrez.getEnPassantVulnerable()) {
                    mat[right.getLinha() + 1][right.getColuna()] = true;
                }
            }
        }
       return mat;
    }

    @Override
    public String toString(){
        return "P";
    }

}
