package view;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import controller.ClienteDAO;
import model.*;
import controller.*;
public class Agenda {

	private ArrayList<Locacao> locacoes;
	private ArrayList<Veiculo> veiculos;
	private ArrayList<Cliente> clientes;
	private Scanner scs = new Scanner(System.in);
	private Scanner sci = new Scanner(System.in);
	private String doc, placa;
	private boolean achou;
	
	private int dia, mes, ano;
	private LocalDate data;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private Connection con;
	
	public Agenda() {
		
	}

	

	public void agendar() {
		Locacao loca;
		Veiculo veiculo;
		LocalDate dataFim, dataInicio;
		String escolha = "2";
		ArrayList<Veiculo> veics = new ArrayList<>(); 
		Cliente cliente = null;
		ClienteDAO Cadd = new ClienteDAO();
		VeiculoDAO Vadd = new VeiculoDAO();
		
		
		veiculos = Vadd.recuperar();
		clientes = Cadd.listar();
		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		
		
		if (veiculos.isEmpty() && clientes.isEmpty()) {
			System.out.println("\nNÃO POSSUI NENHUM VEÍCULO E NENHUM CLIENTE CADASTRADO!!\n");
		} else if (clientes.isEmpty()) {
			System.out.println("\nNÃO POSSUI NENHUM CLIENTE CADASTRADO!!\n");
		} else if (veiculos.isEmpty()){
			System.out.println("\nNÃO POSSUI NENHUM VEÍCULO CADASTRADO!!\n");
		}else {
			
			System.out.println("-------------| Agendamento |-------------\n");

			achou = false;
			do {
				System.out.println("Digite o CPF do cliente: ");
				doc = scs.nextLine();

				for (Cliente c : clientes) {
					if (c instanceof Fisica) {
						if (((Fisica) c).getCpf().equals(doc)) {
							cliente = c;
							achou = true;
						}
					} else {
						if (c instanceof Juridica) {
							if (((Juridica) c).getCnpj().equals(doc))
							cliente = c;
							achou = true;
							escolha = "2";
						}

					}
				}
				if (achou == false) {
					System.out.println("\n\n                Cliente não localizado!!                ");
					System.out.println(" Digite 0 para sair ou qualquer outro dígito para tentar novamente \n\n");
					escolha = scs.nextLine();
					if(escolha.equals("0")) {
						achou = true;
					}
				}
				
				
			
			} while (achou == false);
			if(escolha.equals("0")) {
				System.out.println("                Agendamento cancelado                ");
			}else {
			achou = false;
			boolean fim = false;
			boolean locado;
			do {
				do {
					System.out.println("Digite a placa do veículo que deseja adicionar a locação: ");
					placa = scs.nextLine();
					locado = false;
					achou = false;
					
				
					 for (Veiculo v : veiculos) {
						if (v.getPlaca().equals(placa)) {
							for (Veiculo ve : veics) { 
								if (ve.getPlaca().equals(placa)) {
									locado = true;
									System.out.println("\n\n        Veículo já está locado!         \n\n");
								}
							}

							Iterator<Locacao> it = locacoes.iterator();
																		
							while (it.hasNext()) {
								loca = it.next();
								ArrayList<Veiculo> ve = loca.getVeiculos();
								Iterator<Veiculo> it2 = ve.iterator();
								while (it2.hasNext()) {
									veiculo = it2.next();
									if (veiculo.getPlaca().equals(placa)) {
										if (loca.getStatus() == 1) {
											locado = true;
											System.out.println("\n\n                   Veículo já está locado!      \n\n");
										}

									}
								}
							}
							if (locado == false) {
								veics.add(v);
								achou = true;
							}
						}
					}
					if(locado == true) {
						System.out.println(" Digite 0 para sair ou qualquer outro dígito para tentar novamente \n\n");
						escolha = scs.nextLine();
						
						if(escolha.equals("0")) {
							achou = true;
							fim = true;
						}
					}
					
					
							
							
					if (achou == false && locado != true) {
						System.out.println("\n\n\n                     Veículo não localizado!           ");
						System.out.println(" Digite 0 para sair ou qualquer outro dígito para tentar novamente \n\n");
						escolha = scs.nextLine();
						
						if(escolha.equals("0")) {
							achou = true;
							fim = true;
						}
						
					}
				} while (achou == false);
				
				
				if (veiculos.size() != veics.size() && !escolha.equals("0")) {
					System.out.println("Deseja adicionar mais um veículo?");
					System.out.println("     1 - SIM    |    2 - NÃO    ");
					String op = scs.nextLine();
					if (op.equals("1")) {
						fim = false;
					} else if (op.equals("2")) {
						fim = true;
					}
				} else {
					if(!escolha.equals("0")) {
					System.out.println("\nTodos os veículos da Locadora Tabajara já estão nessa locação \n "
							+ "	não será possível adicionar mais veículos. \n");
					fim = true;
					}
				}
				
			} while (fim == false);
			
			if(escolha.equals("0") && veics.size() == 0) {
				System.out.println("                Agendamento cancelado                ");
			}else {
			boolean teste;

			teste = false;
			System.out.println("-------- Data da Locação: --------");
			System.out.println("Digite o dia: ");
			dia = sci.nextInt();
			System.out.println("Digite o mês: ");
			mes = sci.nextInt();
			System.out.println("Digite o ano: ");
			ano = sci.nextInt();
			dataInicio = LocalDate.of(ano, mes, dia);
			do {
			System.out.println("-- Data prevista para devolução: --");
			System.out.println("Digite o dia: ");
			dia = sci.nextInt();
			System.out.println("Digite o mês: ");
			mes = sci.nextInt();
			System.out.println("Digite o ano: ");
			ano = sci.nextInt();
			dataFim = LocalDate.of(ano, mes, dia);
			
			if(dataFim.isBefore(dataInicio)) {
				System.out.println(" A DATA FINAL NÃO PODE SER MENOR QUE A DATA DE INÍCIO!!! ");
			}
			
			}while(dataFim.isBefore(dataInicio));
			System.out.println("Digite o valor total da locação: ");
			double valor = sci.nextFloat();
			int codigo = add.proximoCodigo();
			Locacao locacao = new Locacao(codigo, cliente, veics, dataInicio, dataFim, null, valor, 0, 1);
			
			add.incluir(locacao);
			System.out.println("Locação realizada com sucesso!");

			}
			}
			}
		
	}

