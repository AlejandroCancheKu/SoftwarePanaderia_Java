/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panaderia;
//Librerias para usar las Clases

import clases.Cliente;//Permite usar la clase Cliente
import clases.ClienteEstandar;
import clases.ClientePremium;
import clases.Direccion;//Permite usar la clase Direccion
import clases.Pan;//Permite usar la clase Pan
import clases.Venta;//Permite usar la clase Ventas
//Librerias para usar Archivos
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//Librerias para usar Excepciones
import java.io.EOFException;
import java.io.IOException;
import java.text.ParseException;
//Librerias para el uso de Fechas
import java.text.SimpleDateFormat;//Permite usar el formato de fecha 
import java.util.Date;//Permite usar el Date
//Librerias para el uso de Arreglos
import java.util.ArrayList;//Permite usar el ArrayList
import javafx.collections.FXCollections;//Permite usar el FXCollections
import javafx.collections.ObservableList;//Permite usar el ObservableList
//Librerias de JavaFX
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//Librerias para usar los Controles
import javafx.scene.control.Alert;//Permite usar Alert
import javafx.scene.control.Button;//Permite usar Button
import javafx.scene.control.ButtonBar.ButtonData;//Permite usar el ButtonData
import javafx.scene.control.ComboBox;//Permite usar ComboBox
import javafx.scene.control.DatePicker;//Permite usar DatePicker
import javafx.scene.control.Label;//Permite usar Label
import javafx.scene.control.Tab;//Permite usar Tab
import javafx.scene.control.TabPane;//Permite usar TabPane
import javafx.scene.control.TableColumn;//Permite usar TableColumn
import javafx.scene.control.TableView;//Permite usar TableView
import javafx.scene.control.TextField;//Permite usar TextField
import javafx.scene.control.cell.PropertyValueFactory;//Permite asignar los valores a las columnas del TableView
import javafx.scene.layout.AnchorPane;//Permite usar AnchorPane
//Librerias para el uso de Events
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
//Librerias para editar TableColumn
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author usuario
 */
public class FXMLPanaderiaController implements Initializable {
    @FXML
    private AnchorPane apOperaciones;
    @FXML
    private TabPane tpOperaciones;
    @FXML
    private Tab tbRegistrarCliente, tbRegistrarPan, tbRealizarVenta, tbHistorialCompras, tbMejorCliente, tbPanMasVendido,
            tbVerConchapuntos, tbCanjearConchapuntos, tbVerClientes, tbVerPanes, tbTicket;
    @FXML
    private Button btnCancelar, btnCargar, btnDevolver, btnCobrar, btnTicket, btnCanjear;
    @FXML
    private Label lbFecha, lbIDV, lbTotal, lbCambio, lbMC, lbPMV, lbVerCP, lbDatosCanjear, lbCanjearCP, lbCPA, lbDatos, lbDetalles;
    @FXML
    private TextField tfIDC, tfNombreC, tfApellido, tfCalle, tfNumero, tfColonia, tfCP, tfCiudad, tfEstado, tfTelefono,
            tfIDP, tfNombreP, tfPrecio, tfExistencia, tfIDV, tfCantidadPan, tfEfectivo, tfMC, tfVerCP, tfConsultarCP, tfCanjear;
    @FXML
    private ChoiceBox chbTipoC;

    @FXML
    private ComboBox<String> cbPanes;
    @FXML
    private TableView<Venta> tvVentas, tvHistorial, tvTicket;
    @FXML
    private TableView<Cliente> tvClientes;
    @FXML
    private TableView<Pan> tvPanes;
    @FXML
    private TableColumn<Cliente, Integer> tcIDC, tcConchapuntos;
    @FXML
    private TableColumn<Cliente, String> tcNombreC, tcApellido;
    @FXML
    private TableColumn<Cliente, Direccion> tcDireccion;
    @FXML
    private TableColumn<Pan, Integer> tcIDP, tcExistencia;
    @FXML
    private TableColumn<Pan, String> tcNombreP;
    @FXML
    private TableColumn<Pan, Float> tcPrecio;
    @FXML
    private TableColumn<Venta, String> tcProductoV, tcFechaH, tcProductoH, tcProductoT;
    @FXML
    private TableColumn<Venta, Integer> tcIdPV, tcCantidadH, tcIdH, tcCantidadV, tcIdPT, tcMontoT, tcCantidadT;
    @FXML
    private TableColumn<Venta, Float> tcMontoV, tcMontoH;
    @FXML
    private DatePicker dpInicioMC, dpFinMC, dpInicioPMV, dpFinPMV;

    ArrayList<Cliente> listaClientes;
    ArrayList<Pan> listaPanes;
    ArrayList<Venta> listaVentas;
    ArrayList<Venta> ventaActual;
    ArrayList<Venta> ventasRango;
       
    ClienteEstandar e;
    ClientePremium r;

    int manteconchas = 20; //Dato de manteconchas disponibles al iniciar el día

    public void leerClientes() {
        listaClientes = new ArrayList<>();

        //Lectura de clientes guardados
        try (ObjectInputStream oisCliente = new ObjectInputStream(new FileInputStream("src/panaderia/archivos/clientes.txt"))) {
            //Cuando no haya más objetos saltará la excepcion EOFException
            while (true) {
                Cliente cliente = (Cliente) oisCliente.readObject();
                listaClientes.add(new ClienteEstandar(cliente.getTipoCliente(), cliente.getIdCliente(), cliente.getNombreCliente(), cliente.getAPaternoCliente(), cliente.getDireccion(), cliente.getConchapuntos()));
            }
        } catch (ClassNotFoundException f) {
            System.out.println("No se encontraron los elementos de tipo Cliente");
        } catch (EOFException f) {
            System.out.println("Se han leido todos los clientes");
        } catch (IOException f) {
            System.out.println("ERROR");
        }
    }

