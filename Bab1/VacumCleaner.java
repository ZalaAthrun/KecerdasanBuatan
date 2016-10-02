import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class VacumCleaner {
    void display_map(){
        System.out.println("==== ====");
        System.out.println("| 5 | | 6 |");
        System.out.println("==== ====");
        System.out.println("| 4 | | 3 |");
        System.out.println("==== ====");
        System.out.println("| 1 | | 2 |");
        System.out.println("==== ====");
    }
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Scanner input = new Scanner(System.in);
        int number_of_nodes = 6;
        VacumCleaner vc = new VacumCleaner(number_of_nodes);
        vc.display_map();
        Location[] l = new Location[number_of_nodes];
        l[0] = new Location(1, "air");
        l[1] = new Location(2, "debu");
        l[2] = new Location(3, "bersih");
        l[3] = new Location(4, "kertas");
        l[4] = new Location(5, "bersih");
        l[5] = new Location(6, "kaleng");
        l[0].set_position_location(l[3], null, null, l[1]);
        l[1].set_position_location(l[2], null, l[0], null);
        l[2].set_position_location(l[5], l[1], l[3], null);
        l[3].set_position_location(l[4], l[0], null, l[2]);
        l[4].set_position_location(null, l[3], null, l[5]);
        l[5].set_position_location(null, l[2], l[4], null);
        System.out.println("Masukkan Titik Start Awal Pembersihan ( 1 - " + number_of_nodes + ")");
        int in = input.nextInt();
        vc.start_position(l[in - 1]);
        vc.clear(l);
    }
    Location is_now_accessed = null;
    int number_of_nodes;
    String filtering_object_litter = "";
    boolean going_backwards = false;
    VacumCleaner(int nodes_location_amount) throws FileNotFoundException {
        File file = new File("sampah.txt");
        filtering_object_litter = new Scanner(file).nextLine();
        number_of_nodes = nodes_location_amount;
    }
    void start_position(Location Start) {
        is_now_accessed = Start;
    }
    void clear(Location [] l) throws FileNotFoundException, InterruptedException {
        boolean status_acessed = false;
        while(status_acessed == false){
        vacuum(l);
        Thread.sleep(1000);
        status_acessed = check_status_accessed_all(l);            
        }
        System.out.println("Selesai");
    }
    boolean check_object(String object) {
        return filtering_object_litter.contains(object);
    }
    boolean check_status_accessed_all(Location [] l){
        boolean status_acessed = true;
        for(int i = 0; i<number_of_nodes; i++){
            if(l[i].status_acessed==false){
                status_acessed = false;
            }
        }
        return status_acessed;
    }
    void vacuum(Location [] l) {
        is_now_accessed.status_acessed = true;
        if(going_backwards == true){
            System.out.println("Kembali ke Location "+is_now_accessed.numerical_identity);
        }else if (!is_now_accessed.now_acessed_condition.equalsIgnoreCase("bersih")) {
            if (check_object(is_now_accessed.now_acessed_condition)) {
                is_now_accessed.now_acessed_condition = "bersih";
                System.out.println("Location " + is_now_accessed.numerical_identity + " telah dibersihkan");
            }else{
                System.out.println("Location " + is_now_accessed.numerical_identity + " tidak bisa dibersihkan karena adanya "+is_now_accessed.now_acessed_condition);
            }
        } else {
            System.out.println("Location " + is_now_accessed.numerical_identity + " sudah bersih sejak awal");
        }
        going_backwards = false;
        Location temporary_nodal = is_now_accessed;
        if (is_now_accessed.going_to_right_side != null && is_now_accessed.going_to_right_side.status_acessed == false) {
            is_now_accessed = is_now_accessed.going_to_right_side;
            is_now_accessed.previous_location = temporary_nodal;
            System.out.println("Jalan Ke Kanan");
        } else if (is_now_accessed.going_to_upper_side != null && is_now_accessed.going_to_upper_side.status_acessed == false) {
            is_now_accessed = is_now_accessed.going_to_upper_side;
            is_now_accessed.previous_location = temporary_nodal;
            System.out.println("Jalan Ke Atas");
        } else if (is_now_accessed.going_to_left_side != null && is_now_accessed.going_to_left_side.status_acessed == false) {
            is_now_accessed = is_now_accessed.going_to_left_side;
            is_now_accessed.previous_location = temporary_nodal;
            System.out.println("Jalan Ke Kiri");
        } else if (is_now_accessed.going_to_lower_side != null && is_now_accessed.going_to_lower_side.status_acessed == false) {
            is_now_accessed = is_now_accessed.going_to_lower_side;
            is_now_accessed.previous_location = temporary_nodal;
            System.out.println("Jalan Ke Bawah");
        } else {
            if(check_status_accessed_all(l)==false){
            if(is_now_accessed.previous_location == is_now_accessed.going_to_right_side){
                System.out.println("Jalan Ke kanan");
            }else if(is_now_accessed.previous_location == is_now_accessed.going_to_left_side){
                System.out.println("Jalan Ke kiri");
            }else if(is_now_accessed.previous_location == is_now_accessed.going_to_upper_side){
                System.out.println("Jalan Ke atas");
            }else{
                System.out.println("Jalan Ke bawah");
            }
            is_now_accessed = is_now_accessed.previous_location;
            going_backwards = true;
            }
        }
    }
}
class Location {
    Location previous_location, going_to_upper_side, going_to_lower_side, going_to_left_side, going_to_right_side;
    String now_acessed_condition;
    boolean status_acessed = false;
    int numerical_identity;
    Location(int number, String condition) {
        numerical_identity = number;
        now_acessed_condition = condition;
    }
    void set_position_location(Location going_to_upper_side, Location going_to_lower_side, Location going_to_left_side, Location going_to_right_side) {
        this.going_to_upper_side = going_to_upper_side;
        this.going_to_lower_side = going_to_lower_side;
        this.going_to_left_side = going_to_left_side;
        this.going_to_right_side = going_to_right_side;
    }
}
