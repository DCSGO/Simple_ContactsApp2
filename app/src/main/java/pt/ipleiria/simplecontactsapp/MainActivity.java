package pt.ipleiria.simplecontactsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new ArrayList<String>();

        contacts.add("Inocêncio Coitadinho | 914596956");
        contacts.add("Vénus Maria | 934524244");
        contacts.add("Leonor Rodrigues | 964335678");
        contacts.add("Ana Matias | 913427864");
        contacts.add("Marta Catita | 927574545");


        //buscar referencia para listview
        ListView listView = (ListView) findViewById(R.id.listView_contacts);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(adapter);



        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterspinner = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapterspinner);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //colocado o codigo a executar quando se clica num item da listview



                ListView listView = (ListView) findViewById(R.id.listView_contacts);

                String item = (String) listView.getItemAtPosition(position);

                contacts.remove(position);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this, android.R.layout.simple_list_item_1, contacts);
                listView.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "Apagou o contacto: " + item, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void onClick_add(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_add, null));

// Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                //ir buscar as editTexts
                AlertDialog al = (AlertDialog) dialog;

                EditText etName = (EditText) al.findViewById(R.id.editText_name);
                EditText etPhone = (EditText) al.findViewById(R.id.editText_phone);

                //ir buscar as strings das editTexts
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();

                //criar novo contacto
                String contact = name + " | " + phone;

                //adicionar contacto à lista de contactos
                contacts.add(contact);


                //mostrar a lista de contactos actualizada
                ListView listView = (ListView) findViewById(R.id.listView_contacts);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this, android.R.layout.simple_list_item_1, contacts);
                listView.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "New contact added", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                Toast.makeText(MainActivity.this, "Contact not added", Toast.LENGTH_SHORT).show();
            }
        });
        // Set other dialog properties
        builder.setTitle("New Contact");
        builder.setMessage("Enter the name and the phone contact");


        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClick_search(View view) {
        //buscar referencias para edittext, spinner e listview
        EditText et = (EditText) findViewById(R.id.editText);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ListView lv = (ListView) findViewById(R.id.listView_contacts);

        //ir edittext buscar o termo a pesquisar
        String termo = et.getText().toString();

        if (termo.equals("")) { //se o termo a pesquisar for uma string vazia mostra os contactos todos
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, contacts);
            lv.setAdapter(adapter);

            Toast.makeText(MainActivity.this, "Showing Searched Contacts", Toast.LENGTH_SHORT).show();

        } else {
            String itemSelecionado = (String) sp.getSelectedItem();


            //pesquisar o termo nos caontatos e guardar o resultado numa lista nova

            ArrayList<String> resultados = new ArrayList<>();

            if (itemSelecionado.equals("All")) {
                for (int i = 0; i < contacts.size(); i++) {
                    String c = contacts.get(i);
                    //boolean cotem termo(verd ou falso)
                    boolean contem = c.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }
                }
            } else if (itemSelecionado.equals("Name")) {
                //todo codigo pesquisar so no nome}
                for (int i = 0; i < contacts.size(); i++) {
                    String c = contacts.get(i);

                    String[] s = c.split("\\|");
                    String name = s[0];

                    boolean contem = name.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }
                }

            } else if (itemSelecionado.equals("Phone")) {
                for (int i = 0; i < contacts.size(); i++) {
                    String c = contacts.get(i);

                    String[] s = c.split("\\|");
                    String phone = s[1];

                    boolean contem = phone.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }
                }
            }

            boolean vazia = resultados.isEmpty();

            if (vazia == false) {

                //mostrar na listview a lista nova q contem o resultado da pesquisa

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_1, resultados);
                lv.setAdapter(adapter);

                //mostrar uma mensagem a dizer que a pesquisa teve sucesso
                Toast.makeText(MainActivity.this, "Showing Searched Contacts", Toast.LENGTH_SHORT).show();
            } else { // se a lista esta vazia mostra os contactos todos

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_1, contacts);
                lv.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "Showing Searched Contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }
}