    public void leerPanes() {
        listaPanes = new ArrayList<>();

        //Lectura de panes guardados
        try (ObjectInputStream oisPan = new ObjectInputStream(new FileInputStream("src/panaderia/archivos/panes.txt"))) {
            //Cuando no haya mas objetos saltara la excepcion EOFException
            while (true) {
                Pan pan = (Pan) oisPan.readObject();
                listaPanes.add(new Pan(pan.getIdPan(), pan.getNombrePan(), pan.getPrecioPan(), pan.getExistenciaPan()));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontraron los elementos de tipo Pan");
        } catch (EOFException e) {
            System.out.println("Se han leido todos los panes");
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    public void leerVentas() {
        listaVentas = new ArrayList<>();

        //Lectura de ventas guardadas
        try (ObjectInputStream oisVenta = new ObjectInputStream(new FileInputStream("src/panaderia/archivos/ventas.txt"))) {
            //Cuando no haya mas objetos saltara la excepcion EOFException
            while (true) {
                Venta venta = (Venta) oisVenta.readObject();
                listaVentas.add(new Venta(venta.getFecha(), venta.getIdCliente(), venta.getIdPan(), venta.getNombrePan(), venta.getCantidadPan(), venta.getPrecioPan(), venta.getMonto()));
            }
        } catch (ClassNotFoundException s) {
            System.out.println("No se encontraron los elementos de tipo Venta");
        } catch (EOFException s) {
            System.out.println("Se han leido todas las ventas");
        } catch (IOException s) {
            System.out.println("ERROR");
        }
    }

    public void escribirClientes() {
        try (ObjectOutputStream oosClientes = new ObjectOutputStream(new FileOutputStream("src/panaderia/archivos/clientes.txt"))) {
            //Escribimos en un fichero
            for (int i = 0; i < listaClientes.size(); i++) {
                oosClientes.writeObject(listaClientes.get(i));
            }
        } catch (IOException e) {
            System.out.println("\tDireccion de archivo errónea");
        }
    }

    public void escribirPanes() {
        try (ObjectOutputStream oosPanes = new ObjectOutputStream(new FileOutputStream("src/panaderia/archivos/panes.txt"))) {
            //Escribimos en un fichero
            for (int i = 0; i < listaPanes.size(); i++) {
                oosPanes.writeObject(listaPanes.get(i));
            }
        } catch (IOException e) {
            System.out.println("Direccion de archivo errónea");
        }
    }

    public void escribirVentas() {
        try (ObjectOutputStream oosVentas = new ObjectOutputStream(new FileOutputStream("src/panaderia/archivos/ventas.txt"))) {
            //Escribimos en un fichero
            for (int i = 0; i < listaVentas.size(); i++) {
                oosVentas.writeObject(listaVentas.get(i));
            }
        } catch (IOException e) {
            System.out.println("Direccion erronea");
        }
    }

//Anchor Pane apOperaciones
    /**
     * Este método permite habilitar el tab de RegistrarCliente
     *
     * @param RC
     */
    @FXML
    private void registrarCliente(ActionEvent RC) {
        chbTipoC.setTooltip(new Tooltip("Selecciona el tipo de cliente"));
        chbTipoC.setItems(FXCollections.observableArrayList("Estándar", "Premium"));

        tpOperaciones.getTabs().add(tbRegistrarCliente);
        apOperaciones.setDisable(true);
        System.out.println("\nTab Registrar Cliente");
    }

    /**
     * Este método permite habilitar el tab RegistrarPan
     *
     * @param RP
     */
    @FXML
    private void registrarPan(ActionEvent RP) {
        tpOperaciones.getTabs().add(tbRegistrarPan);
        apOperaciones.setDisable(true);
        System.out.println("\nTab Registrar Pan");
    }

    /**
     * Este método permite habilitar el tab RealizarCompra
     *
     * @param venta
     */
    @FXML
    private void realizarVenta(ActionEvent venta) {
        ventaActual = new ArrayList<>();

        ObservableList<String> Panes = FXCollections.observableArrayList();
        for (int i = 0; i < listaPanes.size(); i++) {
            Panes.add(listaPanes.get(i).nombrePan);
        }

        cbPanes.setItems(Panes);

        if (cbPanes.getItems().isEmpty()) {
            cbPanes.setPromptText("No hay panes disponibles");
            tfIDV.setEditable(false);
        } else {
            cbPanes.setPromptText("Seleccione un pan...");
            lbIDV.setVisible(true);
            tfIDV.setVisible(true);
            tfIDV.setEditable(true);
            lbTotal.setText("0");
            lbTotal.setVisible(false);
        }

        tfCantidadPan.setDisable(true);
        btnCargar.setDisable(true);
        btnDevolver.setDisable(true);
        tvVentas.setItems(null);
        tfEfectivo.setEditable(false);
        btnCobrar.setDisable(true);
        btnTicket.setDisable(true);

        tpOperaciones.getTabs().add(tbRealizarVenta);
        apOperaciones.setDisable(true);
        System.out.println("\nTab Realizar Venta");
    }

    /**
     * Este método permite habilitar el tab HistorialCompras
     *
     * @param historial
     */
    @FXML
    private void historialCompras(ActionEvent historial) {
        tvHistorial.setVisible(false);

        tpOperaciones.getTabs().add(tbHistorialCompras);
        apOperaciones.setDisable(true);
        System.out.println("\nTab Ver Historial de Compras");
    }

    /**
     * Este método permite habilitar el tab HistorialCompras
     *
     * @param MC
     */
    @FXML
    private void mejorCliente(ActionEvent MC) {
        ventasRango = new ArrayList<>();

        tpOperaciones.getTabs().add(tbMejorCliente);
        apOperaciones.setDisable(true);
        System.out.println("\nTab Mejor Cliente");
    }

    /**
     * Este método permite habilitar el tab PanMasVendido
     *
     * @param PMV
     */
    @FXML
    private void panMasVendido(ActionEvent PMV) {
        ventasRango = new ArrayList<>();

        tpOperaciones.getTabs().add(tbPanMasVendido);
        apOperaciones.setDisable(true);
        System.out.println("\nTab Pan Más Vendido");
    }

    /**
     * Este método permite habilitar el tab VerConchapuntos
     *
     * @param verCP
     */
    @FXML
    private void verConchapuntos(ActionEvent verCP) {
        tpOperaciones.getTabs().add(tbVerConchapuntos);
        apOperaciones.setDisable(true);
        System.out.println("\nTab Ver Conchapuntos");
    }

    /**
     * Este método permite habilitar el tab CanjearConchapuntos
     *
     * @param canjearCP
     */
    @FXML
    private void canjearConchapuntos(ActionEvent canjearCP) {
        if (manteconchas == 0) {
            Alert sinMCo = new Alert(Alert.AlertType.INFORMATION);
            sinMCo.setTitle("Manteconchas");
            sinMCo.setHeaderText("Lo sentimos no contamos con manteconchas");
            sinMCo.setContentText("Desea registrar más para canjeo?");
            sinMCo.show();
        }

        lbCanjearCP.setVisible(false);
        tfCanjear.setVisible(false);
        btnCanjear.setVisible(false);
        lbCPA.setVisible(false);

        tpOperaciones.getTabs().add(tbCanjearConchapuntos);
        apOperaciones.setDisable(true);
        System.out.println ("\nTab Canjear Conchapuntos");
    }

    /**
     * Este método permite habilitar el tab VerClientes
     *
     * @param verC
     */
    @FXML
        private void verClientes(ActionEvent verC) {
        leerClientes();

        System.out.println("Clientes registrados en el arreglo listaClientes");
        for (int i = 0; i < listaClientes.size(); i++) {
            System.out.println(listaClientes.get(i).toString());
        }

        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        for (int i = 0; i < listaClientes.size(); i++) {
            clientes.add(listaClientes.get(i));
        }

        tvClientes.setItems(clientes);

        tcIDC.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tcNombreC.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        tcApellido.setCellValueFactory(new PropertyValueFactory<>("aPaternoCliente"));
        tcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tcConchapuntos.setCellValueFactory(new PropertyValueFactory<>("conchapuntos"));

        System.out.println("\nClientes alojados en al tabla");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(tvClientes.getItems().get(i));
        }

        tpOperaciones.getTabs().add(tbVerClientes);
        apOperaciones.setDisable(true);
        System.out.println("\nTab Ver Clientes");
    }

    /**
     * Este método permite habilitar el tab VerPanes
     *
     * @param verP
     */
    @FXML
        private void verPanes(ActionEvent verP) {
        leerPanes();

        System.out.println("\nPanes registrados en el arreglo listaPanes");
        for (int i = 0; i < listaPanes.size(); i++) {
            System.out.println(listaPanes.get(i).toString());
        }

        ObservableList<Pan> panes = FXCollections.observableArrayList();
        for (int i = 0; i < listaPanes.size(); i++) {
            panes.add(listaPanes.get(i));
        }

        tvPanes.setItems(panes);

        tcIDP.setCellValueFactory(new PropertyValueFactory<>("idPan"));
        tcNombreP.setCellValueFactory(new PropertyValueFactory<>("nombrePan"));
        tcPrecio.setCellValueFactory(new PropertyValueFactory<>("precioPan"));
        tcExistencia.setCellValueFactory(new PropertyValueFactory<>("existenciaPan"));

        System.out.println("\nPanes alojados en la tabla");
        for (int i = 0; i < panes.size(); i++) {
            System.out.println(tvPanes.getItems().get(i));
        }

        tpOperaciones.getTabs().add(tbVerPanes);
        apOperaciones.setDisable(true);
        System.out.println("\nTab Ver Panes");
    }

//Tab RegistrarCliente
    /**
     * Este método permite registrar un nuevo cliente en la BD
     *
     * @param nuevoRC
     */
    @FXML
    private void guardarRC(ActionEvent nuevoRC) throws RuntimeException {
        int b = 0; //Bandera que permite saber si el cliente ya ha sido registrado
        int id;

        if (tfIDC.getText().isEmpty() || tfNombreC.getText().isEmpty() || tfApellido.getText().isEmpty() || chbTipoC.getValue().toString().isEmpty()) {
            Alert datos = new Alert(Alert.AlertType.WARNING);
            datos.setHeaderText("Datos incorretos");
            datos.setContentText("Existen campos vacíos");
            datos.show();
            System.out.println("\tHay campos vacíos");
        } else {
            try {
                id = Integer.parseInt(tfIDC.getText());

                for (int i = 0; i < listaClientes.size(); i++) {
                    if (listaClientes.get(i).idCliente == id) {
                        Alert iD = new Alert(Alert.AlertType.WARNING);
                        iD.setHeaderText("Error de ID");
                        iD.setContentText("El iD ya ha sido registrado");
                        iD.show();

                        tfIDC.setText("");
                        System.out.println("\tEl iD ya ha sido registrado");
                        b = 1;//Cliente ya registrado
                    }
                }

                if (b == 0) {
                    String tipo = (String) chbTipoC.getValue();
                    String nombre = tfNombreC.getText();
                    String apellido = tfApellido.getText();
                    //Datos de la dirección
                    String calle = tfCalle.getText();
                    String numero = tfNumero.getText();
                    String colonia = tfColonia.getText();
                    String CP = tfCP.getText();
                    String ciudad = tfCiudad.getText();
                    String estado = tfEstado.getText();
                    String telefono = tfTelefono.getText();

                    Direccion dir = new Direccion(calle, numero, colonia, CP, ciudad, estado, telefono);//Creación de la dirección
                    
                    if(tipo.equals("Estándar")){                   
                        listaClientes.add(new ClienteEstandar(tipo, id, nombre, apellido, dir, 0));//Creación del cliente Estándar
                    }else{
                        listaClientes.add(new ClientePremium(tipo, id, nombre, apellido, dir, 0));//Creación de cliente Premium
                    }
                    
                    //Guardar datos de los Clientes en el archivo clientes.txt
                    escribirClientes();
                    
                    tfIDC.setText("");
                    tfNombreC.setText("");
                    tfApellido.setText("");
                    tfCalle.setText("");
                    tfNumero.setText("");
                    tfColonia.setText("");
                    tfCP.setText("");
                    tfCiudad.setText("");
                    tfEstado.setText("");
                    tfTelefono.setText("");

                    Alert nCliente = new Alert(Alert.AlertType.INFORMATION);
                    nCliente.setHeaderText("Registrar cliente");
                    nCliente.setContentText("El cliente ha sido registrado");
                    nCliente.show();
                    System.out.println("\tCliente registrado");
                }
            } catch (RuntimeException e) {
                Alert datoID = new Alert(Alert.AlertType.ERROR);
                datoID.setHeaderText("Dato inválido");
                datoID.setContentText("El id debe ser numérico");
                datoID.show();

                tfIDC.setText("");
                System.out.println("\tEl ID ingresado es inválido");
            }
        }
    }

    /**
     * Este método permite cancelar el registro del cliente
     *
     * @param cancelRC
     */
    @FXML
    private void cancelarRC(ActionEvent cancelRC) {
        Alert cancelarRC = new Alert(Alert.AlertType.CONFIRMATION);
        cancelarRC.setHeaderText("Cancelar registro");
        cancelarRC.setContentText("Desea cancelar el registro actual?");
        cancelarRC.showAndWait();

        if (cancelarRC.getResult().getButtonData().equals(ButtonData.OK_DONE)) {
            tfIDC.setText("");
            tfNombreC.setText("");
            tfApellido.setText("");
            tfCalle.setText("");
            tfNumero.setText("");
            tfColonia.setText("");
            tfCP.setText("");
            tfCiudad.setText("");
            tfEstado.setText("");
            tfTelefono.setText("");

            tpOperaciones.getTabs().remove(tbRegistrarCliente);
            apOperaciones.setDisable(false);
            System.out.println("Registro de cliente cancelado");
        }
    }

//Tab RegistrarPan
    /**
     * Este método permite registrar un nuevo pan en la BD
     *
     * @param nuevoRP
     */
    @FXML
    private void guardarRP(ActionEvent nuevoRP) throws RuntimeException {
        int b = 0;//Permite saber si el pan ya ha sido registrado

        if (tfIDP.getText().isEmpty() || tfNombreP.getText().isEmpty() || tfPrecio.getText().isEmpty() || tfExistencia.getText().isEmpty()) {
            Alert datos = new Alert(Alert.AlertType.WARNING);
            datos.setHeaderText("Datos incorretos");
            datos.setContentText("Existen campos vacíos");
            datos.show();
            System.out.println("\tHay campos vacíos");
        } else {
            try {
                int id = Integer.parseInt(tfIDP.getText());

                for (int i = 0; i < listaPanes.size(); i++) {
                    if (listaPanes.get(i).idPan == id) {
                        Alert iD = new Alert(Alert.AlertType.WARNING);
                        iD.setHeaderText("Error de ID");
                        iD.setContentText("El iD ya ha sido registrado");
                        iD.show();

                        tfIDP.setText("");
                        System.out.println("\tEl iD ya ha sido registrado");
                        b = 1;//Pan ya registrado
                    }
                }

                if (b == 0) {
                    String nombre = tfNombreP.getText();

                    try {
                        float precio = Float.parseFloat(tfPrecio.getText());

                        try {
                            int existencia = Integer.parseInt(tfExistencia.getText());

                            listaPanes.add(new Pan(id, nombre, precio, existencia));

                            //Guardar datos de los Panes en el archivo panes.txt
                            escribirPanes();
                            
                            tfIDP.setText("");
                            tfNombreP.setText("");
                            tfPrecio.setText("");
                            tfExistencia.setText("");

                            Alert nPan = new Alert(Alert.AlertType.INFORMATION);
                            nPan.setHeaderText("Registrar pan");
                            nPan.setContentText("El pan ha sido registrado");
                            nPan.show();
                            System.out.println("\tPan registrado");
                        } catch (RuntimeException e) {
                            Alert datoE = new Alert(Alert.AlertType.ERROR);
                            datoE.setHeaderText("Dato inválido");
                            datoE.setContentText("La existencia del pan debe ser numérica");
                            datoE.show();

                            tfExistencia.setText("");
                            System.out.println("\tLa existencia del pan debe ser numérica");
                        }
                    } catch (RuntimeException e) {
                        Alert datoP = new Alert(Alert.AlertType.ERROR);
                        datoP.setHeaderText("Dato inválido");
                        datoP.setContentText("El precio del pan debe ser numérico");
                        datoP.show();

                        tfPrecio.setText("");
                        System.out.println("\tEl precio del pan debe ser numérico");
                    }
                }
            } catch (RuntimeException e) {
                Alert datoID = new Alert(Alert.AlertType.ERROR);
                datoID.setHeaderText("Dato inválido");
                datoID.setContentText("El id debe ser numérico");
                datoID.show();

                tfIDP.setText("");
                System.out.println("\tEl ID ingresado es inválido");
            }
        }
    }

    /**
     * Este método permite cancelar el registro del pan
     *
     * @param cancelRP
     */
    @FXML
    private void cancelarRP(ActionEvent cancelRP) {
        Alert cancelarRP = new Alert(Alert.AlertType.CONFIRMATION);
        cancelarRP.setHeaderText("Cancelar registro");
        cancelarRP.setContentText("Desea cancelar el registro actual?");
        cancelarRP.showAndWait();

        if (cancelarRP.getResult().getButtonData().equals(ButtonData.OK_DONE)) {
            tfIDP.setText("");
            tfNombreP.setText("");
            tfPrecio.setText("");
            tfExistencia.setText("");

            tpOperaciones.getTabs().remove(tbRegistrarPan);
            apOperaciones.setDisable(false);
            System.out.println("Registro de pan cancelado");
        }
    }

//Tab RealizarCompra
    /**
     * Este método permite adquirir un pan registrado en el sistema
     *
     * @param comprar
     */
    @FXML
    private void cargarCompra(ActionEvent comprar) throws RuntimeException {
        int vA = 0;//Permite saber si la venta se autoriza
        int idCliente = 0;//ID asociado a la venta a un cliente no registrado
        float sumVenta = Float.parseFloat(lbTotal.getText());//Sumador de la venta

        Date fecha = new Date();
        String fechaVenta = new SimpleDateFormat("dd/MM/yyyy").format(fecha);

        //Validación del id 
        if (lbIDV.isVisible() && tfIDV.isVisible()) {
            if (tfIDV.getText().isEmpty()) {
                Alert ventaCNR = new Alert(Alert.AlertType.CONFIRMATION);
                ventaCNR.setHeaderText("Venta");
                ventaCNR.setContentText("Desea realizar una venta a un cliente no registrado?");
                ventaCNR.showAndWait();

                if (ventaCNR.getResult().getButtonData().equals(ButtonData.OK_DONE)) {
                    lbIDV.setVisible(false);
                    tfIDV.setVisible(false);
                    vA = 1;
                    System.out.println("\tIniciar venta a cliente no registrado");
                } else {
                    Alert venta = new Alert(Alert.AlertType.INFORMATION);
                    venta.setHeaderText("Venta");
                    venta.setContentText("Ingrese el id del cliente");
                    venta.show();
                    cbPanes.setValue(null);
                    tfCantidadPan.setText("");
                    btnCargar.setDisable(true);
                }
            } else {
                if (idCliente == 0) {
                    if (vA == 0) {
                        System.out.println("\tAsignación id del cliente");
                        try {
                            int b = 0;//Permite saber si el id del cliente es válido
                            int aux = 0;//Permite asignar el id del cliente

                            idCliente = Integer.parseInt(tfIDV.getText());

                            //Validación del cliente
                            for (int i = 0; i < listaClientes.size(); i++) {
                                if (listaClientes.get(i).idCliente == idCliente) {
                                    b = 1;
                                    aux = i;
                                }
                            }

                            if (b == 0) {
                                Alert datoID = new Alert(Alert.AlertType.ERROR);
                                datoID.setHeaderText("Dato inválido");
                                datoID.setContentText("Cliente no registrado");
                                datoID.show();

                                tfIDV.setText("");
                                cbPanes.setValue(null);
                                tfCantidadPan.setText("");
                                tfCantidadPan.setDisable(true);
                                btnCargar.setDisable(true);
                                System.out.println("\tEl cliente no está registrado");
                            } else {
                                System.out.println("\tCliente válido");
                                System.out.println("\tCliente: " + listaClientes.get(aux).nombreCliente + " " + listaClientes.get(aux).aPaternoCliente);

                                tfIDV.setEditable(false);
                                vA = 1;
                            }
                        } catch (RuntimeException e) {
                            Alert datoID = new Alert(Alert.AlertType.ERROR);
                            datoID.setHeaderText("Dato inválido");
                            datoID.setContentText("El iD del cliente debe ser numérico");
                            datoID.show();
                            System.out.println("\tEl iD del cliente debe ser numérico");
                        }
                    }
                } else {
                    System.out.println("\tCliente: " + listaClientes.get(idCliente).nombreCliente + listaClientes.get(idCliente).aPaternoCliente);
                }
            }
        } else {
            System.out.println("\tVenta a cliente no registrado");
            vA = 1;
        }

        //Venta Autorizada
        if (vA == 1) {
            String pan = cbPanes.getValue();
            System.out.println("Pan solicitado: " + pan);

            int index = cbPanes.getSelectionModel().getSelectedIndex();

            if (tfCantidadPan.getText().isEmpty()) {
                Alert datoC = new Alert(Alert.AlertType.WARNING);
                datoC.setHeaderText("Cantidad de pan");
                datoC.setContentText("Ingrese la cantidad de pan que se desea comprar");
                datoC.show();
            } else {
                try {
                    System.out.println(listaPanes.get(index).toString());
                    int cantidad = Integer.parseInt(tfCantidadPan.getText());
                    System.out.println("Cantidad: " + cantidad);
                    int existencia = listaPanes.get(index).existenciaPan;
                    System.out.println("Existencia" + existencia);
                    if (existencia < cantidad) {
                        Alert datoE = new Alert(Alert.AlertType.INFORMATION);
                        datoE.setHeaderText("Existencia del pan");
                        datoE.setContentText("Lo sentimos no contamos con la cantidad suficiente de " + pan + "s");
                        datoE.show();

                        cbPanes.setValue(null);
                        tfCantidadPan.setText("");
                        tfCantidadPan.setDisable(true);
                        btnCargar.setDisable(true);
                        System.out.println("\tSin existencia suficiente");
                    } else {
                        float precio = listaPanes.get(index).precioPan;
                        System.out.println("Precio: " + precio);
                        float monto = cantidad * precio;
                        System.out.println("Monto: " + monto);
                        listaPanes.get(index).setExistenciaPan(existencia - cantidad);
                        System.out.println(listaPanes.get(index).toString());
                        sumVenta += monto;
                        System.out.println("Total de la compra: " + sumVenta);
                        int idPan = listaPanes.get(index).idPan;
                        System.out.println("ID Pan: " + idPan);
                        ventaActual.add(new Venta(fechaVenta, idCliente, idPan, pan, cantidad, precio, monto));

                        ObservableList<Venta> ventas = FXCollections.observableArrayList();
                        for (int i = 0; i < ventaActual.size(); i++) {
                            ventas.add(ventaActual.get(i));
                        }

                        tvVentas.setItems(ventas);

                        tcIdPV.setCellValueFactory(new PropertyValueFactory<>("idPan"));
                        tcProductoV.setCellValueFactory(new PropertyValueFactory<>("nombrePan"));
                        tcCantidadV.setCellValueFactory(new PropertyValueFactory<>("cantidadPan"));
                        tcMontoV.setCellValueFactory(new PropertyValueFactory<>("monto"));

                        //Verificación de existencia de panes
                        for (int i = 0; i < listaPanes.size(); i++) {
                            listaPanes.get(i).toString();
                        }

                        lbTotal.setText(Float.toString(sumVenta));
                        lbTotal.setVisible(true);
                        cbPanes.setValue(null);
                        tfCantidadPan.setText("");
                        tfCantidadPan.setDisable(true);
                        btnCargar.setDisable(true);
                        tfEfectivo.setEditable(true);
                        btnCobrar.setDisable(false);
                    }
                } catch (RuntimeException e) {
                    Alert datoC = new Alert(Alert.AlertType.ERROR);
                    datoC.setHeaderText("Dato inválido");
                    datoC.setContentText("La cantidad de pan debe ser numérica");
                    datoC.show();

                    tfCantidadPan.setText("");
                    System.out.println("\tLa cantidad de pan debe ser numérica");
                }
            }
        }
    }

    /**
     * Este método habilita la lectura de la cantidad de panes solicitados
     *
     * @param seleccionar
     */
    @FXML
    private void seleccionarPan(ActionEvent seleccionar) {
        tfCantidadPan.setDisable(false);
        btnCargar.setDisable(false);
    }

    /**
     * Este método permite descartar una compra
     *
     * @param devolver
     */
    @FXML
    private void descargarCompra(ActionEvent devolver) {
        Alert descargar = new Alert(Alert.AlertType.CONFIRMATION);
        descargar.setHeaderText("Devolver producto");
        descargar.setContentText("Desea cancelar la compra de este artículo?");
        descargar.showAndWait();

        if (descargar.getResult().getButtonData().equals(ButtonData.OK_DONE)) {
            System.out.println("Artículo descartado");
        } else {
            System.out.println("Descarga cancelada");
        }

        //Comparar si la venta ya no tiene articulos
    }

    /**
     * Este método permite una vez desplegado el total e ingresado el efectivo,
     * asi como calculado el cambio, realizar el cobro
     *
     * @param total
     */
    @FXML
    private void cobrarVenta(ActionEvent total) throws RuntimeException {
        if (tfEfectivo.getText().isEmpty()) {
            Alert datoT = new Alert(Alert.AlertType.WARNING);
            datoT.setHeaderText("Cobrar venta");
            datoT.setContentText("Ingrese el efectivo");
            datoT.show();
        } else {
            try {
                float efectivo = Float.parseFloat(tfEfectivo.getText());
                float totalV = Float.parseFloat(lbTotal.getText());

                if (efectivo < totalV) {
                    Alert cobro = new Alert(Alert.AlertType.ERROR);
                    cobro.setHeaderText("Cobrar");
                    cobro.setContentText("No se tiene suficiente efectivo");
                    cobro.show();
                    System.out.println("\tNo se tiene suficiente efectivo");
                } else {
                    float cambio = efectivo - totalV;
                    lbCambio.setText(Float.toString(cambio));

                    if (tfIDV.getText().equals("")) {
                        System.out.println("\tVenta ignorada, no se registro  cliente");
                    } else {
                        //Registro de las ventas después de ser pagadas
                        for (int i = 0; i < ventaActual.size(); i++) {
                            listaVentas.add(ventaActual.get(i));
                        }
                        System.out.println("Lista ventas");
                        for (int i = 0; i < listaVentas.size(); i++) {
                            System.out.println(listaVentas.get(i).toString());
                        }

                        //Guardar datos de las Ventas en el archivo ventas.txt
                        escribirVentas();
                        
                        int idC = listaVentas.get(1).idCliente;
                        
                        String tipo = "";
                        for (int i = 0; i < listaClientes.size(); i++) {
                            if(listaClientes.get(i).idCliente == idC){
                                tipo = listaClientes.get(i).tipoCliente;
                            }
                        }
                        
                        int conchapuntos = 9;
                        if(tipo.equals("Estandar")){
                           // conchapuntos  = e.calcularConchapuntos(totalV);  
                            System.out.println("Estandar");
                        }else{
                            System.out.println("Premium");
                          //  conchapuntos  = r.calcularConchapuntos(totalV);
                        }
                  
                        
                        System.out.println("ID Cliente: " + idC);
                        //listaClientes.get(idC).setConchapuntos(conchapuntos);

                        //Guardar datos de los Clientes en el archivo clienteses.txt
                        //escribirClientes();
                    }

                    //Guardar datos de los Panes en el archivo panes.txt
                    //escribirPanes();
                    
                    Alert cobro = new Alert(Alert.AlertType.CONFIRMATION);
                    cobro.setHeaderText("Venta finalizada");
                    cobro.setContentText("Desea imprimir el Ticket?");
                    cobro.showAndWait();
                    System.out.println("\nTotal: " + totalV + "\nEfectivo: " + efectivo + "\nCambio: " + cambio);

                    if (cobro.getResult().getButtonData().equals(ButtonData.OK_DONE)) {
                        Alert ticket = new Alert(Alert.AlertType.INFORMATION);
                        ticket.setHeaderText("Ticket de venta");
                        ticket.setContentText("Imprimiendo ticket.....");
                        ticket.show();

                        tfIDV.setText("");
                        lbTotal.setText("");
                        tfEfectivo.setText("");
                        lbCambio.setText("");

                        tpOperaciones.getTabs().remove(tbRealizarVenta);
                        apOperaciones.setDisable(false);
                        System.out.println("Impresión Ticket");
                    } else {
                        btnCobrar.setDisable(true);
                        cbPanes.setDisable(true);
                        tfEfectivo.setEditable(false);
                        btnTicket.setDisable(false);
                        btnCancelar.setDisable(false);
                    }
                }
            } catch (RuntimeException e) {
                Alert datoE = new Alert(Alert.AlertType.ERROR);
                datoE.setHeaderText("Dato inválido");
                datoE.setContentText("El efectivo debe ser numérico");
                datoE.show();
                System.out.println("\tEl efectivo debe ser numérico");
            }
        }
    }

    /**
     * Este método permite cancelar la venta
     *
     * @param cancelVenta
     */
    @FXML
    private void cancelarVenta(ActionEvent cancelVenta) {
        int b = 0;//Permite saber si se puede cancelar la venta

        int artVendidos = ventaActual.size();

        if (artVendidos == 0) {
            b = 1;//Como no se han vendido artículos se puede aceptar la cancelación         
            System.out.println("\tCancelación aceptada, no se han realizado compras");
        } else {
            if (btnTicket.isDisable()) {//Como está desabilitado no se ha cobrado
                Alert cancelarVenta = new Alert(Alert.AlertType.CONFIRMATION);
                cancelarVenta.setHeaderText("Cancelar venta");
                cancelarVenta.setContentText("Desea cancelar la venta actual?");
                cancelarVenta.showAndWait();

                if (cancelarVenta.getResult().getButtonData().equals(ButtonData.OK_DONE)) {
                    //Permite devolver la existencia a los productos cancelados
                    for (int i = 0; i < ventaActual.size(); i++) {
                        int idPan = ventaActual.get(i).idPan;

                        for (int j = 0; j < listaPanes.size(); j++) {
                            if (listaPanes.get(i).idPan == idPan) {
                                int existencia = listaPanes.get(j).existenciaPan + ventaActual.get(i).cantidadPan;
                                System.out.println("La existencia de " + listaPanes.get(j).nombrePan + "s es " + existencia);
                                listaPanes.get(i).setExistenciaPan(existencia);
                            }
                        }
                    }
                    System.out.println("\tCancelación aceptada, panes restablecidos");

                    b = 1;//Ya se restauraron las existencias, se puede aceptar la cancelación 

                    //Verificación de existencia de panes
                    for (int i = 0; i < listaPanes.size(); i++) {
                        listaPanes.get(i).toString();
                    }
                }
            } else {  //Ya se cobro
                b = 1;//Ya se cobro, se puede cerrar la ventana
                System.out.println("\tVentana cerrada");
            }
        }

        if (b == 1) {
            tfIDV.setText("");
            cbPanes.setValue(null);
            tfCantidadPan.setText("");
            tvVentas.setItems(null);
            lbTotal.setText("");
            tfEfectivo.setText("");
            lbCambio.setText("");

            tpOperaciones.getTabs().remove(tbRealizarVenta);
            apOperaciones.setDisable(false);
            System.out.println("Realizar Venta Cerrada");
        }
    }

    /**
     * Este método permite dirigirse a la página donde se imprime el ticket
     *
     * @param compra
     */
    @FXML
    private void imprimirTicket(ActionEvent compra) throws IOException {
        tfIDV.setText("");
        tfCantidadPan.setText("");
        tvVentas.setItems(null);
        lbTotal.setText("");
        tfEfectivo.setText("");
        lbCambio.setText("");
      
        tpOperaciones.getTabs().remove(tbRealizarVenta);
        apOperaciones.setDisable(false);
        
        tpOperaciones.getTabs().add(tbTicket);
        System.out.println("\tTicket Impreso");
    }

//Tab HistorialCompras
    /**
     * Este método permite consultar las compras realizadas por un cliente
     *
     * @param iDCliente
     */
    @FXML
    private void consultarHistorial(ActionEvent iDCliente) throws RuntimeException {
        ArrayList<Venta> historialCompras = new ArrayList<>();

        int b = 0;//Permite saber si el cliente está registrado

        if (tfMC.getText().isEmpty()) {
            Alert datoID = new Alert(Alert.AlertType.INFORMATION);
            datoID.setHeaderText("Dato erróneo");
            datoID.setContentText("Ingrese un id para realizar la consulta");
            datoID.show();
            System.out.println("\tNo se ha ingresado el id");
        } else {
            try {
                int idC = Integer.parseInt(tfMC.getText());

                for (int i = 0; i < listaClientes.size(); i++) {
                    if (listaClientes.get(i).idCliente == idC) {
                        b = 1;
                    }
                }

                if (b == 0) {
                    Alert datoID = new Alert(Alert.AlertType.ERROR);
                    datoID.setHeaderText("Dato inválido");
                    datoID.setContentText("Cliente no registrado");
                    datoID.show();

                    tfMC.setText("");
                    System.out.println("\tEl cliente no está registrado");
                } else {
                    //Obtención de las ventas realizadas por el cliente
                    for (int i = 0; i < listaVentas.size(); i++) {
                        if (listaVentas.get(i).idCliente == idC) {
                            historialCompras.add(listaVentas.get(i));
                        }

                    }
                    System.out.println("Compras realizadas por el cliente");
                    for (int i = 0; i < historialCompras.size(); i++) {
                        System.out.println(historialCompras.get(i).toString());
                    }

                    ObservableList<Venta> ventas = FXCollections.observableArrayList();
                    for (int i = 0; i < historialCompras.size(); i++) {
                        ventas.add(historialCompras.get(i));
                    }

                    tvHistorial.setItems(ventas);

                    tcFechaH.setCellValueFactory(new PropertyValueFactory<>("fecha"));
                    tcIdH.setCellValueFactory(new PropertyValueFactory<>("idPan"));
                    tcProductoH.setCellValueFactory(new PropertyValueFactory<>("nombrePan"));
                    tcCantidadH.setCellValueFactory(new PropertyValueFactory<>("cantidadPan"));
                    tcMontoH.setCellValueFactory(new PropertyValueFactory<>("monto"));

                    tfMC.setText("");

                    if (historialCompras.isEmpty()) {
                        Alert datoID = new Alert(Alert.AlertType.INFORMATION);
                        datoID.setHeaderText("Historial compras");
                        datoID.setContentText("El cliente no ha realizado ninguna compra");
                        datoID.show();

                        tvHistorial.setVisible(false);
                        System.out.println("\tEl cliente no ha realiza compras");
                    } else {
                        tvHistorial.setVisible(true);
                    }
                }
            } catch (RuntimeException e) {
                Alert datoID = new Alert(Alert.AlertType.ERROR);
                datoID.setHeaderText("Dato inválido");
                datoID.setContentText("El iD del cliente debe ser numérico");
                datoID.show();

                tfMC.setText("");
                System.out.println("\tEl iD ingresado debe ser numérico");
            }
        }
    }

    /**
     * Este método permite realizar otra consulta de historial de compras
     *
     * @param e
     */
    @FXML
    private void nuevaConsulta(MouseEvent e) {
        tvHistorial.setItems(null);
        tvHistorial.setVisible(false);
    }

    /**
     * Este método permite cancelar la consulta del historial de compras del
     * cliente
     *
     * @param cancelHistorial
     */
    @FXML
    private void cancelarHistorial(ActionEvent cancelHistorial) {
        tfMC.setText("");

        tpOperaciones.getTabs().remove(tbHistorialCompras);
        apOperaciones.setDisable(false);
        System.out.println("Consulta de historial de compras cancelado");
    }

//Tab Mejor Cliente
    /**
     * Este método permite consultar cual es el mejor cliente en un rango de
     * fechas
     *
     * @param verMC
     */
    @FXML
    private void consultarMC(ActionEvent verMC) throws ParseException {
        if (dpInicioMC.getValue() == null || dpFinMC.getValue() == null) {
            Alert fechas = new Alert(Alert.AlertType.WARNING);
            fechas.setHeaderText("Error en consulta");
            fechas.setContentText("Debe ingresar ambas fechas");
            fechas.show();
            System.out.println("\tNo se han ingresado ambas fechas");
        } else {
            try {
                Date fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(dpInicioMC.getValue().toString());
                Date fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(dpFinMC.getValue().toString());

                dpInicioMC.setValue(null);
                dpFinMC.setValue(null);

                if (fechaI.after(fechaF)) {
                    Alert fecha = new Alert(Alert.AlertType.ERROR);
                    fecha.setHeaderText("Error de fechas");
                    fecha.setContentText("La fecha final no debe ser anterior que la inicial");
                    fecha.show();
                    System.out.println("\tFecha final está antes que la inicial");
                } else {
                    if (listaVentas.isEmpty()) {
                        Alert compras = new Alert(Alert.AlertType.INFORMATION);
                        compras.setHeaderText("Error de consulta");
                        compras.setContentText("No se han realizado ventas");
                        compras.show();
                        System.out.println("\tSin ventas realizadas");
                    } else {
                        double trono = 0; //Permite conocer el total de las compras realizadas
                        String mejorCliente = "";//Permite conocer  el MC
                        int numClientes = listaClientes.size();//Permite saber cuántos clientes se evaluarán
                        int indexCliente = 0;//Define el index del cliente evaluado

                        obtenerVentas(fechaI, fechaF);

                        while (numClientes > 0) {
                            float total = 0;

                            int idCliente = listaClientes.get(indexCliente).idCliente;
                            String nombreCliente = listaClientes.get(indexCliente).nombreCliente + " " + listaClientes.get(indexCliente).aPaternoCliente;

                            for (int i = 0; i < ventasRango.size(); i++) {
                                if (ventasRango.get(i).idCliente == idCliente) {
                                    total = total + ventasRango.get(i).monto;
                                }
                            }

                            numClientes--;//Se terminó la consulta del cliente

                            //Se evalua si la monto total de las compras realizadas por el cliente es mayor al del cliente anterior 
                            if (total > trono) {
                                trono = total;
                                mejorCliente = nombreCliente;
                            }

                            indexCliente++;//Se selecciona el siguiente cliente 
                        }

                        lbMC.setText("El mejor cliente es: \n" + mejorCliente);
                    }
                }
            } catch (ParseException e) {
                System.out.println("Conversión de fechas errónea");
            }
        }
    }

    
    @FXML
    private void nuevaConsultaMC(MouseEvent e) {
        //ivMedalla.setVisible(false);
        lbMC.setText("");
    }
    
    /**
     * Este método permite cancelar la consulta del mejor cliente
     *
     * @param cancelMC
     */
    @FXML
    private void cancelarMC(ActionEvent cancelMC) {
        lbMC.setText("");

        tpOperaciones.getTabs().remove(tbMejorCliente);
        apOperaciones.setDisable(false);
        System.out.println("Consulta de mejor cliente cancelado");
    }

//Tab PanMasVendido
    /**
     * Este método permite consultar cual es el pan más vendido en un rango de
     * fechas
     *
     * @param verPMV
     */
    @FXML
    private void consultarPMV(ActionEvent verPMV) {
        if (dpInicioPMV.getValue() == null || dpFinPMV.getValue() == null) {
            Alert fechas = new Alert(Alert.AlertType.WARNING);
            fechas.setHeaderText("Error en consulta");
            fechas.setContentText("Debe ingresar ambas fechas");
            fechas.show();
            System.out.println("\nNo se han ingresado ambas fechas");
        } else {
            try {
                Date fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(dpInicioPMV.getValue().toString());
                Date fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(dpFinPMV.getValue().toString());

                dpInicioPMV.setValue(null);
                dpFinPMV.setValue(null);

                if (fechaI.after(fechaF)) {
                    Alert fecha = new Alert(Alert.AlertType.ERROR);
                    fecha.setHeaderText("Error de fechas");
                    fecha.setContentText("La fecha final no debe ser anterior que la inicial");
                    fecha.show();
                    System.out.println("\tFecha final antes que la inicial");
                } else {
                    if (listaVentas.isEmpty()) {
                        Alert compras = new Alert(Alert.AlertType.INFORMATION);
                        compras.setHeaderText("Error de consulta");
                        compras.setContentText("No se han realizado ventas");
                        compras.show();
                        System.out.println("\tSin ventas realizadas");
                    } else {
                        double trono = 0; //Permite conocer el total de panes vendidos
                        String mejorPan = "";//Permite conocer  el PMV
                        int numPanes = listaPanes.size();//Permite saber cuántos panes se evaluarán
                        int indexPan = 0;//Define el index del pan evaluado

                        obtenerVentas(fechaI, fechaF);

                        while (numPanes > 0) {
                            float total = 0;

                            int idPan = listaPanes.get(indexPan).idPan;
                            String nombrePan = listaPanes.get(indexPan).nombrePan;

                            for (int i = 0; i < ventasRango.size(); i++) {
                                if (ventasRango.get(i).idPan == idPan) {
                                    total = total + ventasRango.get(i).cantidadPan;
                                }
                            }

                            numPanes--;//Se termino la consulta del pan

                            //Se evalua si la cantidad total de panes vendidos del pan actual es mayor al anterior 
                            if (total > trono) {
                                trono = total;
                                mejorPan = nombrePan;
                            }

                            indexPan++;//Se selecciona el siguiente pan 
                        }

                        lbPMV.setText("El pan más vendido es: \n" + mejorPan);
                    }
                }
            } catch (ParseException e) {
                System.out.println("Conversión de fechas errónea");
            }
        }
    }

    /**
     * Este método permite cancelar la consulta del pan más vendido
     *
     * @param cancelPMV
     */
    @FXML
    private void cancelarPMV(ActionEvent cancelPMV) {
        lbPMV.setText("");

        tpOperaciones.getTabs().remove(tbPanMasVendido);
        apOperaciones.setDisable(false);
        System.out.println("Consulta de pan más vendido cancelado");
    }

    /**
     * Este método permite obtener las ventas realizadas durante un rango de
     * fechas
     *
     * @param fechaI
     * @param fechaF
     * @throws ParseException
     */
    public void obtenerVentas(Date fechaI, Date fechaF) throws ParseException {
        String fechaActual, fechaVenta;

        int dia = Integer.parseInt(new SimpleDateFormat("dd").format(fechaI));
        String mes = new SimpleDateFormat("MM").format(fechaI);
        String anio = new SimpleDateFormat("yyyy").format(fechaI);

        while (fechaI.before(fechaF)) {
            fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaI);
            for (int i = 0; i < listaVentas.size(); i++) {
                if (listaVentas.get(i).fecha.equals(fechaActual)) {
                    String diaVenta = listaVentas.get(i).fecha;
                    int idCVenta = listaVentas.get(i).idCliente;
                    int idPVenta = listaVentas.get(i).idPan;
                    String panVenta = listaVentas.get(i).nombrePan;
                    int cantidadPan = listaVentas.get(i).cantidadPan;
                    float precioVenta = listaVentas.get(i).precioPan;
                    float totalVenta = listaVentas.get(i).monto;
                    ventasRango.add(new Venta(diaVenta, idCVenta, idPVenta, panVenta, cantidadPan, precioVenta, totalVenta));
                }
            }
            //Permite saber que artículos se vendieron durante el día consultado
            System.out.println("\tVentas realizas el " + fechaActual);
            for (int i = 0; i < ventasRango.size(); i++) {
                System.out.println(ventasRango.get(i).toString());
            }

            dia += 1;
            fechaVenta = (dia + "/" + mes + "/" + anio);

            try {
                fechaI = new SimpleDateFormat("dd/MM/yyyy").parse(fechaVenta);
            } catch (ParseException e) {
                System.out.println("Fecha inexistenten\n");
            }
        }
    }

//Tab VerConchapuntos
    /**
     * Este método permite consultar el total de conchapuntos acumulados por un
     * cliente
     *
     * @param verCP
     */
    @FXML
    private void verCP(ActionEvent verCP) throws RuntimeException {
        int b = 0;//Permite saber si el cliente está registrado

        if (tfVerCP.getText().isEmpty()) {
            Alert datoID = new Alert(Alert.AlertType.INFORMATION);
            datoID.setHeaderText("Dato inválido");
            datoID.setContentText("Ingrese un id para realizar la consulta");
            datoID.show();
            System.out.println("\tNo se ha ingresado un id");
        } else {
            try {
                int idCliente = Integer.parseInt(tfVerCP.getText());

                for (int i = 0; i < listaClientes.size(); i++) {
                    if (listaClientes.get(i).idCliente == idCliente) {
                        lbVerCP.setText("ID: " + listaClientes.get(i).idCliente
                                + "\nCliente: " + listaClientes.get(i).nombreCliente + " " + listaClientes.get(i).aPaternoCliente
                                + "\nConchapuntos:  " + listaClientes.get(i).conchapuntos);
                        tfVerCP.setText("");

                        System.out.println("ID: " + listaClientes.get(i).idCliente
                                + "\nCliente: " + listaClientes.get(i).nombreCliente + " " + listaClientes.get(i).aPaternoCliente
                                + "\nConchapuntos:  " + listaClientes.get(i).conchapuntos);
                        b = 1;//Se encontró al cliente
                    }
                }

                if (b == 0) {
                    Alert datoID = new Alert(Alert.AlertType.INFORMATION);
                    datoID.setHeaderText("Dato inválido");
                    datoID.setContentText("El cliente no está registrado");
                    datoID.show();

                    tfVerCP.setText("");
                    System.out.println("\tCliente no registrado");
                }
            } catch (RuntimeException e) {
                Alert datoID = new Alert(Alert.AlertType.ERROR);
                datoID.setHeaderText("Dato inválido");
                datoID.setContentText("El iD del cliente debe ser numérico");
                datoID.show();

                tfVerCP.setText("");
                System.out.println("\tEl iD del cliente debe ser numérico");
            }
        }
    }

    /**
     * Este método permite realizar una nueva consulta de conchapuntos
     * acumulados
     *
     * @param e
     */
    @FXML
    private void nuevaConsultaCP(MouseEvent e) {
        lbVerCP.setText("");
    }

    /**
     * Este método permite cancelar la consulta de los conchapuntos acumulados
     *
     * @param cancelVerCP
     */
    @FXML
    private void cancelarCP(ActionEvent cancelVerCP) {
        tfVerCP.setText("");
        lbVerCP.setText("");

        tpOperaciones.getTabs().remove(tbVerConchapuntos);
        apOperaciones.setDisable(false);
        System.out.println("Consulta de conchapuntos cancelada");
    }

//Tab CanjearConchapuntos
    /**
     * Este método permite consultar los conchapuntos acumulados por el cliente
     *
     * @param verCPA
     */
    @FXML
    private void verCPA(ActionEvent verCPA) {
        int b = 0;//Permite saber si el cliente está registrado

        if (tfConsultarCP.getText().isEmpty()) {
            Alert datoID = new Alert(Alert.AlertType.INFORMATION);
            datoID.setHeaderText("Dato inválido");
            datoID.setContentText("Ingrese un id para realizar la consulta");
            datoID.show();
            System.out.println("\tNo se ha ingresado un id");
        } else {
            try {
                int idCliente = Integer.parseInt(tfConsultarCP.getText());

                for (int i = 0; i < listaClientes.size(); i++) {
                    if (listaClientes.get(i).idCliente == idCliente) {

                        lbDatosCanjear.setText("ID: " + listaClientes.get(i).idCliente
                                + "\nCliente: " + listaClientes.get(i).nombreCliente + " " + listaClientes.get(i).aPaternoCliente
                                + "\nConchapuntos:  " + listaClientes.get(i).conchapuntos
                                + "\n\nManteconchas disponibles: " + listaClientes.get(i).conchapuntos / 20
                                + "\nManteconchas en existencia: " + manteconchas);
                        lbCPA.setText(Integer.toString(listaClientes.get(i).conchapuntos));
                        tfConsultarCP.setText("");

                        System.out.println("ID: " + listaClientes.get(i).idCliente
                                + "\nCliente: " + listaClientes.get(i).nombreCliente + " " + listaClientes.get(i).aPaternoCliente
                                + "\nConchapuntos:  " + listaClientes.get(i).conchapuntos);

                        if (Integer.parseInt(lbCPA.getText()) == 0) {
                            Alert CPA = new Alert(Alert.AlertType.INFORMATION);
                            CPA.setHeaderText("Conchapuntos");
                            CPA.setContentText("El cliente no tiene conchapuntos para canjear");
                        } else {
                            lbCanjearCP.setVisible(true);
                            tfCanjear.setVisible(true);
                            btnCanjear.setVisible(true);

                            b = 1;
                            System.out.println("\tCanjeo autorizado");
                        }
                    }
                }

                if (b == 0) {
                    Alert datoID = new Alert(Alert.AlertType.INFORMATION);
                    datoID.setHeaderText("Dato inválido");
                    datoID.setContentText("El cliente no está registrado");
                    datoID.show();
                    System.out.println("\tCliente no registrado");
                }
            } catch (RuntimeException e) {
                Alert datoID = new Alert(Alert.AlertType.ERROR);
                datoID.setHeaderText("Dato inválido");
                datoID.setContentText("El iD del cliente debe ser numérico");
                datoID.show();

                tfConsultarCP.setText("");
                System.out.println("\tEl iD del cliente debe ser numérico");
            }
        }
    }

    /**
     * Este método permite hacer una nueva consulta de conchapuntos acumulados
     * para canjear
     */
    @FXML
    private void nuevaConsultaCCP(MouseEvent e) {
        lbDatosCanjear.setText("");
    }

    /**
     * Este método permite canjear los conchapuntos acumulados
     *
     * @param canjearCP
     */
    @FXML
    private void canjearCP(ActionEvent canjearCP) throws RuntimeException {
        if (tfCanjear.getText().isEmpty()) {
            Alert datoID = new Alert(Alert.AlertType.INFORMATION);
            datoID.setHeaderText("Dato inválido");
            datoID.setContentText("Ingrese la cantidad de conchapuntos que desea canjear");
            datoID.show();
            System.out.println("\tNo se ha ingresado la cantidad de conchapuntos que se desea canjear");
        } else {
            try {
                int cantidad = Integer.parseInt(tfCanjear.getText());

                int CPA = listaClientes.get(Integer.parseInt(tfCanjear.getText())).conchapuntos;
                int CPN = cantidad * 10;
                if (CPA < CPN) {
                    if (manteconchas == 0) {
                        Alert datoMc = new Alert(Alert.AlertType.INFORMATION);
                        datoMc.setHeaderText("Sin conchapuntos");
                        datoMc.setContentText("Lo sentimos no contamos con manteconchas");
                        datoMc.show();
                        System.out.println("Sin existencia de manteconchas");

                    } else {
                        manteconchas -= cantidad;
                        System.out.println("Quedan " + manteconchas + "manteconchas");

                        Alert CCP = new Alert(Alert.AlertType.INFORMATION);
                        CCP.setHeaderText("Canjeo completado");
                        CCP.setContentText("Puede otorgar " + cantidad + " manteconchas al cliente");
                        CCP.show();
                    }
                } else {
                    Alert eCCP = new Alert(Alert.AlertType.INFORMATION);
                    eCCP.setHeaderText("Canjeo cancelado");
                    eCCP.setContentText("El cliente no tiene conchapuntos suficientes");
                    eCCP.show();
                }
            } catch (RuntimeException e) {
                Alert datoC = new Alert(Alert.AlertType.ERROR);
                datoC.setHeaderText("Dato inválido");
                datoC.setContentText("La cantidad de conchapuntos a canjear debe ser numérica");
                datoC.show();
                System.out.println("La cantidad de conchapuntos a canjear debe ser numérica");
            }

        }
    }

    /**
     * Este método permite cancelar el canjeo de los conchapuntos
     *
     * @param cancelCCP
     */
    @FXML
    private void cancelarCCP(ActionEvent cancelCCP) {
        tfConsultarCP.setText("");
        lbDatosCanjear.setText("");
        lbCanjearCP.setText("");
        tfCanjear.setText("");

        tpOperaciones.getTabs().remove(tbCanjearConchapuntos);
        apOperaciones.setDisable(false);
        System.out.println("\nCanjeo de conchapuntos cancelado");
    }

    /**
     * Este método permite cancelar la consulta de clientes
     *
     * @param cancelVC
     */
    @FXML
    private void cancelarVC(ActionEvent cancelVC) {
        tvClientes.setItems(null);

        tpOperaciones.getTabs().remove(tbVerClientes);
        apOperaciones.setDisable(false);
        System.out.println("Ver clientes cancelado");
    }

    /**
     * Este método permite cancelar la consulta de clientes
     *
     * @param cancelVP
     */
    @FXML
    private void cancelarVP(ActionEvent cancelVP) {
        tvPanes.setItems(null);

        tpOperaciones.getTabs().remove(tbVerPanes);
        apOperaciones.setDisable(false);
        System.out.println("Ver panes cancelado");
    }

    @FXML
    private void actualizarDatos(ActionEvent e) {
        Alert fecha = new Alert(Alert.AlertType.CONFIRMATION);
        fecha.setHeaderText("Actualización de existencia");
        fecha.setContentText("Desea modificar la existencia de un pan?");
        fecha.showAndWait();

        if (fecha.getResult().getButtonData().equals(ButtonData.OK_DONE)) {
            tvPanes.setEditable(true);
            tcNombreP.setCellFactory(TextFieldTableCell.forTableColumn());
            tcPrecio.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            tcExistencia.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        }
    }

    public void actualizarNombre(CellEditEvent e) {
        Pan pan = tvPanes.getSelectionModel().getSelectedItem();
        System.out.println(pan);

        pan.setExistenciaPan(Integer.parseInt(e.getNewValue().toString()));
        pan.getExistenciaPan();
    }

    public void actualizarPrecio(CellEditEvent e) {
        Pan pan = tvPanes.getSelectionModel().getSelectedItem();
        System.out.println(pan);

        pan.setExistenciaPan(Integer.parseInt(e.getNewValue().toString()));
        pan.getExistenciaPan();
    }

    public void actualizarExistencia(CellEditEvent e) {
        Pan pan = tvPanes.getSelectionModel().getSelectedItem();
        System.out.println(pan);

        pan.setExistenciaPan(Integer.parseInt(e.getNewValue().toString()));
        pan.getExistenciaPan();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Date fechaActual = new Date();
        String fechaSistema = new SimpleDateFormat("dd/MM/yyyy").format(fechaActual);
        Alert fecha = new Alert(Alert.AlertType.CONFIRMATION);
        fecha.setTitle("Confirmar fecha");
        fecha.setHeaderText("Fecha del sistema: " + fechaSistema);
        fecha.setContentText("¿Desea iniciar el programa?");
        fecha.showAndWait();

        if (fecha.getResult().getButtonData().equals(ButtonData.OK_DONE)) {
            lbFecha.setText(fechaSistema);

            leerClientes();
            leerPanes();
            leerVentas();

            tpOperaciones.getTabs().remove(tbRegistrarCliente);
            tpOperaciones.getTabs().remove(tbRegistrarPan);
            tpOperaciones.getTabs().remove(tbRealizarVenta);
            tpOperaciones.getTabs().remove(tbHistorialCompras);
            tpOperaciones.getTabs().remove(tbMejorCliente);
            tpOperaciones.getTabs().remove(tbPanMasVendido);
            tpOperaciones.getTabs().remove(tbVerConchapuntos);
            tpOperaciones.getTabs().remove(tbCanjearConchapuntos);
            tpOperaciones.getTabs().remove(tbVerClientes);
            tpOperaciones.getTabs().remove(tbVerPanes);
            tpOperaciones.getTabs().remove(tbTicket);
        } else {
            System.exit(0);
        }
    }

}
