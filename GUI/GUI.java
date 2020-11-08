package GUI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.List;
import java.util.ArrayList;

import Models.Medicao;
import Models.Pais;
import Controllers.RankingController;
import Controllers.PaisController;
import Controllers.MedicaoController;

public class GUI extends JFrame {
	private JPanel buttonBar, dateBar, radioBar;
	private JButton updateButton, storeButton, exportButton, searchButton;
	private JLabel fromLabel, toLabel, radiusLabel;
	private JFormattedTextField fromDateField, toDateField, radiusField;
	private JRadioButton absoluteRadio, growthRadio;
	private JTable tableConfirmados, tableRecuperados, tableMortos, tableMortalidade,
			tableProximos;
	private JScrollPane scrollConfirmados, scrollRecuperados, scrollMortos, scrollMortalidade,
			scrollProximos;
	private JTabbedPane tabs;
	private DataTableModel tableModelConfirmados, tableModelRecuperados, tableModelMortos,
			tableModelMortalidade, tableModelProximos;

	private class DataTableModel extends AbstractTableModel {
		private List<Medicao> meds;

		public DataTableModel() {
			meds = new ArrayList();
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
			switch (col) {
				case 0:
					return "Posição";
				case 1:
					return "País";
				case 2:
					return "Casos";
			}
			return null;
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
		exportButton = new JButton("Exportar TSV");
		searchButton = new JButton("Pesquisar");

		// JRadioButton
		absoluteRadio = new JRadioButton("Absoluto", true);
		growthRadio = new JRadioButton("Crescimento");

		// JLabel
		fromLabel = new JLabel("De: ");
		toLabel = new JLabel("Até: ");
		radiusLabel = new JLabel("Raio: ");

		// JFormattedTextField
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		fromDateField = new JFormattedTextField(new DateFormatter(dateFormat));
		toDateField = new JFormattedTextField(new DateFormatter(dateFormat));

		NumberFormat numberFormat = NumberFormat.getInstance();
		NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setMinimum(0);
		radiusField = new JFormattedTextField(numberFormatter);

		// DataTableModel
		tableModelConfirmados = new DataTableModel();
		tableModelRecuperados = new DataTableModel();
		tableModelMortos = new DataTableModel();
		tableModelMortalidade = new DataTableModel();
		tableModelProximos = new DataTableModel();

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
		ButtonGroup group = new ButtonGroup();
		group.add(absoluteRadio);
		group.add(growthRadio);

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
		setSize(500, 500);
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
				System.out.println("lol");
			}
		});

		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("lol");
			}
		});

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});

		tabs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = tabs.getSelectedIndex();
				radiusField.setEnabled(index == 4);
				absoluteRadio.setEnabled(index < 3);
				growthRadio.setEnabled(index < 3);
			}
		});
	}

	private void updateData() {
		System.out.println("atualizando");
		PaisController.getPaisesApi();
		System.out.println("fim paises");
		MedicaoController.getDadosApi();
		System.out.println("atualizado");
	}

	private void search() {
		int tabIndex = tabs.getSelectedIndex();
		String fromDate = fromDateField.getText();
		String toDate = toDateField.getText();

		if (fromDate.isEmpty() || toDate.isEmpty()) {
			return;
		}

		List<Medicao> confirmados = null;
		List<Medicao> mortos = null;
		List<Medicao> recuperados = null;
		if (tabIndex < 3) {
			if (absoluteRadio.isSelected()) {
				confirmados = RankingController.rankingGeral(fromDate, toDate, "confirmados");
				mortos = RankingController.rankingGeral(fromDate, toDate, "mortos");
				recuperados = RankingController.rankingGeral(fromDate, toDate, "recuperados");
			} else {
				confirmados = RankingController.rankingCrescimento(fromDate, toDate, "confirmados");
				mortos = RankingController.rankingCrescimento(fromDate, toDate, "mortos");
				recuperados = RankingController.rankingCrescimento(fromDate, toDate, "recuperados");
			}
		}

		tableModelConfirmados.setMeds(confirmados);
		tableModelMortos.setMeds(mortos);
		tableModelRecuperados.setMeds(recuperados);
	}

	public GUI() {
		initElements();
		configElements();
		configListeners();

		// tableModelConfirmados.setMeds(medicoes);
	}

	public static void main(String[] args) {
		new GUI();
	}
}
