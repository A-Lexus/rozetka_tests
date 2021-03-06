package Utils;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class MyFileManager {

    private static String fileSeparator = System.getProperty("file.separator");
    private static String dir = System.getProperty("user.dir");
    private static String testDataDir = dir + fileSeparator + "test-data";

    public static ArrayList<String> getRowFromFile(String fileName) {

        String fp = testDataDir + fileSeparator + fileName;
        System.out.println(fp);
        ArrayList<String> arr = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fp));
        } catch (FileNotFoundException e) {
            System.out.println("File is not present!");
        }
        String s;
        try {
            while ((s = br.readLine()) != null) {
                arr.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static File writeToTXT(ArrayList<String> arrList, String fileName) {

        String fn = fileName + ".txt"; //For CSV format use .csv
        String filePath = testDataDir + fileSeparator + fn;
        ArrayList<String> arrayList = arrList;
        FileWriter writer;
        try {
            writer = new FileWriter(filePath);
            for (String str : arrayList) {
                writer.write(str + "\n");//add ";" for .csv format file
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace(); //ToDO change
        }
        File f = new File(filePath);
        System.out.println("File saved to:" +
                f.getAbsolutePath());
        return f;
    }

    public static void sendFileToStatedRecipients(String recipientsList, String attachFile) {

        String from = PropLoader.getProp("mail");
        String password = PropLoader.getProp("password");

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        //Getting recipients from source file
        ArrayList<String> recipients = getRowFromFile(recipientsList);

        //2) compose message
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            message.setSubject("Rozetka test");

            //3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("Hello\n" +
                    "There is bellow file generated by test from rozetka.com.ua.\n" +
                    "Please see attached file\n" +
                    "BR");

            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            String filename = attachFile;//change accordingly
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);


            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            //6) set the multipart object to the message object
            message.setContent(multipart);

            //7) send message
            Transport transport = session.getTransport("smtp");


            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("message sent....");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
