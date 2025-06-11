package PI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static String[] clientes = new String[100];
    static String[] rendas = new String[100];
    static String[] investimentos = new String[100];
    static int totalClientes = 0;
    static int totalRendas = 0;
    static int totalInvestimentos = 0;

    public static void main(String[] args) {
        inicializarInvestimentos();
        int opcao;

        if (totalClientes == 0) {
            cadastrarCliente();
        }

        do {
            mostrarMenu();
            opcao = sc.nextInt();
            sc.nextLine();
            executarOpcao(opcao);
        } while (opcao != 6);

        sc.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n=====================================");
        System.out.println("         SISTEMA DE INVESTIMENTOS     ");
        System.out.println("=====================================");
        System.out.println("1. Listar Rendas");
        System.out.println("2. Listar Meios de Investimento");
        System.out.println("3. Simular Investimento");
        System.out.println("4. Ver Clientes Cadastrados");
        System.out.println("5. Cadastrar Novo Cliente");
        System.out.println("6. Sair");
        System.out.println("-------------------------------------");
        System.out.print("Escolha uma opção: ");
    }

    public static void executarOpcao(int opcao) {
        System.out.println();
        switch (opcao) {
            case 1:
                listarRendas();
                break;
            case 2:
                listarInvestimentos();
                break;
            case 3:
                simularInvestimento();
                break;
            case 4:
                listarClientes();
                break;
            case 5:
                cadastrarCliente();
                break;
            case 6:
                System.out.println("Saindo do sistema...\n");
                break;
            default:
                System.out.println("Opção inválida.\n");
        }
    }

    public static void cadastrarCliente() {
        if (totalClientes < 100) {
            System.out.println("\n--- Cadastro do Cliente ---\n");
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("Cargo: ");
            String cargo = sc.nextLine();
            System.out.print("Renda Mensal (R$): ");
            String renda = sc.nextLine();

            clientes[totalClientes] = "Cliente: " + nome + ", CPF: " + cpf + ", Cargo: " + cargo + ", Renda: R$" + renda;
            rendas[totalRendas] = "Fonte: Renda mensal, Valor: R$" + renda;

            salvarCliente(clientes[totalClientes]);
            totalClientes++;
            totalRendas++;

            System.out.println("\nCliente cadastrado com sucesso!\n");
        } else {
            System.out.println("\nLimite de clientes atingido.\n");
        }
    }

    public static void listarClientes() {
        System.out.println("\n--- Clientes Cadastrados ---\n");
        for (int i = 0; i < totalClientes; i++) {
            System.out.println(clientes[i]);
        }
        System.out.println();
    }

    public static void listarRendas() {
        System.out.println("\n--- Rendas Cadastradas ---\n");
        for (int i = 0; i < totalRendas; i++) {
            System.out.println(rendas[i]);
        }
        System.out.println();
    }

    public static void listarInvestimentos() {
        System.out.println("\n--- Meios de Investimento ---\n");
        for (int i = 0; i < totalInvestimentos; i++) {
            System.out.println(investimentos[i]);
        }
        System.out.println();
    }

    public static void simularInvestimento() {
        int escolha;
        double taxa = 0;

        System.out.println("\n--- Simulação de Investimento ---\n");
        listarInvestimentos();
        System.out.print("Digite o número do investimento desejado (1 a 7) ou 0 para voltar: ");
        escolha = sc.nextInt();
        sc.nextLine();

        if (escolha == 0) return;

        switch (escolha) {
            case 1: taxa = 0.07; break;
            case 2: taxa = 0.12; break;
            case 3: taxa = 0.09; break;
            case 4: taxa = 0.08; break;
            case 5: taxa = 0.05; break;
            case 6: taxa = 0.20; break;
            case 7: taxa = 0.10; break;
            default:
                System.out.println("\nOpção inválida.\n");
                return;
        }

        System.out.print("\nQuanto deseja investir (R$)? ");
        double valor = sc.nextDouble();
        System.out.print("Por quantos anos deseja deixar investido? ");
        int anos = sc.nextInt();

        double retorno = valor * Math.pow(1 + taxa, anos);

        System.out.printf("\nInvestimento de R$%.2f por %d anos terá um retorno aproximado de R$%.2f\n\n", valor, anos, retorno);
    }

    public static void inicializarInvestimentos() {
        investimentos[totalInvestimentos++] = "1. Tesouro Direto - investimento de renda fixa, ideal para longo prazo, retorno entre 2 a 5 anos.";
        investimentos[totalInvestimentos++] = "2. Ações - participação em empresas, alto risco e retorno, indicado para médio e longo prazo.";
        investimentos[totalInvestimentos++] = "3. Fundos Imobiliários (FIIs) - renda com aluguéis, retorno mensal, indicado para diversificação.";
        investimentos[totalInvestimentos++] = "4. CDB - Certificado de Depósito Bancário, renda fixa com vencimento de 1 a 3 anos.";
        investimentos[totalInvestimentos++] = "5. Poupança - baixo risco, liquidez imediata, menor rentabilidade.";
        investimentos[totalInvestimentos++] = "6. Criptomoedas - alta volatilidade, risco elevado, potencial de alto retorno a longo prazo.";
        investimentos[totalInvestimentos++] = "7. ETF - fundos que seguem índices da bolsa, diversificação com menor custo, indicado para médio/longo prazo.";
    }
    public static void salvarCliente(String dadosCliente){
       try(BufferedWriter bw = new BufferedWriter(new FileWriter(
        "C:\\CadastroCliente\\ clientes.txt", true))) {
        bw.write(dadosCliente);
        bw.newLine();
       } catch (Exception e) {
        System.out.println("Erro ao salvar os dados do cliente: " +e.getMessage());
       }   
    }
    
}
