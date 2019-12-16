package org.example;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.client.ui.VFormLayout;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.example.db.DBIslemleri;
import org.example.domain.Person;
import org.example.myComponents.RehberTabloField;
import org.example.myComponents.RehberTextField;

import java.util.Collection;
import java.util.List;

/**
 *
 */
@Theme("mytheme")
@Widgetset("org.example.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        FormLayout formLayout = new FormLayout();

        RehberTextField nameField = new RehberTextField();
        nameField.setCaption("Adı");
        formLayout.addComponent(nameField);

        RehberTextField surnameField = new RehberTextField();
        surnameField.setCaption("Soyadı");
        formLayout.addComponent(surnameField);

        RehberTextField phoneField = new RehberTextField();
        phoneField.setCaption("Telefon Numarası");
        formLayout.addComponent(phoneField);

        Button addPerson = new Button();
        addPerson.setCaption("Kaydet");
        addPerson.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {

                Person person = new Person();
                person.setName(nameField.getValue());
                person.setSurname(surnameField.getValue());
                person.setPhone(phoneField.getValue());

                DBIslemleri dbIslemleri = new DBIslemleri();
                dbIslemleri.addPerson(person);
                Notification.show("Kişi Eklendi");
            }
        });

        Button deletePerson = new Button();
        deletePerson.setCaption("Kişi Sil");
        deletePerson.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                deletionScreen();
            }
        });

        Button updatePerson = new Button();
        updatePerson.setCaption("Kişi Güncelle");
        updatePerson.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                updatePersonScreen();
            }
        });

        formLayout.addComponent(addPerson);
        formLayout.addComponent(deletePerson);
        formLayout.addComponent(updatePerson);
        formLayout.setMargin(true);
        formLayout.setSpacing(true);
        setContent(formLayout);
    }

    public void deletionScreen(){
        FormLayout deletionContent = new FormLayout();

        DBIslemleri dbIslemleri = new DBIslemleri();

        List<Person> personList = dbIslemleri.listPerson();

        Table tabloField = new Table("Rehberdeki Kişiler");
        tabloField.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                Collection<?> itemPropertyIds = itemClickEvent.getItem().getItemPropertyIds();
            }
        });

        tabloField.addContainerProperty("Id", Integer.class,null);
        tabloField.addContainerProperty("Adı", String.class,null);
        tabloField.addContainerProperty("Soyadı", String.class,null);
        tabloField.addContainerProperty("Telefon Numarası", String.class,null);


        for (Person person : personList) {

            Object newItemId = tabloField.addItem();
            Item row1 = tabloField.getItem(newItemId);
            row1.getItemProperty("Id").setValue(person.getId());
            row1.getItemProperty("Adı").setValue(person.getName());
            row1.getItemProperty("Soyadı").setValue(person.getSurname());
            row1.getItemProperty("Telefon Numarası").setValue(person.getPhone());

           // tabloField.addItem(new Object[]{person.getId(), person.getName(), person.getSurname(), person.getPhone()});
        }
        tabloField.setPageLength(tabloField.size());

        RehberTextField idField = new RehberTextField();
        idField.setCaption("Silinecek Kişi Id");

        Button deletePerson = new Button();
        deletePerson.setCaption("Kişiyi Sil");

        Button backButton = new Button();
        backButton.setCaption("Geri");

        deletePerson.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                int personId = Integer.parseInt(idField.getValue());
                dbIslemleri.deletePerson(personId);
                Notification.show("Kişi Silindi");
            }
        });

        backButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                /////// init metodu nasıl tekrar çağrılır ?

            }
        });

        deletionContent.addComponent(tabloField);
        deletionContent.addComponent(idField);
        deletionContent.addComponent(deletePerson);
        deletionContent.addComponent(backButton);
        setContent(deletionContent);
    }

    public void updatePersonScreen(){
        FormLayout updateContent = new FormLayout();

        DBIslemleri dbIslemleri = new DBIslemleri();

        List<Person> personList = dbIslemleri.listPerson();

        Table tabloField = new Table("Rehberdeki Kişiler");

        tabloField.addContainerProperty("Id", Integer.class,null);
        tabloField.addContainerProperty("Adı", String.class,null);
        tabloField.addContainerProperty("Soyadı", String.class,null);
        tabloField.addContainerProperty("Telefon Numarası", String.class,null);

        for (Person person : personList) {
            Object newItemId = tabloField.addItem();
            Item row1 = tabloField.getItem(newItemId);
            row1.getItemProperty("Id").setValue(person.getId());
            row1.getItemProperty("Adı").setValue(person.getName());
            row1.getItemProperty("Soyadı").setValue(person.getSurname());
            row1.getItemProperty("Telefon Numarası").setValue(person.getPhone());

           // tabloField.addItem(new Object[]{person.getId(), person.getName(), person.getSurname(), person.getPhone()});
        }
        tabloField.setPageLength(tabloField.size());

        RehberTextField idField = new RehberTextField();
        idField.setCaption("Güncellenecek Kişi Id");

        RehberTextField nameField = new RehberTextField();
        nameField.setCaption("Güncel Adı");
        updateContent.addComponent(nameField);

        RehberTextField surnameField = new RehberTextField();
        surnameField.setCaption("Güncel Soyadı");
        updateContent.addComponent(surnameField);

        RehberTextField phoneField = new RehberTextField();
        phoneField.setCaption("Güncel Telefon Numarası");
        updateContent.addComponent(phoneField);

        Button updatePerson = new Button();
        updatePerson.setCaption("Kişiyi Güncelle");

        Button backButton = new Button();
        backButton.setCaption("Geri");

        updatePerson.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                int personId = Integer.parseInt(idField.getValue());

                Person person = new Person();
                person.setName(nameField.getValue());
                person.setSurname(surnameField.getValue());
                person.setPhone(phoneField.getValue());

                DBIslemleri dbTransaction = new DBIslemleri();
                dbTransaction.updatePerson(personId, person);
                Notification.show("Kişi Güncellendi");
            }
        });
        updatePerson.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                /////// init metodu nasıl tekrar çağrılır ?
            }
        });

        updateContent.addComponent(tabloField);
        updateContent.addComponent(idField);
        updateContent.addComponent(nameField);
        updateContent.addComponent(surnameField);
        updateContent.addComponent(phoneField);
        updateContent.addComponent(updatePerson);
        updateContent.addComponent(backButton);
        setContent(updateContent);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}
