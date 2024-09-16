package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author Samue
 */
public class FiltroPanel extends JPanel implements ActionListener, FocusListener {

    Vector atributos;
    //Valor del tipo de dato
    String tipoDato;

    //En caso de tener valores enteros
    JSpinner txtValorAtributoEntero;

    //Campos de texto
    JComboBox txtNombreAtributo;
    JTextField txtValorAtributo;

    //Botones
    JButton bEliminar;
    JButton bAgregar; //Este botón es en caso del tipo de dato 'Arreglo'

    //En caso de tener un tipo de dato 'Arreglo'
    JComboBox lista;

    //
    JComboBox tipoDatoLista;

    public FiltroPanel(int ancho, int altura, Vector atributos) {
        this.atributos = atributos;

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
        txtNombreAtributo = new JComboBox(atributos);
        txtNombreAtributo.setBackground(Color.WHITE);
        txtNombreAtributo.setBounds(10, 10, 120, 30);
        add(txtNombreAtributo);

        //Componente dependiendo del tipo de dato
        tipoDatoLista = new JComboBox(new String[]{"Cadena", "Entero", "Arreglo"});
        tipoDatoLista.setBounds(150, 10, 100, 30);
        tipoDatoLista.setBackground(Color.WHITE);
        tipoDatoLista.addActionListener(this);
        add(tipoDatoLista);

        //Boton
        bEliminar = new JButton("Eliminar");
        bEliminar.setBackground(Color.WHITE);
        bEliminar.setBounds(600, 10, 100, 30);
        bEliminar.addActionListener(this);
        add(bEliminar);
    }

    private void componenteValor() {
        if (getComponentCount() == 3) {
            switch (tipoDato) {
                case "Cadena":
                    campoTextoValor();
                    break;
                case "Arreglo":
                    campoTextoValor();
                    break;
                case "Entero":
                    txtValorAtributoEntero = new JSpinner();
                    txtValorAtributoEntero.setBackground(Color.WHITE);
                    txtValorAtributoEntero.setBounds(280, 10, 200, 30);
                    add(txtValorAtributoEntero);
                    break;
                default:
                    System.out.println("ERRORRRRRRRRRRRRRRRRRRRRRRRR");
            }
        }else{
            remove(getComponentCount()-1);
            componenteValor();
        }
    }

    private void campoTextoValor() {
        txtValorAtributo = new JTextField("Valor");
        txtValorAtributo.setFont(new Font("Tahoma", 1, 12));
        txtValorAtributo.setForeground(new Color(200, 200, 200));
        txtValorAtributo.setBounds(280, 10, 200, 30);
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
        } else if (e.getSource() == tipoDatoLista) {
            tipoDato = tipoDatoLista.getSelectedItem().toString();

            componenteValor();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == txtValorAtributo && txtValorAtributo.getText().endsWith("Valor")) {
            txtValorAtributo.setText("");
            txtValorAtributo.setFont(new Font("Tahoma", 0, 12));
            txtValorAtributo.setForeground(new Color(0, 0, 0));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == txtValorAtributo && txtValorAtributo.getText().isEmpty()) {
            txtValorAtributo.setText("Valor");
            txtValorAtributo.setFont(new Font("Tahoma", 1, 12));
            txtValorAtributo.setForeground(new Color(200, 200, 200));
        }
    }
}
