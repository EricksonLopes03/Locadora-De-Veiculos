package view;

import java.util.*;

import model.*;

public class Principal {
	public static void main(String[] args) {

		ArrayList<Veiculo> veiculos = new ArrayList<>();
		ArrayList<Cliente> clientes = new ArrayList<>();
		ArrayList<Locacao> locacoes = new ArrayList<>();
		GerenciaVeiculo gv = new GerenciaVeiculo(veiculos, locacoes);
		GerenciaCliente gc = new GerenciaCliente(clientes, locacoes);
		Agenda ag = new Agenda();
		Caixa cx = new Caixa();
		int op, op2, op3 = 0;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("\n\n ----[ Menu Principal ]---- ");
			System.out.println(" |  1 - Clientes          |");
			System.out.println(" |  2 - Veículos          | ");
			System.out.println(" |  3 - Agenda            |");
			System.out.println(" |  4 - Caixa             |");
			System.out.println(" |  0 - Encerrar programa |");
			System.out.println(" --------------------------");
			op = sc.nextInt();
			switch (op) {

			case 1:
				do {
					System.out.println("\n\n ------- [ Menu Clientes ] ------ ");
					System.out.println(" |  1 - Cadastrar               |");
					System.out.println(" |  2 - Alterar                 |");
					System.out.println(" |  3 - Excluir                 |");
					System.out.println(" |  4 - Consulta                |");
					System.out.println(" |  5 - Relatório               |");
					System.out.println(" |  0 - Voltar ao menu anterior |");
					System.out.println(" --------------------------------");
					op2 = sc.nextInt();
					switch (op2) {
					case 0:
						break;
					case 1:
						gc.cadastro();
						break;
					case 2:
						gc.alterar();
						break;
					case 3:
						gc.excluir();
						break;
					case 4:
						gc.consulta();
						break;
					case 5:
						gc.relatorio();
						break;
					default:
						System.out.println("------------Opção Inválida------------");

					}

				} while (op2 != 0);
				break;
			case 2:
				do {
					System.out.println("\n\n ----- [ Menu Veiculos ] ----- ");
					System.out.println(" |  1 - Cadastrar               |");
					System.out.println(" |  2 - Alterar                 |");
					System.out.println(" |  3 - Excluir                 |");
					System.out.println(" |  4 - Consulta/Relatório      |");
					System.out.println(" |  0 - Voltar ao menu anterior |");
					System.out.println(" --------------------------------");
					op2 = sc.nextInt();
					switch (op2) {
					case 1:
						gv.cadastrarVeiculo();
						break;
					case 2:
						gv.alterar();
						break;
					case 3:
						gv.excluir();
						break;
					case 4:
						gv.relatorio();
						break;
					case 0:
						break;
					default:
						System.out.println("------------Opção Inválida------------");
						break;
					}

				} while (op2 != 0);
				break;
			case 3:
				do {
					System.out.println("\n\n ------------[ Menu Locação ] ----------- ");
					System.out.println(" | 1 - Agendar                          |");
					System.out.println(" | 2 - Cancelar                         |");
					System.out.println(" | 3 - Alterar                          |");
					System.out.println(" | 4 - Relatório dos veículos agendados |");
					System.out.println(" | 5 - Relatório dos veículos em dia    |");
					System.out.println(" | 6 - Relatório dos veículos em atraso |");
					System.out.println(" | 0 - Voltar ao menu anterior          |");
					System.out.println(" ---------------------------------------");
					op2 = sc.nextInt();
					switch (op2) {
					case 1:
						ag.agendar();
						break;
					case 2:
						ag.cancelar();
						break;
					case 3:
						ag.alterar();
						break;
					case 4:
						ag.relatorioAgendado();
						break;
					case 5:
						ag.relatorioLocados();
						break;
					case 6:
						ag.relatorioAtrasado();
						break;
					case 0:
						break;
					default:
						System.out.println("------------Opção Inválida------------");
						break;
						
					}

				} while (op2 != 0);
				break;
			case 4:
				do {
					System.out.println("\n\n ---------- [ Menu Caixa ] ---------- ");
					System.out.println(" | 1 - Pagamento de locação         |");
					System.out.println(" | 2 - Total arrecadado             |");
					System.out.println(" | 3 - Total arrecadado por período |");
					System.out.println(" | 4 - Total a receber              |");
					System.out.println(" | 0 - Voltar ao menu anterior      |");
					System.out.println(" ------------------------------------");
					op2 = sc.nextInt();
					switch (op2) {
					case 1:
						cx.pagamentoLocacao();
						break;
					case 2:
						cx.totalArrecadado();
						break;
					case 3:
						cx.totalArrecadadoPorPeriodo();
						break;
					case 4:
						cx.totalAReceber();
						break;
					case 0:
						break;						
					default:
						System.out.println("------------Opção Inválida------------");
						break;
						
					}

				} while (op2 != 0);
				break;
				
			case 0:
				System.out.println("\n\n--------------- PROGRAMA ENCERRADO ---------------\n\n");
				break;
			default:
				System.out.println("\n\n--------------- OPÇÃO INVÁLIDA ---------------\n\n");
				break;
			}
		} while (op != 0);
	}
}