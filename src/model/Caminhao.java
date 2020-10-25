package model;

public class Caminhao extends Veiculo{

	private double capacidadeCarga;
	private int quantidadeEixos;
	
	public Caminhao(int codigo, String marca, String modelo, int anoFabricacao, int anoModelo, String placa, double capacidadeCarga,
			int quantidadeEixos) {
		super(codigo, marca, modelo, anoFabricacao, anoModelo, placa);
		this.capacidadeCarga = capacidadeCarga;
		this.quantidadeEixos = quantidadeEixos;
	}

	public Caminhao() {
		super();
	}

	public double getCapacidadeCarga() {
		return capacidadeCarga;
	}

	public void setCapacidadeCarga(double capacidadeCarga) {
		this.capacidadeCarga = capacidadeCarga;
	}

	public int getQuantidadeEixos() {
		return quantidadeEixos;
	}

	public void setQuantidadeEixos(int quantidadeEixos) {
		this.quantidadeEixos = quantidadeEixos;
	}
	
	
	public void imprimir() {
		super.imprimir();
		System.out.println("Quantidade de eixos: " + quantidadeEixos);
		System.out.println("Capacidade de Carga: " + capacidadeCarga);
		
	}
	
	
	
}
