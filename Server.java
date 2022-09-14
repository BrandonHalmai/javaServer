package application; //This refers to the folder that is currently holding the class; this is so classes can access our
//public methods, i.e. our main method.

//These are imported libraries that are not built into java, so we import them to utilise their functionalities.
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
Here is our main method; it is set to public, so it can be accessed by the other classes in our program. Along with
static and void, so we don't need an object to use it and return a value.
*/
public class Server {
    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6789); //Here we are creating a new object called
        //'serverSocket' from the library ServerSocket. This will allow us to establish, maintain and terminate a
        //connection. We've also inputted an integer value of 6789 which will be port the connection is running on.
        System.out.println("Listening to port 6789"); //Here we are printing out what port the server is running on.

        new MenuThread().start(); //This will be used to create a new object from MenuThread, create a new thread and
        //run it. This will make the program run more smoothly and efficiently because it's utilising multiple threads.

        /**
        We've included a while loop that starts a new thread for each new connection. This is done by accepting the
        new connection and using a random port to communicate to the clients. Finally, we print our client's IP
        address as well as its port.
         */
        while(true) {
            Socket socket = serverSocket.accept();
            new EchoThread(socket).start();
            InetAddress ip = socket.getInetAddress();
            int port = socket.getPort();
            System.out.println("New client connected from " + ip + ":" + port);

        }
    }
}