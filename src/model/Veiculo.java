package model;

public class Veiculo {

	private String marca;
	private String modelo;
	private int anoFabricacao;
	private int anoModelo;
	private String placa;
	private int codigo;
	public Veiculo() {
		
	}
	
	public Veiculo(int codigo, String marca, String modelo, int anoFabricacao, int anoModelo, String placa) {
		this.codigo = codigo;
		this.marca = marca;
		this.modelo = modelo;
		this.anoFabricacao = anoFabricacao;
		this.anoModelo = anoModelo;
		this.placa = placa;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(int anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public int getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(int anoModelo) {
		this.anoModelo = anoModelo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	
	
	public void imprimir() {
		System.out.println("\n\n -----| Veiculo " + placa + " |-----");
		System.out.println("Marca: " + marca);
		System.out.println("Modelo: " + modelo);
		System.out.println("Ano de fabricação: "+ anoFabricacao);
		System.out.println("Ano do modelo: " + anoModelo);
		
	}
	
}
