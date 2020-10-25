package model;

public class Juridica extends Cliente {

	private String cnpj;

	public Juridica(int codigo, String nome, String endereco, String telefone, String cnpj) {
		super(codigo, nome, endereco, telefone);
		this.cnpj = cnpj;
	}

	public Juridica() {
		super();
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	
	public void mostra() {
		super.mostra();
		System.out.println("CNPJ: " + getCnpj());
		System.out.println("-------------------------");
	}
	
	
}
