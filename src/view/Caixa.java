package view;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

import controller.*;
import model.*;

public class Caixa {
	private ArrayList<Locacao> locacoes;
	private Scanner sc;
	private LocalDate dt1;
	private LocalDate dt2;
	private LocalDate dt3;
	private Double total;
	private int dia, mes, ano;
	
	public Caixa() {
		sc = new Scanner(System.in);
		total = 0.0;

	}



	public void pagamentoLocacao() {
		System.out.println("===--- PAGAMENTO DA LOCAÇÃO ---===");
		boolean achou = false;
		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		
		if (locacoes.isEmpty()) {
			System.out.println("Não há nenhuma locação");
		} else {
			for (Locacao loc : locacoes) {
				if (loc.getStatus() == 1) {
					achou = true;
				}
			}
			if (achou) {
				int ind, i=0;
				double multa = 0.0;
				Locacao locacao;
				System.out.println("Insira a posição que deseja realizar o pagamento da locação: ");
				ind = sc.nextInt();
				if (ind >= locacoes.size() || ind < 0) {
					System.out.println("Posição Inválida!");
				} else {
					locacao = locacoes.get(ind);
					if (locacao.getStatus() == 1) {
						System.out.println("\nCliente: " + locacao.getCliente().getNome());
						for (Veiculo v : locacao.getVeiculos()) {
							System.out.println("Veiculo " + i + " - " + "placa: " + v.getPlaca());
							i++;
						}
						System.out.printf("Data de início: %d/%d/%d\n", locacao.getDataInicio().getDayOfMonth(), locacao.getDataInicio().getMonthValue(), locacao.getDataInicio().getYear());
						System.out.printf("Data prevista de devolução: %d/%d/%d\n", locacao.getDataPrevistaDevolucao().getDayOfMonth(), locacao.getDataPrevistaDevolucao().getMonthValue(), locacao.getDataPrevistaDevolucao().getYear() );
						System.out.println("Preço da locação: R$" + locacao.getPreco() + "\n\n\n");
						dt1 = locacao.getDataInicio();
						dt2 = locacao.getDataPrevistaDevolucao();
						System.out.println("Informe a data do pagamento: ");
						System.out.println("Dia: ");
						dia = sc.nextInt();
						System.out.println("Mês: ");
						mes = sc.nextInt();
						System.out.println("Ano: ");
						ano = sc.nextInt();
						dt3 = LocalDate.of(ano, mes, dia); 
						if (dt3.isAfter(dt2)) {
							int data = (int) ChronoUnit.DAYS.between(dt2, dt3);
							System.out.println("DIAS: " + data);
							multa = (locacao.getPreco() * (Math.pow((1 + 0.003), data))) - (locacao.getPreco());
						}
						locacao.setMulta(multa);
						System.out.printf("Preço da locação: R$ %.2f\n", locacao.getPreco());
						System.out.printf("Multa: R$ %.2f\n", locacao.getMulta());
						System.out.printf("\nTotal de R$ %.2f pago com sucesso!\n",
								locacao.getMulta() + locacao.getPreco());
						locacao.setStatus(2);
						locacao.setDataDevolucao(dt3);
						add.atualizar(locacao);
					} else {
						System.out.println("Esta locação já foi paga ou está cancelada!");
					}
				}
			} else {
				System.out.println("Não existe locação em aberto para ser paga!");
			}

		}

	}

	public void totalArrecadado() {
		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		System.out.println("===--- TOTAL ARRECADADO ---===");
		double total = 0;
		boolean achou = false;
		if (locacoes.isEmpty()) {
			System.out.println("Não há nenhuma locação");
		} else {
			for (Locacao loc : locacoes) {
				if (loc.getStatus() == 2) {
					total += loc.getPreco() + loc.getMulta();
					achou = true;
				}
			}
			if (achou) {
				System.out.printf("\nTotal arrecadado desde a abertura da empresa: R$ %.2f\n", total);
			} else {
				System.out.println("A Locadora Tabajara não arrecadou nenhum valor até o momento!");
			}
		}

	}

	public void totalArrecadadoPorPeriodo() {
		int dia, mes, ano;
		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		System.out.println("===--- TOTAL ARRECADADO POR PERÍODO ---===");
		double total = 0;
		boolean achou = false;
		if (locacoes.isEmpty()) {
			System.out.println("Não há nenhuma locação");
		} else {
			System.out.println("Insira a data de início ");
			System.out.println("Dia: ");
			dia = sc.nextInt();
			System.out.println("Mês: ");
			mes = sc.nextInt();
			System.out.println("Ano: ");
			ano = sc.nextInt();
			dt1 = LocalDate.of(ano, mes, dia);
			System.out.println("Insira a data final ");
			System.out.println("Dia: ");
			dia = sc.nextInt();
			System.out.println("Mês: ");
			mes = sc.nextInt();
			System.out.println("Ano: ");
			ano = sc.nextInt();
			dt2 = LocalDate.of(ano, mes, dia);
			for (Locacao loc : locacoes) {
				if (loc.getStatus() == 2) {
					if ((loc.getDataDevolucao().equals(dt1) || loc.getDataDevolucao().isAfter(dt1))
							&& (loc.getDataDevolucao().equals(dt2) || loc.getDataDevolucao().isBefore(dt2))) {
						total += loc.getMulta() + loc.getPreco();
						achou = true;

					}
				}
			}
			if (achou) {
				System.out.printf("\nTotal arrecadado neste período: R$ %.2f\n", total);
			} else {
				System.out
						.println("A Locadora Tabajara não arrecadou nenhum valor no período informado até o momento!");
			}
		}
	}

	public void totalAReceber() {
		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		System.out.println("===--- TOTAL A RECEBER ---===");
		double total = 0;
		boolean achou = false;
		if (locacoes.isEmpty()) {
			System.out.println("Não há nenhuma locação");
		} else {
			for (Locacao loc : locacoes) {
				if (loc.getStatus() == 1) {
					total += loc.getPreco();
					achou = true;
				}
			}
			if (achou) {
				System.out.printf("\nTotal a receber: R$ %.2f\n", total);
				System.out.println("AVISO");
				System.out.println("Neste total, não estão incluídas as possíveis multas por atraso!");
			} else {
				System.out.println("A Locadora Tabajara não tem pagamentos para receber no momento!");
			}
		}

	}

}
