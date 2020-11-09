package GUI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

import Models.Medicao;
import Models.Pais;
import Controllers.RankingController;
import Controllers.PaisController;
import Controllers.MedicaoController;
import Controllers.FileController;

public class GUI extends JFrame {
	private JPanel buttonBar, dateBar, radioBar;
	private JButton updateButton, storeButton, restoreButton, exportButton, searchButton;
	private JLabel fromLabel, toLabel, radiusLabel;
	private JFormattedTextField fromDateField, toDateField, radiusField;
	private JRadioButton absoluteRadio, growthRadio, mortalidadeRadio, proximosRadio;
	private JTable tableConfirmados, tableRecuperados, tableMortos, tableMortalidade,
			tableProximos;
	private JScrollPane scrollConfirmados, scrollRecuperados, scrollMortos, scrollMortalidade,
			scrollProximos;
	private JTabbedPane tabs;
	private DataTableModel tableModelConfirmados, tableModelRecuperados, tableModelMortos,
			tableModelMortalidade, tableModelProximos;

	private static final String[] header1 = {"Posição", "País", "Casos"};
	private static final String[] header2 = {"Posição", "País", "Taxa (%)"};

	private class DataTableModel extends AbstractTableModel {
		private List<Medicao> meds;
		private String[] header;

		public DataTableModel(String[] header) {
			meds = new ArrayList();
			this.header = header;
		}

		public List<Medicao> getMeds() {
			return meds;
		}

		public void setMeds(List<Medicao> meds) {
			this.meds = meds;
			fireTableDataChanged();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public int getRowCount() {
			return meds.size();
		}

		@Override
		public String getColumnName(int col) {
			return header[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			switch (col) {
				case 0:
					return row + 1;
				case 1:
					return meds.get(row).getPais().getNome();
				case 2:
					return meds.get(row).getCasos();
			}
			return null;
		}

		@Override
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}

	private void initElements() {
		// JPanel
		buttonBar = new JPanel();
		dateBar = new JPanel();
		radioBar = new JPanel();

		// JButton
		updateButton = new JButton("Atualizar");
		storeButton = new JButton("Salvar consulta");
		restoreButton = new JButton("Restaurar consulta");
		exportButton = new JButton("Exportar TSV");
		searchButton = new JButton("Pesquisar");

		// JRadioButton
		absoluteRadio = new JRadioButton("Absoluto", true);
		growthRadio = new JRadioButton("Crescimento");
		mortalidadeRadio = new JRadioButton("Mortalidade");
		proximosRadio = new JRadioButton("Próximos");

		// JLabel
		fromLabel = new JLabel("De: ");
		toLabel = new JLabel("Até: ");
		radiusLabel = new JLabel("Raio: ");

		// JFormattedTextField
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		fromDateField = new JFormattedTextField(new DateFormatter(dateFormat));
		toDateField = new JFormattedTextField(new DateFormatter(dateFormat));

		NumberFormat numberFormat = DecimalFormat.getInstance();
		numberFormat.setGroupingUsed(false);
		NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
		numberFormatter.setValueClass(Float.class);
		radiusField = new JFormattedTextField(numberFormatter);

		// DataTableModel
		tableModelConfirmados = new DataTableModel(header1);
		tableModelRecuperados = new DataTableModel(header1);
		tableModelMortos = new DataTableModel(header1);
		tableModelMortalidade = new DataTableModel(header2);
		tableModelProximos = new DataTableModel(header2);

		// JTable
		tableConfirmados = new JTable(tableModelConfirmados);
		tableRecuperados = new JTable(tableModelRecuperados);
		tableMortos = new JTable(tableModelMortos);
		tableMortalidade = new JTable(tableModelMortalidade);
		tableProximos = new JTable(tableModelProximos);

		// JScrollPane
		scrollConfirmados = new JScrollPane(tableConfirmados);
		scrollRecuperados = new JScrollPane(tableRecuperados);
		scrollMortos = new JScrollPane(tableMortos);
		scrollMortalidade = new JScrollPane(tableMortalidade);
		scrollProximos = new JScrollPane(tableProximos);

		tabs = new JTabbedPane();
	}

	private void configElements() {
		// buttonBar
		buttonBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonBar.add(updateButton);
		buttonBar.add(storeButton);
		buttonBar.add(restoreButton);
		buttonBar.add(exportButton);
		buttonBar.add(searchButton);

		// dateBar
		dateBar.setLayout(new BoxLayout(dateBar, BoxLayout.X_AXIS));
		dateBar.add(fromLabel);
		dateBar.add(fromDateField);
		dateBar.add(toLabel);
		dateBar.add(toDateField);
		dateBar.add(radiusLabel);
		dateBar.add(radiusField);

		// radioBar
		radioBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioBar.add(absoluteRadio);
		radioBar.add(growthRadio);
		radioBar.add(mortalidadeRadio);
		radioBar.add(proximosRadio);
		ButtonGroup group = new ButtonGroup();
		group.add(absoluteRadio);
		group.add(growthRadio);
		group.add(mortalidadeRadio);
		group.add(proximosRadio);

		// tables
		tableConfirmados.setFocusable(false);
		tableConfirmados.setRowSelectionAllowed(false);
		tableRecuperados.setFocusable(false);
		tableRecuperados.setRowSelectionAllowed(false);
		tableMortos.setFocusable(false);
		tableMortos.setRowSelectionAllowed(false);
		tableMortalidade.setFocusable(false);
		tableMortalidade.setRowSelectionAllowed(false);
		tableProximos.setFocusable(false);
		tableProximos.setRowSelectionAllowed(false);

		// tabs
		tabs.add("Confirmados", scrollConfirmados);
		tabs.add("Recuperados", scrollRecuperados);
		tabs.add("Mortos", scrollMortos);
		tabs.add("Mortalidade", scrollMortalidade);
		tabs.add("Próximos", scrollProximos);

		// main JFrame
		add(buttonBar);
		add(dateBar);
		add(radioBar);
		add(tabs);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setVisible(true);
	}

	private void configListeners() {
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateData();
			}
		});

		storeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String category;
				if (absoluteRadio.isSelected()) {
					category = "absoluto";
				} else if (growthRadio.isSelected()) {
					category = "crescimento";
				} else if (mortalidadeRadio.isSelected()) {
					category = "mortalidade";
				} else {
					category = "próximos";
				}

				String path = pickFile(true);
				float radius = radiusField.getText().isEmpty() ? 0 : (float) radiusField.getValue();

				if (path != null && !fromDateField.getText().isEmpty() && !toDateField.getText().isEmpty() &&
				    (tabs.getSelectedIndex() != 4 || !radiusField.getText().isEmpty())) {
					RankingController.salvarPesquisa(fromDateField.getText(), toDateField.getText(),
													 category, radius, path);
					JOptionPane.showMessageDialog(GUI.this, "Busca salva");
				} else {
					JOptionPane.showMessageDialog(GUI.this, "Todos os campos devem estar preenchidos para salvar uma busca");
				}
			}
		});

		restoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = pickFile(false);
				if (path != null) {
					List<HashMap<String, String>> list = RankingController.pegarPesquisa(path);
					HashMap<String, String> search = list.get(0);

					fromDateField.setText(search.get("dataInicio"));
					toDateField.setText(search.get("dataFinal"));

					String category = search.get("categoria");
					if (category.equals("próximos")) {
						radiusField.setText(search.get("raio"));
						proximosRadio.setSelected(true);
					} else if (category.equals("absoluto")) {
						absoluteRadio.setSelected(true);
					} else if (category.equals("crescimento")) {
						growthRadio.setSelected(true);
					} else if (category.equals("mortalidade")) {
						mortalidadeRadio.setSelected(true);
					}

					search();
				}
			}
		});

		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DataTableModel model = modelByIndex(tabs.getSelectedIndex());
				List<Medicao> meds = model.getMeds();
				if (meds.isEmpty()) {
					JOptionPane.showMessageDialog(GUI.this, "Você precisa fazer uma pesquisa antes de exportar um TSV");
					return;
				}

				String path = pickFile(true);
				if (path != null) {
					FileController.escreverArquivoTsv(path, meds);
					JOptionPane.showMessageDialog(GUI.this, String.format("Dados exportados para %s.tsv", path));
				}
			}
		});

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!radiusField.getText().isEmpty()){
					radiusField.setText(Float.toString(Math.abs((float) radiusField.getValue())));
				}
				if (mortalidadeRadio.isSelected() || proximosRadio.isSelected()) {
					tabs.setEnabledAt(0, false);
					tabs.setEnabledAt(1, false);
					tabs.setEnabledAt(2, false);

					if (mortalidadeRadio.isSelected()) {
						tabs.setSelectedIndex(3);
						tabs.setEnabledAt(3, true);
						tabs.setEnabledAt(4, false);
					}

					if (proximosRadio.isSelected()) {
						tabs.setSelectedIndex(4);
						tabs.setEnabledAt(4, true);
						tabs.setEnabledAt(3, false);
					}
				} else {
					if (tabs.getSelectedIndex() > 2) {
						tabs.setSelectedIndex(0);
					}
					tabs.setEnabledAt(0, true);
					tabs.setEnabledAt(1, true);
					tabs.setEnabledAt(2, true);
					tabs.setEnabledAt(3, false);
					tabs.setEnabledAt(4, false);
				}

				search();
			}
		});

		proximosRadio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				radiusField.setEnabled(proximosRadio.isSelected());
			}
		});
	}

	private void setInitialState() {
		radiusField.setEnabled(false);
		tabs.setEnabledAt(3, false);
		tabs.setEnabledAt(4, false);
	}

	private void updateData() {
		String msg = "Isso pode levar alguns minutos. Tem certeza?";
		int answer = JOptionPane.showConfirmDialog(this, msg, "Atualizar", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			JOptionPane loading = new JOptionPane();
			loading.setMessageType(JOptionPane.PLAIN_MESSAGE);
			loading.setMessage("Aguarde, os dados estão sendo atualizados");
			loading.setOptionType(JOptionPane.DEFAULT_OPTION);
			loading.setOptions(new Object[] {});
			JDialog dialog = loading.createDialog(this, "Atualizando...");

			SwingWorker worker = new SwingWorker() {
				@Override
				public String doInBackground() throws Exception {
					System.out.println("Atualizando países...");
					PaisController.getPaisesApi();
					System.out.println("Atualizando dados...");
					MedicaoController.getDadosApi();
					System.out.println("Atualizado com sucesso.");
					return null;
				}

				@Override
				public void done() {
					dialog.dispose();
				}
			};

			worker.execute();
			dialog.setVisible(true);
		}
	}

	private void search() {
		int tabIndex = tabs.getSelectedIndex();
		String fromDate = fromDateField.getText();
		String toDate = toDateField.getText();

		if (fromDate.isEmpty() || toDate.isEmpty()) {
			return;
		}

		if (absoluteRadio.isSelected() || growthRadio.isSelected()) {
			List<Medicao> confirmados;
			List<Medicao> mortos;
			List<Medicao> recuperados;

			if (absoluteRadio.isSelected()) {
				confirmados = RankingController.rankingGeral(fromDate, toDate, "confirmados");
				mortos = RankingController.rankingGeral(fromDate, toDate, "mortos");
				recuperados = RankingController.rankingGeral(fromDate, toDate, "recuperados");
			} else {
				confirmados = RankingController.rankingCrescimento(fromDate, toDate, "confirmados");
				mortos = RankingController.rankingCrescimento(fromDate, toDate, "mortos");
				recuperados = RankingController.rankingCrescimento(fromDate, toDate, "recuperados");
			}

			tableModelConfirmados.setMeds(confirmados);
			tableModelMortos.setMeds(mortos);
			tableModelRecuperados.setMeds(recuperados);
		} else if (mortalidadeRadio.isSelected()) {
			List<Medicao> mortalidade = RankingController.rankingMortalidade(fromDate, toDate);
			tableModelMortalidade.setMeds(mortalidade);
		} else {
			List<Medicao> proximos = PaisController.comparacaoRaio(fromDate, toDate, (float) radiusField.getValue());
			tableModelProximos.setMeds(proximos);
		}
	}

	private DataTableModel modelByIndex(int index) {
		switch (index) {
			case 0:
				return tableModelConfirmados;
			case 1:
				return tableModelRecuperados;
			case 2:
				return tableModelMortos;
			case 3:
				return tableModelMortalidade;
			case 4:
				return tableModelProximos;
		}
		return null;
	}

	private String pickFile(boolean newFile) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Serialisáveis", "ser");
		chooser.setFileFilter(filter);

		int response = newFile ? chooser.showSaveDialog(this) : chooser.showOpenDialog(this);
		if (response == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (newFile && file.exists()) {
				JOptionPane.showMessageDialog(this, "Um arquivo com esse nome já existe");
				return null;
			} else if (!newFile && !file.exists()) {
				JOptionPane.showMessageDialog(this, "Esse arquivo não existe");
				return null;
			}
			return file.getPath().substring(0, file.getPath().length() - 4);
		}
		return null;
	}

	public GUI() {
		super("covidJava");
		initElements();
		configElements();
		configListeners();
		setInitialState();
	}

	public static void main(String[] args) {
		new GUI();
	}
}
