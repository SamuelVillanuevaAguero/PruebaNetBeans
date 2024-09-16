package vista;

import java.awt.Color;
import java.util.Map;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;

/**
 *
 * @author Samue
 */
public class ConsultarRelaciones extends javax.swing.JPanel {
    Vector relaciones;
    /**
     * Creates new form insertarPersonaPane
     *
     */

    public ConsultarRelaciones() {
        initComponents();

        //ComboBox
        listaRelaciones.setBackground(Color.WHITE);

        //Scroll
        jscrollNodoA.getViewport().setBackground(Color.WHITE);
        jscrollNodob.getViewport().setBackground(Color.WHITE);
        

        //Boton
        bConsultar.setBackground(Color.WHITE);
        
        //Llenar el JComboBox de las relaciones
        relaciones = consultarRelaciones();
        listaRelaciones.removeAllItems();
        llenarLista();
        
        
    }
    
    private Vector consultarRelaciones() {
        try {
            Driver driver;
            driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "s,dycsj01"));
            Session session = driver.session();
            String expC = "MATCH ()-[r]->() RETURN DISTINCT type(r)";
            Result r = session.run(expC);

            relaciones = new Vector();

            while (r.hasNext()) {
                org.neo4j.driver.Record record = r.next();
                relaciones.add(record.get(0).toString().replace("\"", ""));
            }
            //driver.close();

            return relaciones;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    private void llenarLista(){
        for (int i = 0; i < relaciones.size(); i++) {
            listaRelaciones.addItem(relaciones.get(i).toString());
        }
    }
    
