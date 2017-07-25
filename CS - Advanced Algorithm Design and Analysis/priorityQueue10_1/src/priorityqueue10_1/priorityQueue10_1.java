import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.*;



    public class priorityQueue10_1 {
        public static void main(String[] Args) throws FileNotFoundException {



            Scanner input = new Scanner(System.in);


            System.out.println("Would you like to input the names for your queue or import with cmd line args?");
            String answer = input.nextLine();

            if (answer.equalsIgnoreCase("INPUT")) {

                String line = input.nextLine(); //receives everything they type until they hit enter

                String[] values = line.split(" ");
                        //for(String s: values) //This will loop through the values array and then print the values to console
                          // System.out.println(s);

                PriorityQueue<String> queue1 = new PriorityQueue<String>(Arrays.asList(values));
                System.out.println("\nPriority Queue: " + "\n"+ queue1);

            } else {
                System.out.print("Enter a file name for ranking: ");
                
                File file = new File(input.nextLine());
                if (!file.exists()) {
                    System.out.println("File " + file + " does not exist");
                    System.exit(1);
                }
                // Open the file
                try {
                    input = new Scanner(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }



            /**System.out.println("Priority queue using Comparable:");
            while (queue1.size() > 0) {
            System.out.print(queue1.remove() + " ");
             }*/

        }
    }