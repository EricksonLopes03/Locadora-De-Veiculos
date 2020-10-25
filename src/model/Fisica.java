package model;

import java.time.*;

public class Fisica extends Cliente{

	private String cpf;
	private LocalDate dataNascimento;
	
	public Fisica(int codigo, String nome, String endereco, String telefone, String cpf, LocalDate dataNascimento) {
		super(codigo, nome, endereco, telefone);
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}
	
	public Fisica() {
		super();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	
	public void mostra () {
		super.mostra();
		System.out.println("CPF: " + getCpf());
		System.out.printf("Data de nascimento: %d/%d/%d\n", dataNascimento.getDayOfMonth(), dataNascimento.getMonthValue(), dataNascimento.getYear());
		System.out.println("-------------------------");
	}
	
	
}
