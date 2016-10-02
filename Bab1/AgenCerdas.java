public class AgenCerdas {
    private String lingkungan[][];
    AgenCerdas(int location){
        lingkungan = new String[location][7];
    }
    public void setKondisi(int i, String Nama,String location,String status, String atas, String bawah, String kanan, String kiri){
        lingkungan[i-1][0]=Nama;
        lingkungan[i-1][1]=location;
        lingkungan[i-1][2]=status;
        lingkungan[i-1][3]=String.valueOf(atas);
        lingkungan[i-1][4]=String.valueOf(bawah);
        lingkungan[i-1][5]=String.valueOf(kanan);
        lingkungan[i-1][6]=String.valueOf(kiri);
    }
     public void vacumm (int i,String location, String status){
        if ("kotor".equals(status.toLowerCase())){
            System.out.println("----bersih-bersih----");
            status = "bersih";
            lingkungan[i][2]=status;
            vacumm(i,location,status);
        }else if ("kiri".equals(location.toLowerCase())){
            System.out.println("geser kanan-->");
        }else if ("kanan".equals(location.toLowerCase())){
            System.out.println("<--geser kiri");
        }else if ("atas".equals(location.toLowerCase())){
            System.out.println("^--naik");
        }else if ("bawah".equals(location.toLowerCase())){
            System.out.println("v--turun");
        }
    }
    public void bersihkan(){
        for (int i = 0; i<lingkungan.length; i++){
            System.out.println("Lokasi"+lingkungan[i][0]);
            vacumm(i,lingkungan[i][1],lingkungan[i][2]);
        }
        cek(); 
    }
    public void cek (){
        for (int i = 0; i<lingkungan.length; i++){
            if(!"bersih".equals(lingkungan[i][2])){
                bersihkan();
                break;
            }
        }
        System.out.println("SEMUA SUDAH BERSIH");
    }
    public static void main(String[] args) {
        AgenCerdas a = new AgenCerdas(6);
        a.setKondisi(1, "Lokasi 1", "kiri", "bersih",null,null,"2",null);
        a.setKondisi(2, "Lokasi 2", "kanan", "kotor","3",null,null,"1");
        a.setKondisi(3, "Lokasi 3", "atas", "kotor","6","2",null,"4");
        a.setKondisi(4, "Lokasi 4", "kiri","kaleng","5","1","3",null );
        a.setKondisi(5, "Lokasi 5", "atas", "kotor",null,"4","6",null);
        a.setKondisi(6, "Lokasi 6", "kiri", "bersih",null,"3",null,"5");
        a.bersihkan();
    }
}
