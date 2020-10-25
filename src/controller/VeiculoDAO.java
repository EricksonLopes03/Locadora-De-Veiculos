package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.*;

public class VeiculoDAO {
	private Connection conn;

	public VeiculoDAO() {
		this.conn = Conexao.getConexao();
	}

	public void inserir(Veiculo vei) {
		String sql;
		PreparedStatement ps;

		Veiculo v = vei;

		if (v instanceof Carro) {

			sql = "INSERT INTO veiculo(id_veiculo, marca, modelo, ano_fabricacao, ano_modelo, placa, cap_passageiros, qtd_portas) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, v.getMarca());
				ps.setString(2, v.getModelo());
				ps.setInt(3, v.getAnoFabricacao());
				ps.setInt(4, v.getAnoModelo());
				ps.setString(5, v.getPlaca());
				ps.setInt(6, ((Carro) v).getCapacidadePassageiros());
				ps.setInt(7, ((Carro) v).getQuantidadePortas());
				ps.execute();
				ps.close();

			} catch (Exception e) {
				System.out.println("Erro ao inserir dados: " + e.getMessage());
			}

		} else {
			sql = "INSERT INTO veiculo(id_veiculo, marca, modelo, ano_fabricacao, ano_modelo, placa, cap_carga, num_eixos) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, v.getMarca());
				ps.setString(2, v.getModelo());
				ps.setInt(3, v.getAnoFabricacao());
				ps.setInt(4, v.getAnoModelo());
				ps.setString(5, v.getPlaca());
				ps.setDouble(6, ((Caminhao) v).getCapacidadeCarga());
				ps.setInt(7, ((Caminhao) v).getQuantidadeEixos());
				ps.execute();
				ps.close();

			} catch (Exception e) {
				System.out.println("Erro ao inserir dados: " + e.getMessage());
			}
		}
	}

	public void atualizar(Veiculo vei, String placa) {

		String sql;
		PreparedStatement ps;

		Veiculo v = vei;

		if (v instanceof Carro) {

			
			try {
				sql = "UPDATE veiculo SET marca = ?, modelo = ?, ano_fabricacao = ?, ano_modelo = ?, placa = ?, cap_passageiros = ?, qtd_portas = ? WHERE placa = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, v.getMarca());
				ps.setString(2, v.getModelo());
				ps.setInt(3, v.getAnoFabricacao());
				ps.setInt(4, v.getAnoModelo());
				ps.setString(5, v.getPlaca());
				ps.setInt(6, ((Carro) v).getCapacidadePassageiros());
				ps.setInt(7, ((Carro) v).getQuantidadePortas());
				ps.setString(8, placa);
				ps.execute();
				ps.close();
			

			} catch (Exception e) {
				System.out.println("Erro ao atualizar dados: " + e.getMessage());
			}

		} else {
			
			try {
				sql = "UPDATE veiculo SET marca = ?, modelo = ?, ano_fabricacao = ?, ano_modelo = ?, placa = ?, cap_carga = ?, num_eixos = ? WHERE placa = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, v.getMarca());
				ps.setString(2, v.getModelo());
				ps.setInt(3, v.getAnoFabricacao());
				ps.setInt(4, v.getAnoModelo());
				ps.setString(5, v.getPlaca());
				ps.setDouble(6, ((Caminhao) v).getCapacidadeCarga());
				ps.setInt(7, ((Caminhao) v).getQuantidadeEixos());
				ps.setString(8, placa);
				ps.execute();
				ps.close();
				

			} catch (Exception e) {
				System.out.println("Erro ao atualizar dados: " + e.getMessage());
			}
		}

	}

	public ArrayList<Veiculo> recuperar() {
		ArrayList<Veiculo> veiculos = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM veiculo";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String capPass = rs.getString("cap_passageiros");
				if (capPass != null) {
					Carro car = new Carro();
					car.setCodigo(rs.getInt("id_veiculo"));
					car.setMarca(rs.getString("marca"));
					car.setModelo(rs.getString("modelo"));
					car.setAnoFabricacao(rs.getInt("ano_fabricacao"));
					car.setAnoModelo(rs.getInt("ano_modelo"));
					car.setPlaca(rs.getString("placa"));
					car.setCapacidadePassageiros(rs.getInt("cap_passageiros"));
					car.setQuantidadePortas(rs.getInt("qtd_portas"));
								
					veiculos.add(car);
				} else {
					Caminhao cam = new Caminhao();
					cam.setCodigo(rs.getInt("id_veiculo"));
					cam.setMarca(rs.getString("marca"));
					cam.setModelo(rs.getString("modelo"));
					cam.setAnoFabricacao(rs.getInt("ano_fabricacao"));
					cam.setAnoModelo(rs.getInt("ano_modelo"));
					cam.setPlaca(rs.getString("placa"));
					cam.setCapacidadeCarga(rs.getDouble("cap_carga"));
					cam.setQuantidadeEixos(rs.getInt("num_eixos"));
					
					
					veiculos.add(cam);
				}

			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			System.err.println("Erro na operação de listar registros: " + e.getMessage());
		}
		return veiculos;
	}
	
	public void apagar (Veiculo vei) {
		
		String sql;
		PreparedStatement ps;

		Veiculo v = vei;
		
		try {
			sql = "DELETE FROM veiculo WHERE placa = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, v.getPlaca());
			ps.execute();
			ps.close();
						
		}catch (Exception e) {
			System.err.println("Erro ao apagar veículo: " + e.getMessage());
			
		}
				
		
	}

	
	
	
	
	public int proximoCodigo() {
		String sql;
		PreparedStatement ps = null;
		int proximoCodigo = -1;

		sql = "select max(id_veiculo) from veiculo";

		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = null;
			rs = ps.executeQuery();

			if (rs.next()) {
				proximoCodigo = rs.getInt(1);
				proximoCodigo++;
			}
			ps.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return proximoCodigo;
	}
	
	public static LocalDate convertToEntityAttribute(Date date) {
	    return date.toLocalDate();
	  }	
	
	
	
	
	
	
	
	
	
	
	
}
