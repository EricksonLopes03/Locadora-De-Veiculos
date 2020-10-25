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
			System.out.println("\nN�O POSSUI NENHUM VE�CULO E NENHUM CLIENTE CADASTRADO!!\n");
		} else if (clientes.isEmpty()) {
			System.out.println("\nN�O POSSUI NENHUM CLIENTE CADASTRADO!!\n");
		} else if (veiculos.isEmpty()){
			System.out.println("\nN�O POSSUI NENHUM VE�CULO CADASTRADO!!\n");
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
					System.out.println("\n\n                Cliente n�o localizado!!                ");
					System.out.println(" Digite 0 para sair ou qualquer outro d�gito para tentar novamente \n\n");
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
					System.out.println("Digite a placa do ve�culo que deseja adicionar a loca��o: ");
					placa = scs.nextLine();
					locado = false;
					achou = false;
					
				
					 for (Veiculo v : veiculos) {
						if (v.getPlaca().equals(placa)) {
							for (Veiculo ve : veics) { 
								if (ve.getPlaca().equals(placa)) {
									locado = true;
									System.out.println("\n\n        Ve�culo j� est� locado!         \n\n");
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
											System.out.println("\n\n                   Ve�culo j� est� locado!      \n\n");
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
						System.out.println(" Digite 0 para sair ou qualquer outro d�gito para tentar novamente \n\n");
						escolha = scs.nextLine();
						
						if(escolha.equals("0")) {
							achou = true;
							fim = true;
						}
					}
					
					
							
							
					if (achou == false && locado != true) {
						System.out.println("\n\n\n                     Ve�culo n�o localizado!           ");
						System.out.println(" Digite 0 para sair ou qualquer outro d�gito para tentar novamente \n\n");
						escolha = scs.nextLine();
						
						if(escolha.equals("0")) {
							achou = true;
							fim = true;
						}
						
					}
				} while (achou == false);
				
				
				if (veiculos.size() != veics.size() && !escolha.equals("0")) {
					System.out.println("Deseja adicionar mais um ve�culo?");
					System.out.println("     1 - SIM    |    2 - N�O    ");
					String op = scs.nextLine();
					if (op.equals("1")) {
						fim = false;
					} else if (op.equals("2")) {
						fim = true;
					}
				} else {
					if(!escolha.equals("0")) {
					System.out.println("\nTodos os ve�culos da Locadora Tabajara j� est�o nessa loca��o \n "
							+ "	n�o ser� poss�vel adicionar mais ve�culos. \n");
					fim = true;
					}
				}
				
			} while (fim == false);
			
			if(escolha.equals("0") && veics.size() == 0) {
				System.out.println("                Agendamento cancelado                ");
			}else {
			boolean teste;

			teste = false;
			System.out.println("-------- Data da Loca��o: --------");
			System.out.println("Digite o dia: ");
			dia = sci.nextInt();
			System.out.println("Digite o m�s: ");
			mes = sci.nextInt();
			System.out.println("Digite o ano: ");
			ano = sci.nextInt();
			dataInicio = LocalDate.of(ano, mes, dia);
			do {
			System.out.println("-- Data prevista para devolu��o: --");
			System.out.println("Digite o dia: ");
			dia = sci.nextInt();
			System.out.println("Digite o m�s: ");
			mes = sci.nextInt();
			System.out.println("Digite o ano: ");
			ano = sci.nextInt();
			dataFim = LocalDate.of(ano, mes, dia);
			
			if(dataFim.isBefore(dataInicio)) {
				System.out.println(" A DATA FINAL N�O PODE SER MENOR QUE A DATA DE IN�CIO!!! ");
			}
			
			}while(dataFim.isBefore(dataInicio));
			System.out.println("Digite o valor total da loca��o: ");
			double valor = sci.nextFloat();
			int codigo = add.proximoCodigo();
			Locacao locacao = new Locacao(codigo, cliente, veics, dataInicio, dataFim, null, valor, 0, 1);
			
			add.incluir(locacao);
			System.out.println("Loca��o realizada com sucesso!");

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
			System.out.println("\n\n N�O H� NENHUMA LOCA��O CADASTRADA NO SISTEMA! \n\n");
		} else {
			System.out.println("Digite o c�digo do agendamento que deseja cancelar: ");
			pos = sci.nextInt();

			for(Locacao l : locacoes) {
				if(l.getCodigo() == pos) {
					loca = l;
					achou = true;
				}	
			}
			if(achou == true){
				if (loca.getStatus() == 0) {
					System.out.println(" A LOCA��O J� FOI CANCELADA! ");
				} else if (loca.getStatus() == 2) {
					System.out.println(" A LOCA��O J� FOI PAGA! N�O � POSS�VEL CANCELAR. "); 
				} else {																																				
					System.out.println("Informe a data do cancelamento: ");				
					System.out.println(" Dia: ");										
					dia = sci.nextInt();
					System.out.println(" M�s: ");
					mes = sci.nextInt();
					System.out.println(" Ano: ");
					ano = sci.nextInt();
					LocalDate date = LocalDate.of(ano, mes, dia);
					if (date.isAfter(loca.getDataInicio())) {
						System.out.println("\n\n                          ERRO!!!                          ");
						System.out.println("N�o � poss�vel cancelar um agendamento com o ve�culo locado");
						System.out.println("O cancelamento deve ser feito antes da data de in�cio   \n\n");
						achou = false;
					} else {
						achou = true;
					}
				}
				if (achou == true) {
					int i = 1;
					System.out.println("-------- Loca��o encontrada --------");
					System.out.println(" Cliente: " + loca.getCliente().getNome());
					for (Veiculo v : loca.getVeiculos()) {
						System.out.println("Veiculo " + i + " - " + "placa: " + v.getPlaca());
						i++;
					}
					String date = loca.getDataInicio().format(formatter);
					System.out.println("Data de in�cio: " + date);
					date = loca.getDataPrevistaDevolucao().format(formatter);
					System.out.println("Data prevista de devolu��o: " + date);
					System.out.println("Pre�o da loca��o: R$" + loca.getPreco());
					System.out.println("\n\n Deseja realmente cancelar essa loca��o?\n");
					System.out.println("          1 - SIM       |       2 - N�O       ");
					int op = sci.nextInt();
					if (op == 1) {
						loca.setStatus(0);
						add.atualizar(loca);
						System.out.println(" Agendamento cancelado! ");
					} else if (op == 2) {
						System.out.println(" Agendamento n�o cancelado! ");
					} else {
						System.out.println(" \n\n\n      OP��O INV�LIDA \n Retornando ao menu anterior! \n\n\n");
					}

				}
			}else {
				System.out.println("       Loca��o n�o localizada        ");
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
			System.out.println("\n\n N�O H� NENHUMA LOCA��O CADASTRADA NO SISTEMA! \n\n");
		} else {
			System.out.println("Digite o c�digo do agendamento que deseja alterar: ");
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
				System.out.println("          C�digo Inv�lido!!        \n\n");
			} else {
				if (loca.getStatus() == 0) {
					System.out.println(" A LOCA��O J� FOI CANCELADA! ");
				} else if (loca.getStatus() == 2) {
					System.out.println(" A LOCA��O J� FOI PAGA! N�O � POSS�VEL ALTERAR. ");
				} else {
					achou = true;
				}
				int i = 1;
				if (achou == true) {
					
					System.out.println("--------------- Loca��o encontrada ---------------");
					System.out.println("Nome do cliente: " + loca.getCliente().getNome());
					for (Veiculo v : loca.getVeiculos()) {
						System.out.println("Veiculo " + i + " - " + "placa: " + v.getPlaca());
						i++;
					}
					String date = loca.getDataInicio().format(formatter);
					System.out.println("Data de in�cio: " + date);
					date = loca.getDataPrevistaDevolucao().format(formatter);
					System.out.println("Data prevista de devolu��o: " + date);
					System.out.println("Pre�o da loca��o: R$" + loca.getPreco() + "\n");
					System.out.println(" Deseja alterar esta loca��o??");
					System.out.println("1 para sim ou 2 para n�o");
					int op = sci.nextInt();
					do {
						if(op == 1) {
							System.out.println("----- Informe a nova data de in�cio -----");
							System.out.println("Digite o dia: ");
							dia = sci.nextInt();
							System.out.println("Digite o m�s: ");
							mes = sci.nextInt();
							System.out.println("Digite o ano: ");
							ano = sci.nextInt();
							dataInicio = LocalDate.of(ano, mes, dia);
							do {
							System.out.println("---- Informe a nova data de devolu��o ----");
							System.out.println("Digite o dia: ");
							dia = sci.nextInt();
							System.out.println("Digite o m�s: ");
							mes = sci.nextInt();
							System.out.println("Digite o ano: ");
							ano = sci.nextInt();
							dataFim = LocalDate.of(ano, mes, dia);
							
							if(dataFim.isBefore(dataInicio)) {
								System.out.println("\n\n A DATA FINAL N�O PODE SER MENOR QUE A DATA DE IN�CIO!!! \n\n");
							}
							
							}while(dataFim.isBefore(dataInicio));
							
							
							loca.setDataInicio(dataInicio);
							loca.setDataPrevistaDevolucao(dataFim);
							
							add.atualizar(loca);
							
							System.out.println("\n\n ------------ Alterada com sucesso ------------ \n\n");
						}else if(op == 2) {
							System.out.println(" \n\n ------------ Altera��o cancelada ------------ \n\n");
						}else {
							System.out.println("\n\n ------------ Op��o inv�lida!!! ------------ \n\n");
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
			System.out.println("    Ainda n�o foi registrada nenhuma loca��o no sistema!   \n\n");
		} else {
			while (it.hasNext()) {
				locs = it.next();
				i = 1;
				
				if (locs.getStatus() == 1) {
					System.out.println("----------- Loca��o " + locs.getCodigo() + " -----------");
					System.out.println("Cliente: " + locs.getCliente().getNome());
					System.out.println("------- Veiculos locados: -------");
					for (Veiculo v : locs.getVeiculos()) {
						System.out.println("Veiculo " + i + " - Placa: " + v.getPlaca());
						i++;
					}
					
					data = locs.getDataInicio().format(formatter);
					System.out.println("Data de in�cio: " + data);
					data = locs.getDataPrevistaDevolucao().format(formatter);
					System.out.println("Data prevista para devolu��o: " + data);
					System.out.println("Valor da loca��o: " + locs.getPreco() + "\n\n");
					
					aux++;
				}
			}
			
		}
		if(aux == 0) {
			System.out.println("\n            N�o h� ve�culos agendados!");
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
			System.out.println("    Ainda n�o foi registrada nenhuma loca��o no sistema!   \n\n");
		} else {

			System.out.println(" Informe a data para usar como refer�ncia: ");
			System.out.println(" Dia: ");
			dia = sci.nextInt();
			System.out.println(" M�s: ");
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
						System.out.println("----------- Loca��o " + locs.getCodigo() + " -----------");
						System.out.println("Cliente: " + locs.getCliente().getNome());
						System.out.println("Veiculos locados: ");
						int j = 1;
						for (Veiculo v : locs.getVeiculos()) {
							System.out.println("Placa do ve�culo " + j + " : " + v.getPlaca());
							j++;
						}
						data = locs.getDataInicio().format(formatter);
						System.out.println("Data de in�cio: " + data);
						data = locs.getDataPrevistaDevolucao().format(formatter);
						System.out.println("Data prevista para devolu��o: " + data);
						System.out.println("Valor da loca��o: " + locs.getPreco() + "\n\n");
						naoAchou = false;

					}

				}
				i++;
			}
			if(naoAchou) {
				System.out.println("N�o h� nenhuma loca��o no status --- EM DIA --- ");
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
			System.out.println("    Ainda n�o foi registrada nenhuma loca��o no sistema!   \n\n");
		} else {

			System.out.println(" Informe a data para usar como refer�ncia: ");
			System.out.println(" Dia: ");
			dia = sci.nextInt();
			System.out.println(" M�s: ");
			mes = sci.nextInt();
			System.out.println(" Ano: ");
			ano = sci.nextInt();
			date = LocalDate.of(ano, mes, dia);

			while (it.hasNext()) {
				locs = it.next();
				if (locs.getStatus() == 1) {
					if (date.isAfter(locs.getDataPrevistaDevolucao())) {
						System.out.println("\n----------- Loca��o " + locs.getCodigo() + " -----------");
						System.out.println("Cliente: " + locs.getCliente().getNome());
						System.out.println(" ---- Veiculos locados: ---- ");
						int j = 1;
						for (Veiculo v : locs.getVeiculos()) {
							System.out.println("Ve�culo " + j + ": " + v.getPlaca());
							j++;
						}
						data = locs.getDataInicio().format(formatter);
						System.out.println("\nData de in�cio: " + data);
						data = locs.getDataPrevistaDevolucao().format(formatter);
						System.out.println("Data prevista para devolu��o: " + data);
						int dias = (int) ChronoUnit.DAYS.between(locs.getDataPrevistaDevolucao(), date);
						System.out.println("Dias de atraso: " + dias);
						double multa = (locs.getPreco() * (Math.pow((1 + 0.003), dias))) - (locs.getPreco());
						System.out.println("Valor da loca��o: R$ " + locs.getPreco());
						System.out.printf("Multa atual: R$ %.2f \n", multa);
						double t = multa + locs.getPreco();
						System.out.printf("Valor total da loca��o: R$%.2f \n", t);
						naoAchou = false;
					}
				}
				i++;
			}
			if(naoAchou) {
				System.out.println("N�o h� nenhuma loca��o no status --- EM ATRASO ---");
			}
		}
	}

}