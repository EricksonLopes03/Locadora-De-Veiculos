package controller;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import model.*;

public class LocacaoDAO {
	
	private Connection conn;
	private PreparedStatement psVei;
		
		public LocacaoDAO() {
			conn = Conexao.getConexao();
		}
		
		public void incluir(Locacao loc) {
			String sql;
			PreparedStatement ps;

			sql = "INSERT INTO locacao(id_locacao, id_cliente, preco, multa, status, data_inicio, data_prev_devolucao) VALUES (?,?,?,?,?,?,?)";
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, loc.getCodigo());
				ps.setInt(2, loc.getCliente().getCodigo());
				ps.setDouble(3, loc.getPreco());
				ps.setDouble(4, loc.getMulta());
				ps.setInt(5, loc.getStatus());
				ps.setDate(6, java.sql.Date.valueOf(loc.getDataInicio()));
				ps.setDate(7, java.sql.Date.valueOf(loc.getDataPrevistaDevolucao()));
				ps.execute();
			} catch (Exception e) {
				System.out.println("Erro ao inserir dados: " + e.getMessage());
			}
			
			cadastrarVeiculos(loc);
		}
			
			
		public void cadastrarVeiculos(Locacao loc) {
				String sql;
				PreparedStatement ps;
				
				sql = "INSERT INTO veiculo_locacao(id_veiculo, id_locacao) VALUES (?,?)";
				
				try {
				ArrayList veics = loc.getVeiculos();
				Veiculo veiculo = null;
				Iterator<Veiculo> it = veics.iterator();
				while(it.hasNext()) {
					veiculo = it.next();
					ps = conn.prepareStatement(sql);
					//veiculo.imprimir();
					ps.setInt(1, veiculo.getCodigo());
					ps.setInt(2, loc.getCodigo());
					ps.execute();
				
				}
				} catch (Exception e) {
					System.out.println("Erro ao inserir dados: " + e.getMessage());
				}		
				
				}
				
			
		public ArrayList<Locacao> listar(){
			
			ArrayList<Locacao> locacoes = new ArrayList();
			
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			PreparedStatement psVei = null;
			ResultSet rsVei = null;
			PreparedStatement psCli = null;
			ResultSet rsCli = null;
			PreparedStatement psVeiLoc = null;
			ResultSet rsVeiLoc = null;
			
			String sql = "SELECT * FROM locacao";
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					Locacao loc = new Locacao();
					loc.setCodigo(rs.getInt("id_locacao"));
					int codigo = rs.getInt("id_cliente");
	
					
					String sqlCli = "SELECT * FROM cliente WHERE id_cliente = "
							+ codigo + "";
					try {
						psCli = conn.prepareStatement(sqlCli);
						rsCli = psCli.executeQuery();
						
						while (rsCli.next()) {
							
							String doc = rsCli.getString("cpf");
							if(doc != null) {
							Fisica cli = new Fisica();
							cli.setCodigo(rsCli.getInt("id_cliente"));
							cli.setNome(rsCli.getString("nome"));
							cli.setTelefone(rsCli.getString("telefone"));
							cli.setEndereco(rsCli.getString("endereco"));
							cli.setCpf(rsCli.getString("cpf"));
							cli.setDataNascimento(convertToEntityAttribute(rsCli.getDate("datanascimento")));
							loc.setCliente(cli);
							
							}else{
							Juridica cli = new Juridica();
							cli.setCodigo(rsCli.getInt("id_cliente"));
							cli.setNome(rsCli.getString("nome"));
							cli.setTelefone(rsCli.getString("telefone"));
							cli.setEndereco(rsCli.getString("endereco"));
							cli.setCnpj(rsCli.getString("cnpj"));
							loc.setCliente(cli);
							}
						}
						
					} catch (Exception e) {
						System.err.println("Erro na operação de listar registros do cliente: " + e.getMessage());
					}
				
					loc.setMulta(rs.getDouble("multa"));
				
					loc.setStatus(rs.getInt("status"));
				
					loc.setDataInicio(convertToEntityAttribute(rs.getDate("data_inicio")));
					loc.setPreco(rs.getDouble("preco"));
					
					loc.setDataPrevistaDevolucao(convertToEntityAttribute(rs.getDate("data_prev_devolucao")));
					Date var = rs.getDate("data_devolucao");
					if(var != null) {
					loc.setDataDevolucao(convertToEntityAttribute(rs.getDate("data_devolucao")));
					}
				
					
					
					ArrayList<Veiculo> veiculos = new ArrayList<>();

					

					String sqlVeiLoc = "SELECT * FROM veiculo_locacao WHERE id_locacao = " + loc.getCodigo() + "";
					
					try {
						psVeiLoc = conn.prepareStatement(sqlVeiLoc);
						rsVeiLoc = psVeiLoc.executeQuery();
						
						while (rsVeiLoc.next()) {
							
							int code = rsVeiLoc.getInt("id_veiculo");
							
							String sqlVei = "SELECT * FROM veiculo WHERE id_veiculo = " + code + "";
							psVei = conn.prepareStatement(sqlVei);
							rsVei = psVei.executeQuery();
							rsVei.next();
							String capPass = rsVei.getString("cap_passageiros");
							if (capPass != null) {
								Carro car = new Carro();
								car.setCodigo(rsVei.getInt("id_veiculo"));
								car.setMarca(rsVei.getString("marca"));
								car.setModelo(rsVei.getString("modelo"));
								car.setAnoFabricacao(rsVei.getInt("ano_fabricacao"));
								car.setAnoModelo(rsVei.getInt("ano_modelo"));
								car.setPlaca(rsVei.getString("placa"));
								car.setCapacidadePassageiros(rsVei.getInt("cap_passageiros"));
								car.setQuantidadePortas(rsVei.getInt("qtd_portas"));					
								veiculos.add(car);
								loc.setVeiculos(veiculos);
							} else {
								Caminhao cam = new Caminhao();
								cam.setCodigo(rsVei.getInt("id_veiculo"));
								cam.setMarca(rsVei.getString("marca"));
								cam.setModelo(rsVei.getString("modelo"));
								cam.setAnoFabricacao(rsVei.getInt("ano_fabricacao"));
								cam.setAnoModelo(rsVei.getInt("ano_modelo"));
								cam.setPlaca(rsVei.getString("placa"));
								cam.setCapacidadeCarga(rsVei.getDouble("cap_carga"));
								cam.setQuantidadeEixos(rsVei.getInt("num_eixos"));
								
								veiculos.add(cam);
								loc.setVeiculos(veiculos);
							}

						}
						
						
					} catch (Exception e) {
						System.err.println("Erro na operação de listar registros do veiculo: " + e.getMessage());
					}
					
					
					
					locacoes.add(loc);
				
				
				}
				rs.close();
				ps.close();
				rsCli.close();
				psCli.close();
				rsVei.close();
				psVei.close();
				rsVeiLoc.close();
				psVeiLoc.close();
				
				
			} catch (Exception e) {
				System.err.println("Erro na operação de listar registros da locacao: " + e.getMessage());
			}
			
			
			
			return locacoes;
		}
		
		public void atualizar(Locacao locacao){
			
			String sql;
			PreparedStatement ps = null;
			Locacao loc = locacao;
			
			
			try {
				sql = "UPDATE locacao SET id_cliente = ?, preco = ?, multa = ?, status = ?, data_inicio = ?, data_prev_devolucao = ?, data_devolucao = ? WHERE id_locacao = ?";
				ps = conn.prepareStatement(sql);
				
				ps.setInt(1, loc.getCliente().getCodigo());
				ps.setDouble(2, loc.getPreco());
				ps.setDouble(3, loc.getMulta());
				ps.setInt(4, loc.getStatus());
				ps.setDate(5, java.sql.Date.valueOf(loc.getDataInicio()));
				ps.setDate(6, java.sql.Date.valueOf(loc.getDataPrevistaDevolucao()));
				if(loc.getDataDevolucao() != null) {
				ps.setDate(7, java.sql.Date.valueOf(loc.getDataDevolucao()));
				}else {
					ps.setDate(7, null);	
				}
				ps.setInt(8, loc.getCodigo());
				
				ps.execute();
				ps.close();
			} catch (Exception e) {
				System.out.println("Erro na operção de alterar registro: " + e.getMessage());
			}
			
			
			
		}
		
		public int proximoCodigo() {
			String sql;
			PreparedStatement ps = null;
			int proximoCodigo = -1;

			sql = "select max(id_locacao) from locacao";

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
