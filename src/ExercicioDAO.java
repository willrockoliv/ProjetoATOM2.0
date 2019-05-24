import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExercicioDAO {

	private Connection connection;

	public ExercicioDAO() {

		try {
			this.connection = DBConnect.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // ExercicioDAO

	public int ProximoId() {

		int prox_id = 0;

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT COUNT(*) AS ID FROM ATOM.EXERCICIO");

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next())
				prox_id = rs.getInt("ID") + 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return prox_id;
	}

	public ArrayList<ExercicioVO> GetTotosExercicios() {

		ArrayList<ExercicioVO> listExercicioVO = new ArrayList<ExercicioVO>();

		try {

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM ATOM.EXERCICIO");

			while (rs.next()) {

				ExercicioVO exercicioVO = new ExercicioVO();

				exercicioVO.id = rs.getInt("ID");
				exercicioVO.id_aluno = rs.getInt("ID_ALUNO");
				exercicioVO.exercicio = rs.getString("EXERCICIO");
				exercicioVO.carga = rs.getDouble("CARGA");
				exercicioVO.series = rs.getInt("SERIES");
				exercicioVO.repeticoes = rs.getInt("REPETICOES");
				exercicioVO.divisao = rs.getString("DIVISAO");
				exercicioVO.volume = rs.getDouble("VOLUME");

				listExercicioVO.add(exercicioVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listExercicioVO;
	}

	public ExercicioVO GetExercicioPorID(int id) {

		ExercicioVO exercicioVO = new ExercicioVO();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from ATOM.EXERCICIO WHERE ID=?");

			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				exercicioVO.id = rs.getInt("ID");
				exercicioVO.id_aluno = rs.getInt("ID_ALUNO");
				exercicioVO.exercicio = rs.getString("EXERCICIO");
				exercicioVO.carga = rs.getDouble("CARGA");
				exercicioVO.series = rs.getInt("SERIES");
				exercicioVO.repeticoes = rs.getInt("REPETICOES");
				exercicioVO.divisao = rs.getString("DIVISAO");
				exercicioVO.volume = rs.getDouble("VOLUME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exercicioVO;
	}

	public ArrayList<ExercicioVO> GetExercicioPorId_aluno(int id_aluno) {

		ArrayList<ExercicioVO> listExercicioVO = new ArrayList<ExercicioVO>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from ATOM.EXERCICIO WHERE ID_ALUNO=?");
			preparedStatement.setLong(1, id_aluno);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				ExercicioVO exercicioVO = new ExercicioVO();

				exercicioVO.id = rs.getInt("ID");
				exercicioVO.id_aluno = rs.getInt("ID_ALUNO");
				exercicioVO.exercicio = rs.getString("EXERCICIO");
				exercicioVO.carga = rs.getDouble("CARGA");
				exercicioVO.series = rs.getInt("SERIES");
				exercicioVO.repeticoes = rs.getInt("REPETICOES");
				exercicioVO.divisao = rs.getString("DIVISAO");
				exercicioVO.volume = rs.getDouble("VOLUME");

				listExercicioVO.add(exercicioVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listExercicioVO;
	}

	public ArrayList<ExercicioVO> GetExercicioPorDivisao(String divisao) {

		ArrayList<ExercicioVO> listExercicioVO = new ArrayList<ExercicioVO>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * from ATOM.EXERCICIO WHERE DIVISAO=?");
			preparedStatement.setString(1, divisao);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				ExercicioVO exercicioVO = new ExercicioVO();

				exercicioVO.id = rs.getInt("ID");
				exercicioVO.id_aluno = rs.getInt("ID_ALUNO");
				exercicioVO.exercicio = rs.getString("EXERCICIO");
				exercicioVO.carga = rs.getDouble("CARGA");
				exercicioVO.series = rs.getInt("SERIES");
				exercicioVO.repeticoes = rs.getInt("REPETICOES");
				exercicioVO.divisao = rs.getString("DIVISAO");
				exercicioVO.volume = rs.getDouble("VOLUME");

				listExercicioVO.add(exercicioVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listExercicioVO;
	}

	public ArrayList<String> GetDivisoes(int id_aluno) {

		ArrayList<String> listDivisoes = new ArrayList<String>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT DISTINCT DIVISAO from ATOM.EXERCICIO WHERE ID=?");
			preparedStatement.setLong(1, id_aluno);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String divisao = rs.getString("DIVISAO");

				listDivisoes.add(divisao);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listDivisoes;
	}

	public void Salvar(ExercicioVO exercicioVO) throws IOException {

		try {
		
			String sql = "INSERT INTO ATOM.EXERCICIO (ID, ID_ALUNO, EXERCICIO, CARGA, SERIES, REPETICOES, DIVISAO, VOLUME) VALUES(?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

			preparedStatement.setInt(1, exercicioVO.id);
			preparedStatement.setInt(2, exercicioVO.id_aluno);
			preparedStatement.setString(3, exercicioVO.exercicio);
			preparedStatement.setDouble(4, exercicioVO.carga);
			preparedStatement.setInt(5, exercicioVO.series);
			preparedStatement.setInt(6, exercicioVO.repeticoes);
			preparedStatement.setString(7, exercicioVO.divisao);
			preparedStatement.setDouble(8, exercicioVO.volume);

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} // try
	}

	public void Salvar(ArrayList<ExercicioVO> listExercicioVO) throws IOException {
		for (ExercicioVO exercicioVO : listExercicioVO)
			Salvar(exercicioVO);
	}

	public void Alterar(ExercicioVO exercicioVO) throws IOException {

		try {

			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE ATOM.ALUNO ID_ALUNO=?, EXERCICIO=?, CARGA=?, SERIES=?, REPETICOES=?, DIVISAO=?, VOLUME=? WHERE ID=?");

			// Parameters start with 1
			preparedStatement.setInt(1, exercicioVO.id_aluno);
			preparedStatement.setString(2, exercicioVO.exercicio);
			preparedStatement.setDouble(3, exercicioVO.carga);
			preparedStatement.setInt(4, exercicioVO.series);
			preparedStatement.setInt(5, exercicioVO.repeticoes);
			preparedStatement.setString(6, exercicioVO.divisao);
			preparedStatement.setDouble(7, exercicioVO.volume);

			preparedStatement.setInt(8, exercicioVO.id);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Alterar(ArrayList<ExercicioVO> listExercicioVO) throws IOException {
		for (ExercicioVO exercicioVO : listExercicioVO) {
			Alterar(exercicioVO);
		}
	}

}
