package view;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;

import controller.*;
import model.*;

public class GerenciaCliente {
	private Scanner sci;
	private Scanner scs;
	private ArrayList<Cliente> cli;
	private ArrayList<Locacao> locacoes;
	private String nome, cnpj, endereco, telefone, cpf;
	private int dia, mes, ano;
	private LocalDate dataNascimento;
	private Cliente cliente;
	private Juridica juri;
	private Fisica fis;
	private boolean igual;
	private Connection con;

	public GerenciaCliente(ArrayList<Cliente> cli, ArrayList<Locacao> locacoes) {
		this.cli = cli;
		this.locacoes = locacoes;
		con = Conexao.getConexao();
		scs = new Scanner(System.in);
		sci = new Scanner(System.in);
	}

	public void cadastro() {
		int esc;
		Fisica fis2 = new Fisica();
		Juridica juri2 = new Juridica();
		ClienteDAO add = new ClienteDAO();
		System.out.println("-----------=== CADASTRO ====-----------\n");
		System.out.println("Qual tipo de cliente deseja cadastrar?");
		do {
			System.out.println("1 - Pessoa Física");
			System.out.println("2 - Pessoa Jurídica");
			System.out.println("Escolha: ");
			esc = sci.nextInt();
			if (esc != 1 && esc != 2) {
				System.out.println("\nOpção inválida!\n");
			}
		} while (esc != 1 && esc != 2);
		System.out.println("Insira os dados para");
		System.out.println("Nome: ");
		nome = scs.nextLine();
		System.out.println("Endereço: ");
		endereco = scs.nextLine();
		System.out.println("Telefone: ");
		telefone = scs.nextLine();
		if (esc == 1) {
			do {
				igual = false;
				System.out.println("CPF: ");
				cpf = scs.nextLine();
				for (Cliente cliente2 : cli) {
					if (cliente2 instanceof Fisica) {
						fis2 = (Fisica) cliente2;
						if (fis2.getCpf().equals(cpf)) {
							System.out.println("Este CPF já consta no sistema. Insira outro valor!");
							igual = true;
						}
					} else {
						juri2 = (Juridica) cliente2;
						if (juri2.getCnpj().equals(cpf)) {
							System.out.println(
									"Este valor já consta no sistema como CNPJ de uma Pessoa Jurídica. Insira outro valor!");
							igual = true;
						}
					}
				}
			} while (igual);
			System.out.println("Data de nascimento");
			System.out.println("Dia:");
			dia = sci.nextInt();
			System.out.println("Mês:");
			mes = sci.nextInt();
			System.out.println("Ano:");
			ano = sci.nextInt();
			dataNascimento = LocalDate.of(ano, mes, dia);
			int codigo = (add.proximoCodigo());
			fis = new Fisica(codigo, nome, endereco, telefone, cpf, dataNascimento);
			
			
			
			
			add.incluir(fis);
			fis = null;
		} else {
			do {
				igual = false;
				System.out.println("CNPJ: ");
				cnpj = scs.nextLine();
				for (Cliente cliente2 : cli) {
					if (cliente2 instanceof Juridica) {
						juri2 = (Juridica) cliente2;
						if (juri2.getCnpj().equals(cnpj)) {
							System.out.println("Este CNPJ já consta no sistema. Insira outro CNPJ");
							igual = true;
						}
					} else {
						fis2 = (Fisica) cliente2;
						if (fis2.getCpf().equals(cnpj)) {
							System.out.println(
									"Este valor já consta no sistema como CPF de uma Pessoa Física. Insira outro valor!");
							igual = true;
						}
					}
				}
			} while (igual);
			int codigo = (add.proximoCodigo());
			juri = new Juridica(codigo, nome, endereco, telefone, cnpj);
			
			add.incluir(juri);
			juri = null;
		}
		System.out.println("Cliente cadastrado com sucesso!");

	}

