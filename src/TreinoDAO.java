import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TreinoDAO {

	private Connection connection;

	public TreinoDAO() {

		try {
			this.connection = DBConnect.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // TreinoDAO

	public int ProximoId() {

		int prox_id = 0;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS ID FROM ATOM.TREINO");

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next())
				prox_id = rs.getInt("ID") + 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return prox_id;
	}

	public ArrayList<TreinoVO> GetTotosTreinosAbertos() {

		ArrayList<TreinoVO> listTreinoVO = new ArrayList<TreinoVO>();

		try {

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM ATOM.TREINO WHERE AVALIACAO=-1");

			while (rs.next()) {

				TreinoVO treinoVO = new TreinoVO();

				treinoVO.id = rs.getInt("ID");
				treinoVO.id_aluno = rs.getInt("ID_ALUNO");
				treinoVO.data = rs.getDate("DATA");
				treinoVO.divisao = rs.getString("DIVISAO");
				treinoVO.avaliacao = rs.getInt("AVALIACAO");
				treinoVO.volume = rs.getDouble("VOLUME");

				listTreinoVO.add(treinoVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listTreinoVO;
	}

	public ArrayList<TreinoVO> GetTotosTreinos() {

		ArrayList<TreinoVO> listTreinoVO = new ArrayList<TreinoVO>();

		try {

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM ATOM.TREINO");

			while (rs.next()) {

				TreinoVO treinoVO = new TreinoVO();

				treinoVO.id = rs.getInt("ID");
				treinoVO.id_aluno = rs.getInt("ID_ALUNO");
				treinoVO.data = rs.getDate("DATA");
				treinoVO.divisao = rs.getString("DIVISAO");
				treinoVO.avaliacao = rs.getInt("AVALIACAO");
				treinoVO.volume = rs.getDouble("VOLUME");

				listTreinoVO.add(treinoVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listTreinoVO;
	}

	public TreinoVO GetTreinoPorID(int id) {

		TreinoVO treinoVO = new TreinoVO();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from ATOM.TREINO WHERE ID=?");

			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				treinoVO.id = rs.getInt("ID");
				treinoVO.id_aluno = rs.getInt("ID_ALUNO");
				treinoVO.data = rs.getDate("DATA");
				treinoVO.divisao = rs.getString("DIVISAO");
				treinoVO.avaliacao = rs.getInt("AVALIACAO");
				treinoVO.volume = rs.getDouble("VOLUME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return treinoVO;
	}

	public ArrayList<TreinoVO> GetTreinoPorID_aluno(int id_aluno) {

		ArrayList<TreinoVO> listTreinoVO = new ArrayList<TreinoVO>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * from ATOM.TREINO WHERE ID_ALUNO=?");
			preparedStatement.setLong(1, id_aluno);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				TreinoVO treinoVO = new TreinoVO();

				treinoVO.id = rs.getInt("ID");
				treinoVO.id_aluno = rs.getInt("ID_ALUNO");
				treinoVO.data = rs.getDate("DATA");
				treinoVO.divisao = rs.getString("DIVISAO");
				treinoVO.avaliacao = rs.getInt("AVALIACAO");
				treinoVO.volume = rs.getDouble("VOLUME");

				listTreinoVO.add(treinoVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listTreinoVO;
	}

	public void Salvar(TreinoVO treinoVO) throws IOException {

		try {

			String sql = "INSERT INTO ATOM.TREINO (ID, ID_ALUNO, DATA, DIVISAO, AVALIACAO, VOLUME) VALUES(?,?,?,?,?,?)";

			PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

			preparedStatement.setInt(1, treinoVO.id);
			preparedStatement.setInt(2, treinoVO.id_aluno);
			preparedStatement.setDate(3, treinoVO.data);
			preparedStatement.setString(4, treinoVO.divisao);
			preparedStatement.setInt(5, treinoVO.avaliacao);
			preparedStatement.setDouble(6, treinoVO.volume);

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} // try
	}

	public void Salvar(ArrayList<TreinoVO> listTreinoVO) throws IOException {
		for (TreinoVO treinoVO : listTreinoVO)
			Salvar(treinoVO);
	}

	public void Alterar(TreinoVO treinoVO) throws IOException {

		try {

			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE ATOM.TREINO ID_ALUNO=?, DATA=?, DIVISAO=?, AVALIACAO=?, VOLUME=? WHERE ID=?");

			// Parameters start with 1
			preparedStatement.setInt(1, treinoVO.id_aluno);
			preparedStatement.setDate(2, treinoVO.data);
			preparedStatement.setString(3, treinoVO.divisao);
			preparedStatement.setInt(4, treinoVO.avaliacao);
			preparedStatement.setDouble(5, treinoVO.volume);

			preparedStatement.setInt(6, treinoVO.id);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Alterar(ArrayList<TreinoVO> listTreinoVO) throws IOException {
		for (TreinoVO treinoVO : listTreinoVO)
			Alterar(treinoVO);
	}
}
