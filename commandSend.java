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

//FRED 4-10-2015
//THIS PROGRAM TESTS SENDING A SINGLE P CHAR TO XPLANE TO PAUSE
//STATUS: SUCCESS
class commandSend
{
	public static void main(String args[]) throws Exception
    {

		Scanner in = new Scanner(System.in);
		System.out.println("Test Program for sending char P to Xplane 9");
	      System.out.println("Press Enter to Toggle Pause State");
	      in.nextLine();

	      final DatagramSocket testSocket = new DatagramSocket();
	      final int PORT = 5555;
	      InetAddress IPAddress = InetAddress.getByName("128.213.48.72");
	      System.out.println(IPAddress);
	      byte[] sendData = new byte[64];
	      sendData[4] = (byte) 48; //0
	      sendData[5] = (byte) 112; //p

	      //Header
	      sendData[0] = (byte) 67; //C
	      sendData[1] = (byte) 72; //H
	      sendData[2] = (byte) 65; //A
	      sendData[3] = (byte) 82; //R

	      //sendData = "CHAR0p".getBytes();
	      //sendData[] = "CMND0".getBytes();
	     System.out.println(InetAddress.getByName("localhost"));
	     DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORT);

	     testSocket.send(sendPacket);
	      
	}

}