    private void consultarNodos(String relacion, String query){
        Vector atributosA = new Vector();
        Vector atributosB = new Vector();
        
        DefaultTableModel modeloTablaA = new DefaultTableModel();
        DefaultTableModel modeloTablaB = new DefaultTableModel();
                
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "s,dycsj01"));
        Session session = driver.session();
        
        //Llenar las columnas del nodo A
        Result result = session.run("MATCH (a)-[:"+relacion+"]->(b) UNWIND keys(a) AS key RETURN collect(DISTINCT key) AS AllKeys");
        
        while(result.hasNext()){
            Record record = result.next();
            for (int i = 0; i < record.get(0).asList().size(); i++) {
                modeloTablaA.addColumn(record.get(0).asList().get(i));
                atributosA.add(record.get(0).asList().get(i));
            }
        }
        
        //Llenar las columnas del nodo B
        result = session.run("MATCH (a)-[:"+relacion+"]->(b) UNWIND keys(b) AS key RETURN collect(DISTINCT key) AS AllKeys");
        
        while(result.hasNext()){
            Record record = result.next();
            for (int i = 0; i < record.get(0).asList().size(); i++) {
                modeloTablaB.addColumn(record.get(0).asList().get(i));
                atributosB.add(record.get(0).asList().get(i));
            }
        }
        
        result = session.run(query+" RETURN a");
        llenadoTabla(result, atributosA, modeloTablaA, "a");
        
        result = session.run(query+" RETURN b");
        llenadoTabla(result, atributosB, modeloTablaB, "b");
        
        JTable tablaA = new JTable(modeloTablaA);
        JTable tablaB = new JTable(modeloTablaB);
        
        jscrollNodoA.getViewport().add(tablaA);
        jscrollNodob.getViewport().add(tablaB);
    }
    
    private void llenadoTabla(Result result, Vector atributos, DefaultTableModel modeloTabla, String n){
        while (result.hasNext()) {
                Record record = result.next();
                Map<String, Object> properties = record.get(n).asNode().asMap();

                //Llenar la tabla
                Vector nodo = (Vector) atributos.clone();

                //LENAR EL VECTOR DE DATOS POR CADA NODO
                for (Map.Entry<String, Object> entry : properties.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    for (int i = 0; i < nodo.size(); i++) {
                        if (key.equals(nodo.get(i).toString())) {
                            nodo.set(i, value);
                        }

                    }
                }//FIN DEL LLENADO DE DATOS

                //VACIADO DE DATOS QUE NO COINCIDEN CON EL NUMERO DE ATRIBUTOS DEL NODO
                for (int i = 0; i < nodo.size(); i++) {

                    if (nodo.get(i).equals(atributos.get(i))) {
                        nodo.set(i, "");
                    }

                }//FIN DEL VACIADO

                modeloTabla.addRow(nodo);

            }
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
        listaRelaciones = new javax.swing.JComboBox<>();
        jscrollNodoA = new javax.swing.JScrollPane();
        bConsultar = new javax.swing.JButton();
        contenedorLabels = new javax.swing.JPanel();
        nombreAtributoLabel = new javax.swing.JLabel();
        tipoDatoLabel = new javax.swing.JLabel();
        jscrollNodob = new javax.swing.JScrollPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        setPreferredSize(new java.awt.Dimension(750, 560));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tituloLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tituloLabel.setForeground(new java.awt.Color(102, 102, 102));
        tituloLabel.setText("CONSULTAR RELACIONES");
        add(tituloLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, -1, -1));

        contenedorOpciones.setBackground(new java.awt.Color(252, 252, 252));
        contenedorOpciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Relaciones");

        listaRelaciones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        listaRelaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaRelacionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenedorOpcionesLayout = new javax.swing.GroupLayout(contenedorOpciones);
        contenedorOpciones.setLayout(contenedorOpcionesLayout);
        contenedorOpcionesLayout.setHorizontalGroup(
            contenedorOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 507, Short.MAX_VALUE)
                .addComponent(listaRelaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        contenedorOpcionesLayout.setVerticalGroup(
            contenedorOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(listaRelaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        add(contenedorOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 730, 40));

        jscrollNodoA.setBackground(new java.awt.Color(255, 255, 255));
        add(jscrollNodoA, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 360, 460));

        bConsultar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bConsultar.setText("Consultar Relación");
        bConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConsultarActionPerformed(evt);
            }
        });
        add(bConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, -1, -1));

        contenedorLabels.setBackground(new java.awt.Color(252, 252, 252));
        contenedorLabels.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        nombreAtributoLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nombreAtributoLabel.setForeground(new java.awt.Color(102, 102, 102));
        nombreAtributoLabel.setText("Nodo a");

        tipoDatoLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tipoDatoLabel.setForeground(new java.awt.Color(102, 102, 102));
        tipoDatoLabel.setText("Nodo b");

        javax.swing.GroupLayout contenedorLabelsLayout = new javax.swing.GroupLayout(contenedorLabels);
        contenedorLabels.setLayout(contenedorLabelsLayout);
        contenedorLabelsLayout.setHorizontalGroup(
            contenedorLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLabelsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombreAtributoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
                .addComponent(tipoDatoLabel)
                .addGap(306, 306, 306))
        );
        contenedorLabelsLayout.setVerticalGroup(
            contenedorLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLabelsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreAtributoLabel)
                    .addComponent(tipoDatoLabel))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        add(contenedorLabels, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 730, 30));
        add(jscrollNodob, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 350, 460));
    }// </editor-fold>//GEN-END:initComponents

    private void listaRelacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaRelacionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaRelacionesActionPerformed


    private void bConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConsultarActionPerformed
        // TODO add your handling code here:
        String relacion = listaRelaciones.getSelectedItem().toString();
        String query = "MATCH (a)-[:"+relacion+"]->(b)";
        
        System.out.println(query);
        consultarNodos(relacion, query);
    }//GEN-LAST:event_bConsultarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bConsultar;
    private javax.swing.JPanel contenedorLabels;
    private javax.swing.JPanel contenedorOpciones;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jscrollNodoA;
    private javax.swing.JScrollPane jscrollNodob;
    private javax.swing.JComboBox<String> listaRelaciones;
    private javax.swing.JLabel nombreAtributoLabel;
    private javax.swing.JLabel tipoDatoLabel;
    private javax.swing.JLabel tituloLabel;
    // End of variables declaration//GEN-END:variables
}
