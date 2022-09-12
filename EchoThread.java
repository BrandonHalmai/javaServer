package application; //This refers to the folder that is currently holding the class; this is so classes can access our
//public methods, i.e. our main method.

//These are imported libraries that are not built into java, so we import them to utilise their functionalities.

import java.io.*;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
Here we're creating a class called EchoThread that utilises the existing library 'Thread'. This will be used so that
the server can perform many tasks more efficiently and effectively over multiple threads. We first create an object
called socket from the Socket library and assign that to the EchoThread class by equating it to the variable
'clientsocket'. After this we create our 'run' method which will gather the data from the client and write it to a
file. This is done by creating the object 'InputStreamReader' from the Thread library that reads the stream of data
coming from the client. It's then supported by the 'BufferedReader' object that will receive that input and
output the contents into a variable. We then define a variable called 'path' that will store the invoice's file
location; this will be /$USER/home/Invoices/ where $USER is the user currently executing the application. If this does
exist, then we simply create a folder in the home directory. The last part handles the file saves; we use the
'BufferedWriter' object to save the file to the location defined in the 'path' variable along with the time. We then
finish by stating the file has been saved.
 */
public class EchoThread extends Thread {
    protected Socket socket;

    public EchoThread(Socket clientSocket) {

        this.socket = clientSocket;
    }

    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder out = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                out.append(line + "\n");
            }
            String clientString = out.toString();
            bufferedReader.close();

            String path = System.getProperty("user.home") + File.separator + "Invoices";

            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (clientString.length() > 0) {
                ZonedDateTime zdt = ZonedDateTime.now();
                String time = zdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                time = time.replaceAll(":", "-");

                String saveAs = null;

                if (clientString.startsWith("{")) {
                    saveAs = ".json";
                } else if (clientString.startsWith("<")) {
                    saveAs = ".xml";
                } else {
                    saveAs = ".txt";
                }

                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path + File.separator + time + saveAs, true));
                char[] charArray = clientString.toCharArray();
                for (char c : charArray) {
                    bufferedWriter.append(c);
                    bufferedWriter.flush();
                }

                bufferedWriter.close();
                System.out.println("File saved as: " + saveAs);
            }
        } catch (IOException e) {
            System.out.print(e);
            return;
        }
    }
}
