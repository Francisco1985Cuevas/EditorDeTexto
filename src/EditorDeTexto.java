import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class EditorDeTexto extends JFrame implements ActionListener {
    JTextArea areaDeTexto;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu archivoMenu, editarMenu;
    JMenuItem nuevoItem, abrirItem, guardarItem, guardarComoItem, salirItem;
    JMenuItem copiarItem, cortarItem, pegarItem;

    public EditorDeTexto() {
        //Configurar la ventana principal
        super("Editor de texto");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Configurar el área de texto
        //Crear el área de texto y agregarla al panel de desplazamiento.
        areaDeTexto = new JTextArea();
        scrollPane = new JScrollPane(areaDeTexto);

        //Configurar la barra de menú.
        //Crear la barra de menú y agregar el menú: Archivo y Editar.
        menuBar = new JMenuBar();
        archivoMenu = new JMenu("Archivo");
        editarMenu = new JMenu("Editar");

        //Crear los elementos del Menu.
        //Elementos de menú Archivo para realizar las siguientes acciones.
        nuevoItem = new JMenuItem("Nuevo");
        abrirItem = new JMenuItem("Abrir");
        guardarItem = new JMenuItem("Guardar");
        guardarComoItem = new JMenuItem("Guardar como...");
        salirItem = new JMenuItem("Salir");

        //Elementos de menú Editar para realizar las siguientes acciones.
        copiarItem = new JMenuItem("Copiar");
        cortarItem = new JMenuItem("Cortar");
        pegarItem = new JMenuItem("Pegar");

        //Agregar items a las opciones del Menu.
        //Agregar items al menu Archivo.
        archivoMenu.add(nuevoItem);
        archivoMenu.add(abrirItem);
        archivoMenu.add(guardarItem);
        archivoMenu.add(guardarComoItem);
        archivoMenu.addSeparator();
        archivoMenu.add(salirItem);

        //Agregar items al menu Editar.
        editarMenu.add(copiarItem);
        editarMenu.add(cortarItem);
        editarMenu.add(pegarItem);

        //Agregar a la barra de menú.
        menuBar.add(archivoMenu);
        menuBar.add(editarMenu);

        //Agregar los escuchadores de eventos
        nuevoItem.addActionListener(this);
        abrirItem.addActionListener(this);
        guardarItem.addActionListener(this);
        guardarComoItem.addActionListener(this);
        salirItem.addActionListener(this);

        copiarItem.addActionListener(this);
        cortarItem.addActionListener(this);
        pegarItem.addActionListener(this);

        setJMenuBar(menuBar);
        add(scrollPane);

        //Hacer visible el marco
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Implementar la lógica de los botones de menú aquí

        if (e.getSource() == nuevoItem) {
            //Si el area de texto esta vacio No hay problema Borrar el contenido del área de texto y crear
            // un nuevo archivo.
            //Sino esta vacio y queremos abrir un nuevo archivo, primero guardar el contenido.
            if (areaDeTexto.getText().equals("")) {
                areaDeTexto.setText("");
            } else {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(areaDeTexto) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        //QUEDA PENDIENTE QUE MUESTRE PRIMERO UN CUADRO DE DIALOGO DONDE PREGUNTE SI QUEREMOS GUARDAR DATOS.
                        FileWriter writer = new FileWriter(file);
                        writer.write(areaDeTexto.getText());
                        writer.close();//QUEDA PENDIENTE GUARDAR CON LA EXTENSION TXT
                        areaDeTexto.setText("");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        } else if (e.getSource() == abrirItem) {
            // Mostrar un cuadro de diálogo de abrir archivo
            // y cargar el contenido del archivo en el área de texto
            // utilizando un objeto de la clase FileReader

            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(areaDeTexto) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();//Obtiene el archivo seleccionado
                try {
                    //Si el usuario selecciona un archivo y hace clic en "Abrir", obtenemos
                    // el archivo seleccionado y creamos un objeto de la clase FileReader
                    // utilizando el archivo.
                    FileReader reader = new FileReader(file);
                    //Luego, creamos un objeto BufferedReader a partir del objeto FileReader
                    // para poder leer el contenido del archivo línea por línea.
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    //Utilizamos un objeto StringBuilder para construir una cadena de
                    // texto que contiene todo el contenido del archivo.
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line);
                        stringBuilder.append(System.lineSeparator());
                        line = bufferedReader.readLine();
                    }
                    //Finalmente, establecemos el contenido del área de texto utilizando el método setText()
                    areaDeTexto.setText(stringBuilder.toString());

                    //Cerramos el objeto FileReader y BufferedReader utilizando el método close()
                    // para liberar los recursos del sistema.
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } else if (e.getSource() == guardarItem) {
            // Mostrar un cuadro de diálogo de guardar archivo
            // y guardar el contenido del área de texto en un archivo
            // utilizando un objeto de la clase FileWriter
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(areaDeTexto) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(areaDeTexto.getText());
                    writer.close();//QUEDA PENDIENTE GUARDAR CON LA EXTENSION TXT
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } else if (e.getSource() == guardarComoItem) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(areaDeTexto) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(areaDeTexto.getText());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == salirItem) {
            int result = JOptionPane.showConfirmDialog(areaDeTexto, "¿Esta seguro que desea salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }

    }
}
