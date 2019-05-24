import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class Controller implements Initializable {

	// -----------------------------------------------------------------------------------------//
	// Tela Principal //
	// -----------------------------------------------------------------------------------------//

	public void CarregaPane(String pane) throws IOException {

		MainPane.getChildren().clear();
		Pane newLoadedPane = FXMLLoader.load(getClass().getResource(pane));
		MainPane.getChildren().add(newLoadedPane);
	}

	public void ClearPane() throws IOException {
		MainPane.getChildren().clear();
	}

	@FXML
	private Pane MainPane;
	@FXML
	private Button btnIniciarTreino;
	@FXML
	private Button btnTreinosAbertos;
	@FXML
	private Button btnHistorico;
	@FXML
	private Button btnCadAluno;

	@FXML
	public void btnIniciarTreino_Click(ActionEvent actionEvent) throws IOException {
		CarregaPane("IniciarTreino.fxml");
	}

	@FXML
	public void btnTreinosAbertos_Click(ActionEvent actionEvent) throws IOException {
		CarregaPane("TreinosAbertos.fxml");
	}

	@FXML
	public void btnHistorico_Click(ActionEvent actionEvent) throws IOException {
		CarregaPane("Historico.fxml");
	}

	@FXML
	public void btnCadAluno_Click(ActionEvent actionEvent) throws IOException {
		CarregaPane("CadastroAluno.fxml");
	}

	// -----------------------------------------------------------------------------------------//
	// Iniciar Treino //
	// -----------------------------------------------------------------------------------------//

	ArrayList<ExercicioVO> listAbrirTreinoExercicioVO = new ArrayList<ExercicioVO>();

	public void CarregalstAbrirTreinoExercicios() {
		ExercicioDAO exercicioDAO = new ExercicioDAO();
		listAbrirTreinoExercicioVO = exercicioDAO.GetExercicioPorDivisao(
				lstAbrirTreinoSelecionarDivisao.getSelectionModel().getSelectedItem().toString());
		lstAbrirTreinoExercicios.getItems().clear();
		for (ExercicioVO exercicioVO : listAbrirTreinoExercicioVO) {

			String exercicio = "Exerc�cio: " + exercicioVO.exercicio + " | " + "Carga: " + exercicioVO.carga + " | "
					+ "Series: " + exercicioVO.series + " | " + "Repeti��es: " + exercicioVO.repeticoes + " | "
					+ "Divis�o: " + exercicioVO.divisao + " | Volume : " + exercicioVO.volume;

			lstAbrirTreinoExercicios.getItems().add(exercicio);
		}
	}

	public boolean ValidaCamposEditarExercicio() {

		if (txtAbrirTreinoCarga.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a Carga");
			return false;
		} else
			try {
				Integer.parseInt(txtAbrirTreinoCarga.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Carga Inv�lida");
				return false;
			}

		if (txtAbrirTreinoSeries.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe as S�ries");
			return false;
		} else
			try {
				Integer.parseInt(txtAbrirTreinoSeries.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "S�ries Inv�lidas");
				return false;
			}

		if (txtAbrirTreinoRepet.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe as Repeti��es");
			return false;
		} else
			try {
				Integer.parseInt(txtAbrirTreinoRepet.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Repeti��es Inv�lidas");
				return false;
			}

		if (txtAbrirTreinoRepet.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe as Repeti��es");
			return false;
		} else
			try {
				Integer.parseInt(txtAbrirTreinoRepet.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Repeti��es Inv�lidas");
				return false;
			}

		return true;
	}

	public boolean ValidaCamposNovoTreino() {
		if (lstAbrirTreinoAlunos.getSelectionModel().getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "Selecione o Aluno");
			return false;
		}

		if (lstAbrirTreinoSelecionarDivisao.getSelectionModel().getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "Selecione a Divis�o");
			return false;
		}

		return true;
	}

	public void AbrirTreino() throws IOException {

		TreinoDAO treinoDAO = new TreinoDAO();

		TreinoVO treinoVO = new TreinoVO();
		treinoVO.id = treinoDAO.ProximoId();
		treinoVO.id_aluno = listAlunoVO.get(lstAbrirTreinoAlunos.getSelectionModel().getSelectedIndex()).id;
		treinoVO.data = new Date(new java.util.Date().getTime());
		treinoVO.divisao = lstAbrirTreinoSelecionarDivisao.getSelectionModel().getSelectedItem().toString();
		treinoVO.avaliacao = -1;

		ExercicioDAO exercicioDAO = new ExercicioDAO();

		ArrayList<ExercicioVO> listExercicioVO = exercicioDAO.GetExercicioPorDivisao(treinoVO.divisao);
		for (ExercicioVO exercicioVO : listExercicioVO) {
			treinoVO.volume += exercicioVO.volume;
		}

		treinoDAO.Salvar(treinoVO);
	}

	public void LimpaCamposNovoTreino() {
		listAbrirTreinoExercicioVO.clear();
		listAlunoVO.clear();
		lstAbrirTreinoAlunos.getItems().clear();
		lblAbrirTreinoEnd.setText(null);
		lblAbrirTreinoBairro.setText(null);
		lblAbrirTreinoCidade.setText(null);
		lblAbrirTreinoUf.setText(null);
		lblAbrirTreinoIdade.setText(null);
		lblAbrirTreinoCelular.setText(null);
		lblAbrirTreinoEmail.setText(null);
		lblAbrirTreinoSexo.setText(null);
		lstAbrirTreinoSelecionarDivisao.getItems().clear();
		lstAbrirTreinoExercicios.getItems().clear();

		txtAbrirTreinoCarga.setText(null);
		txtAbrirTreinoSeries.setText(null);
		txtAbrirTreinoRepet.setText(null);
	}

	@FXML
	private Button btnAbrirTreinoCarregarAlunos;
	@FXML
	private ListView<String> lstAbrirTreinoAlunos;
	@FXML
	private Label lblAbrirTreinoNome;
	@FXML
	private Label lblAbrirTreinoIdade;
	@FXML
	private Label lblAbrirTreinoEnd;
	@FXML
	private Label lblAbrirTreinoBairro;
	@FXML
	private Label lblAbrirTreinoCidade;
	@FXML
	private Label lblAbrirTreinoUf;
	@FXML
	private Label lblAbrirTreinoCelular;
	@FXML
	private Label lblAbrirTreinoEmail;
	@FXML
	private Label lblAbrirTreinoSexo;
	@FXML
	private ListView<String> lstAbrirTreinoSelecionarDivisao;
	@FXML
	private ListView<String> lstAbrirTreinoExercicios;
	@FXML
	private TextField txtAbrirTreinoCarga;
	@FXML
	private TextField txtAbrirTreinoSeries;
	@FXML
	private TextField txtAbrirTreinoRepet;
	@FXML
	private Button btnAbrirTreinoEditar;
	@FXML
	private Button btnAbrirTreinoAbrirTreino;
	@FXML
	private Button btnAbrirTreinoCancelar;

	@FXML
	public void btnAbrirTreinoCarregarAlunos_Click(ActionEvent actionEvent) {

		AlunoDAO alunoDAO = new AlunoDAO();

		listAlunoVO = alunoDAO.GetTotosAlunosAtivos();

		lstAbrirTreinoAlunos.getItems().clear();

		for (int i = 0; i < listAlunoVO.size(); i++)
			lstAbrirTreinoAlunos.getItems().add(listAlunoVO.get(i).nome);

	}

	@FXML
	public void lstAbrirTreinoAlunos_Click(MouseEvent mouseEvent) {

		AlunoVO aluno = listAlunoVO.get(lstAbrirTreinoAlunos.getSelectionModel().getSelectedIndex());

		lblAbrirTreinoNome.setText(aluno.nome);
		lblAbrirTreinoIdade.setText(String.valueOf(aluno.idade));
		lblAbrirTreinoEnd.setText(aluno.endereco);
		lblAbrirTreinoBairro.setText(aluno.bairro);
		lblAbrirTreinoCidade.setText(aluno.cidade);
		lblAbrirTreinoUf.setText(aluno.uf);
		lblAbrirTreinoCelular.setText(aluno.celular);
		lblAbrirTreinoEmail.setText(aluno.email);
		lblAbrirTreinoSexo.setText(aluno.sexo);

		ExercicioDAO exercicioDAO = new ExercicioDAO();

		ArrayList<String> listDivisoes = exercicioDAO.GetDivisoes(aluno.id);

		for (String string : listDivisoes)
			lstAbrirTreinoSelecionarDivisao.getItems().add(string);
	}

	@FXML
	public void lstAbrirTreinoSelecionarDivisao_Click(MouseEvent mouseEvent) {
		CarregalstAbrirTreinoExercicios();
	}

	@FXML
	public void lstAbrirTreinoExercicios_Click(MouseEvent mouseEvent) {
		ExercicioVO exercicioVO = listAbrirTreinoExercicioVO
				.get(lstAbrirTreinoExercicios.getSelectionModel().getSelectedIndex());
		txtAbrirTreinoCarga.setText(String.valueOf(exercicioVO.carga));
		txtAbrirTreinoRepet.setText(String.valueOf(exercicioVO.repeticoes));
		txtAbrirTreinoSeries.setText(String.valueOf(exercicioVO.series));
	}

	@FXML
	public void btnAbrirTreinoEditar_Click(ActionEvent actionEvent) throws IOException {
		if (lstAbrirTreinoExercicios.getSelectionModel().getSelectedIndex() != -1) {
			if (ValidaCamposEditarExercicio()) {

				ExercicioVO exercicioVO = listAbrirTreinoExercicioVO
						.get(lstAbrirTreinoExercicios.getSelectionModel().getSelectedIndex());
				exercicioVO.carga = Double.valueOf(txtAbrirTreinoCarga.getText());
				exercicioVO.repeticoes = Integer.valueOf(txtAbrirTreinoRepet.getText());
				exercicioVO.series = Integer.valueOf(txtAbrirTreinoSeries.getText());
				exercicioVO.volume = exercicioVO.carga * exercicioVO.repeticoes * exercicioVO.series;

				ExercicioDAO exercicioDAO = new ExercicioDAO();

				exercicioDAO.Alterar(exercicioVO);

				CarregalstAbrirTreinoExercicios();

				txtAbrirTreinoCarga.setText(null);
				txtAbrirTreinoSeries.setText(null);
				txtAbrirTreinoRepet.setText(null);
			}
		}
	}

	@FXML
	public void btnAbrirTreinoAbrirTreino_Click(ActionEvent actionEvent) throws IOException {

		if (ValidaCamposNovoTreino()) {
			AbrirTreino();
			JOptionPane.showMessageDialog(null, "Treino Aberto com sucesso!");
			LimpaCamposNovoTreino();
		}
	}

	@FXML
	public void btnAbrirTreinoCancelar_Click(ActionEvent actionEvent) {
		LimpaCamposNovoTreino();
	}

	// -----------------------------------------------------------------------------------------//
	// Treinos Abertos //
	// -----------------------------------------------------------------------------------------//

	ArrayList<TreinoVO> listTreinoVO = new ArrayList<TreinoVO>();

	public boolean ValidaCamposTreinosAbertos() {
		if (txtAvaliacao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a PSE");
			return false;
		} else {
			try {
				Integer.parseInt(txtAvaliacao.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "PSE Inv�lida");
				return false;
			}
		}

		if (Integer.valueOf(txtAvaliacao.getText()) < 0 || Integer.valueOf(txtAvaliacao.getText()) > 10) {
			JOptionPane.showMessageDialog(null, "PSE deve ser entre 0 e 10");
			return false;
		}

		return true;
	}

	public void CarregarTreinosAbertos(ArrayList<TreinoVO> listTreinoVO) {

		lstTreinosAbertos.getItems().clear();

		for (int i = 0; i < listTreinoVO.size(); i++) {

			AlunoDAO alunoDAO = new AlunoDAO();

			AlunoVO alunoVO = alunoDAO.GetAlunoPorID(listTreinoVO.get(i).id_aluno);

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

			String treino = "Aluno: " + alunoVO.nome + " | Data: " + formato.format(listTreinoVO.get(i).data)
					+ " | Divis�o: " + listTreinoVO.get(i).divisao + " | PSE: " + listTreinoVO.get(i).avaliacao;

			lstTreinosAbertos.getItems().add(treino);
		}
	}

	@FXML
	private Button btnCarregarTreinosAbertos;
	@FXML
	private ListView<String> lstTreinosAbertos;
	@FXML
	private TextField txtAvaliacao;
	@FXML
	private Button btnAvaliarTreino;
	@FXML
	private Button btnSalvarAvaliacoes;

	@FXML
	public void btnCarregarTreinosAbertos_Click(ActionEvent actionEvent) {

		TreinoDAO treinoDAO = new TreinoDAO();

		listTreinoVO = treinoDAO.GetTotosTreinosAbertos();
		CarregarTreinosAbertos(listTreinoVO);
	}

	@FXML
	public void btnAvaliarTreino_Click(ActionEvent actionEvent) throws IOException {
		if (lstTreinosAbertos.getSelectionModel().getSelectedIndex() != -1) {
			if (ValidaCamposTreinosAbertos()) {
				listTreinoVO.get(lstTreinosAbertos.getSelectionModel().getSelectedIndex()).avaliacao = Integer
						.valueOf(txtAvaliacao.getText());
				CarregarTreinosAbertos(listTreinoVO);
			}
		}
	}

	@FXML
	public void btnSalvarAvaliacoes_Click(ActionEvent actionEvent) throws IOException {

		TreinoDAO treinoDAO = new TreinoDAO();

		treinoDAO.Alterar(listTreinoVO);

		for (TreinoVO treinoVO : listTreinoVO) {

			if (treinoVO.avaliacao != -1) {

				ExercicioDAO exercicioDAO = new ExercicioDAO();

				listExercicioVO = exercicioDAO.GetExercicioPorId_aluno(treinoVO.id_aluno);

				for (ExercicioVO exercicioVO : listExercicioVO) {

					if (treinoVO.divisao.intern() == exercicioVO.divisao.intern()) {

						HistoricoExerciciosVO historicoExerciciosVO = new HistoricoExerciciosVO();

						historicoExerciciosVO.data = treinoVO.data;
						historicoExerciciosVO.id_aluno = treinoVO.id_aluno;
						historicoExerciciosVO.exercicio = exercicioVO.exercicio;
						historicoExerciciosVO.carga = exercicioVO.carga;
						historicoExerciciosVO.series = exercicioVO.series;
						historicoExerciciosVO.repeticoes = exercicioVO.repeticoes;
						historicoExerciciosVO.divisao = exercicioVO.divisao;
						historicoExerciciosVO.volume = exercicioVO.volume;
						historicoExerciciosVO.avaliacao = treinoVO.avaliacao;

						HistoricoExerciciosDAO historicoExerciciosDAO = new HistoricoExerciciosDAO();
						historicoExerciciosDAO.Salvar(historicoExerciciosVO);
					}
				}
			}
		}

		JOptionPane.showMessageDialog(null, "Avalia��es salvas com sucesso");
		listTreinoVO = treinoDAO.GetTotosTreinosAbertos();
		CarregarTreinosAbertos(listTreinoVO);
	}

	// -----------------------------------------------------------------------------------------//
	// Hist�rico de Treino //
	// -----------------------------------------------------------------------------------------//

	@FXML
	private Button btnHistPesquisarAlunos;
	@FXML
	private ListView<String> lstHistAlunos;
	@FXML
	private ListView<String> lstHistTreino;
	@FXML
	private Label lblHistVolumeTotal;

	@FXML
	public void btnHistPesquisarAlunos_Click(ActionEvent actionEvent) {
		AlunoDAO alunoDAO = new AlunoDAO();
		listAlunoVO = alunoDAO.GetTotosAlunosAtivos();

		for (AlunoVO alunoVO : listAlunoVO) {
			lstHistAlunos.getItems().add(alunoVO.nome);
		}
	}

	@FXML
	public void lstHistAlunos_Click(MouseEvent mouseEvent) {

		AlunoVO alunoVO = listAlunoVO.get(lstHistAlunos.getSelectionModel().getSelectedIndex());
		TreinoDAO treinoDAO = new TreinoDAO();
		ArrayList<TreinoVO> listTreinoVO = treinoDAO.GetTreinoPorID_aluno(alunoVO.id);

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		double volumeTotal = 0;

		for (TreinoVO treinoVO : listTreinoVO) {
			if (treinoVO.avaliacao != -1) {

				String dados = formato.format(treinoVO.data) + " | Divis�o: " + treinoVO.divisao + " | " + "Volume: "
						+ treinoVO.volume + " | " + "PSE do Aluno: " + treinoVO.avaliacao;

				volumeTotal += treinoVO.volume;

				lstHistTreino.getItems().add(dados);
			}
		}

		lblHistVolumeTotal.setText(String.valueOf(volumeTotal));
	}

	// -----------------------------------------------------------------------------------------//
	// CadastroAluno //
	// -----------------------------------------------------------------------------------------//

	// M�todos Auxiliares
	// //--------------------------------------------------------------------//

	ArrayList<AlunoVO> listAlunoVO = new ArrayList<AlunoVO>();

	ArrayList<ExercicioVO> listExercicioVO = new ArrayList<ExercicioVO>();

	public void CarregaAlunos() {
		AlunoDAO alunoDAO = new AlunoDAO();
		listAlunoVO = alunoDAO.GetTotosAlunosAtivos();
		lstCadAlunoPesquisa.getItems().clear();
		for (AlunoVO alunoVO : listAlunoVO) {
			lstCadAlunoPesquisa.getItems().add(alunoVO.nome);
		}
	}

	public boolean ValidaCamposCadAluno() {

		if (txtCadAlunoNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Nome");
			return false;
		}

		if (txtCadAlunoIdade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a Idade");
			return false;
		} else
			try {
				Integer.parseInt(txtCadAlunoIdade.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Idade Inv�lida");
				return false;
			}

		if (txtCadAlunoEnd.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Endere�o");
			return false;
		}

		if (txtCadAlunoUf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a UF");
			return false;
		}

		if (txtCadAlunoBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Bairro");
			return false;
		}

		if (txtCadAlunoCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a Cidade");
			return false;
		}

		if (txtCadAlunoCelular.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Celular");
			return false;
		}

		if (txtCadAlunoEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o E-mail");
			return false;
		}

		if (!rdbCadAlunoMasculino.isSelected() && !rdbCadAlunoFeminino.isSelected()) {
			JOptionPane.showMessageDialog(null, "Informe o Sexo");
			return false;
		}

//		if (lstTreinoExercicios.getItems().size() == 0) {
//			JOptionPane.showMessageDialog(null, "Informe o treino");
//			return false;
//		}

		return true;
	}

	public void LimpaCamposCadAluno() {

		txtCadAlunoNome.setText(null);
		txtCadAlunoIdade.setText(null);
		txtCadAlunoEnd.setText(null);
		txtCadAlunoBairro.setText(null);
		txtCadAlunoCidade.setText(null);
		txtCadAlunoUf.setText(null);
		txtCadAlunoCelular.setText(null);
		txtCadAlunoEmail.setText(null);
		rdbCadAlunoMasculino.setSelected(false);
		rdbCadAlunoFeminino.setSelected(false);

		txtTreinoExerc.setText(null);
		txtTreinoCarga.setText(null);
		txtTreinoSeries.setText(null);
		txtTreinoRepet.setText(null);
		txtTreinoDivisao.setText(null);
		lstTreinoExercicios.getItems().clear();
	}

	public boolean ValidaCamposExercicio() {

		if (txtTreinoExerc.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Exerc�cio");
			return false;
		}

		if (txtTreinoCarga.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a Carga");
			return false;
		} else {
			try {
				Integer.parseInt(txtTreinoCarga.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Carga Inv�lida");
				return false;
			}
		}

		if (txtTreinoSeries.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe as S�ries");
			return false;
		} else {
			try {
				Integer.parseInt(txtTreinoSeries.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Valor S�ries Inv�lido");
				return false;
			}
		}

		if (txtTreinoRepet.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe as Repeti��es");
			return false;
		} else {
			try {
				Integer.parseInt(txtTreinoRepet.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Valor Repeti��es Inv�lido");
				return false;
			}
		}

		if (txtTreinoDivisao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a Divis�o");
			return false;
		}

		return true;
	}

	public void PreencheDadosGerais(AlunoVO alunoVO) {

		txtCadAlunoNome.setText(alunoVO.nome);
		txtCadAlunoIdade.setText(String.valueOf(alunoVO.idade));
		txtCadAlunoEnd.setText(alunoVO.endereco);
		txtCadAlunoBairro.setText(alunoVO.bairro);
		txtCadAlunoCidade.setText(alunoVO.cidade);
		txtCadAlunoUf.setText(alunoVO.uf);
		txtCadAlunoCelular.setText(alunoVO.celular);
		txtCadAlunoEmail.setText(alunoVO.email);
		if (alunoVO.sexo.intern() == "Masculino") {
			rdbCadAlunoMasculino.setSelected(true);
			rdbCadAlunoFeminino.setSelected(false);
		} else if (alunoVO.sexo.intern() == "Feminino") {
			rdbCadAlunoFeminino.setSelected(true);
			rdbCadAlunoMasculino.setSelected(false);
		}
	}

	public void PreencheTreino(int id_aluno) {
		ExercicioDAO exercicioDAO = new ExercicioDAO();
		listExercicioVO = exercicioDAO.GetExercicioPorId_aluno(id_aluno);

		for (ExercicioVO exercicioVO : listExercicioVO) {

			String exercicio = "Exerc�cio: " + exercicioVO.exercicio + " | " + "Carga: " + exercicioVO.carga + " | "
					+ "Series: " + exercicioVO.series + " | " + "Repeti��es: " + exercicioVO.repeticoes + " | "
					+ "Divis�o: " + exercicioVO.divisao + " | Volume : " + exercicioVO.volume;

			lstTreinoExercicios.getItems().add(exercicio);
		}
	}

	// Componentes Pricipais
	// //------------------------------------------------------------------//

	@FXML
	private Button btnCadAlunoPesquisar;
	@FXML
	private ListView<String> lstCadAlunoPesquisa;
	@FXML
	private Button btnCadAlunoAbrirCadastro;
	@FXML
	private Button btnCadAlunoExcluir;
	@FXML
	private Button btnCadAlunoSalvar;
	@FXML
	private Button btnCadAlunoCancelar;

	@FXML
	public void btnCadAlunoPesquisar_Click(ActionEvent actionEvent) {
		CarregaAlunos();
	}

	@FXML
	public void btnCadAlunoAbrirCadastro_Click(ActionEvent actionEvent) {
		PreencheDadosGerais(listAlunoVO.get(lstCadAlunoPesquisa.getSelectionModel().getSelectedIndex()));
		PreencheTreino(listAlunoVO.get(lstCadAlunoPesquisa.getSelectionModel().getSelectedIndex()).id);
	}

	@FXML
	public void btnCadAlunoExcluir_Click(ActionEvent actionEvent) throws IOException {
		if (lstCadAlunoPesquisa.getSelectionModel().getSelectedIndex() != -1) {
			AlunoDAO alunoDAO = new AlunoDAO();
			alunoDAO.Excluir(listAlunoVO.get(lstCadAlunoPesquisa.getSelectionModel().getSelectedIndex()).id);
			CarregaAlunos();
			LimpaCamposCadAluno();
		}
	}

	@FXML
	public void btnCadAlunoSalvar_Click(ActionEvent actionEvent) throws IOException {

		AlunoVO alunoVO = new AlunoVO();

		if (ValidaCamposCadAluno()) {

			alunoVO.nome = txtCadAlunoNome.getText();
			alunoVO.idade = Integer.valueOf(txtCadAlunoIdade.getText());
			alunoVO.endereco = txtCadAlunoEnd.getText();
			alunoVO.bairro = txtCadAlunoBairro.getText();
			alunoVO.cidade = txtCadAlunoCidade.getText();
			alunoVO.uf = txtCadAlunoUf.getText();
			alunoVO.celular = txtCadAlunoCelular.getText();
			alunoVO.email = txtCadAlunoEmail.getText();
			if (rdbCadAlunoFeminino.isSelected())
				alunoVO.sexo = "Feminino";
			else if (rdbCadAlunoMasculino.isSelected())
				alunoVO.sexo = "Masculino";
			alunoVO.ativo = Boolean.valueOf(true);

			ExercicioDAO exercicioDAO = new ExercicioDAO();
			AlunoDAO alunoDAO = new AlunoDAO();

			if (lstCadAlunoPesquisa.getSelectionModel().getSelectedIndex() == -1) {
				try {
					alunoVO.id = alunoDAO.ProximoId();
					alunoDAO.Salvar(alunoVO);
					if (listExercicioVO.size() > 0)
						exercicioDAO.Salvar(listExercicioVO);
					JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");
					LimpaCamposCadAluno();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao salvar: " + e.getMessage());
				}
			} else {
				try {
					alunoVO.id = listAlunoVO.get(lstCadAlunoPesquisa.getSelectionModel().getSelectedIndex()).id;
					alunoDAO.Alterar(alunoVO);
					exercicioDAO.Alterar(listExercicioVO);
					JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");
					LimpaCamposCadAluno();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao alterar: " + e.getMessage());
				}
			}
		}
	}

	@FXML
	public void btnCadAlunoCancelar_Click(ActionEvent actionEvent) {
		LimpaCamposCadAluno();
	}

	// Tab Dados Gerais
	// //-----------------------------------------------------------------------//

	@FXML
	private TextField txtCadAlunoNome;
	@FXML
	private TextField txtCadAlunoIdade;
	@FXML
	private TextField txtCadAlunoEnd;
	@FXML
	private TextField txtCadAlunoBairro;
	@FXML
	private TextField txtCadAlunoCidade;
	@FXML
	private TextField txtCadAlunoUf;
	@FXML
	private TextField txtCadAlunoCelular;
	@FXML
	private TextField txtCadAlunoEmail;
	@FXML
	private RadioButton rdbCadAlunoMasculino;
	@FXML
	private RadioButton rdbCadAlunoFeminino;

	@FXML
	public void rdbCadAlunoMasculino_Click(ActionEvent actionEvent) {
		rdbCadAlunoFeminino.setSelected(false);
	}

	@FXML
	public void rdbCadAlunoFeminino_Click(ActionEvent actionEvent) {
		rdbCadAlunoMasculino.setSelected(false);
	}

	// Tab Treino
	// //-----------------------------------------------------------------------------//

	@FXML
	private TextField txtTreinoExerc;
	@FXML
	private TextField txtTreinoCarga;
	@FXML
	private TextField txtTreinoSeries;
	@FXML
	private TextField txtTreinoRepet;
	@FXML
	private TextField txtTreinoDivisao;
	@FXML
	private Button btnTreinoInserir;
	@FXML
	private ListView<String> lstTreinoExercicios;
	@FXML
	private Button btnTreinoRemover;

	@FXML
	public void btnTreinoInserir_Click(ActionEvent actionEvent) {

		AlunoDAO alunoDAO = new AlunoDAO();

		if (ValidaCamposExercicio()) {

			ExercicioVO exercicioVO = new ExercicioVO();
			if (lstCadAlunoPesquisa.getSelectionModel().getSelectedIndex() == -1)
				exercicioVO.id_aluno = alunoDAO.ProximoId();
			else
				exercicioVO.id_aluno = listAlunoVO.get(lstCadAlunoPesquisa.getSelectionModel().getSelectedIndex()).id;
			exercicioVO.exercicio = txtTreinoExerc.getText();
			exercicioVO.carga = Integer.valueOf(txtTreinoCarga.getText());
			exercicioVO.series = Integer.valueOf(txtTreinoSeries.getText());
			exercicioVO.repeticoes = Integer.valueOf(txtTreinoRepet.getText());
			exercicioVO.divisao = txtTreinoDivisao.getText();
			exercicioVO.volume = exercicioVO.carga * exercicioVO.repeticoes * exercicioVO.series;

			String dado = "Exerc�cio: " + exercicioVO.exercicio + " | " + "Carga: " + exercicioVO.carga + " | "
					+ "Series: " + exercicioVO.series + " | " + "Repeti��es: " + exercicioVO.repeticoes + " | "
					+ "Divis�o: " + exercicioVO.divisao + " | Volume: " + exercicioVO.volume;

			lstTreinoExercicios.getItems().add(dado);
			listExercicioVO.add(exercicioVO);

			txtTreinoExerc.setText(null);
			txtTreinoCarga.setText(null);
			txtTreinoSeries.setText(null);
			txtTreinoRepet.setText(null);
			txtTreinoDivisao.setText(null);
		}
	}

	@FXML
	public void btnTreinoRemover_Click(ActionEvent actionEvent) {
		listExercicioVO.remove(lstTreinoExercicios.getSelectionModel().getSelectedIndex());
		lstTreinoExercicios.getItems().remove(lstTreinoExercicios.getSelectionModel().getSelectedIndex());
	}

	// -----------------------------------------------------------------------------------------//

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// TODO Auto-generated method stub

	}
}
