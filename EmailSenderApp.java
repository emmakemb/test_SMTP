package main.java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSenderApp extends Application {

    // Replace these with your Mailtrap credentials
    private final String username = "89766d32e5e0ac"; // from Mailtrap SMTP settings
    private final String password = "d6583a1238aeb9"; // from Mailtrap SMTP settings
    private final String host = "smtp.mailtrap.io";
    private final int port = 587;

    @Override
    public void start(Stage stage) {
        // Label of the receiver
        Label toLabel = new Label("Empfänger:");
        toLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 12px;");
        toLabel.setWrapText(true);
        // Create JavaFX components for the email form
        TextField toField = new TextField();
        toField.setPromptText("Enter email address:");
        // Put them in a horizontal box
       //  HBox toRow = new HBox(10, toLabel, toField); // 10px spacing
      //  toRow.setAlignment(Pos.CENTER_LEFT); // align 

        // Label of the Subject(Bettreff)
        Label subjectLabel = new Label("Betreff:");
        subjectLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 12px;");
        subjectLabel.setWrapText(true);

        TextField subjectField = new TextField();
        subjectField.setPromptText("Enter subject:");

       //  HBox subjectRow = new HBox(10,  subjectLabel, subjectField); // 10px spacing
        // subjectRow.setAlignment(Pos.CENTER_LEFT); // align 

        Label bodyLabel = new Label("Nachricht:");
        bodyLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        TextArea bodyArea = new TextArea();
        bodyArea.setPromptText("Message body");

        Button sendButton = new Button("Send");
        Label statusLabel = new Label();

        // Layout avec GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10); // espace horizontal entre colonnes
        grid.setVgap(10); // espace vertical entre lignes

        // Configure column constraints so fields grow horizontally
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER); // Labels don't grow
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS); // Fields grow horizontally
        grid.getColumnConstraints().addAll(col1, col2);

        // Configure row constraints so TextArea grows vertically
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        RowConstraints row4 = new RowConstraints();
        row4.setVgrow(Priority.ALWAYS); // TextArea row grows vertically
        RowConstraints row5 = new RowConstraints();
        grid.getRowConstraints().addAll(row1, row2, row3, row4, row5);

        // Ajouter les composants dans la grille
        grid.add(toLabel, 0, 0);
        grid.add(toField, 1, 0);

        grid.add(subjectLabel, 0, 1);
        grid.add(subjectField, 1, 1);

        grid.add(bodyLabel, 0, 2);
        grid.add(bodyArea, 0, 3, 2, 1); // span 2 columns, 1 row

        grid.add(sendButton, 0, 4);
        grid.add(statusLabel, 1, 4);

        // Handle the send button click event
        sendButton.setOnAction(e -> {
            try {
                sendMail(toField.getText(), subjectField.getText(), bodyArea.getText());
                statusLabel.setText("✅ Email sent successfully!");
            } catch (Exception ex) {
                statusLabel.setText("❌ Error: " + ex.getMessage());
            }
        });

        // Create the scene and set it to the stage (window)
        stage.setScene(new Scene(grid, 500, 400));
        stage.setTitle("WNG Email Sender");
        stage.show();
    }

    // Method to send the email using Jakarta Mail (SMTP)
    private void sendMail(String to, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));

        // Create the email session using authentication details
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(username, password);
            }
        });

        // Create the message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("e.kembou@live.de")); // Sender email (must match Mailtrap domain)
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        // Send the email
        Transport.send(message);
    }

    // Launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