	public void cancelar() { 
		Locacao loca = null; 
		int pos;
		achou = false;
		
		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		
		
		System.out.println("------------------ Cancelar Agendamento ------------------");
		if (locacoes.isEmpty()) {
			System.out.println("\n\n NÃO HÁ NENHUMA LOCAÇÃO CADASTRADA NO SISTEMA! \n\n");
		} else {
			System.out.println("Digite o código do agendamento que deseja cancelar: ");
			pos = sci.nextInt();

			for(Locacao l : locacoes) {
				if(l.getCodigo() == pos) {
					loca = l;
					achou = true;
				}	
			}
			if(achou == true){
				if (loca.getStatus() == 0) {
					System.out.println(" A LOCAÇÃO JÁ FOI CANCELADA! ");
				} else if (loca.getStatus() == 2) {
					System.out.println(" A LOCAÇÃO JÁ FOI PAGA! NÃO É POSSÍVEL CANCELAR. "); 
				} else {																																				
					System.out.println("Informe a data do cancelamento: ");				
					System.out.println(" Dia: ");										
					dia = sci.nextInt();
					System.out.println(" Mês: ");
					mes = sci.nextInt();
					System.out.println(" Ano: ");
					ano = sci.nextInt();
					LocalDate date = LocalDate.of(ano, mes, dia);
					if (date.isAfter(loca.getDataInicio())) {
						System.out.println("\n\n                          ERRO!!!                          ");
						System.out.println("Não é possível cancelar um agendamento com o veículo locado");
						System.out.println("O cancelamento deve ser feito antes da data de início   \n\n");
						achou = false;
					} else {
						achou = true;
					}
				}
				if (achou == true) {
					int i = 1;
					System.out.println("-------- Locação encontrada --------");
					System.out.println(" Cliente: " + loca.getCliente().getNome());
					for (Veiculo v : loca.getVeiculos()) {
						System.out.println("Veiculo " + i + " - " + "placa: " + v.getPlaca());
						i++;
					}
					String date = loca.getDataInicio().format(formatter);
					System.out.println("Data de início: " + date);
					date = loca.getDataPrevistaDevolucao().format(formatter);
					System.out.println("Data prevista de devolução: " + date);
					System.out.println("Preço da locação: R$" + loca.getPreco());
					System.out.println("\n\n Deseja realmente cancelar essa locação?\n");
					System.out.println("          1 - SIM       |       2 - NÃO       ");
					int op = sci.nextInt();
					if (op == 1) {
						loca.setStatus(0);
						add.atualizar(loca);
						System.out.println(" Agendamento cancelado! ");
					} else if (op == 2) {
						System.out.println(" Agendamento não cancelado! ");
					} else {
						System.out.println(" \n\n\n      OPÇÃO INVÁLIDA \n Retornando ao menu anterior! \n\n\n");
					}

				}
			}else {
				System.out.println("       Locação não localizada        ");
			}
		}
		
	}

