package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author Samue
 */
public class AtributoPanel extends JPanel implements ActionListener, FocusListener {
    String tipoDato;
    
    //En caso de tener valores enteros
    JSpinner txtValorAtributoEntero;
    //Campos de texto
    JTextField txtValorAtributo;
    JTextField txtNombreAtributo;
    //Etiquetas
    JLabel txtTipoDato;
    //Botones
    JButton bEliminar;
    JButton bAgregar; //Este botón es en caso del tipo de dato 'Arreglo'
    //En caso de tener un tipo de dato 'Arreglo'
    JComboBox lista;

    public AtributoPanel(int ancho, int altura, String tipoDato) {
        this.tipoDato = tipoDato;

        setLayout(null);
        setSize(ancho, altura);
        iniciarComponentes();

        setBorder(new LineBorder(Color.BLACK));
        setVisible(true);

        revalidate();
        repaint();
    }

    private void iniciarComponentes() {
        setBackground(Color.WHITE);

        //Campos de texto
        txtNombreAtributo = new JTextField("Nombre atributo");
        txtNombreAtributo.setFont(new Font("Tahoma", 1, 12));
        txtNombreAtributo.setForeground(new Color(200, 200, 200));
        txtNombreAtributo.setBounds(10, 10, 120, 30);
        txtNombreAtributo.addFocusListener(this);
        add(txtNombreAtributo);

        //Componente dependiendo del tipo de dato
        componenteValor();

        //Etiqutas
        txtTipoDato = new JLabel(tipoDato);
        txtTipoDato.setBounds(410, 10, 80, 30);
        add(txtTipoDato);

        //Boton
        bEliminar = new JButton("Eliminar");
        bEliminar.setBackground(Color.WHITE);
        bEliminar.setBounds(470, 10, 100, 30);
        bEliminar.addActionListener(this);
        add(bEliminar);
    }

    private void componenteValor() {
        switch (tipoDato) {
            case "Cadena":
                campoTextoValor();
                break;
            case "Arreglo":
                campoTextoValor();
                //Re posicionar el campo de texto 'Valor'
                txtValorAtributo.setBounds(200, 10, 90, 30);
                //Crear la lista de valores
                lista = new JComboBox();
                lista.setBounds(300, 10, 90, 30);
                lista.setBackground(Color.WHITE);
                add(lista);
                //Crear botón de agregar valores a la lista
                bAgregar = new JButton("Agregar");
                bAgregar.setBounds(580, 10, 100, 30);
                bAgregar.setBackground(Color.WHITE);
                bAgregar.addActionListener(this);
                add(bAgregar);
                break;
            case "Entero":
                txtValorAtributoEntero = new JSpinner();
                txtValorAtributoEntero.setBackground(Color.WHITE);
                txtValorAtributoEntero.setBounds(200, 10, 200, 30);
                add(txtValorAtributoEntero);
                break;
            default:
                System.out.println("ERRORRRRRRRRRRRRRRRRRRRRRRRR");
        }
    }

    private void campoTextoValor() {
        txtValorAtributo = new JTextField("Valor");
        txtValorAtributo.setFont(new Font("Tahoma", 1, 12));
        txtValorAtributo.setForeground(new Color(200, 200, 200));
        txtValorAtributo.setBounds(200, 10, 200, 30);
        txtValorAtributo.addFocusListener(this);
        add(txtValorAtributo);
    }

    //Eventos
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bEliminar) {

            Container parent = getParent();
            getParent().remove(this);

            parent.revalidate();
            parent.repaint();

            parent.getParent().requestFocus();
        } else if (e.getSource() == bAgregar) {            
            lista.addItem(txtValorAtributo.getText());
            txtValorAtributo.setText("");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == txtNombreAtributo && txtNombreAtributo.getText().equals("Nombre atributo")) {
            txtNombreAtributo.setText("");
            txtNombreAtributo.setFont(new Font("Tahoma", 0, 12));
            txtNombreAtributo.setForeground(new Color(0, 0, 0));
        } else if (e.getSource() == txtValorAtributo && txtValorAtributo.getText().endsWith("Valor")) {
            txtValorAtributo.setText("");
            txtValorAtributo.setFont(new Font("Tahoma", 0, 12));
            txtValorAtributo.setForeground(new Color(0, 0, 0));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == txtNombreAtributo && txtNombreAtributo.getText().isEmpty()) {
            txtNombreAtributo.setText("Nombre atributo");
            txtNombreAtributo.setFont(new Font("Tahoma", 1, 12));
            txtNombreAtributo.setForeground(new Color(200, 200, 200));
        } else if (e.getSource() == txtValorAtributo && txtValorAtributo.getText().isEmpty()) {
            txtValorAtributo.setText("Valor");
            txtValorAtributo.setFont(new Font("Tahoma", 1, 12));
            txtValorAtributo.setForeground(new Color(200, 200, 200));
        }
    }
}
