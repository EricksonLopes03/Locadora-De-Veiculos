package model;

import java.time.*;
import java.util.ArrayList;

public class Locacao {
	private int codigo;
	private Cliente cliente;
	private ArrayList<Veiculo> veiculos;
	private LocalDate dataInicio;
	private LocalDate dataPrevistaDevolucao;
	private LocalDate dataDevolucao;
	private double preco;
	private double multa;
	private int status; //1 - locado, 2 - pago, 0 - cancelado 
	
	
	public Locacao() {
		
	}
	
	public Locacao(int codigo, Cliente cliente, ArrayList<Veiculo> veiculos, LocalDate dataInicio, LocalDate dataPrevistaDevolucao,
			LocalDate dataDevolucao, double preco, double multa, int status) {
		super();
		this.codigo = codigo;
		this.cliente = cliente;
		this.veiculos = veiculos;
		this.dataInicio = dataInicio;
		this.dataPrevistaDevolucao = dataPrevistaDevolucao;
		this.dataDevolucao = dataDevolucao;
		this.preco = preco;
		this.multa = multa;
		this.status = status;
	}
	
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public ArrayList<Veiculo> getVeiculos() {
		return veiculos;
	}
	public void setVeiculos(ArrayList<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataPrevistaDevolucao() {
		return dataPrevistaDevolucao;
	}
	public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
		this.dataPrevistaDevolucao = dataPrevistaDevolucao;
	}
	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public double getMulta() {
		return multa;
	}
	public void setMulta(double multa) {
		this.multa = multa;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
