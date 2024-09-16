package vista;

import java.awt.Color;
import java.util.Map;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

/**
 *
 * @author Samue
 */
public class consultarPanel extends javax.swing.JPanel {

    //Estos vectores van a contener los atributos de nuestra base de datos
    Vector atributos;

    /**
     * Creates new form insertarPersonaPane
     *
     * @param titulo
     */
    String titulo;

    public consultarPanel(String titulo) {
        initComponents();

        //Modelo de la tabla
        modeloTabla = new DefaultTableModel();

        tabla = new JTable(modeloTabla);

        //Asignamos el titulo
        this.titulo = titulo;

        //Titulo del panel
        tituloLabel.setText("Consultar " + titulo);

        //Boton Agregar
        bAgregarFiltro.setBackground(Color.white);

        //Scroll
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        //Boton
        bBuscar.setBackground(Color.WHITE);
        conjuntoAtributos.setLayout(new BoxLayout(conjuntoAtributos, BoxLayout.Y_AXIS));
        atributos = consultarAtributos();
        
        //Inicializar contador de nodos
        contador.setText("("+numeroNodos()+")");
    }

    private Vector consultarAtributos() {
        try {
            Driver driver;
            driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "s,dycsj01"));
            Session session = driver.session();
            String expC = "MATCH (p:" + titulo + ") UNWIND keys(p) AS atributo RETURN DISTINCT atributo";
            Result r = session.run(expC);

            atributos = new Vector();

            while (r.hasNext()) {
                Record record = r.next();
                atributos.add(record.get(0).toString().replace("\"", ""));
            }
            //driver.close();

            return atributos;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void consultarNodo(String atributos, String where) {
        modeloTabla = new DefaultTableModel();

        try {
            //Obtener los nombres de las columnas

            for (int i = 0; i < this.atributos.size(); i++) {
                modeloTabla.addColumn(this.atributos.get(i));
            }

            Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "s,dycsj01"));
            Session session = driver.session();

            String query = "MATCH (n:" + titulo + " {" + atributos + "}) "+where+" RETURN n";
            query = query.replace(",}", "}");

            // Ejecutar la consulta
            Result result = session.run(query);

            // Procesar los resultados
            while (result.hasNext()) {
                System.out.println("WHILE");
                Record record = result.next();
                Map<String, Object> properties = record.get("n").asNode().asMap();

                //Llenar la tabla
                Vector nodo = (Vector) this.atributos.clone();

                //LENAR EL VECTOR DE DATOS POR CADA NODO
                for (Map.Entry<String, Object> entry : properties.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    for (int i = 0; i < nodo.size(); i++) {
                        if (key.equals(nodo.get(i).toString())) {
                            System.out.println("KEY: " + key + "ATRIBUTO: " + this.atributos.get(i));
                            nodo.set(i, value);
                        }

                    }
                }//FIN DEL LLENADO DE DATOS

                //VACIADO DE DATOS QUE NO COINCIDEN CON EL NUMERO DE ATRIBUTOS DEL NODO
                for (int i = 0; i < nodo.size(); i++) {
                    System.out.println("NODO: " + nodo.get(i));
                    System.out.println("ATRIBUTO: " + this.atributos.get(i));

                    if (nodo.get(i).equals(this.atributos.get(i))) {
                        nodo.set(i, "");
                    }

                }//FIN DEL VACIADO

                modeloTabla.addRow(nodo);

            }

