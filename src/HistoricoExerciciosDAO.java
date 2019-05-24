import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HistoricoExerciciosDAO {

	private Connection connection;

	public HistoricoExerciciosDAO() {

		try {
			this.connection = DBConnect.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // HistoricoExerciciosDAO

	public int ProximoId() {

		int prox_id = 0;

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT COUNT(*) AS ID FROM ATOM.HIST_EXERCICIO");

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next())
				prox_id = rs.getInt("ID") + 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return prox_id;
	}

	public ArrayList<HistoricoExerciciosVO> GetTotoHistoricoExercicios() {

		ArrayList<HistoricoExerciciosVO> listHistoricoExerciciosVO = new ArrayList<HistoricoExerciciosVO>();

		try {

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM ATOM.HIST_EXERCICIO");

			while (rs.next()) {

				HistoricoExerciciosVO historicoExerciciosVO = new HistoricoExerciciosVO();

				historicoExerciciosVO.data = rs.getDate("DATA");
				historicoExerciciosVO.id_aluno = rs.getInt("ID_ALUNO");
				historicoExerciciosVO.exercicio = rs.getString("EXERCICIO");
				historicoExerciciosVO.carga = rs.getDouble("CARGA");
				historicoExerciciosVO.series = rs.getInt("SERIES");
				historicoExerciciosVO.repeticoes = rs.getInt("REPETICOES");
				historicoExerciciosVO.divisao = rs.getString("DIVISAO");
				historicoExerciciosVO.volume = rs.getDouble("VOLUME");
				historicoExerciciosVO.avaliacao = rs.getInt("AVALIACAO");

				listHistoricoExerciciosVO.add(historicoExerciciosVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listHistoricoExerciciosVO;
	}

	public ArrayList<HistoricoExerciciosVO> GetHistoricoExerciciosVOPorId_aluno(int id_aluno) {

		ArrayList<HistoricoExerciciosVO> listHistoricoExerciciosVO = new ArrayList<HistoricoExerciciosVO>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM ATOM.HIST_EXERCICIO WHERE ID_ALUNO=?");

			preparedStatement.setLong(1, id_aluno);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				HistoricoExerciciosVO historicoExerciciosVO = new HistoricoExerciciosVO();

				historicoExerciciosVO.data = rs.getDate("DATA");
				historicoExerciciosVO.id_aluno = rs.getInt("ID_ALUNO");
				historicoExerciciosVO.exercicio = rs.getString("EXERCICIO");
				historicoExerciciosVO.carga = rs.getDouble("CARGA");
				historicoExerciciosVO.series = rs.getInt("SERIES");
				historicoExerciciosVO.repeticoes = rs.getInt("REPETICOES");
				historicoExerciciosVO.divisao = rs.getString("DIVISAO");
				historicoExerciciosVO.volume = rs.getDouble("VOLUME");
				historicoExerciciosVO.avaliacao = rs.getInt("AVALIACAO");

				listHistoricoExerciciosVO.add(historicoExerciciosVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listHistoricoExerciciosVO;
	}

	public void Salvar(HistoricoExerciciosVO historicoExerciciosVO) {
		try {

			String sql = "INSERT INTO ATOM.HIST_EXERCICIO (DATA, ID_ALUNO, EXERCICIO, CARGA, SERIES, REPETICOES, DIVISAO, VOLUME, AVALIACAO) VALUES(?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

			preparedStatement.setDate(1, historicoExerciciosVO.data);
			preparedStatement.setInt(2, historicoExerciciosVO.id_aluno);
			preparedStatement.setString(3, historicoExerciciosVO.exercicio);
			preparedStatement.setDouble(4, historicoExerciciosVO.carga);
			preparedStatement.setInt(5, historicoExerciciosVO.series);
			preparedStatement.setInt(6, historicoExerciciosVO.repeticoes);
			preparedStatement.setString(7, historicoExerciciosVO.divisao);
			preparedStatement.setDouble(8, historicoExerciciosVO.volume);
			preparedStatement.setInt(9, historicoExerciciosVO.avaliacao);

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} // try
	}

	public void Salvar(ArrayList<HistoricoExerciciosVO> listHistoricoExerciciosVO) {
		for (HistoricoExerciciosVO historicoExerciciosVO : listHistoricoExerciciosVO)
			Salvar(historicoExerciciosVO);
	}
}