	public void alterar() {
		LocalDate dataInicio, dataFim;
		Locacao loca = null;
		int pos = 1;
		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		System.out.println("------------------ Alterar Agendamento ------------------");
		if (locacoes.isEmpty()) {
			System.out.println("\n\n NÃO HÁ NENHUMA LOCAÇÃO CADASTRADA NO SISTEMA! \n\n");
		} else {
			System.out.println("Digite o código do agendamento que deseja alterar: ");
			pos = sci.nextInt();
			achou = false;
			for(Locacao l : locacoes) {
				if(l.getCodigo() == pos) {
					achou = true;
					loca = l;
				}
			}
			
			if (achou == false) {
				System.out.println("\n\n               ERRO!!!             ");
				System.out.println("          Código Inválido!!        \n\n");
			} else {
				if (loca.getStatus() == 0) {
					System.out.println(" A LOCAÇÃO JÁ FOI CANCELADA! ");
				} else if (loca.getStatus() == 2) {
					System.out.println(" A LOCAÇÃO JÁ FOI PAGA! NÃO É POSSÍVEL ALTERAR. ");
				} else {
					achou = true;
				}
				int i = 1;
				if (achou == true) {
					
					System.out.println("--------------- Locação encontrada ---------------");
					System.out.println("Nome do cliente: " + loca.getCliente().getNome());
					for (Veiculo v : loca.getVeiculos()) {
						System.out.println("Veiculo " + i + " - " + "placa: " + v.getPlaca());
						i++;
					}
					String date = loca.getDataInicio().format(formatter);
					System.out.println("Data de início: " + date);
					date = loca.getDataPrevistaDevolucao().format(formatter);
					System.out.println("Data prevista de devolução: " + date);
					System.out.println("Preço da locação: R$" + loca.getPreco() + "\n");
					System.out.println(" Deseja alterar esta locação??");
					System.out.println("1 para sim ou 2 para não");
					int op = sci.nextInt();
					do {
						if(op == 1) {
							System.out.println("----- Informe a nova data de início -----");
							System.out.println("Digite o dia: ");
							dia = sci.nextInt();
							System.out.println("Digite o mês: ");
							mes = sci.nextInt();
							System.out.println("Digite o ano: ");
							ano = sci.nextInt();
							dataInicio = LocalDate.of(ano, mes, dia);
							do {
							System.out.println("---- Informe a nova data de devolução ----");
							System.out.println("Digite o dia: ");
							dia = sci.nextInt();
							System.out.println("Digite o mês: ");
							mes = sci.nextInt();
							System.out.println("Digite o ano: ");
							ano = sci.nextInt();
							dataFim = LocalDate.of(ano, mes, dia);
							
							if(dataFim.isBefore(dataInicio)) {
								System.out.println("\n\n A DATA FINAL NÃO PODE SER MENOR QUE A DATA DE INÍCIO!!! \n\n");
							}
							
							}while(dataFim.isBefore(dataInicio));
							
							
							loca.setDataInicio(dataInicio);
							loca.setDataPrevistaDevolucao(dataFim);
							
							add.atualizar(loca);
							
							System.out.println("\n\n ------------ Alterada com sucesso ------------ \n\n");
						}else if(op == 2) {
							System.out.println(" \n\n ------------ Alteração cancelada ------------ \n\n");
						}else {
							System.out.println("\n\n ------------ Opção inválida!!! ------------ \n\n");
						}
						
						
						
					}while(op < 1 || op > 2);

				}
			}
		}
		

	}


	public void relatorioAgendado() {
		
		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		
		Iterator<Locacao> it = locacoes.iterator();
		Locacao locs;
		String data;
		int i = 0;
		int aux = 0;
		if (locacoes.isEmpty()) {
			System.out.println("    Ainda não foi registrada nenhuma locação no sistema!   \n\n");
		} else {
			while (it.hasNext()) {
				locs = it.next();
				i = 1;
				
				if (locs.getStatus() == 1) {
					System.out.println("----------- Locação " + locs.getCodigo() + " -----------");
					System.out.println("Cliente: " + locs.getCliente().getNome());
					System.out.println("------- Veiculos locados: -------");
					for (Veiculo v : locs.getVeiculos()) {
						System.out.println("Veiculo " + i + " - Placa: " + v.getPlaca());
						i++;
					}
					
					data = locs.getDataInicio().format(formatter);
					System.out.println("Data de início: " + data);
					data = locs.getDataPrevistaDevolucao().format(formatter);
					System.out.println("Data prevista para devolução: " + data);
					System.out.println("Valor da locação: " + locs.getPreco() + "\n\n");
					
					aux++;
				}
			}
			
		}
		if(aux == 0) {
			System.out.println("\n            Não há veículos agendados!");
		}
	}

