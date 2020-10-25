package view;

import java.sql.Connection;
import java.util.*;

import model.*;
import controller.*;

public class GerenciaVeiculo {

	private ArrayList<Veiculo> veiculos;
	private ArrayList<Locacao> locacoes;
	private Scanner scs = new Scanner(System.in);
	private Scanner sci = new Scanner(System.in);
	private int tipoVeiculo;
	private String placa, marca, modelo;
	private int qtdEixos, qtdPortas, capPassageiros, anoFab, anoMod;
	private double capCarga;
	private Connection con;
	private VeiculoDAO add;

	public GerenciaVeiculo(ArrayList<Veiculo> veiculos, ArrayList<Locacao> locacoes) {
		this.veiculos = veiculos;
		this.locacoes = locacoes;
		this.add  = new VeiculoDAO();
	}

	public void cadastrarVeiculo() {

		System.out.println("-----------<[ Cadastro de Veiculos ]>-----------");
		boolean teste;
		String OPCAO = "1";
		veiculos = add.recuperar();

		do {
			teste = true;
			System.out.println("Digite o tipo de ve�culo que deseja cadastrar");
			System.out.println("1 - Carro");
			System.out.println("2 - Caminhao");
			System.out.println("0 - Cancelar cadastro");
			tipoVeiculo = sci.nextInt();
		} while (tipoVeiculo < 0 || tipoVeiculo > 2);
		if (tipoVeiculo == 0) {
			System.out.println("Cadastro de ve�culo cancelado");
		} else {
			do {
				teste = false;
				System.out.println("Informe a placa do ve�culo: ");
				placa = scs.nextLine();
				for (Veiculo veiculos : veiculos) {
					if (veiculos.getPlaca().equals(placa)) {
						teste = true;
					}
				}
				if (teste) {
					System.out.println("\n             Essa placa j� est� cadastrada no sistema");
					System.out.println("*Digite 0 para sair ou qualquer outro d�gito para tentar novamente*\n");
					OPCAO = scs.nextLine();
					if (OPCAO.equals("0")) {
						teste = true;
					}
				}
			} while (teste);

			if (OPCAO == "0") {
				System.out.println("-------------------Cadastro Cancelado-------------------");
			} else {
				System.out.println("Digite a marca do ve�culo: ");
				marca = scs.nextLine();
				System.out.println("Digite o modelo do ve�culo: ");
				modelo = scs.nextLine();
				System.out.println("Digite o ano de fabrica��o: ");
				anoFab = sci.nextInt();
				System.out.println("Digite o ano do modelo: ");
				anoMod = sci.nextInt();
				if (tipoVeiculo == 1) {
					System.out.println("Digite a capacidade de passageiros do ve�culo: ");
					capPassageiros = sci.nextInt();
					System.out.println("Digite a quantidade de portas do ve�culo: " );
					qtdPortas = sci.nextInt();
					int codigo = (add.proximoCodigo());
					Veiculo carro = new Carro(codigo, marca, modelo, anoFab, anoMod, placa, capPassageiros, qtdPortas);
					add.inserir(carro);
				} else {
					System.out.println("Digite a quantidade de eixos do caminh�o: ");
					qtdEixos = sci.nextInt();
					System.out.println("Digite a capacidade de carga do caminh�o: ");
					capCarga = sci.nextDouble();
					int codigo = (add.proximoCodigo());
					Veiculo caminhao = new Caminhao(codigo, marca, modelo, anoFab, anoMod, placa, capCarga, qtdEixos);
					add.inserir(caminhao);
					
				}
				System.out.println("Ve�culo cadastrado com sucesso!");

			}

		}
	}

	public void alterar() {
		Veiculo veiculo = null;
		boolean achou, teste;
		String auxPlaca = null;
		veiculos = add.recuperar();
		System.out.println("-----------<[ Altera��o de Ve�culos ]>-----------");
		if (veiculos.isEmpty()) {
			System.out.println("*************ERRO*************");
			System.out.println("  N�o h� veiculos cadastrados ");
		} else {

			System.out.println("Informe a placa do ve�culo que deseja alterar os dados: ");
			placa = scs.nextLine();
			do {
				achou = false;
				for (Veiculo veiculos : veiculos) {
					if (veiculos.getPlaca().equals(placa)) {
						achou = true;
						veiculo = veiculos;
					}
				}
				if (achou == false) {
					System.out.println("Veiculo n�o localizado");
					System.out.println("Informe novamente a placa do ve�culo que deseja alterar ");
					System.out.println("           Ou digite '0' para cancelar ");
					placa = scs.nextLine();
				}
				if (placa.equals("0")) {
					achou = true;
				}
			} while (!achou);
			if (placa.equals("0")) {
				System.out.println("Altera��o cancelada! ");
			} else {
				do {
					teste = false;
					auxPlaca = placa;
					
					System.out.println("Informe a nova placa do ve�culo: ");
					placa = scs.nextLine();
					for (Veiculo veiculos : veiculos) {
						if (veiculos.getPlaca().equals(placa)) {
							if (veiculo.getPlaca().equals(placa)) {
								System.out.println("* A placa ser� mantida *");
							} else {
								teste = true;
							}
						}
					}
					if (teste) {
						System.out.println(" A placa j� existe no sistema, tente novamente! ");
					}
				} while (teste);
				veiculo.setPlaca(placa);
				System.out.println("Informe o novo modelo do ve�culo: ");
				veiculo.setMarca(scs.nextLine());
				System.out.println("Informe a nova marca do ve�culo: ");
				veiculo.setModelo(scs.nextLine());
				System.out.println("Digite o novo ano de fabrica��o: ");
				veiculo.setAnoFabricacao(sci.nextInt());
				System.out.println("Digite o novo ano do modelo: ");
				veiculo.setAnoModelo(sci.nextInt());
				if (veiculo instanceof Carro) {
					System.out.println("Digite a nova capacidade de passageiros: ");
					((Carro) veiculo).setCapacidadePassageiros(sci.nextInt());
					System.out.println("Digite a nova quantidade de portas: ");
					((Carro) veiculo).setQuantidadePortas(sci.nextInt());
					System.out.println("placa para alterar no banco... " + auxPlaca);
					add.atualizar(veiculo, auxPlaca);
				} else if (veiculo instanceof Caminhao) {
					System.out.println("Digite capacidade de carga: ");
					((Caminhao) veiculo).setCapacidadeCarga(sci.nextFloat());
					System.out.println("Digite a nova quantidade de eixos: ");
					((Caminhao) veiculo).setQuantidadeEixos(scs.nextInt());
					System.out.println("placa...." + veiculo.getPlaca());
					
					add.atualizar(veiculo, auxPlaca);
					
				}
				System.out.println("Dados do ve�culo alterados com sucesso!");
			}
		}
	}

