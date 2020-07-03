public class mainProva {


    public static void main(String[] args){

String x="http://sag.art.uniroma2.it/didattica/basili/IA_19_20/Progetti_2019_20_Marzo2020.pdf";
        String extension = "";

        int i = x.lastIndexOf('.');
        if (i > 0) {
            extension = x.substring(i+1);
        }
        System.out.println(extension );
    }

}
