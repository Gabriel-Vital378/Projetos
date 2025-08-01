package Application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Xadrez.ExcessaoXadrez;
import Xadrez.PartidaDeXadrez;
import Xadrez.PecaDeXadrez;
import Xadrez.PosicaoXadrez;

public class Program {
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
   
    PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
    List<PecaDeXadrez> captured = new ArrayList<>();

    while(!partidaDeXadrez.getCheckMate()){
        try{
        UI.clearScreen();
        UI.printPartida(partidaDeXadrez , captured);
        System.out.println();
        System.out.print("Posição de Origem: ");
        PosicaoXadrez source = UI.readPosicaoXadrez(sc);

        boolean[][] possibleMoves = partidaDeXadrez.possibleMoves(source);
        UI.clearScreen();
        UI.printTabuleiro(partidaDeXadrez.getPecas(), possibleMoves);
        
        System.out.println();
        System.out.print("Destino: ");
        PosicaoXadrez target = UI.readPosicaoXadrez(sc);

        PecaDeXadrez capturaPeca = partidaDeXadrez.performPecaDeXadrez(source, target);
        if (capturaPeca != null) {
            captured.add(capturaPeca);
        }
          if (partidaDeXadrez.getPromoted() != null) {
            System.out.print("Entre com a peça desejada para promoção: (B/C/T/Q)");
            String type = sc.nextLine().toUpperCase();

            while (!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
            System.out.print("Valor inválido! Entre com a peça desejada para promoção: (B/C/T/Q)");
            type = sc.nextLine().toUpperCase();         
            }
            partidaDeXadrez.replacePromotedPiece(type); 
          }

         }catch(ExcessaoXadrez e ){
          System.out.println(e.getMessage());
          sc.nextLine();
       }catch(InputMismatchException e ){
          System.out.println(e.getMessage());
          sc.nextLine();
       }
    }
    UI.clearScreen();
    UI.printPartida(partidaDeXadrez, captured);
}
}
