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


class dataRefRequestSend
{
	//ADD FUNCTION THATS TAKEN FROM UDP-TCP CONVERTER THAT DISPLAYS FIRST 4 DATA STREAMS
	//THEN TEST BY TOGGLINGS ON AND OFF
	public static boolean selecting;
	public static void main(String args[]) throws Exception
    {

		  Scanner in = new Scanner(System.in);
	      System.out.println("Test Program to select data streams remotely from X Plane");
	      System.out.println("Remember to set this laptops IP in Xplane");
	      
	      System.out.println("Type Either UNSEL/SEL and press Enter for Unselecting or Selecting Data Streams");
	      String commandType = in.nextLine();
	      if(commandType.equals("UNSEL"))
	      {
	      	selecting = false;
	      }
	      else if(commandType.equals("SEL"))
	      {
	      	selecting = true;
	      }
	      else
	      {
	      	System.out.println("INVALID INPUT PROGRAM TERMINATE");
	      	return;
	      }

	      //Take in the input and translate to ASCII bytes
	      System.out.println("Enter in the datastreams you want selected seperated by a comma | Example: '8,9,10,15'");
	      String dataStreamSelection = in.nextLine();
	      List<String> dataStreamList = Arrays.asList(dataStreamSelection.split(","));

	      byte[] fullCommand = new byte[(dataStreamList.size() * 4) + 5];
	      System.out.println(dataStreamList.size());

	      if(selecting)
	      {
	      	fullCommand[0] = (byte) 68; //D
	      }
	      else
	      {
	      	fullCommand[0] = (byte) 85; //U
	      }
	      //ADD DSEL0 ONE BY ONE
	      //fullCommand[0] = (byte) 68; //D
	      fullCommand[1] = (byte) 83; //S
	      fullCommand[2] = (byte) 69; //E
	      fullCommand[3] = (byte) 76; //L
	      fullCommand[4] = (byte) 48; //0
	      //FOR LOOP start at 5 for each list item add little endian
	      
	      for(int x = 0; x < dataStreamList.size(); x++)
	      {	    
	      	//INT IS 32 BITS BYTE IS 8 BITS SO 4 ALLOCATE 
	      	int intFromString = Integer.parseInt(dataStreamList.get(x)); 	
	      	ByteBuffer bb = ByteBuffer.allocate(4);
	      	bb.order(ByteOrder.LITTLE_ENDIAN);
	      	System.out.println("CONVERTING: " + intFromString);
	      	bb.putInt(intFromString);
	      	byte[] result = bb.array();

	      	for(int y = 0; y < result.length; y++)
	      	{
	      		fullCommand[(x * 4) + 5 + y] = result[y];
	      	}
	      }
	      
	      	//TEST FOR ONE INTEGER ONLY
	      /*
	      	int intFromString = Integer.parseInt(dataStreamList.get(0));
	      	ByteBuffer bb = ByteBuffer.allocate(4);
	      	bb.order(ByteOrder.LITTLE_ENDIAN);
	      	bb.putInt(intFromString);
	      	byte[] result = bb.array();
	      	for(int y = 0; y < result.length; y++)
	      	{
	      		fullCommand[y + 5] = result[y];
	      	}
	      	*/
	      //byte[] cmd = command.getBytes("US-ASCII");

	      final DatagramSocket testSocket = new DatagramSocket();
	      final int PORT = 5555;
	      InetAddress IPAddress = InetAddress.getByName("128.213.48.72");
	      System.out.println(IPAddress);
	      

	      //SEND THE COMMAND TO SELECT VARIOUS DATA STREAMS
	      //NUMBERS OF SELECTED MUST BE IN MACHINE BYTE ORDER (Not yet complete)
	      //String fullCommand = "DSEL0" + dataStreamSelection;
	      //byte[] sendData = fullCommand.getBytes();
	      
	     System.out.println(InetAddress.getByName("localhost"));
	     DatagramPacket sendPacket = new DatagramPacket(fullCommand, fullCommand.length, IPAddress, PORT);

	     testSocket.send(sendPacket);

	     int input_port = 9876;
	     DatagramSocket serverSocket = new DatagramSocket(input_port);
	     

	     //Only need to print it once to verify the data streams being sent
	     byte[] receiveData = new byte[41+36*(4-1)]; //Number of bytes represents size of buffer in chars
         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         serverSocket.receive(receivePacket);
         System.out.println(receiveData[0]);
         for (int data_group = 0; data_group<4; ++data_group) //For each data stream
         {
	            int xplane_index = receiveData[5+36*data_group];
	            System.out.println("Index: " + xplane_index);
	     }
	      
	}

}