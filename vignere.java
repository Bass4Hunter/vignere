package vignere;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

public class vignere {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("//////////      Ingrese el texto a codificar        /////////"); 
        String text = "";
        String readString = input.nextLine();
        text+= readString;
        while(readString!=null) {
            if (readString.isEmpty()) {
                break;
            }

            if (input.hasNextLine()) {
                readString = input.nextLine();
                text +=readString;  
            } else {
                readString = null;
            }
        }
        
        System.out.println("/////////       Seleccioné el alfabeto ( Digite 1 o 2 )       //////////"); 
        System.out.println("1)Español Z27 (A-Z)\n2) ASCII 191 \n"); 
        
        int tipe = input.nextInt();
        System.out.println("/////////       Ingrese la clave       //////////"); 

        String clave = input.next();

        System.out.println("/////////       El texto codificado        //////////"); 

        String textoCodificado = vignere.codificar(text,clave,tipe);
        System.out.println( textoCodificado); 

    }
    static public String preprocesado(String text){
        String preprocesado="";
        char current;
        for(int i=0;i<text.length();i++){
            current = text.charAt(i);
            if(current == 'Á' ||  current== 'á'){
                preprocesado += (char)'A';
            }
            else if(current == 'É' ||  current== 'é'){
                preprocesado += 'E';
            }
            else if(current == 'Í' ||  current== 'í'){
                preprocesado += 'I';
            }
            else if(current == 'Ó' ||  current== 'ó'){
                preprocesado += 'O';
            }
            else if(current == 'Ú' ||  current== 'ú'){
                preprocesado += 'U';
            }
            else if(current == 'Ñ' ||  current== 'ñ'){
                preprocesado += 'Ñ';
            }
            else if (current < 65 || current >122 || ( current >= 91 && current<=96 )) {
                // avoid 

            }
            else if (current >=97){
                preprocesado += (char)(current - 32);
            }
            else{
                preprocesado+= (char)current;
            }
        }
        return preprocesado;
    }

    static public String preprocesado_ASCCI_191(String text){
        String preprocesado="";
        char current;
        for(int i=0;i<text.length();i++){
            current = text.charAt(i);
            if (current < 33) {
                // avoid 
            }
            else{
                preprocesado+= (char)current;
            }
        }
        return preprocesado;
    }

    static public String codificar(String text,String clave,int tipe){

        if(tipe == 1){
            String preprocesado = preprocesado(text);
            String pclave = preprocesado(clave);
            String claveText = "";
            String codificado_espaniol_27 = "";
            int ite = 0;
            for(int i=0;i<preprocesado.length();i++){
                if(ite == pclave.length()){
                    ite = 0;
                }
                claveText += pclave.charAt(ite); 
                ite += 1 ;
            }
    
            char[] espaniol_27 = new char[27];
    
            for(int i=0;i<27;i++){
                if(i == 14 ){
                    espaniol_27[i]=(char)('Ñ');
                }
                else if(i > 14){
                    espaniol_27[i]=(char)('A'+ i - 1);
                }
                else{
                    espaniol_27[i]=(char)('A'+ i );
                }
            }
            
            int ite1=0;
            int ite2=0;
            for(int i=0;i < preprocesado.length();i++){
                for(int j=0;j<27;j++){
                    if(espaniol_27[j] == preprocesado.charAt(i)){
                        ite1 = j;
                    }
                }
                for(int j=0;j<27;j++){
                    if(espaniol_27[j] == claveText.charAt(i)){
                        ite2 = j;
                    }
                }
                codificado_espaniol_27 += ""+espaniol_27[(ite1 + ite2)%27]; 
            }
            return codificado_espaniol_27;
        }else {
            char[] ascci_191 = new char[191];   
            String preprocesado = preprocesado_ASCCI_191(text);
            String claveText = "";
            String codificado_ASCCI_191 = "";
            int ite = 0 ;
            for(int i=0;i<preprocesado.length();i++){

                if (ite == clave.length()){
                    ite = 0;
                }
                claveText += clave.charAt(ite); 
                ite++;
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader("SC/vignere/ASCCI_191.txt"));
                int i = 0 ;
                String strLine = "";
                while (strLine != null){
                    strLine = br.readLine();
                    if (strLine == null)
                      break;
    
                    ascci_191[i] = strLine.charAt(0);
                    i++;
                }
                 br.close();
            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            } catch (IOException e) {
                System.err.println("Unable to read the file.");
            }
            int ite1=0;
            int ite2=0;
            for(int i=0;i < preprocesado.length();i++){
                for(int j=0;j<191;j++){
                    if(ascci_191[j] == preprocesado.charAt(i)){
                        ite1 = j;
                    }
                }
                for(int j=0;j<191;j++){
                    if(ascci_191[j] == claveText.charAt(i)){
                        ite2 = j;
                    }
                }
                codificado_ASCCI_191 += ""+ascci_191[(ite1 + ite2)%191]; 
            }
            return codificado_ASCCI_191;
        }
        
    }
}