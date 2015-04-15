import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.FileSystemException;
import java.nio.file.StandardOpenOption;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.*;
import java.text.*;
import java.util.Scanner;
import java.util.Properties;
import java.lang.Math;

class anyKeySend
{
	public static void main(String args[]) throws Exception
    {

		  Scanner in = new Scanner(System.in);
	      System.out.println("Test Program to send any keystroke to Xplane 9 (Only takes the first char)");
	      System.out.println("Type the command followed by Enter");
	      
	      //Take in the input and translate to ASCII bytes
	      String command = in.nextLine();
	      //byte[] cmd = command.getBytes("US-ASCII");

	      final DatagramSocket testSocket = new DatagramSocket();
	      final int PORT = 5555;
	      InetAddress IPAddress = InetAddress.getByName("128.213.48.72");
	      System.out.println(IPAddress);
	      
	      /*
	      //NOT NEEDED ANYMORE
	      byte[] header= new byte[64];
	      header[4] = (byte) 48; //0

	      //Header
	      header[0] = (byte) 67; //C
	      header[1] = (byte) 72; //H
	      header[2] = (byte) 65; //A
	      header[3] = (byte) 82; //R

	      //Merge cmd byte array with CHAR header and 0 delimiter
		  */

	      String fullCommand = "CHAR0" + command;
	      byte[] sendData = fullCommand.getBytes();
	      
	     System.out.println(InetAddress.getByName("localhost"));
	     DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORT);

	     testSocket.send(sendPacket);
	      
	}

}