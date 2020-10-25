package model;

public class Carro extends Veiculo {

	private int capacidadePassageiros;
	private int quantidadePortas;
	public Carro() {
		super();
	}
	
	
	public Carro(int codigo, String marca, String modelo, int anoFabricacao, int anoModelo, String placa, int capacidadePassageiros,
			int quantidadePortas) {
		super(codigo, marca, modelo, anoFabricacao, anoModelo, placa);
		this.capacidadePassageiros = capacidadePassageiros;
		this.quantidadePortas = quantidadePortas;
	}
	

	
	public int getCapacidadePassageiros() {
		return capacidadePassageiros;
	}


	public void setCapacidadePassageiros(int capacidadePassageiros) {
		this.capacidadePassageiros = capacidadePassageiros;
	}


	public int getQuantidadePortas() {
		return quantidadePortas;
	}


	public void setQuantidadePortas(int quantidadePortas) {
		this.quantidadePortas = quantidadePortas;
	}
	
	public void imprimir() {
		super.imprimir();
		System.out.println("Quantidade de Portas: "+ quantidadePortas);
		System.out.println("Capacidade de Passageiros: " + capacidadePassageiros);
		
	}
	
}