	public void alterar() {
		Fisica fis2 = new Fisica();
		Juridica juri2 = new Juridica();
		ClienteDAO add = new ClienteDAO();
		cli = add.listar();
		System.out.println("-----------=== ALTERAÇÃO ====-----------\n");
		int esc;
		String doc;
		if (cli.isEmpty()) {
			System.out.println("Não há clientes cadastrados!");

		} else {

			System.out.println("Insira a posição do cliente que deseja alterar dados");
			esc = sci.nextInt();
			if (esc >= cli.size() || esc < 0) {
				System.out.println("Posiçao inválida!");
			} else {
				cliente = cli.get(esc);
				if (cliente instanceof Fisica) {
					fis = (Fisica) cliente;
					fis.mostra();
					doc = fis.getCpf();
				} else {
					juri = (Juridica) cliente;
					juri.mostra();
					doc = juri.getCnpj();
				}
				System.out.println("Insira os novos dados para...");
				System.out.println("Nome: ");
				cliente.setNome(scs.nextLine());
				System.out.println("Endereço: ");
				cliente.setEndereco(scs.nextLine());
				System.out.println("Telefone: ");
				cliente.setTelefone(scs.nextLine());
				if (cliente instanceof Fisica) {
					do {
						int aux = 0;
						igual = false;
						System.out.println("CPF: ");
						cpf = scs.nextLine();
						for (Cliente cliente2 : cli) {
							if (cliente2 instanceof Fisica) {
								fis2 = (Fisica) cliente2;
								if (fis2.getCpf().equals(cpf) && aux != esc) {
									System.out.println("Este CPF já consta no sistema. Insira outro valor!");
									igual = true;
								}
							} else {
								juri2 = (Juridica) cliente2;
								if (juri2.getCnpj().equals(cpf) && aux != esc) {
									System.out.println(
											"Este CNPJ já consta no sistema como CNPJ de uma Pessoa Jurídica. Insira outro valor!");
									igual = true;
								}
							}
							aux++;
						}
					} while (igual);
					fis.setCpf(cpf);
					System.out.println("Data de nascimento");
					System.out.println("Dia:");
					dia = sci.nextInt();
					System.out.println("Mês:");
					mes = sci.nextInt();
					System.out.println("Ano:");
					ano = sci.nextInt();
					dataNascimento = LocalDate.of(ano, mes, dia);
					fis.setDataNascimento(dataNascimento);
					add.alterar(doc, fis);
				} else {
					do {
						int aux = 0;
						igual = false;
						System.out.println("CNPJ: ");
						cnpj = scs.nextLine();
						for (Cliente cliente2 : cli) {
							if (cliente2 instanceof Juridica) {
								juri2 = (Juridica) cliente2;
								if (juri2.getCnpj().equals(cnpj) && aux != esc) {
									System.out.println("Este CNPJ já consta no sistema. Insira outro CNPJ");
									igual = true;
								}
							} else {
								fis2 = (Fisica) cliente2;
								if (fis2.getCpf().equals(cnpj) && aux != esc) {
									System.out.println(
											"Este valor já consta no sistema como CPF de uma Pessoa Física. Insira outro valor!");
									igual = true;
								}
							}
							aux++;
						}
						add.alterar(doc, juri);
					} while (igual);

				}
				System.out.println("Dados alterados com sucesso!");
			}

		}
	}

	public void excluir() {
		Fisica fis2 = new Fisica();
		Juridica juri2 = new Juridica();
		Locacao locacao = null;
		boolean locado;
		ClienteDAO add = new ClienteDAO();
		cli = add.listar();

		System.out.println("-----------=== EXCLUSÃO ====-----------\n");

		int pos, esc;
		if (cli.isEmpty()) {
			System.out.println("Não há clientes cadastrados!");
		} else {
			System.out.println("Insira a posição que deseja excluir: ");
			pos = sci.nextInt();
			locado = false;
			if (pos >= cli.size() || pos < 0) {
				System.out.println("Posição inválida");
			} else {
				Iterator<Locacao> it = locacoes.iterator();
				while (it.hasNext()) {
					locacao = it.next();
					if (locacao.getCliente().equals(cli.get(pos))) {
						System.out.println("----------- Impossível Excluir -----------");
						System.out.println("     Cliente está cadastrado em uma locação     ");
						locado = true;
					}
				}
				if (locado == false) {
					cliente = cli.get(pos);

					if (cliente instanceof Fisica) {
						fis = (Fisica) cliente;
						fis.mostra();
					} else {
						juri = (Juridica) cliente;
						juri.mostra();
					}
					System.out.println("Deseja confirmar a exclusão?");
					System.out.println("   1 - SIM   |    2 - NÃO   ");
					esc = sci.nextInt();
					if (esc == 1) {
						add.apagar(cliente);
						
						System.out.println("Exclusão realizada com sucesso!");
					} else {
						System.out.println("Exclusão não confirmada!");
					}
				}
			}
		}
	}

	public void consulta() {
		ClienteDAO add = new ClienteDAO();
		System.out.println("-----------=== CONSULTA ====-----------\n");
		int pos;
		cli = add.listar();
		if (cli.isEmpty()) {
			System.out.println("Não há clientes cadastrados!");
		} else {
			System.out.println("Insira a posição que deseja consultar: ");
			pos = sci.nextInt();
			if (pos >= cli.size() || pos < 0) {
				System.out.println("Posição inválida");
			} else {
				cliente = cli.get(pos);
				if (cliente instanceof Fisica) {
					fis = (Fisica) cliente;
					fis.mostra();
				} else {
					juri = (Juridica) cliente;
					juri.mostra();
				}
			}
		}
	}

	public void relatorio() {
		System.out.println("-----------=== RELATÓRIO ====-----------\n");
		ClienteDAO add = new ClienteDAO();

		cli = add.listar();

		if (cli.isEmpty()) {
			System.out.println("\nNÃO POSSUI NENHUM CLIENTE CADASTRADO!!\n");
		} else {

			Iterator<Cliente> it = cli.iterator();
			Cliente obj;

			while (it.hasNext()) {
				obj = it.next();
				obj.mostra();
				System.out.println("\n---------------\n");
			}
			cli = null;
		}

	}

}
