import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import DBConnect;

public class AlunoDAO {

	private Connection connection;

	public AlunoDAO() {

		try {
			this.connection = DBConnect.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // AlunoDAO

	public int ProximoId() {

		int prox_id = 0;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS ID FROM ATOM.ALUNO;");

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next())
				prox_id = rs.getInt("ID");
			
			System.out.println(prox_id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return prox_id;
	}

	public ArrayList<AlunoVO> GetTotosAlunosAtivos() {

		ArrayList<AlunoVO> listAlunos = new ArrayList<AlunoVO>();

		try {

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM ATOM.ALUNO WHERE ATIVO=TRUE");

			while (rs.next()) {

				AlunoVO alunoVO = new AlunoVO();

				alunoVO.id = rs.getInt("ID");
				alunoVO.nome = rs.getString("NAME");
				alunoVO.idade = rs.getInt("IDADE");
				alunoVO.endereco = rs.getString("ENDERECO");
				alunoVO.bairro = rs.getString("BAIRRO");
				alunoVO.cidade = rs.getString("CIDADE");
				alunoVO.uf = rs.getString("UF");
				alunoVO.celular = rs.getString("CELULAR");
				alunoVO.email = rs.getString("EMAIL");
				alunoVO.sexo = rs.getString("SEXO");
				alunoVO.ativo = rs.getBoolean("ATIVO");

				listAlunos.add(alunoVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listAlunos;
	}

	public ArrayList<AlunoVO> GetTotosAlunos() {

		ArrayList<AlunoVO> listAlunos = new ArrayList<AlunoVO>();

		try {

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM ATOM.ALUNO");

			while (rs.next()) {

				AlunoVO alunoVO = new AlunoVO();

				alunoVO.id = rs.getInt("ID");
				alunoVO.nome = rs.getString("NAME");
				alunoVO.idade = rs.getInt("IDADE");
				alunoVO.endereco = rs.getString("ENDERECO");
				alunoVO.bairro = rs.getString("BAIRRO");
				alunoVO.cidade = rs.getString("CIDADE");
				alunoVO.uf = rs.getString("UF");
				alunoVO.celular = rs.getString("CELULAR");
				alunoVO.email = rs.getString("EMAIL");
				alunoVO.sexo = rs.getString("SEXO");
				alunoVO.ativo = rs.getBoolean("ATIVO");

				listAlunos.add(alunoVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listAlunos;
	}

	public AlunoVO GetAlunoPorID(int id) {

		AlunoVO alunoVO = new AlunoVO();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from ATOM.ALUNO WHERE ID=?");

			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				alunoVO.id = rs.getInt("ID");
				alunoVO.nome = rs.getString("NAME");
				alunoVO.idade = rs.getInt("IDADE");
				alunoVO.endereco = rs.getString("ENDERECO");
				alunoVO.bairro = rs.getString("BAIRRO");
				alunoVO.cidade = rs.getString("CIDADE");
				alunoVO.uf = rs.getString("UF");
				alunoVO.celular = rs.getString("CELULAR");
				alunoVO.email = rs.getString("EMAIL");
				alunoVO.sexo = rs.getString("SEXO");
				alunoVO.ativo = rs.getBoolean("ATIVO");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alunoVO;
	}

	public void Salvar(AlunoVO alunoVO) throws IOException {

		try {

			String sql = "INSERT INTO ATOM.ALUNO (ID, NOME, IDADE, ENDERECO, BAIRRO, CIDADE, UF, CELULAR, EMAIL, SEXO, ATIVO) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

			preparedStatement.setInt(1, alunoVO.id);
			preparedStatement.setString(2, alunoVO.nome);
			preparedStatement.setInt(3, alunoVO.idade);
			preparedStatement.setString(4, alunoVO.endereco);
			preparedStatement.setString(5, alunoVO.bairro);
			preparedStatement.setString(6, alunoVO.cidade);  
			preparedStatement.setString(7, alunoVO.uf);
			preparedStatement.setString(8, alunoVO.celular);
			preparedStatement.setString(9, alunoVO.email);
			preparedStatement.setString(10, alunoVO.sexo);
			preparedStatement.setBoolean(11, alunoVO.ativo);

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} // try
	}

	public void Salvar(ArrayList<AlunoVO> listAlunoVO) throws IOException {		
		for (AlunoVO alunoVO : listAlunoVO)
			Salvar(alunoVO);
	}

	public void Alterar(AlunoVO alunoVO) throws IOException {

		try {

			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE ATOM.ALUNO NOME=?, IDADE=?, ENDERECO=?, BAIRRO=?, CIDADE=?, UF=?, CELULAR=?, EMAIL=?, SEXO=?, ATIVO=? WHERE ID=?");

			// Parameters start with 1
			preparedStatement.setString(1, alunoVO.nome);
			preparedStatement.setInt(2, alunoVO.idade);
			preparedStatement.setString(3, alunoVO.endereco);
			preparedStatement.setString(4, alunoVO.bairro);
			preparedStatement.setString(5, alunoVO.cidade);
			preparedStatement.setString(6, alunoVO.uf);
			preparedStatement.setString(7, alunoVO.celular);
			preparedStatement.setString(8, alunoVO.email);
			preparedStatement.setString(9, alunoVO.sexo);
			preparedStatement.setBoolean(10, alunoVO.ativo);

			preparedStatement.setInt(11, alunoVO.id);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Excluir(int id) throws IOException {

		String sql = "DELETE FROM ATOM.ALUNO WHERE ID=?";

		try {

			PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

			preparedStatement.setLong(1, id);

			preparedStatement.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