	public void excluir() {
		boolean achou, locado;
		int op = 0;
		veiculos = add.recuperar();
		System.out.println("-----------<[ Exclus�o de Veiculos ]>-----------");
		Veiculo veiculo = null, v = null;
		Locacao locacao = null;
		if (veiculos.isEmpty()) {
			System.out.println("*************ERRO*************");
			System.out.println("  N�o h� veiculos cadastrados ");
		} else {
			System.out.println("Informe a placa do ve�culo que deseja excluir: ");
			placa = scs.nextLine();
			do {
				achou = false;
				locado = false;
				Iterator<Veiculo> it = veiculos.iterator();
				while (it.hasNext()) {
					veiculo = it.next();
					if (veiculo.getPlaca().equals(placa)) {
						achou = true;
						Iterator<Locacao> it2 = locacoes.iterator();
						while (it2.hasNext()) {
							locacao = it2.next();
							Iterator<Veiculo> it3 = locacao.getVeiculos().iterator();
							while (it3.hasNext()) {
								v = it3.next();
							if(v.getPlaca().equals(veiculo.getPlaca())){
								achou = false;
								locado = true;
								System.out.println("\n\n----------- Imposs�vel Excluir -----------");
								System.out.println(" Este ve�culo est� inserido em uma loca��o \n\n");
							}
								
							}
						}
						break;

					}
				}
				if (achou == false) {
					if(locado == false) {
					System.out.println("\n\n              Veiculo n�o localizado                ");
					}
					System.out.println("Informe novamente a placa do ve�culo que deseja alterar ");
					System.out.println("           Ou digite '0' para cancelar \n\n");
					placa = scs.nextLine();
				}
				if (placa.equals("0")) {
					achou = true;
				}
			} while (!achou);
			if (placa.equals("0")) {
				System.out.println("\n             Exclus�o cancelada!              \n");
			} else {
				System.out.println("---------- Veiculo localizado ----------");
				veiculo.imprimir();
				System.out.println(" Deseja realmente deletar este ve�culo? ");
				System.out.println("      1 - SIM       /      2 - N�O      ");
				do {
					op = sci.nextInt();
					if (op == 1) {
						add.apagar(veiculo);
						System.out.println("Exclus�o do ve�culo confirmada!");
					} else if (op == 2) {
						System.out.println("\n---------- Exclus�o cancelada ----------\n");
					} else {
						System.out.println("----------- OP��O INV�LIDA ----------");
						System.out.println(" Deseja realmente deletar este ve�culo? ");
						System.out.println("      1 - SIM       /      2 - N�O      ");
					}

				} while (op < 1 || op > 2);
			}
		}

	}

	public void relatorio() {
		int op;
		Veiculo veiculo = null;
		boolean achou = false;
		veiculos = add.recuperar();
		System.out.println(" Deseja consultar uma placa no sistema ou exibir um relat�rio completo? ");
		System.out.println("          1 - Consultar placa         /          2 - Relat�rio          ");
		op = sci.nextInt();
		if(op == 1) {
			System.out.println("Digite a placa que deseja consultar: ");
			String placa = scs.nextLine();
			Iterator<Veiculo> it = veiculos.iterator();
			while(it.hasNext()) {
				veiculo = it.next();
				if(veiculo.getPlaca().equals(placa)) {
					achou = true;
				System.out.println("\n\n\n-------- Veiculo Localizado --------");
					veiculo.imprimir();
				}
			}
			if(achou == false) {
				System.out.println("\n\n--------------- Placa n�o consta no sistema ---------------\n\n");
			}
		}else if(op == 2) {
		Iterator<Veiculo> it = veiculos.iterator();
		Veiculo v;

		if (veiculos.isEmpty()) {
			System.out.println("N�o existem veiculos cadastrados no momento");
		} else {
			while (it.hasNext()) {
				v = it.next();
				if (v instanceof Carro) {
					v.imprimir();
					System.out.println("Tipo de veiculo: Carro\n");
				} else {
					if (v instanceof Caminhao) {
						v.imprimir();
						System.out.println("Tipo de veiculo: Caminh�o\n");
					}
				}
			}
		}
		v = null;
		
		}else {
			System.out.println("\n\n**********************OP��O INV�LIDA**********************\n\n");
		}
	}

}
