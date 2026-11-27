package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import Control.ControlRecetas;
import Logica.Receta;

public class VentanaReceta extends JFrame{
	private JPanel panelFotos;
	private ArrayList<File> fotos;
	
	private JTextField txtNombre;
	private JComboBox<String> cmbDificultad;
	private JTextField txtTiempo;
	private JTextField txtPorciones;
	
	private JTable tablaIngredientes;
	private DefaultTableModel modeloIngredientes;
	
	private JList<String> listaUtensilios;
	private DefaultListModel<String> modeloUtensilios;
	
	private JList<String> listaEtiquetas;
	private DefaultListModel<String> modeloEtiquetas;
	
	private JTextArea txtProcedimiento;
	
	private JButton btnEditar;
	private JButton btnGuardar;
	private JButton btnCancelar;
	
	private ControlRecetas control;
	private Receta receta;
	private Receta copiaTemporal;
	private Modo modo;
	private JButton btnAgregarFoto;
	
	//public VentanaReceta(ControlRecetas control, Receta receta, Modo modo) {
		//this.control = control;
		//this.receta = receta;
		//this.modo = modo;
	
	//Para la prueba el constructor no recibe parametros
	public VentanaReceta() {
		fotos = new ArrayList<>();
		
		modo = Modo.AGREGAR; //Prueba temporal
		
		setTitle("Receta");
		setSize(600, 750);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		hacerInterfaz();
		configurarModo();
	}
	private void hacerInterfaz() {
		JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		panelFotos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		btnAgregarFoto = new JButton("+");
		btnAgregarFoto.setPreferredSize(new Dimension(100, 100));
		btnAgregarFoto.setFont(new Font("Arial", Font.BOLD, 28));
		btnAgregarFoto.addActionListener(e -> agregarFotografias());
		
		panelFotos.add(btnAgregarFoto);
		JScrollPane scrollFotos = new JScrollPane(panelFotos,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollFotos.setPreferredSize(new Dimension(650, 125));
		scrollFotos.setBorder(BorderFactory.createTitledBorder("Fotos"));
		
		panelPrincipal.add(scrollFotos, BorderLayout.NORTH);
		
		JPanel panelContenido = new JPanel(new GridLayout(1, 2, 10, 10));
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
		
		JPanel panelInformacion = new JPanel(new GridLayout(4, 2, 5, 8));
		panelInformacion.setBorder(BorderFactory.createTitledBorder("Información General"));
		
		txtNombre = new JTextField();
		txtTiempo = new JTextField();
		txtPorciones = new JTextField();
		cmbDificultad = new JComboBox<>();
		
		panelInformacion.add(new JLabel("Nombre: "));
		panelInformacion.add(txtNombre);
		panelInformacion.add(new JLabel("Tiempo: "));
		panelInformacion.add(txtTiempo);
		panelInformacion.add(new JLabel("Dificultad: "));
		panelInformacion.add(cmbDificultad);
		panelInformacion.add(new JLabel("Porciones: "));
		panelInformacion.add(txtPorciones);
		panelIzquierdo.add(panelInformacion);
		
		JPanel panelIngredientes = new JPanel(new BorderLayout(5, 5));
		panelIngredientes.setBorder(BorderFactory.createTitledBorder("Ingredientes"));
		modeloIngredientes = new DefaultTableModel(new Object[] {"Ingrediente", "Cantidad"}, 0);
		tablaIngredientes = new JTable(modeloIngredientes);
		panelIngredientes.add(new JScrollPane(tablaIngredientes), BorderLayout.CENTER);
		
		JPanel botonesIngredientes = new JPanel();
		JButton btnAgregarIngrediente = new JButton("+");
		JButton btnEliminarIngrediente = new JButton("-");
		botonesIngredientes.add(btnAgregarIngrediente);
		botonesIngredientes.add(btnEliminarIngrediente);
		panelIngredientes.add(botonesIngredientes, BorderLayout.SOUTH);
		btnAgregarIngrediente.addActionListener(e -> {String ingrediente = JOptionPane.showInputDialog(this, "Nombre del Ingredeiente:");
			if (ingrediente != null && !ingrediente.trim().isEmpty()) {
				String cantidad = JOptionPane.showInputDialog(this,"Cantidad:");
				if (cantidad != null && !cantidad.trim().isEmpty()) {
					modeloIngredientes.addRow(new Object[] {ingrediente, cantidad});
				}
			}
		});
		
		btnEliminarIngrediente.addActionListener(e -> {int fila = tablaIngredientes.getSelectedRow();
			if (fila != -1) {
				modeloIngredientes.removeRow(fila);
			}
		});
		panelIzquierdo.add(panelIngredientes);
		
		JPanel panelUtensilios = new JPanel(new BorderLayout(5, 5));
		panelUtensilios.setBorder(BorderFactory.createTitledBorder("Utensilios"));
		modeloUtensilios = new DefaultListModel<>();
		listaUtensilios = new JList<>(modeloUtensilios);
		panelUtensilios.add(new JScrollPane(listaUtensilios), BorderLayout.CENTER);
		JPanel botonesUtensilios = new JPanel();
		JButton btnAgregarUtensilio = new JButton("+");
		JButton btnEliminarUtensilio = new JButton("-");
		botonesUtensilios.add(btnEliminarUtensilio);
		botonesUtensilios.add(btnAgregarUtensilio);
		panelUtensilios.add(botonesUtensilios, BorderLayout.SOUTH);
		btnAgregarUtensilio.addActionListener(e -> {String utensilio = JOptionPane.showInputDialog(this, "Nombre del utensilio: ");
				if (utensilio != null && !utensilio.trim().isEmpty()) {
					modeloUtensilios.addElement(utensilio);
				}
		});
		btnEliminarUtensilio.addActionListener(e -> {int indice = listaUtensilios.getSelectedIndex();
				if (indice != -1) {
					modeloUtensilios.remove(indice);
				}
		});
		panelIzquierdo.add(panelUtensilios);
		
		JPanel panelDerecho = new JPanel();
		panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
		
		JPanel panelEtiquetas = new JPanel(new BorderLayout(5, 5));
		panelEtiquetas.setBorder(BorderFactory.createTitledBorder("Etiquetas"));
		modeloEtiquetas = new DefaultListModel<>();
		listaEtiquetas = new JList<>(modeloEtiquetas);
		panelEtiquetas.add(new JScrollPane(listaEtiquetas), BorderLayout.CENTER);
		
		JPanel botonesEtiquetas = new JPanel();
		JButton btnAgregarEtiqueta = new JButton("+");
		JButton btnEliminarEtiqueta = new JButton("-");
		botonesEtiquetas.add(btnEliminarEtiqueta);
		botonesEtiquetas.add(btnAgregarEtiqueta);
		panelEtiquetas.add(botonesEtiquetas, BorderLayout.SOUTH);
		
		btnAgregarEtiqueta.addActionListener(e -> { String etiqueta = JOptionPane.showInputDialog(this, "Nombre de la etiqueta");
				if (etiqueta != null && !etiqueta.trim().isEmpty()) {
					modeloEtiquetas.addElement(etiqueta);
				}
		});
		btnEliminarEtiqueta.addActionListener(e ->{ int indice = listaEtiquetas.getSelectedIndex();
			if (indice != -1) {
				modeloEtiquetas.remove(indice);
			}
		});
		panelDerecho.add(panelEtiquetas);
		
		JPanel panelProcedimiento = new JPanel(new BorderLayout());
		panelProcedimiento.setBorder(BorderFactory.createTitledBorder("Procedimiento"));
		txtProcedimiento = new JTextArea();
		txtProcedimiento.setLineWrap(true);
		txtProcedimiento.setWrapStyleWord(true);
		panelProcedimiento.add(new JScrollPane(txtProcedimiento), BorderLayout.CENTER);
		
		panelDerecho.add(panelProcedimiento);
		panelContenido.add(panelIzquierdo);
		panelContenido.add(panelDerecho);
		
		panelPrincipal.add(panelContenido, BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnEditar = new JButton("Editar");
		btnGuardar = new JButton("Guardar");
		btnCancelar = new JButton("Cancelar");
		panelBotones.add(btnEditar);
		panelBotones.add(btnGuardar);
		panelBotones.add(btnCancelar);
		panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
		btnEditar.addActionListener(e -> {
			modo = Modo.EDITAR;
			configurarModo();
		});
		btnGuardar.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Cambios preparados para guardar.");
			modo = Modo.VER;
			configurarModo();
		});
		btnCancelar.addActionListener(e -> {
			modo = Modo.VER;
			configurarModo();
		});
		add(panelPrincipal);
	}
	private void agregarFotografias() {
		JFileChooser selector = new JFileChooser();
		selector.setMultiSelectionEnabled(true);
		selector.setFileFilter(new FileNameExtensionFilter("Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"));
		int resultado = selector.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File[] archivos = selector.getSelectedFiles();
			for (File archivo : archivos) {
				fotos.add(archivo);
				mostrarFotografias(archivo);
			}
			panelFotos.revalidate();
			panelFotos.repaint();
		}
	}
	private void mostrarFotografias(File archivo) {
		JPanel tarjetaFoto = new JPanel(new BorderLayout(2, 2));
		tarjetaFoto.setPreferredSize(new Dimension(100, 100));
		tarjetaFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		ImageIcon icono = new ImageIcon(archivo.getAbsolutePath());
		Image imagen = icono.getImage().getScaledInstance(90, 75, Image.SCALE_SMOOTH);
		
		JLabel etiquetaFoto = new JLabel(new ImageIcon(imagen));
		etiquetaFoto.setHorizontalAlignment(SwingConstants.CENTER);
		JButton btnEliminar = new JButton("X");
		btnEliminar.addActionListener(e -> {
			fotos.remove(archivo);
			panelFotos.remove(tarjetaFoto);
			panelFotos.revalidate();
			panelFotos.repaint();
		});
		tarjetaFoto.add(btnEliminar, BorderLayout.NORTH);
		tarjetaFoto.add(etiquetaFoto, BorderLayout.CENTER);
		
		panelFotos.remove(btnAgregarFoto);
		panelFotos.add(tarjetaFoto);
		panelFotos.add(btnAgregarFoto);
	}
	
	private void configurarModo() {
		boolean editar = modo != Modo.VER;
		
		txtNombre.setEditable(editar);
		txtTiempo.setEditable(editar);
		txtPorciones.setEditable(editar);
		cmbDificultad.setEnabled(editar);
		tablaIngredientes.setEnabled(editar);
		listaUtensilios.setEnabled(editar);
		listaEtiquetas.setEnabled(editar);
		txtProcedimiento.setEditable(editar);
		btnEditar.setVisible(modo == Modo.VER);
		btnGuardar.setVisible(editar);
		btnCancelar.setVisible(editar);
	}
	public enum Modo {
		VER, AGREGAR, EDITAR}
	
}
