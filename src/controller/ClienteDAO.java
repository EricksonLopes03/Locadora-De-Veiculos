package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


import model.*;

public class ClienteDAO {
	
	private Connection conn;

	public ClienteDAO() {
		conn = Conexao.getConexao();
	}
	
	
	
		public void incluir(Cliente cli) {
			String sql;
			PreparedStatement ps;

			

			Cliente cliente = (Cliente) cli;
			
			if(cliente instanceof Fisica) {
				sql = "INSERT INTO cliente(id_cliente, nome, endereco, telefone, cpf, datanascimento) VALUES (?,?,?,?,?,?)";
				try {
					ps = conn.prepareStatement(sql);
					ps.setInt(1, cli.getCodigo());
					ps.setString(2, cli.getNome());
					ps.setString(3, cli.getEndereco());
					ps.setString(4, cli.getTelefone());
					ps.setString(5, ((Fisica) cliente).getCpf());
					ps.setDate(6, java.sql.Date.valueOf(((Fisica) cliente).getDataNascimento()));
					ps.execute();
					ps.close();
				} catch (Exception e) {
					System.out.println("Erro ao inserir dados: " + e.getMessage());
				}
			
		} else if (cliente instanceof Juridica) {
			sql = "INSERT INTO cliente(id_cliente, nome, endereco, telefone, cnpj) VALUES (?,?,?,?,?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, cli.getCodigo());
				ps.setString(2, cli.getNome());
				ps.setString(3, cli.getEndereco());
				ps.setString(4, cli.getTelefone());
				ps.setString(5, ((Juridica) cliente).getCnpj());
				ps.execute();
				ps.close();
			} catch (Exception e) {
				System.out.println("Erro ao inserir dados: " + e.getMessage());
			}
		}
		}
		
		
		
		
		public void alterar(String doc, Cliente cli) {
			String sql;
			PreparedStatement ps = null;
			Cliente cliente = (Cliente) cli;
			
			
			if(cliente instanceof Fisica) {
			try {
				sql = "UPDATE cliente SET nome = ?, endereco = ?, telefone = ?, cpf = ?, datanascimento = ? WHERE cpf = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, cli.getNome());
				ps.setString(2, cli.getEndereco());
				ps.setString(3, cli.getTelefone());
				ps.setString(4, ((Fisica) cliente).getCpf());
				ps.setDate(5, java.sql.Date.valueOf(((Fisica) cliente).getDataNascimento()));
				ps.setString(6, doc);
				ps.execute();
				ps.close();
			} catch (Exception e) {
				System.out.println("Erro na operção de alterar registro: " + e.getMessage());
			}
			}else {
				try {
					sql = "UPDATE cliente SET nome = ?, endereco = ?, telefone = ?, cnpj = ? WHERE cnpj = ?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, cli.getNome());
					ps.setString(2, cli.getEndereco());
					ps.setString(3, cli.getTelefone());
					ps.setString(4, ((Juridica) cliente).getCnpj());
					ps.setString(5, doc);
					ps.execute();
					ps.close();
				} catch (Exception e) {
					System.out.println("Erro na operção de alterar registro: " + e.getMessage());
				}
			}
		}
		
		
		public ArrayList<Cliente> listar() {
			ArrayList<Cliente> clientes = new ArrayList<>();

			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "SELECT id_cliente, nome, telefone, endereco, cpf, datanascimento, cnpj FROM cliente";
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					String doc = rs.getString("cpf");
					if(doc != null) {
					Fisica cli = new Fisica();
					cli.setCodigo(rs.getInt("id_cliente"));
					cli.setNome(rs.getString("nome"));
					cli.setTelefone(rs.getString("telefone"));
					cli.setEndereco(rs.getString("endereco"));
					cli.setCpf(rs.getString("cpf"));
					cli.setDataNascimento(convertToEntityAttribute(rs.getDate("datanascimento")));
					clientes.add(cli);
					}else{
					Juridica cli = new Juridica();
					cli.setCodigo(rs.getInt("id_cliente"));
					cli.setNome(rs.getString("nome"));
					cli.setTelefone(rs.getString("telefone"));
					cli.setEndereco(rs.getString("endereco"));
					cli.setCnpj(rs.getString("cnpj"));
					clientes.add(cli);
					}

				
				}
				rs.close();
				ps.close();
			} catch (Exception e) {
				System.err.println("Erro na operação de listar registros: " + e.getMessage());
			}
			return clientes;
		}
		
		
		
		
		
		
		public void apagar(Cliente cli) {
			String sql;
			PreparedStatement ps = null;

			if(cli instanceof Fisica) {
			
			sql = "delete from cliente where cpf = ?";

			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, ((Fisica)cli).getCpf());
				ps.execute();
				ps.close();
			} catch (Exception e) {
				System.out.println("Erro na operação de apagar registro: " + e.getMessage());
			}
			}else {
				
				sql = "delete from cliente where cnpj = ?";

				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, ((Juridica) cli).getCnpj());
					ps.execute();
					ps.close();
				} catch (Exception e) {
					System.out.println("Erro na operação de apagar registro: " + e.getMessage());
				}
			}
		}
		
		
		
		public int proximoCodigo() {
			String sql;
			PreparedStatement ps = null;
			int proximoCodigo = -1;

			sql = "select max(id_cliente) from cliente";

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