            ventana = new JFrame();
            ventana.setBounds(0, 0, 1300, 500);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);

            tabla = new JTable(modeloTabla);

            barras = new JScrollPane(tabla);
            barras.setBounds(10, 10, 490, 490);
            ventana.add(barras);

            tabla.setModel(modeloTabla);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    private int numeroNodos(){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "s,dycsj01"));
        Session session = driver.session();
        
        Result r = session.run("MATCH (n:"+titulo+") RETURN count(n)");
        r.hasNext();
        Record record = r.next();
        return record.get(0).asInt();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tituloLabel = new javax.swing.JLabel();
        contenedorOpciones = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bAgregarFiltro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        conjuntoAtributos = new javax.swing.JPanel();
        bBuscar = new javax.swing.JButton();
        contenedorLabels = new javax.swing.JPanel();
        nombreAtributoLabel = new javax.swing.JLabel();
        tipoDatoLabel = new javax.swing.JLabel();
        valorLabel = new javax.swing.JLabel();
        contador = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        setPreferredSize(new java.awt.Dimension(750, 560));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tituloLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tituloLabel.setForeground(new java.awt.Color(102, 102, 102));
        tituloLabel.setText("CONSULTAR");
        add(tituloLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, -1, -1));

        contenedorOpciones.setBackground(new java.awt.Color(252, 252, 252));
        contenedorOpciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Filtro");

        bAgregarFiltro.setText("Agregar");
        bAgregarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarFiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenedorOpcionesLayout = new javax.swing.GroupLayout(contenedorOpciones);
        contenedorOpciones.setLayout(contenedorOpcionesLayout);
        contenedorOpcionesLayout.setHorizontalGroup(
            contenedorOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 594, Short.MAX_VALUE)
                .addComponent(bAgregarFiltro)
                .addGap(18, 18, 18))
        );
        contenedorOpcionesLayout.setVerticalGroup(
            contenedorOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(bAgregarFiltro))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        add(contenedorOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 730, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        conjuntoAtributos.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout conjuntoAtributosLayout = new javax.swing.GroupLayout(conjuntoAtributos);
        conjuntoAtributos.setLayout(conjuntoAtributosLayout);
        conjuntoAtributosLayout.setHorizontalGroup(
            conjuntoAtributosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 728, Short.MAX_VALUE)
        );
        conjuntoAtributosLayout.setVerticalGroup(
            conjuntoAtributosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(conjuntoAtributos);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 730, 460));

        bBuscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bBuscar.setText("Consultar nodo");
        bBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBuscarActionPerformed(evt);
            }
        });
        add(bBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, -1, -1));

        contenedorLabels.setBackground(new java.awt.Color(252, 252, 252));
        contenedorLabels.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        nombreAtributoLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nombreAtributoLabel.setForeground(new java.awt.Color(102, 102, 102));
        nombreAtributoLabel.setText("Nombre de atributo");

        tipoDatoLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tipoDatoLabel.setForeground(new java.awt.Color(102, 102, 102));
        tipoDatoLabel.setText("Tipo de dato");

        valorLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        valorLabel.setForeground(new java.awt.Color(102, 102, 102));
        valorLabel.setText("Valor");

        javax.swing.GroupLayout contenedorLabelsLayout = new javax.swing.GroupLayout(contenedorLabels);
        contenedorLabels.setLayout(contenedorLabelsLayout);
        contenedorLabelsLayout.setHorizontalGroup(
            contenedorLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLabelsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombreAtributoLabel)
                .addGap(34, 34, 34)
                .addComponent(tipoDatoLabel)
                .addGap(57, 57, 57)
                .addComponent(valorLabel)
                .addContainerGap(410, Short.MAX_VALUE))
        );
        contenedorLabelsLayout.setVerticalGroup(
            contenedorLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLabelsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreAtributoLabel)
                    .addComponent(tipoDatoLabel)
                    .addComponent(valorLabel))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        add(contenedorLabels, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 730, 30));

        contador.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        contador.setForeground(new java.awt.Color(102, 102, 102));
        contador.setText("0");
        add(contador, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 2, -1, 30));
    }// </editor-fold>//GEN-END:initComponents


    private void bBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBuscarActionPerformed
        // TODO add your handling code here:
        String condiciones = "";
        String where = "";
        String nombreAtributo;
        String tipoDato;

        int n = conjuntoAtributos.getComponentCount();

        for (int i = 0; i < n; i++) {
            nombreAtributo = ((FiltroPanel) conjuntoAtributos.getComponent(i)).txtNombreAtributo.getSelectedItem().toString();
            tipoDato = ((FiltroPanel) conjuntoAtributos.getComponent(i)).tipoDato;

            switch (tipoDato) {
                case "Cadena":
                    if (i == n - 1) {
                        condiciones += nombreAtributo + ":" + ((FiltroPanel) conjuntoAtributos.getComponent(i)).txtValorAtributo.getText() + "$";
                    } else {
                        condiciones += nombreAtributo + ":" + ((FiltroPanel) conjuntoAtributos.getComponent(i)).txtValorAtributo.getText() + ",";
                    }
                    break;
                case "Entero":
                    if (i == n - 1) {
                        condiciones += nombreAtributo + ":" + ((FiltroPanel) conjuntoAtributos.getComponent(i)).txtValorAtributoEntero.getValue().toString() + "$";
                    } else {
                        condiciones += nombreAtributo + ":" + ((FiltroPanel) conjuntoAtributos.getComponent(i)).txtValorAtributoEntero.getValue().toString() + ",";
                    }
                    break;
                case "Arreglo":
                    
                    
                    if (where.isEmpty()) {
                        where = "WHERE '"+((FiltroPanel) conjuntoAtributos.getComponent(i)).txtValorAtributo.getText() + "' IN n."+nombreAtributo;
                    }else{
                        where += " AND '"+((FiltroPanel) conjuntoAtributos.getComponent(i)).txtValorAtributo.getText()+"' IN n."+nombreAtributo;
                    }
                    break;
                default:
                    throw new AssertionError();
            }

        }

        condiciones = condiciones.replace(":", ":\"");
        condiciones = condiciones.replace(",", "\",");
        condiciones = condiciones.replace("$", "\"");
        

        for (int i = 0; i < n; i++) {
            tipoDato = ((FiltroPanel) conjuntoAtributos.getComponent(i)).tipoDato;

            if (tipoDato.equals("Entero")) {
                condiciones = condiciones.replace("\"" + ((FiltroPanel) conjuntoAtributos.getComponent(i)).txtValorAtributoEntero.getValue().toString() + "\"", ((FiltroPanel) conjuntoAtributos.getComponent(i)).txtValorAtributoEntero.getValue().toString());
            }

        }
        
        System.out.println(condiciones);
        consultarNodo(condiciones, where);
    }//GEN-LAST:event_bBuscarActionPerformed

    private void bAgregarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarFiltroActionPerformed
        // TODO add your handling code here:
        FiltroPanel filtro = new FiltroPanel(620, 50, atributos);
        conjuntoAtributos.add(filtro);

        conjuntoAtributos.revalidate();
        conjuntoAtributos.repaint();

        revalidate();
        repaint();
    }//GEN-LAST:event_bAgregarFiltroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAgregarFiltro;
    private javax.swing.JButton bBuscar;
    private javax.swing.JPanel conjuntoAtributos;
    private javax.swing.JLabel contador;
    private javax.swing.JPanel contenedorLabels;
    private javax.swing.JPanel contenedorOpciones;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nombreAtributoLabel;
    private javax.swing.JLabel tipoDatoLabel;
    private javax.swing.JLabel tituloLabel;
    private javax.swing.JLabel valorLabel;
    // End of variables declaration//GEN-END:variables
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JScrollPane barras;
    private JFrame ventana;
}