	public void relatorioLocados() { 
		LocalDate dataPrevistaDevolucao;
		Iterator<Locacao> it = locacoes.iterator();
		Locacao locs;
		String data;
		LocalDate date;
		int ano, mes, dia, i = 0;
		boolean naoAchou = true;

		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		
		if (locacoes.isEmpty()) { 
			System.out.println("    Ainda não foi registrada nenhuma locação no sistema!   \n\n");
		} else {

			System.out.println(" Informe a data para usar como referência: ");
			System.out.println(" Dia: ");
			dia = sci.nextInt();
			System.out.println(" Mês: ");
			mes = sci.nextInt();
			System.out.println(" Ano: ");
			ano = sci.nextInt();
			date = LocalDate.of(ano, mes, dia);

			while (it.hasNext()) {
				locs = it.next();
				dataPrevistaDevolucao = locs.getDataPrevistaDevolucao();
				if (locs.getStatus() == 1) {
					if ((date.equals(locs.getDataPrevistaDevolucao())
							|| date.isBefore(locs.getDataPrevistaDevolucao()))  && (date.equals(locs.getDataInicio()) || date.isAfter(locs.getDataInicio()))) {
						System.out.println("----------- Locação " + locs.getCodigo() + " -----------");
						System.out.println("Cliente: " + locs.getCliente().getNome());
						System.out.println("Veiculos locados: ");
						int j = 1;
						for (Veiculo v : locs.getVeiculos()) {
							System.out.println("Placa do veículo " + j + " : " + v.getPlaca());
							j++;
						}
						data = locs.getDataInicio().format(formatter);
						System.out.println("Data de início: " + data);
						data = locs.getDataPrevistaDevolucao().format(formatter);
						System.out.println("Data prevista para devolução: " + data);
						System.out.println("Valor da locação: " + locs.getPreco() + "\n\n");
						naoAchou = false;

					}

				}
				i++;
			}
			if(naoAchou) {
				System.out.println("Não há nenhuma locação no status --- EM DIA --- ");
			}
		}
	}

	public void relatorioAtrasado() {
		Iterator<Locacao> it = locacoes.iterator();
		Locacao locs;
		String data;
		LocalDate date;
		boolean naoAchou = true;
		int ano, mes, dia, i = 0;

		LocacaoDAO add = new LocacaoDAO();
		locacoes = add.listar();
		
		if (locacoes.isEmpty()) {
			System.out.println("    Ainda não foi registrada nenhuma locação no sistema!   \n\n");
		} else {

			System.out.println(" Informe a data para usar como referência: ");
			System.out.println(" Dia: ");
			dia = sci.nextInt();
			System.out.println(" Mês: ");
			mes = sci.nextInt();
			System.out.println(" Ano: ");
			ano = sci.nextInt();
			date = LocalDate.of(ano, mes, dia);

			while (it.hasNext()) {
				locs = it.next();
				if (locs.getStatus() == 1) {
					if (date.isAfter(locs.getDataPrevistaDevolucao())) {
						System.out.println("\n----------- Locação " + locs.getCodigo() + " -----------");
						System.out.println("Cliente: " + locs.getCliente().getNome());
						System.out.println(" ---- Veiculos locados: ---- ");
						int j = 1;
						for (Veiculo v : locs.getVeiculos()) {
							System.out.println("Veículo " + j + ": " + v.getPlaca());
							j++;
						}
						data = locs.getDataInicio().format(formatter);
						System.out.println("\nData de início: " + data);
						data = locs.getDataPrevistaDevolucao().format(formatter);
						System.out.println("Data prevista para devolução: " + data);
						int dias = (int) ChronoUnit.DAYS.between(locs.getDataPrevistaDevolucao(), date);
						System.out.println("Dias de atraso: " + dias);
						double multa = (locs.getPreco() * (Math.pow((1 + 0.003), dias))) - (locs.getPreco());
						System.out.println("Valor da locação: R$ " + locs.getPreco());
						System.out.printf("Multa atual: R$ %.2f \n", multa);
						double t = multa + locs.getPreco();
						System.out.printf("Valor total da locação: R$%.2f \n", t);
						naoAchou = false;
					}
				}
				i++;
			}
			if(naoAchou) {
				System.out.println("Não há nenhuma locação no status --- EM ATRASO ---");
			}
		}
	}

}